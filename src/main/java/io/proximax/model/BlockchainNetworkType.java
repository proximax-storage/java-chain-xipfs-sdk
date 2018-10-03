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
     *
     * @param networkType the network type
     * @return the enum value
     */

    public static BlockchainNetworkType fromString(String networkType) {
        return Stream.of(values()).filter(val -> val.name().equals(networkType)).findFirst().orElse(null);
    }
}
