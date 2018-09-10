package io.proximax.upload;

import io.proximax.model.DataInfoModel;
import io.proximax.utils.JsonUtils;

import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.lang.String.format;

/**
 * The abstract model class that defines the common fields of an upload data
 */
public abstract class UploadParameterData extends DataInfoModel {

    public static final int MAX_DESCRIPTION_LENGTH = 200;
    public static final int MAX_NAME_LENGTH = 200;
    public static final int MAX_METADATA_JSON_LENGTH = 500;

    UploadParameterData(String description, String name, String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata);

        checkParameter(description == null || description.length() <= MAX_DESCRIPTION_LENGTH,
                format("description cannot be more than %d characters", MAX_DESCRIPTION_LENGTH));
        checkParameter(name == null || name.length() <= MAX_NAME_LENGTH,
                format("name cannot be more than %d characters", MAX_NAME_LENGTH));
        checkParameter(metadata == null || JsonUtils.toJson(metadata).length() <= MAX_METADATA_JSON_LENGTH,
                format("metadata as JSON string cannot be more than %d characters", MAX_METADATA_JSON_LENGTH));
    }
}
