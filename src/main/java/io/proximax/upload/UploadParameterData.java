package io.proximax.upload;

import io.proximax.model.RawDataModel;

import java.util.Map;

/**
 * The abstract model class that defines the common fields of an upload data
 */
public abstract class UploadParameterData extends RawDataModel {

    UploadParameterData(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
        super(data, description, name, contentType, metadata);
    }
}
