package io.proximax.connection;

/**
 * The config class that sdk used to connect to work on upload
 * <br>
 * <br>
 * This config details the blockchain network connection and ipfs connection
 */
public class ConnectionConfig {

    private BlockchainNetworkConnection blockchainNetworkConnection;
    private IpfsConnection ipfsConnection;

    private ConnectionConfig(BlockchainNetworkConnection blockchainNetworkConnection, IpfsConnection ipfsConnection) {
        this.blockchainNetworkConnection = blockchainNetworkConnection;
        this.ipfsConnection = ipfsConnection;
    }

    /**
     * Get the connection to blockchain network
     * @return the blockchain network connection
     */
    public BlockchainNetworkConnection getBlockchainNetworkConnection() {
        return blockchainNetworkConnection;
    }

    /**
     * Get the connection to IPFS
     * @return the IPFS connection
     */
    public IpfsConnection getIpfsConnection() {
        return ipfsConnection;
    }

    /**
     * Create instance of this connection config
     * @param blockchainNetworkConnection the blockchain network connection
     * @param ipfsConnection the IPFS connection
     * @return instance of this connection config
     */
    public static ConnectionConfig create(BlockchainNetworkConnection blockchainNetworkConnection, IpfsConnection ipfsConnection) {
        return new ConnectionConfig(blockchainNetworkConnection, ipfsConnection);
    }
}
