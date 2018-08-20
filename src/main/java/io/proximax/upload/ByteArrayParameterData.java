package io.proximax.upload;

import java.util.Map;

import static io.proximax.model.Constants.RESEVERVE_CONTENT_TYPES;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a byte array upload
 */
public class ByteArrayParameterData extends UploadParameterData {

    private final byte[] data;

    ByteArrayParameterData(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
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

    /**
     * Start creating an instance of ByteArrayParameterData using the ByteArrayParameterDataBuilder
     * @param data the byte array to upload
     * @return the byte array parameter data builder
     */
    public static ByteArrayParameterDataBuilder create(byte[] data) {
        checkParameter(data != null, "data is required");

        return new ByteArrayParameterDataBuilder(data);
    }

    /**
     * This builder class creates the ByteArrayParameterData
     */
    public static class ByteArrayParameterDataBuilder extends AbstractParameterDataBuilder<ByteArrayParameterDataBuilder> {
        private byte[] data;
        private String contentType;

        ByteArrayParameterDataBuilder(byte[] data) {
            this.data = data;
        }

        /**
         * Set the content type for the data
         * @param contentType the content type
         * @return same instance of the builder class
         */
        public ByteArrayParameterDataBuilder contentType(String contentType) {
            checkParameter(!RESEVERVE_CONTENT_TYPES.contains(contentType), String.format("%s cannot be used as it is reserved", contentType));
            this.contentType = contentType;
            return this;
        }

        /**
         * Builds the ByteArrayParameterData
         * @return the byte array parameter data
         */
        public ByteArrayParameterData build() {
            return new ByteArrayParameterData(data, description, name, contentType, metadata);
        }
    }
}
