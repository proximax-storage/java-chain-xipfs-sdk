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

import io.nem.sdk.model.blockchain.NetworkType;
import io.proximax.exceptions.ConnectionConfigNotValidException;
import io.proximax.model.BlockchainNetworkType;
import org.apache.http.client.utils.URIBuilder;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The config class to connect to blockchain network
 */
public class BlockchainNetworkConnection {

    private final NetworkType networkType;
    private final String restApiUrl;
    private final String apiHost;
    private final int apiPort;
    private final HttpProtocol apiProtocol;

    /**
     * Construct instance of this config
     *
     * @param networkType the network type of the blockchain
     * @param apiHost     the domain or IP of blockchain API
     * @param apiPort     the port of blockchain API
     * @param apiProtocol the scheme used of blockchain API
     */
    public BlockchainNetworkConnection(BlockchainNetworkType networkType, String apiHost, int apiPort, HttpProtocol apiProtocol) {
        try {
            checkParameter(networkType != null, "networkType is required");
            checkParameter(apiHost != null, "apiHost is required");
            checkParameter(apiPort > 0, "apiPort must be non-negative int");
            checkParameter(apiProtocol != null, "apiPort is required");

            this.networkType = networkType.networkType;
            this.apiHost = apiHost;
            this.apiPort = apiPort;
            this.apiProtocol = apiProtocol;
            this.restApiUrl = new URIBuilder()
                    .setHost(apiHost)
                    .setPort(apiPort)
                    .setScheme(apiProtocol.getProtocol())
                    .build()
                    .toString();
        } catch (Exception e) {
            throw new ConnectionConfigNotValidException("Invalid api config provided", e);
        }
    }

    /**
     * Get the network type of the blockchain
     *
     * @return the network type
     */
    public NetworkType getNetworkType() {
        return networkType;
    }

    /**
     * Get the REST API endpoint URL of the blockchain
     *
     * @return the REST API endpoint URL
     */
    public String getApiUrl() {
        return restApiUrl;
    }

    /**
     * Get the domain or IP of blockchain API
     *
     * @return the domain or IP of blockchain API
     */
    public String getApiHost() {
        return apiHost;
    }

    /**
     * Get the port of blockchain API
     *
     * @return the port of blockchain API
     */
    public int getApiPort() {
        return apiPort;
    }

    /**
     * Get the scheme used of blockchain API
     *
     * @return the scheme used of blockchain API
     */
    public HttpProtocol getApiProtocol() {
        return apiProtocol;
    }
}
