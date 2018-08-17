package io.proximax.upload;

import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a byte array upload
 */
public class ByteArrayParameterData extends UploadParameterData {

    ByteArrayParameterData(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
        super(data, description, name, contentType, metadata);
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

        ByteArrayParameterDataBuilder(byte[] data) {
            this.data = data;
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
