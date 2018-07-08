/*
 * 
 */
package io.proximax.connection;


import java.util.List;
import io.proximax.factory.BlockchainNetworkConnection;
import static java.util.Collections.emptyList;


/**
 * The Class PeerConnection.
 */
public abstract class PeerConnection {
	
	private BlockchainNetworkConnection blockchainNetworkConnection;

    private List<String> syncGateways;


    public PeerConnection() {
        syncGateways = emptyList();
    }
    


}
