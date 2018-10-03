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
package io.proximax.service.client.catapult;

import io.nem.core.crypto.PublicKey;
import io.nem.sdk.infrastructure.AccountHttp;
import io.nem.sdk.model.account.AccountInfo;
import io.nem.sdk.model.account.Address;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.exceptions.AccountNotFoundException;
import io.proximax.exceptions.PublicKeyNotFoundException;

import java.net.MalformedURLException;

import static io.nem.core.crypto.PublicKey.fromHexString;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.lang.String.format;

/**
 * The client class that directly interface with the blockchain's transaction APIs
 */
public class AccountClient {

    /**
     * The public key constant when it is not yet used to send transaction on catapult.
     */
    public static final String PUBLIC_KEY_NOT_FOUND = "0000000000000000000000000000000000000000000000000000000000000000";

    private final AccountHttp accountHttp;

    /**
     * Create instance of AccountClient
     *
     * @param blockchainNetworkConnection the blockchain connection
     * @throws MalformedURLException exception when invalid blockchain URl
     */
    public AccountClient(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        checkParameter(blockchainNetworkConnection != null, "blockchainNetworkConnection is required");

        this.accountHttp = new AccountHttp(blockchainNetworkConnection.getApiUrl());
    }

    AccountClient(AccountHttp accountHttp) {
        this.accountHttp = accountHttp;
    }

    /**
     * Retrieves public key of address
     *
     * @param address the address
     * @return the public key of address
     */
    public PublicKey getPublicKey(String address) {
        checkParameter(address != null, "address is required.");

        try {
            final AccountInfo accountInfo = accountHttp.getAccountInfo(Address.createFromRawAddress(address)).blockingFirst();
            if (accountInfo.getPublicKey().equals(PUBLIC_KEY_NOT_FOUND))
                throw new PublicKeyNotFoundException(format("Address %s has no public key yet on blockchain", address));

            return fromHexString(accountInfo.getPublicKey());
        } catch (PublicKeyNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new AccountNotFoundException(
                    format("Failed to retrieve account for %s. Probably address is not yet revealed on blockchain.", address), e);
        }
    }

}
