package io.proximax.connection;

import io.proximax.service.client.StorageNodeClient;
import io.proximax.service.client.StorageNodeClient.NodeInfoResponseBlockchainNetwork;

/**
 * The config class that sdk used to connect to work on upload
 * <br>
 * <br>
 * This config details the blockchain network connection and ipfs connection
 */
public class ConnectionConfig {

    private final BlockchainNetworkConnection blockchainNetworkConnection;
    private final FileStorageConnection fileStorageConnection;

    private ConnectionConfig(BlockchainNetworkConnection blockchainNetworkConnection,
                             IpfsConnection ipfsConnection) {
        this.blockchainNetworkConnection = blockchainNetworkConnection;
        this.fileStorageConnection = ipfsConnection;
    }

    private ConnectionConfig(BlockchainNetworkConnection blockchainNetworkConnection, StorageConnection storageConnection) {
        this.blockchainNetworkConnection = blockchainNetworkConnection;
        this.fileStorageConnection = storageConnection;
    }

    /**
     * Get the connection to blockchain network
     *
     * @return the blockchain network connection
     */
    public BlockchainNetworkConnection getBlockchainNetworkConnection() {
        return blockchainNetworkConnection;
    }

    /**
     * Get the connection to file storage
     *
     * @return the file storage connection
     */
    public FileStorageConnection getFileStorageConnection() {
        return fileStorageConnection;
    }

    /**
     * Create connection config with local IPFS connection
     *
     * @param blockchainNetworkConnection the blockchain network connection
     * @param ipfsConnection              the IPFS connection
     * @return instance of this connection config
     */
    public static ConnectionConfig createWithLocalIpfsConnection(BlockchainNetworkConnection blockchainNetworkConnection, IpfsConnection ipfsConnection) {
        return new ConnectionConfig(blockchainNetworkConnection, ipfsConnection);
    }

    /**
     * Create connection config with storage connection
     *
     * @param storageConnection the storage node connection
     * @return instance of this connection config
     */
    public static ConnectionConfig createWithStorageConnection(StorageConnection storageConnection) {
        final StorageNodeClient storageNodeClient = new StorageNodeClient(storageConnection);
        final NodeInfoResponseBlockchainNetwork blockchainNetwork =
                storageNodeClient.getNodeInfo().blockingFirst().getBlockchainNetwork();
        final BlockchainNetworkConnection blockchainNetworkConnection = new BlockchainNetworkConnection(
                blockchainNetwork.getNetworkType(), blockchainNetwork.getHost(),
                blockchainNetwork.getPort(), blockchainNetwork.getProtocol());
        return new ConnectionConfig(blockchainNetworkConnection, storageConnection);
    }

    /**
     * Create connection config with storage connection
     *
     * @param blockchainNetworkConnection the blockchain network connection
     * @param storageConnection           the storage node connection
     * @return instance of this connection config
     */
    public static ConnectionConfig createWithStorageConnection(BlockchainNetworkConnection blockchainNetworkConnection, StorageConnection storageConnection) {
        return new ConnectionConfig(blockchainNetworkConnection, storageConnection);
    }
}
