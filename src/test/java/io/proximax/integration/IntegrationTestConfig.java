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
package io.proximax.integration;

import io.proximax.connection.HttpProtocol;
import io.proximax.model.BlockchainNetworkType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class IntegrationTestConfig {

    private static final File INTEGRATION_TEST_PROPERTIES = new File("src//test/resources//integration-test-config.properties");
    private static Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(new FileInputStream(INTEGRATION_TEST_PROPERTIES));
        } catch (IOException e) {
            throw new RuntimeException("failed to load integration test properties");
        }
    }

    public static String getIpfsApiHost() {
        return PROPERTIES.getProperty("ipfs.api.host");
    }

    public static int getIpfsApiPort() {
        return Integer.parseInt(PROPERTIES.getProperty("ipfs.api.port"));
    }

    public static BlockchainNetworkType getBlockchainNetworkType() {
        return BlockchainNetworkType.fromString(PROPERTIES.getProperty("blockchain.networktype"));
    }

    public static String getBlockchainApiHost() {
        return PROPERTIES.getProperty("blockchain.api.host");
    }

    public static int getBlockchainApiPort() {
        return Integer.parseInt(PROPERTIES.getProperty("blockchain.api.port"));
    }

    public static HttpProtocol getBlockchainApiProtocol() {
        return HttpProtocol.fromString(PROPERTIES.getProperty("blockchain.api.protocol"));
    }

    public static String getStorageNodeApiHost() {
        return PROPERTIES.getProperty("storage.node.api.host");
    }

    public static int getStorageNodeApiPort() {
        return Integer.parseInt(PROPERTIES.getProperty("storage.node.api.port"));
    }

    public static HttpProtocol getStorageNodeApiProtocol() {
        return HttpProtocol.fromString(PROPERTIES.getProperty("storage.node.api.protocol"));
    }

    public static String getStorageNodeApiBearerToken() {
        return PROPERTIES.getProperty("storage.node.api.bearer");
    }

    public static String getStorageNodeApiNemAddress() {
        return PROPERTIES.getProperty("storage.node.api.nemaddress");
    }

    public static String getPrivateKey1() {
        return PROPERTIES.getProperty("privatekey.1");
    }

    public static String getPublicKey1() {
        return PROPERTIES.getProperty("publickey.1");
    }

    public static String getAddress1() {
        return PROPERTIES.getProperty("address.1");
    }

    public static String getPrivateKey2() {
        return PROPERTIES.getProperty("privatekey.2");
    }

    public static String getPublicKey2() {
        return PROPERTIES.getProperty("publickey.2");
    }

    public static String getAddress2() {
        return PROPERTIES.getProperty("address.2");
    }

}
