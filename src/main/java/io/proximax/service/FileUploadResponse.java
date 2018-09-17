package io.proximax.service;

import java.io.File;
import java.io.InputStream;

/**
 * The model class that defines the result of uploading to IPFS
 *
 * @see FileUploadService#uploadByteStream(InputStream)
 * @see FileUploadService#uploadPath(File)
 */
public class FileUploadResponse {

    private final String dataHash;
    private final Long timestamp;

    FileUploadResponse(String dataHash, Long timestamp) {
        this.dataHash = dataHash;
        this.timestamp = timestamp;
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
}
