package io.proximax.upload;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class StringParameterData extends UploadParameterData {

    private final String string;

    StringParameterData(String string, String encoding, String description, String name, String contentType,
                               Map<String, String> metadata) throws UnsupportedEncodingException {
        super(toStringByteArray(string, encoding), description, name, contentType, metadata);
        this.string = string;
    }

    public String getString() {
        return string;
    }

    private static byte[] toStringByteArray(String string, String encoding) throws UnsupportedEncodingException {
        checkParameter(string != null, "string is required");

        return encoding == null ? string.getBytes() : string.getBytes(encoding);
    }

    public static StringParameterDataBuilder create(String string) {
        return new StringParameterDataBuilder(string);
    }

    public static class StringParameterDataBuilder extends AbstractParameterDataBuilder<StringParameterDataBuilder> {
        private String string;
        private String encoding;

        StringParameterDataBuilder(String string) {
            this.string = string;
        }

        public StringParameterDataBuilder encoding(String encoding) {
            this.encoding = encoding;
            return this;
        }

        public StringParameterData build() throws UnsupportedEncodingException {
            return new StringParameterData(string, encoding, description, name, contentType, metadata);
        }
    }
}
