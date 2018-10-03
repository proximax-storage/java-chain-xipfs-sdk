/*
 * Copyright 2018 ProximaX Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
     *
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
     *
     * @return the IPFS client
     */
    public IPFS getIpfs() {
        return ipfs;
    }

    /**
     * Get the domain or IP of local IPFS API
     *
     * @return the domain or IP of local IPFS API
     */
    public String getApiHost() {
        return apiHost;
    }

    /**
     * Get the port of local IPFS API
     *
     * @return the port of local IPFS API
     */
    public int getApiPort() {
        return apiPort;
    }
}
