package io.proximax.upload;

import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class StringParameterDataTest {

    public static final String SAMPLE_DATA = "the quick brown fox jumps over the lazy dog";

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullString() throws IOException {
        StringParameterData.create((String) null);
    }

    @Test
    public void createWithStringOnly() throws UnsupportedEncodingException {
        final StringParameterData param = StringParameterData.create(SAMPLE_DATA);

        assertThat(param, is(notNullValue()));
        assertThat(param.getData(), is(SAMPLE_DATA.getBytes()));
        assertThat(param.getContentType(), is(nullValue()));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(nullValue()));
        assertThat(param.getName(), is(nullValue()));
    }

    @Test
    public void createWithCompleteDetails() throws UnsupportedEncodingException {
        final StringParameterData param = StringParameterData.create(SAMPLE_DATA, "UTF-8",
                "describe me", "name here", "text/plain", singletonMap("mykey", "myvalue"));

        assertThat(param, is(notNullValue()));
        assertThat(param.getData(), is(SAMPLE_DATA.getBytes("UTF-8")));
        assertThat(param.getContentType(), is("text/plain"));
        assertThat(param.getDescription(), is("describe me"));
        assertThat(param.getMetadata(), is(singletonMap("mykey", "myvalue")));
        assertThat(param.getName(), is("name here"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenContentTypeIsReservedExist() throws IOException {
        StringParameterData.create(SAMPLE_DATA, null, null, null, PATH_UPLOAD_CONTENT_TYPE, null);
    }
}
