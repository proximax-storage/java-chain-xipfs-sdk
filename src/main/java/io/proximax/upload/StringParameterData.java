package io.proximax.upload;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static io.proximax.model.Constants.RESERVED_CONTENT_TYPES;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a string upload
 */
public class StringParameterData extends ByteArrayParameterData {

    private final String string;

    private StringParameterData(String string, String encoding, String description, String name, String contentType,
                               Map<String, String> metadata) throws UnsupportedEncodingException {
        super(toStringByteArray(string, encoding), description, name, contentType, metadata);

        checkParameter(contentType == null || !RESERVED_CONTENT_TYPES.contains(contentType),
                String.format("%s cannot be used as it is reserved", contentType));

        this.string = string;
    }

    /**
     * Get the string
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
     * Create instance by providing the string
     * @param string the string to upload
     * @return the instance of this class
     * @throws UnsupportedEncodingException invalid encoding
     */
    public static StringParameterData create(String string) throws UnsupportedEncodingException {
        return create(string, null,null, null, null, null);
    }

    /**
     * Create instance by providing the string
     * @param string the string to upload
     * @param encoding the encoding the string to assist on byte array conversion
     * @param description a searchable description attach on the upload
     * @param name a searchable name attach on the upload
     * @param contentType the content type attach on the upload
     * @param metadata a searchable key-pair metadata attach on the upload
     * @return the instance of this class
     * @throws UnsupportedEncodingException invalid encoding
     */
    public static StringParameterData create(String string, String encoding, String description, String name, String contentType, Map<String, String> metadata) throws UnsupportedEncodingException {
        return new StringParameterData(string, encoding, description, name, contentType, metadata);
    }
}
