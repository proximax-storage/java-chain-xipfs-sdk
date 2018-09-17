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
