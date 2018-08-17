package io.proximax.model;

import java.util.Collections;
import java.util.Map;

/**
 * This model class defines what the the fields of a data
 * <br>
 * <br>
 * The following are its fields:
 * <ul>
 *     <li><b>data</b> - the raw and unencrypted data</li>
 *     <li><b>description</b> - a description of the data</li>
 *     <li><b>metadata</b> - an additional metadata for the data</li>
 *     <li><b>name</b> - the name for the data</li>
 *     <li><b>contentType</b> - the content type of the data</li>
 * </ul>
 */
public abstract class RawDataModel {

    private final byte[] data;
    private final String description;
    private final Map<String, String> metadata;
    private final String name;
    private final String contentType;

    /**
     * Construct instance of this model
     * @param data the raw and unencrypted data
     * @param description a description of the data
     * @param name the name for the data
     * @param contentType the content type of the data
     * @param metadata an additional metadata for the data
     */
    public RawDataModel(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
        this.data = data;
        this.description = description;
        this.metadata = metadata == null ? Collections.emptyMap() : Collections.unmodifiableMap(metadata);
        this.name = name;
        this.contentType = contentType;
    }

    /**
     * Get the raw and unencrypted data
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Get the description of the data
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the additional metadata for the data
     * @return the metadata
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Get the name for the data
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the content type of the data
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }
}
