package io.proximax.service;

import io.nem.sdk.model.transaction.PlainMessage;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.exceptions.DownloadForMessageTypeNotSupportedException;
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
     * @param accountPrivateKey the private key of either signer or recipient to read secure message
     * @return the message payload
     */
    public ProximaxMessagePayloadModel getMessagePayload(TransferTransaction transferTransaction, String accountPrivateKey) {
        checkParameter(transferTransaction != null, "transferTransaction is required");

        // TODO handle secure message
        if (transferTransaction.getMessage() instanceof PlainMessage) {
            final String messagePayload = transferTransaction.getMessage().getPayload();
            return JsonUtils.fromJson(messagePayload, ProximaxMessagePayloadModel.class);
        } else {
            throw new DownloadForMessageTypeNotSupportedException(
                    String.format("Download of message type %s is not supported", transferTransaction.getMessage().getClass().getSimpleName()));
        }
    }
}