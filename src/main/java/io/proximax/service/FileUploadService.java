package io.proximax.service;

import io.proximax.connection.ConnectionConfig;
import io.proximax.service.api.FileStorageClientApi;
import io.proximax.service.factory.FileStorageClientFactory;
import io.reactivex.Observable;

import java.io.File;
import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for uploading data/file
 */
public class FileUploadService {

    private final FileStorageClientApi fileStorageClientApi;

    /**
     * Construct this class
     *
     * @param connectionConfig the connection config
     */
    public FileUploadService(final ConnectionConfig connectionConfig) {
        this.fileStorageClientApi =
                FileStorageClientFactory.createFromConnectionConfig(connectionConfig);
    }

    FileUploadService(final FileStorageClientApi fileStorageClientApi) {
        this.fileStorageClientApi = fileStorageClientApi;
    }

    /**
     * Upload byte stream
     *
     * @param byteStream the byte stream
     * @return the IPFS upload response
     */
    public Observable<FileUploadResponse> uploadByteStream(final InputStream byteStream) {
        checkParameter(byteStream != null, "byteStream is required");

        return fileStorageClientApi.addByteStream(byteStream)
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

        return fileStorageClientApi.addPath(path)
                .map(dataHash -> new FileUploadResponse(dataHash, System.currentTimeMillis()));
    }
}
