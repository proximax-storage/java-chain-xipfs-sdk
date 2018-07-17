package io.proximax.connection;

public class ConnectionConfig {

    private BlockchainNetworkConnection blockchainNetworkConnection;
    private IpfsConnection ipfsConnection;

    private ConnectionConfig(BlockchainNetworkConnection blockchainNetworkConnection, IpfsConnection ipfsConnection) {
        this.blockchainNetworkConnection = blockchainNetworkConnection;
        this.ipfsConnection = ipfsConnection;
    }

    public BlockchainNetworkConnection getBlockchainNetworkConnection() {
        return blockchainNetworkConnection;
    }

    public IpfsConnection getIpfsConnection() {
        return ipfsConnection;
    }

    public static ConnectionConfig create(BlockchainNetworkConnection blockchainNetworkConnection, IpfsConnection ipfsConnection) {
        return new ConnectionConfig(blockchainNetworkConnection, ipfsConnection);
    }
}
