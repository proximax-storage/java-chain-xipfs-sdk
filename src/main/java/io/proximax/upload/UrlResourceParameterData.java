package io.proximax.upload;

import io.proximax.exceptions.GetByteStreamFailureException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import static io.proximax.model.Constants.RESERVED_CONTENT_TYPES;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a URL resource upload
 */
public class UrlResourceParameterData extends AbstractByteStreamParameterData {

    private final URL url;

    private UrlResourceParameterData(URL url, String description, String name, String contentType, Map<String, String> metadata) throws IOException {
        super(description, name, contentType, metadata);

        checkParameter(url != null, "url is required");

        this.url = url;
    }

    /**
     * Get the byte stream
     * @return the byte stream
     */
    @Override
    public InputStream getByteStream() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new GetByteStreamFailureException("Failed to open byte stream", e);
        }
    }

    /**
     * Get the URL resource to upload
     * @return the URL resource
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Create instance by providing the url
     * @param url the URL resource to upload
     * @return the instance of this class
     * @throws IOException read failures
     */
    public static UrlResourceParameterData create(URL url) throws IOException {
        return create(url, null, null, null, null);
    }

    /**
     * Create instance by providing the file
     * @param url the URL resource to upload
     * @param description a searchable description attach on the upload
     * @param name a searchable name attach on the upload
     * @param contentType the content type attach on the upload
     * @param metadata a searchable key-pair metadata attach on the upload
     * @return the instance of this class
     * @throws IOException read failures
     */
    public static UrlResourceParameterData create(URL url, String description, String name, String contentType, Map<String, String> metadata) throws IOException {
        return new UrlResourceParameterData(url, description, name, contentType, metadata);
    }
}
