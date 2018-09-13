package io.proximax.upload;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.testsupport.Constants.TEST_HTML_FILE;
import static io.proximax.testsupport.Constants.TEST_IMAGE_PNG_FILE;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class UrlResourceParameterDataTest {

    public static final String URL_PDF = "https://proximax.io/ProximaX-Whitepaper-v1.4.pdf";

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullUrl() throws IOException {
        UrlResourceParameterData.create((URL) null);
    }

    @Test
    public void createWithUrlOnly() throws IOException {
        final URL url = TEST_IMAGE_PNG_FILE.toURI().toURL();

        final UrlResourceParameterData param = UrlResourceParameterData.create(url);

        assertThat(param, is(notNullValue()));
        assertThat(param.getUrl(), is(url));
        assertThat(param.getByteStream(), is(notNullValue()));
        assertThat(param.getContentType(), is(nullValue()));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(nullValue()));
        assertThat(param.getName(), is(nullValue()));
    }

    @Test
    public void createWithCompleteDetails() throws IOException {
        final URL url = TEST_IMAGE_PNG_FILE.toURI().toURL();

        final UrlResourceParameterData param = UrlResourceParameterData.create(url, "describe me",
                "name here", "text/plain", singletonMap("mykey", "myvalue"));

        assertThat(param, is(notNullValue()));
        assertThat(param.getUrl(), is(url));
        assertThat(param.getByteStream(), is(notNullValue()));
        assertThat(param.getContentType(), is("text/plain"));
        assertThat(param.getDescription(), is("describe me"));
        assertThat(param.getMetadata(), is(singletonMap("mykey", "myvalue")));
        assertThat(param.getName(), is("name here"));
    }

    @Test
    public void createWithHtmlFileUrlOnly() throws IOException {
        final URL url = TEST_HTML_FILE.toURI().toURL();

        final UrlResourceParameterData param = UrlResourceParameterData.create(url);

        assertThat(param, is(notNullValue()));
        assertThat(param.getUrl(), is(url));
        assertThat(param.getByteStream(), is(notNullValue()));
        assertThat(param.getContentType(), is(nullValue()));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(nullValue()));
        assertThat(param.getName(), is(nullValue()));
    }

    @Test
    @Ignore("creates dependency to internet, test only when required")
    public void createWithHtmlHttpUrlOnly() throws IOException {
        final URL url = new URL(URL_PDF);
        final UrlResourceParameterData param = UrlResourceParameterData.create(url);

        assertThat(param, is(notNullValue()));
        assertThat(param.getUrl(), is(url));
        assertThat(param.getByteStream(), is(notNullValue()));
        assertThat(param.getContentType(), is(nullValue()));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(nullValue()));
        assertThat(param.getName(), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenContentTypeIsReservedExist() throws IOException {
        final URL URL = TEST_HTML_FILE.toURI().toURL();

        UrlResourceParameterData.create(URL, null, null, PATH_UPLOAD_CONTENT_TYPE, null);
    }
}
