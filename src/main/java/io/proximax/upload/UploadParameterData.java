package io.proximax.upload;

import io.proximax.model.DataInfoModel;

import java.util.Map;

/**
 * The abstract model class that defines the common fields of an upload data
 */
public abstract class UploadParameterData extends DataInfoModel {

    UploadParameterData(String description, String name, String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata);
    }
}
