package io.proximax.service;

import io.proximax.privacy.strategy.PrivacyStrategy;

import java.io.File;
import java.util.function.Supplier;

/**
 * The model class that defines the result of uploading to IPFS
 *
 * @see FileUploadService#uploadByteStream(Supplier, PrivacyStrategy, Boolean)
 * @see FileUploadService#uploadPath(File)
 */
public class FileUploadResponse {

    private final String dataHash;
    private final Long timestamp;
    private final String digest;

    FileUploadResponse(String dataHash, Long timestamp, String digest) {
        this.dataHash = dataHash;
        this.timestamp = timestamp;
        this.digest = digest;
    }

    /**
     * Get the data hash of the IPFS upload result
     *
     * @return the data hash
     */
    public String getDataHash() {
        return dataHash;
    }

    /**
     * Get the timestamp of upload
     *
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Get the digest
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }
}
