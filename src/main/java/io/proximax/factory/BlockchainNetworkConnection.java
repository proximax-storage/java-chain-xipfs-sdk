package io.proximax.factory;

import io.nem.sdk.model.blockchain.NetworkType;

public class BlockchainNetworkConnection {

	private NetworkType networkType;
	private String endpointUrl;
	
	public BlockchainNetworkConnection(NetworkType networkType,String endpointUrl) {
		this.endpointUrl = endpointUrl;
		this.networkType = networkType;
	}
	public NetworkType getNetworkType() {
		return networkType;
	}
	public void setNetworkType(NetworkType networkType) {
		this.networkType = networkType;
	}
	public String getEndpointUrl() {
		return endpointUrl;
	}
	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}
	
}
