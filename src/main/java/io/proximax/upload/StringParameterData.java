package io.proximax.upload;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static io.proximax.model.Constants.RESEVERVE_CONTENT_TYPES;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a string upload
 */
public class StringParameterData extends ByteArrayParameterData {

    private final String string;

    StringParameterData(String string, String encoding, String description, String name, String contentType,
                               Map<String, String> metadata) throws UnsupportedEncodingException {
        super(toStringByteArray(string, encoding), description, name, contentType, metadata);
        this.string = string;
    }

    /**
     * Get the string to upload
     * @return the string
     */
    public String getString() {
        return string;
    }

    private static byte[] toStringByteArray(String string, String encoding) throws UnsupportedEncodingException {
        checkParameter(string != null, "string is required");

        return encoding == null ? string.getBytes() : string.getBytes(encoding);
    }

    /**
     * Start creating an instance of StringParameterData using the StringParameterDataBuilder
     * @param string the string to upload
     * @return the string parameter data builder
     */
    public static StringParameterDataBuilder create(String string) {
        return new StringParameterDataBuilder(string);
    }

    /**
     * This builder class creates the StringParameterData
     */
    public static class StringParameterDataBuilder extends AbstractParameterDataBuilder<StringParameterDataBuilder> {
        private String string;
        private String encoding;
        private String contentType;

        StringParameterDataBuilder(String string) {
            this.string = string;
        }

        /**
         * The encoding to use to convert the string into byte array
         * @param encoding the encoding
         * @return same instance of this builder
         */
        public StringParameterDataBuilder encoding(String encoding) {
            this.encoding = encoding;
            return this;
        }

        /**
         * Set the content type for the data
         * @param contentType the content type
         * @return same instance of the builder class
         */
        public StringParameterDataBuilder contentType(String contentType) {
            checkParameter(!RESEVERVE_CONTENT_TYPES.contains(contentType), String.format("%s cannot be used as it is reserved", contentType));
            this.contentType = contentType;
            return this;
        }

        /**
         * Builds the StringParameterData
         * @return the string parameter data
         * @throws UnsupportedEncodingException when the conversion of string to byte array fails
         */
        public StringParameterData build() throws UnsupportedEncodingException {
            return new StringParameterData(string, encoding, description, name, contentType, metadata);
        }
    }
}
