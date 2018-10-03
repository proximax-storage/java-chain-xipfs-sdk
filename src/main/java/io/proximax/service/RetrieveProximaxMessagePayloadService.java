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
package io.proximax.service;

import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.utils.JsonUtils;

import java.net.MalformedURLException;

/**
 * The service class responsible for retrieving message payload from transaction
 */
public class RetrieveProximaxMessagePayloadService {

    private final BlockchainMessageService blockchainMessageService;

    public RetrieveProximaxMessagePayloadService(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        this.blockchainMessageService = new BlockchainMessageService(blockchainNetworkConnection);
    }

    RetrieveProximaxMessagePayloadService(BlockchainMessageService blockchainMessageService) {
        this.blockchainMessageService = blockchainMessageService;
    }

    /**
     * Retrieves message payload
     *
     * @param transferTransaction the blockchain transaction
     * @param accountPrivateKey   the private key of either signer or recipient to read secure message
     * @return the message payload
     */
    public ProximaxMessagePayloadModel getMessagePayload(TransferTransaction transferTransaction, String accountPrivateKey) {
        final String payload = blockchainMessageService.getMessagePayload(transferTransaction, accountPrivateKey);
        return JsonUtils.fromJson(payload, ProximaxMessagePayloadModel.class);
    }
}