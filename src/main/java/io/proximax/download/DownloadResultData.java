package io.proximax.download;

import io.proximax.model.DataInfoModel;
import io.proximax.upload.StreamUtils;
import io.proximax.utils.TimeoutUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
     * Get the byte stream with timeout
     * @param timeout timeout length
     * @param timeUnit time unit of timeout
     * @return the byte stream
     */
    public InputStream getByteStream(long timeout, TimeUnit timeUnit) {
        return TimeoutUtils.get(byteStreamSupplier, timeout, timeUnit);
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
     * Get content as string with timeout
     * @param encoding string encoding
     * @param timeout timeout length
     * @param timeUnit time unit of timeout
     * @return the content as string
     */
    public String getContentAsString(String encoding, long timeout, TimeUnit timeUnit) {
        return StreamUtils.toString(getByteStream(timeout, timeUnit), encoding);
    }

    /**
     * Get content as byte array
     * @return the content as byte array
     */
    public byte[] getContentAsByteArray() {
        return StreamUtils.toByteArray(byteStreamSupplier.get());
    }

    /**
     * Get content as byte array with timeout
     * @param timeout timeout length
     * @param timeUnit time unit of timeout
     * @return the content as byte array
     */
    public byte[] getContentAsByteArray(long timeout, TimeUnit timeUnit) {
        return StreamUtils.toByteArray(getByteStream(timeout, timeUnit));
    }

    /**
     * Save content to file
     * @param file the file to save
     */
    public void saveToFile(File file) {
        checkParameter(file != null, "file is required");

        StreamUtils.saveToFile(byteStreamSupplier.get(), file);
    }

    /**
     * Save content to file with timeout
     * @param timeout timeout length
     * @param timeUnit time unit of timeout
     * @param file the file to save
     */
    public void saveToFile(File file, long timeout, TimeUnit timeUnit) {
        checkParameter(file != null, "file is required");

        StreamUtils.saveToFile(getByteStream(timeout, timeUnit), file);
    }
}
