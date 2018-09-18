package io.proximax.model;

import io.nem.sdk.model.blockchain.NetworkType;

import java.util.stream.Stream;

/**
 * Enumerates the blokchain network types
 */
public enum BlockchainNetworkType {

    /**
     * The network type for mainnet
     */
    MAIN_NET(io.nem.sdk.model.blockchain.NetworkType.MAIN_NET),

    /**
     * The network type for testnet
     */
    TEST_NET(io.nem.sdk.model.blockchain.NetworkType.TEST_NET),

    /**
     * The network type for mijin
     */
    MIJIN(io.nem.sdk.model.blockchain.NetworkType.MIJIN),

    /**
     * The network type for mijin test
     */
    MIJIN_TEST(io.nem.sdk.model.blockchain.NetworkType.MIJIN_TEST);

    /**
     * The network type equivalent on nem sdk
     */
    public io.nem.sdk.model.blockchain.NetworkType networkType;

    BlockchainNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    /**
     * Get the enum value from string
     * @param networkType the network type
     * @return the enum value
     */

    public static BlockchainNetworkType fromString(String networkType) {
        return Stream.of(values()).filter(val -> val.name().equals(networkType)).findFirst().orElse(null);
    }
}
