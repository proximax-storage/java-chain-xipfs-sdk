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

import io.proximax.exceptions.ConnectionConfigNotValidException;
import org.apache.http.client.utils.URIBuilder;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The config class to connect to storage API
 */
public class StorageConnection {

    private final String apiUrl;
    private final String apiHost;
    private final int apiPort;
    private final HttpProtocol apiProtocol;
    private final String bearerToken;
    private final String nemAddress;

    /**
     * Construct instance of this config
     *
     * @param apiHost     the domain or IP of storage node API
     * @param apiPort     the port of storage node API
     * @param apiProtocol the scheme used of storage node API
     * @param bearerToken the bearer token to authenticate with storage bide API
     * @param nemAddress  the nem address to authenticate with storage node API
     */
    public StorageConnection(String apiHost, int apiPort, HttpProtocol apiProtocol, String bearerToken, String nemAddress) {
        try {
            checkParameter(apiHost != null, "apiHost is required");
            checkParameter(apiPort > 0, "apiPort must be non-negative int");
            checkParameter(apiProtocol != null, "apiPort is required");
            checkParameter(bearerToken != null, "bearerToken is required");
            checkParameter(nemAddress != null, "nemAddress is required");

            this.apiHost = apiHost;
            this.apiPort = apiPort;
            this.apiProtocol = apiProtocol;
            this.bearerToken = bearerToken;
            this.nemAddress = nemAddress;
            this.apiUrl = new URIBuilder()
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
     * Get the REST API endpoint URL of the blockchain
     *
     * @return the REST API endpoint URL
     */
    public String getApiUrl() {
        return apiUrl;
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
     * Get the bearer token to authenticate with storage bide API
     *
     * @return the bearer token to authenticate with storage bide API
     */
    public String getBearerToken() {
        return bearerToken;
    }

    /**
     * Get the nem address to authenticate with storage node API
     *
     * @return the nem address to authenticate with storage node API
     */
    public String getNemAddress() {
        return nemAddress;
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
