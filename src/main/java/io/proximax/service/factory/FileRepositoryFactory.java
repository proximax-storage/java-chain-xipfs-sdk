package io.proximax.service.factory;

import io.proximax.connection.FileStorageConnection;
import io.proximax.connection.IpfsConnection;
import io.proximax.connection.StorageConnection;
import io.proximax.service.client.IpfsClient;
import io.proximax.service.client.StorageNodeClient;
import io.proximax.service.repository.FileRepository;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.lang.String.format;

/**
 * The factory class to create the file storage client based on connection config
 */
public class FileRepositoryFactory {

    private FileRepositoryFactory() {

    }

    /**
     * Create the file storage client based on file storage
     *
     * @param fileStorageConnection the connection to file storage
     * @return the file storage client created
     */
    public static FileRepository create(FileStorageConnection fileStorageConnection) {
        checkParameter(fileStorageConnection != null, "fileStorageConnection is required");

        if (fileStorageConnection instanceof IpfsConnection) {
            return new IpfsClient((IpfsConnection) fileStorageConnection);
        } else if (fileStorageConnection instanceof StorageConnection){
            return new StorageNodeClient((StorageConnection) fileStorageConnection);
        } else {
            throw new IllegalArgumentException(format("Unknown file storage connection %s", fileStorageConnection.getClass().getSimpleName()));
        }
    }
}
