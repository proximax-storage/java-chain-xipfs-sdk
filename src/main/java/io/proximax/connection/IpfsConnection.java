package io.proximax.connection;

import io.ipfs.api.IPFS;

public class IpfsConnection {

    private IPFS ipfs;

    public IpfsConnection(String multiAddr) {
        ipfs = new IPFS(multiAddr);
    }

    public IPFS getIpfs() {
        return ipfs;
    }
}
