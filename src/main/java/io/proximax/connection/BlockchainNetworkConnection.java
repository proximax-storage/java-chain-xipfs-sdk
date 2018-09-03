package io.proximax.connection;

import io.nem.sdk.model.blockchain.NetworkType;
import io.proximax.model.BlockchainNetworkType;

/**
 * The config class to connect to blockchain network
 */
public class BlockchainNetworkConnection {

	private NetworkType networkType;
	private String endpointUrl;

	/**
	 * Construct instance of this config
	 * @param networkType the network type of the blockchain
	 * @param endpointUrl the REST API endpoint URL of the blockchain
	 */
	public BlockchainNetworkConnection(BlockchainNetworkType networkType, String endpointUrl) {
		this.endpointUrl = endpointUrl;
		this.networkType = networkType.networkType;
	}

	/**
	 * Get the network type of the blockchain
	 * @return the network type
	 */
	public NetworkType getNetworkType() {
		return networkType;
	}

	/**
	 * Get the REST API endpoint URL of the blockchain
	 * @return the REST API endpoint URL
	 */
	public String getEndpointUrl() {
		return endpointUrl;
	}
}
