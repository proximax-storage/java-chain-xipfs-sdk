package io.proximax.upload;

import io.proximax.model.RawDataModel;

import java.util.Map;

public abstract class UploadParameterData extends RawDataModel {

    public UploadParameterData(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
        super(data, description, name, contentType, metadata);
    }
}
