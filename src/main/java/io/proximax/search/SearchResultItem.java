package io.proximax.search;

import io.proximax.model.ProximaxMessagePayloadModel;

public class SearchResultItem {

    private final String transactionHash;
    private final ProximaxMessagePayloadModel messagePayload;

    SearchResultItem(String transactionHash, ProximaxMessagePayloadModel messagePayload) {
        this.transactionHash = transactionHash;
        this.messagePayload = messagePayload;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public ProximaxMessagePayloadModel getMessagePayload() {
        return messagePayload;
    }
}
