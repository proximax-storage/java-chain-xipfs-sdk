package io.proximax.service;

public class IpfsUploadResponse {

    private final String dataHash;
    private final Long timestamp;

    public IpfsUploadResponse(String dataHash, Long timestamp) {
        this.dataHash = dataHash;
        this.timestamp = timestamp;
    }

    public String getDataHash() {
        return dataHash;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
