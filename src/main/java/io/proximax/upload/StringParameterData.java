package io.proximax.upload;

import io.proximax.exceptions.ParamDataCreationException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a string upload
 */
public class StringParameterData extends AbstractByteStreamParameterData {

    private final String string;
    private final byte[] stringData;

    private StringParameterData(String string, String encoding, String description, String name, String contentType,
                               Map<String, String> metadata) {
        super(description, name, contentType, metadata);

        checkParameter(string != null, "string is required");

        this.string = string;
        this.stringData = toStringByteArray(string, encoding);
    }

    /**
     * Get the byte stream
     * @return the byte stream
     */
    @Override
    public InputStream getByteStream() {
        return new ByteArrayInputStream(stringData);
    }

    /**
     * Get the string
     * @return the string
     */
    public String getString() {
        return string;
    }

    private byte[] toStringByteArray(String string, String encoding) {
        try {
            return encoding == null ? string.getBytes() : string.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new ParamDataCreationException("Failed to convert string to bytes", e);
        }
    }

    /**
     * Create instance by providing the string
     * @param string the string to upload
     * @return the instance of this class
     */
    public static StringParameterData create(String string) {
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
     */
    public static StringParameterData create(String string, String encoding, String description, String name, String contentType, Map<String, String> metadata) {
        return new StringParameterData(string, encoding, description, name, contentType, metadata);
    }
}
