package io.proximax.upload;

import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class ByteArrayParameterData extends UploadParameterData {

    ByteArrayParameterData(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
        super(data, description, name, contentType, metadata);
    }

    public static ByteArrayParameterDataBuilder create(byte[] data) {
        checkParameter(data != null, "data is required");

        return new ByteArrayParameterDataBuilder(data);
    }

    public static class ByteArrayParameterDataBuilder extends AbstractParameterDataBuilder<ByteArrayParameterDataBuilder> {
        private byte[] data;

        ByteArrayParameterDataBuilder(byte[] data) {
            this.data = data;
        }

        public ByteArrayParameterData build() {
            return new ByteArrayParameterData(data, description, name, contentType, metadata);
        }
    }
}
