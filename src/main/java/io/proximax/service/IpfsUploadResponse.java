package io.proximax.service;

import java.util.List;

/**
 * The model class that defines the result of uploading to IPFS
 * @see IpfsUploadService#upload(byte[])
 * @see IpfsUploadService#uploadList(List)
 */
public class IpfsUploadResponse {

    private final String dataHash;
    private final Long timestamp;

    IpfsUploadResponse(String dataHash, Long timestamp) {
        this.dataHash = dataHash;
        this.timestamp = timestamp;
    }

    /**
     * Get the data hash of the IPFS upload result
     * @return the data hash
     */
    public String getDataHash() {
        return dataHash;
    }

    /**
     * Get the timestamp of upload
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }
}
