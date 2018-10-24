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

    /**
     * Construct this class
     *
     * @param blockchainNetworkConnection the blockchain connection config
     * @throws MalformedURLException malformed URL
     */
    public RetrieveProximaxMessagePayloadService(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        this.blockchainMessageService = new BlockchainMessageService(blockchainNetworkConnection);
    }

    RetrieveProximaxMessagePayloadService(BlockchainMessageService blockchainMessageService) {
        this.blockchainMessageService = blockchainMessageService;
    }

    /**
     * Retrieves message payload
     * @param transferTransaction the blockchain transaction
     * @param accountPrivateKey the private key of either signer or recipient to read secure message
     * @return the message payload
     */
    public ProximaxMessagePayloadModel getMessagePayload(TransferTransaction transferTransaction, String accountPrivateKey) {
        final String payload = blockchainMessageService.getMessagePayload(transferTransaction, accountPrivateKey);
        return JsonUtils.fromJson(payload, ProximaxMessagePayloadModel.class);
    }
}