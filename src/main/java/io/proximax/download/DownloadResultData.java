package io.proximax.download;

import io.proximax.model.DataInfoModel;

import java.util.Map;

/**
 * The model class that defines the downloaded data
 */
public class DownloadResultData extends DataInfoModel {

    private final byte[] data;

    DownloadResultData(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata);
        this.data = data;
    }

    /**
     * Get the raw/unencrypted data
     * @return the data
     */
    public byte[] getData() {
        return data;
    }
}
