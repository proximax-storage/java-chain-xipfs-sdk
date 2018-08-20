package io.proximax.upload;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static io.proximax.model.Constants.RESEVERVE_CONTENT_TYPES;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a URL resource upload
 */
public class UrlResourceParameterData extends ByteArrayParameterData {

    private final URL url;

    UrlResourceParameterData(URL url, String description, String name, String contentType, Map<String, String> metadata) throws IOException {
        super(toUrlResourceByteArray(url), description, name, contentType, metadata);
        this.url = url;
    }

    /**
     * Get the URL resource to upload
     * @return the URL resource
     */
    public URL getUrl() {
        return url;
    }

    private static byte[] toUrlResourceByteArray(URL url) throws IOException {
        checkParameter(url != null, "url is required");

        return IOUtils.toByteArray(url);
    }

    /**
     * Start creating an instance of UrlResourceParameterData using the UrlResourceParameterDataBuilder
     * @param url the URL resource to upload
     * @return the URL resource parameter data builder
     */
    public static UrlResourceParameterDataBuilder create(URL url) {
        return new UrlResourceParameterDataBuilder(url);
    }

    /**
     * This builder class creates the UrlResourceParameterData
     */
    public static class UrlResourceParameterDataBuilder extends AbstractParameterDataBuilder<UrlResourceParameterDataBuilder> {
        private URL url;
        private String contentType;

        UrlResourceParameterDataBuilder(URL url) {
            this.url = url;
        }

        /**
         * Set the content type for the data
         * @param contentType the content type
         * @return same instance of the builder class
         */
        public UrlResourceParameterDataBuilder contentType(String contentType) {
            checkParameter(!RESEVERVE_CONTENT_TYPES.contains(contentType), String.format("%s cannot be used as it is reserved", contentType));
            this.contentType = contentType;
            return this;
        }

        /**
         * Builds the UrlResourceParameterData
         * @return the URL resource parameter data
         * @throws IOException when reading the URL resource fails
         */
        public UrlResourceParameterData build() throws IOException {
            return new UrlResourceParameterData(url, description, name, contentType, metadata);
        }
    }
}
