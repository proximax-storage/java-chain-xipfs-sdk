package io.proximax.service.factory;

import io.proximax.connection.ConnectionConfig;
import io.proximax.service.repository.FileRepository;
import io.proximax.service.client.IpfsClient;
import io.proximax.service.client.StorageNodeClient;

/**
 * The factory class to create the file storage client based on connection config
 */
public class FileRepositoryFactory {

    private FileRepositoryFactory() {

    }

    /**
     * Create the file storage client based on connection config
     *
     * @param connectionConfig the connection config
     * @return the file storage client created
     */
    public static FileRepository createFromConnectionConfig(ConnectionConfig connectionConfig) {
        if (connectionConfig.getIpfsConnection() != null) {
            return new IpfsClient(connectionConfig.getIpfsConnection());
        } else {
            return new StorageNodeClient(connectionConfig.getStorageConnection());
        }
    }
}
