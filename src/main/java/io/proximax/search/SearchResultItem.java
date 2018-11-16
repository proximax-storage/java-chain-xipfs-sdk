package io.proximax.search;

import io.proximax.model.ProximaxMessagePayloadModel;

public class SearchResultItem {

    private final String transactionHash;
    private final String transactionId;
    private final ProximaxMessagePayloadModel messagePayload;

    SearchResultItem(String transactionHash, String transactionId, ProximaxMessagePayloadModel messagePayload) {
        this.transactionHash = transactionHash;
        this.transactionId = transactionId;
        this.messagePayload = messagePayload;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public ProximaxMessagePayloadModel getMessagePayload() {
        return messagePayload;
    }
}
