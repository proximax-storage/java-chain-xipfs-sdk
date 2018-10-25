package io.proximax.download;

import io.proximax.model.DataInfoModel;
import io.proximax.upload.StreamUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The model class that defines the downloaded data
 * @see DownloadResult
 */
public class DownloadResultData extends DataInfoModel {

    private final Supplier<InputStream> byteStreamSupplier;
    private final String digest;
    private final String dataHash;
    private final long timestamp;

    DownloadResultData(Supplier<InputStream> byteStreamSupplier, String digest, String dataHash, long timestamp, String description, String name,
                       String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata == null ? Collections.emptyMap() : Collections.unmodifiableMap(metadata));
        this.byteStreamSupplier = byteStreamSupplier;
        this.digest = digest;
        this.dataHash = dataHash;
        this.timestamp = timestamp;
    }

    /**
     * Get the byte stream
     * @return the byte stream
     */
    public InputStream getByteStream() {
        return byteStreamSupplier.get();
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

    /**
     * Get content as string
     * @param encoding string encoding
     * @return the content as string
     */
    public String getContentAsString(String encoding) {
        return StreamUtils.toString(byteStreamSupplier.get(), encoding);
    }

    /**
     * Get content as byte array
     * @return the content as byte array
     */
    public byte[] getContentAsByteArray() {
        return StreamUtils.toByteArray(byteStreamSupplier.get());
    }

    /**
     * Save content to file
     * @param file the file to save
     */
    public void saveToFile(File file) {
        checkParameter(file != null, "file is required");

        StreamUtils.saveToFile(byteStreamSupplier.get(), file);
    }
}
