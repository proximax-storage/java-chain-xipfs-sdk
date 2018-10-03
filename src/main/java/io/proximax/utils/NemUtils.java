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
package io.proximax.utils;

import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.Transaction;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The utility class for handling blockchain-related actions
 */
public class NemUtils {

    private final NetworkType networkType;

    /**
     * Construct an instance of this utility class
     *
     * @param networkType the network type of the blockchain
     */
    public NemUtils(NetworkType networkType) {
        this.networkType = networkType;
    }

    /**
     * Convert to nem address
     *
     * @param address the address
     * @return the address
     */
    public Address getAddress(String address) {
        checkParameter(address != null, "address is required");

        return Address.createFromRawAddress(address);
    }

    /**
     * Converts a public key to an address
     *
     * @param publicKey the public key
     * @return the address
     */
    public Address getAddressFromPublicKey(String publicKey) {
        checkParameter(publicKey != null, "publicKey is required");

        return Address.createFromPublicKey(publicKey, networkType);
    }

    /**
     * Converts a private key to an address
     *
     * @param privateKey the public key
     * @return the address
     */
    public Address getAddressFromPrivateKey(String privateKey) {
        checkParameter(privateKey != null, "privateKey is required");

        return getAccount(privateKey).getAddress();
    }

    /**
     * Converts a private key to an account
     *
     * @param privateKey the public key
     * @return the account
     */
    public Account getAccount(String privateKey) {
        checkParameter(privateKey != null, "privateKey is required");

        return Account.createFromPrivateKey(privateKey, networkType);
    }

    /**
     * Sign a transaction
     *
     * @param signerPrivateKey the signer's private key
     * @param transaction      the transaction to sign
     * @return the signed transaction
     */
    public SignedTransaction signTransaction(String signerPrivateKey, Transaction transaction) {
        checkParameter(signerPrivateKey != null, "signerPrivateKey is required");
        checkParameter(transaction != null, "transaction is required");

        return getAccount(signerPrivateKey).sign(transaction);
    }
}
