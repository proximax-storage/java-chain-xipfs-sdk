package io.proximax.model;

import io.nem.sdk.model.blockchain.NetworkType;

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
}
