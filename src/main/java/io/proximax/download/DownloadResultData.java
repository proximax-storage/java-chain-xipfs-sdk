package io.proximax.download;

import io.proximax.model.DataInfoModel;

import java.util.Collections;
import java.util.Map;

/**
 * The model class that defines the downloaded data
 * @see DownloadResult
 */
public class DownloadResultData extends DataInfoModel {

    private final byte[] data;
    private final String digest;
    private final String dataHash;
    private final long timestamp;

    DownloadResultData(byte[] data, String digest, String dataHash, long timestamp, String description, String name,
                       String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata == null ? Collections.emptyMap() : Collections.unmodifiableMap(metadata));
        this.data = data;
        this.digest = digest;
        this.dataHash = dataHash;
        this.timestamp = timestamp;
    }

    /**
     * Get the raw/unencrypted data
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Get the digest
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Get the data hash
     * @return the data hash
     */
    public String getDataHash() {
        return dataHash;
    }

    /**
     * Get the timestamp of upload
     * @return the timestamp of upload
     */
    public long getTimestamp() {
        return timestamp;
    }
}
