package io.proximax.service;

import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.service.repository.FileRepository;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Supplier;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for uploading data/file
 */
public class FileUploadService {

    private final FileRepository fileRepository;
    private final DigestUtils digestUtils;

    /**
     * Construct this class
     *
     * @param fileRepository the file repository
     */
    public FileUploadService(final FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.digestUtils = new DigestUtils();
    }

    FileUploadService(final FileRepository fileRepository, final DigestUtils digestUtils) {
        this.fileRepository = fileRepository;
        this.digestUtils = digestUtils;
    }

    /**
     * Upload byte stream
     *
     * @param byteStreamSupplier the byte stream supplier
     * @param privacyStrategy the privacy strategy
     * @param computeDigest the compute digest
     * @return the IPFS upload response
     */
    public Observable<FileUploadResponse> uploadByteStream(final Supplier<InputStream> byteStreamSupplier,
                                                           final PrivacyStrategy privacyStrategy,
                                                           final boolean computeDigest) {
        checkParameter(byteStreamSupplier != null, "byteStreamSupplier is required");

        final PrivacyStrategy privacyStrategyToUse = privacyStrategy == null ? PlainPrivacyStrategy.create() : privacyStrategy;

        final Observable<Optional<String>> digestObservable = computeDigest(byteStreamSupplier, privacyStrategyToUse, computeDigest);
        final Observable<String> dataHashObservable = fileRepository.addByteStream(privacyStrategyToUse.encryptStream(byteStreamSupplier.get()));

        return Observable.zip(digestObservable, dataHashObservable, (digest, dataHash) ->
                        new FileUploadResponse(dataHash, System.currentTimeMillis(), digest.orElse(null))
                );
    }

    /**
     * Upload a path
     *
     * @param path the data
     * @return the IPFS upload response
     */
    public Observable<FileUploadResponse> uploadPath(final File path) {
        checkParameter(path != null, "path is required");

        return fileRepository.addPath(path)
                .map(dataHash -> new FileUploadResponse(dataHash, System.currentTimeMillis(), null));
    }

    private Observable<Optional<String>> computeDigest(final Supplier<InputStream> byteStreamSupplier,
                                                       final PrivacyStrategy privacyStrategy,
                                                       final boolean computeDigest) {
        return computeDigest
                ? digestUtils.digest(privacyStrategy.encryptStream(byteStreamSupplier.get())).map(Optional::of)
                : Observable.just(Optional.empty());
    }

}
