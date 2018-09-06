package io.proximax.download;

import io.proximax.model.DataInfoModel;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

/**
 * The model class that defines the downloaded data
 * @see DownloadResult
 */
public class DownloadResultData extends DataInfoModel {

    private final InputStream byteStream;
    private final String digest;
    private final String dataHash;
    private final long timestamp;

    DownloadResultData(InputStream byteStream, String digest, String dataHash, long timestamp, String description, String name,
                       String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata == null ? Collections.emptyMap() : Collections.unmodifiableMap(metadata));
        this.byteStream = byteStream;
        this.digest = digest;
        this.dataHash = dataHash;
        this.timestamp = timestamp;
    }

    /**
     * Get the byte stream
     * @return the byte stream
     */
    public InputStream getByteStream() {
        return byteStream;
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
