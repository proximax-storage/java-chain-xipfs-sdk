package io.proximax.model;

import java.util.Collections;
import java.util.Map;

public abstract class RawDataModel {

    private final byte[] data;
    private final String description;
    private final Map<String, String> metadata;
    private final String name;
    private final String contentType;

    public RawDataModel(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
        this.data = data;
        this.description = description;
        this.metadata = metadata == null ? Collections.emptyMap() : Collections.unmodifiableMap(metadata);
        this.name = name;
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }
}
