package io.proximax.model;

import io.nem.sdk.model.blockchain.NetworkType;

public enum BlockchainNetwork {

    MAIN_NET(io.nem.sdk.model.blockchain.NetworkType.MAIN_NET),
    TEST_NET(io.nem.sdk.model.blockchain.NetworkType.TEST_NET),
    MIJIN(io.nem.sdk.model.blockchain.NetworkType.MIJIN),
    MIJIN_TEST(io.nem.sdk.model.blockchain.NetworkType.MIJIN_TEST);

    public io.nem.sdk.model.blockchain.NetworkType networkType;

    BlockchainNetwork(NetworkType networkType) {
        this.networkType = networkType;
    }
}
