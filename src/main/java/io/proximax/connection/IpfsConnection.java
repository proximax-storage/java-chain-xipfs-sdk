package io.proximax.connection;

import io.ipfs.api.IPFS;

/**
 * The config class to connect to IPFS
 */
public class IpfsConnection {

    private IPFS ipfs;

    /**
     * Construct instance of this config
     * @param multiAddr the IPFS multi address
     */
    public IpfsConnection(String multiAddr) {
        ipfs = new IPFS(multiAddr);
    }

    /**
     * Get the IPFS client
     * @return the IPFS client
     */
    public IPFS getIpfs() {
        return ipfs;
    }
}
