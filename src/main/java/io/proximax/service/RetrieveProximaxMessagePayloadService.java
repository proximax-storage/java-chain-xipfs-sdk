package io.proximax.service;

import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.utils.JsonUtils;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for retrieving message payload from transaction
 */
public class RetrieveProximaxMessagePayloadService {

    /**
     * Retrieves message payload from blockchain transaction
     * @param transferTransaction the blockchain transaction
     * @return the message payload
     */
    public ProximaxMessagePayloadModel getMessagePayload(TransferTransaction transferTransaction) {
        checkParameter(transferTransaction != null, "transferTransaction is required");

        final String messagePayload = transferTransaction.getMessage().getPayload();
        return JsonUtils.fromJson(messagePayload, ProximaxMessagePayloadModel.class);
    }
}