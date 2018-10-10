package io.proximax.service;

import io.proximax.connection.ConnectionConfig;
import io.proximax.exceptions.DownloadForDataTypeNotSupportedException;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.service.repository.FileRepository;
import io.proximax.service.factory.FileRepositoryFactory;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;

import java.io.InputStream;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for retrieving data
 */
public class RetrieveProximaxDataService {

    private final FileRepository fileRepository;
    private final DigestUtils digestUtils;

    /**
     * Construct this class
     *
     * @param connectionConfig the connection config
     */
    public RetrieveProximaxDataService(ConnectionConfig connectionConfig) {
        this.fileRepository = FileRepositoryFactory.createFromConnectionConfig(connectionConfig);
        this.digestUtils = new DigestUtils();
    }

    RetrieveProximaxDataService(FileRepository fileRepository, DigestUtils digestUtils) {
        this.fileRepository = fileRepository;
        this.digestUtils = digestUtils;
    }

    /**
     * Retrieve data's byte stream
     *
     * @param dataHash        the data hash of the target download
     * @param privacyStrategy the privacy strategy to decrypt the data
     * @param validateDigest  the flag whether to validate digest
     * @param digest          the digest of the target download
     * @param contentType     the content type of the target download
     * @return the data's byte stream
     */
    public Observable<InputStream> getDataByteStream(String dataHash, PrivacyStrategy privacyStrategy, boolean validateDigest,
                                                     String digest, String contentType) {
        checkParameter(dataHash != null, "dataHash is required");
        checkParameter(privacyStrategy != null, "privacyStrategy is required");

        if (contentType != null && contentType.equals(PATH_UPLOAD_CONTENT_TYPE)) { // path
            throw new DownloadForDataTypeNotSupportedException("download of path is not yet supported");
        } else { // byte array
            return fileRepository.getByteStream(dataHash)
                    .flatMap(undecryptedStream -> validateDigest(validateDigest, digest, dataHash)
                            .map(result -> undecryptedStream))
                    .map(privacyStrategy::decryptStream);
        }
    }

    private Observable<Boolean> validateDigest(boolean validateDigest, String digest, String dataHash) {
        return validateDigest ? fileRepository.getByteStream(dataHash)
                .flatMap(undecryptedStream -> digestUtils.validateDigest(undecryptedStream, digest)) : Observable.just(true);
    }
}
