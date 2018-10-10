package io.proximax.service;

import io.proximax.connection.ConnectionConfig;
import io.proximax.service.repository.FileRepository;
import io.proximax.service.factory.FileRepositoryFactory;
import io.reactivex.Observable;

import java.io.File;
import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for uploading data/file
 */
public class FileUploadService {

    private final FileRepository fileRepository;

    /**
     * Construct this class
     *
     * @param connectionConfig the connection config
     */
    public FileUploadService(final ConnectionConfig connectionConfig) {
        this.fileRepository = FileRepositoryFactory.createFromConnectionConfig(connectionConfig);
    }

    FileUploadService(final FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Upload byte stream
     *
     * @param byteStream the byte stream
     * @return the IPFS upload response
     */
    public Observable<FileUploadResponse> uploadByteStream(final InputStream byteStream) {
        checkParameter(byteStream != null, "byteStream is required");

        return fileRepository.addByteStream(byteStream)
                .map(dataHash -> new FileUploadResponse(dataHash, System.currentTimeMillis()));
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
                .map(dataHash -> new FileUploadResponse(dataHash, System.currentTimeMillis()));
    }
}
