package io.proximax.service;

import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.utils.JsonUtils;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for retrieving message payload from transaction
 */
public class RetrieveProximaxMessagePayloadService {

    /**
     * Retrieves message payload from blockchain transaction
     * @param privacyStrategy the privacy strategy the decodes message from payload
     * @param transferTransaction the blockchain transaction
     * @return the message payload
     */
    public ProximaxMessagePayloadModel getMessagePayload(PrivacyStrategy privacyStrategy, TransferTransaction transferTransaction) {
        checkParameter(privacyStrategy != null, "privacyStrategy is required");
        checkParameter(transferTransaction != null, "transferTransaction is required");

        final String messagePayload = privacyStrategy.decodePayloadFromMessage(transferTransaction.getMessage());
        return JsonUtils.fromJson(messagePayload, ProximaxMessagePayloadModel.class);
    }
}