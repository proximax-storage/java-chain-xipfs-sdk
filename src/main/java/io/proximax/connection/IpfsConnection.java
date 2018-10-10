package io.proximax.connection;

import io.ipfs.api.IPFS;
import io.proximax.exceptions.ConnectionConfigNotValidException;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The config class to connect to IPFS
 */
public class IpfsConnection {

    private final IPFS ipfs;
    private final String apiHost;
    private final int apiPort;

    /**
     * Construct instance of this config
     * @param apiHost the IPFS multi address
     * @param apiPort the IPFS multi address
     */
    public IpfsConnection(String apiHost, int apiPort) {
        try {
            checkParameter(apiHost != null, "apiHost is required");
            checkParameter(apiPort > 0, "apiPort must be non-negative int");

            this.apiHost = apiHost;
            this.apiPort = apiPort;
            this.ipfs = new IPFS(apiHost, apiPort);
        } catch (RuntimeException e) {
            throw new ConnectionConfigNotValidException("Invalid api config provided", e);
        }
    }

    /**
     * Get the IPFS client
     * @return the IPFS client
     */
    public IPFS getIpfs() {
        return ipfs;
    }

    /**
     * Get the domain or IP of local IPFS API
     * @return the domain or IP of local IPFS API
     */
    public String getApiHost() {
        return apiHost;
    }

    /**
     * Get the port of local IPFS API
     * @return the port of local IPFS API
     */
    public int getApiPort() {
        return apiPort;
    }
}
