package io.proximax.upload;

import java.io.InputStream;
import java.util.Map;

import static io.proximax.model.Constants.RESERVED_CONTENT_TYPES;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a byte stream upload
 */
public abstract class AbstractByteStreamParameterData extends UploadParameterData {

    protected AbstractByteStreamParameterData(String description, String name, String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata);

        checkParameter(contentType == null || !RESERVED_CONTENT_TYPES.contains(contentType),
                String.format("%s cannot be used as it is reserved", contentType));
    }

    /**
     * Get the byte stream
     * @return the byte stream
     */
    public abstract InputStream getByteStream();
}
