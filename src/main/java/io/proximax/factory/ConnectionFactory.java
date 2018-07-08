package io.proximax.factory;

import io.ipfs.api.IPFS;
import io.ipfs.multiaddr.MultiAddress;
import io.nem.sdk.model.blockchain.NetworkType;

/**
 * A factory for creating Connector objects.
 */
public class ConnectionFactory {

	public static BlockchainNetworkConnection createBlockchainNetworkConnection(NetworkType networkType,
			String endpointUrl) {
		return new BlockchainNetworkConnection(networkType, endpointUrl);
	}

	/**
	 * Creates a new Connection object.
	 *
	 * @param multiAddress
	 *            the multi address
	 * @return the ipfs
	 */
	public static IPFS createIPFSNodeConnection(String multiAddress) {
		return new IPFS(new MultiAddress(multiAddress));
	}

}
