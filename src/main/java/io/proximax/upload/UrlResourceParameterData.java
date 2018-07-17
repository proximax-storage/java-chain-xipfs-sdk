package io.proximax.upload;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class UrlResourceParameterData extends UploadParameterData {

    private final URL url;

    UrlResourceParameterData(URL url, String description, String name, String contentType, Map<String, String> metadata) throws IOException {
        super(toUrlResourceByteArray(url), description, name, contentType, metadata);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    private static byte[] toUrlResourceByteArray(URL url) throws IOException {
        checkParameter(url != null, "url is required");

        return IOUtils.toByteArray(url);
    }

    public static UrlResourceParameterDataBuilder create(URL url) {
        return new UrlResourceParameterDataBuilder(url);
    }

    public static class UrlResourceParameterDataBuilder extends AbstractParameterDataBuilder<UrlResourceParameterDataBuilder> {
        private URL url;

        UrlResourceParameterDataBuilder(URL url) {
            this.url = url;
        }

        public UrlResourceParameterData build() throws IOException {
            return new UrlResourceParameterData(url, description, name, contentType, metadata);
        }
    }
}
