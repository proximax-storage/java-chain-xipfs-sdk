package io.proximax.upload;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.io.IOException;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class ByteArrayParameterDataTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullByteArray() {
        ByteArrayParameterData.create(null);
    }

    @Test
    public void createWithByteArrayOnly() throws IOException {
        final ByteArrayParameterData param = ByteArrayParameterData.create(SAMPLE_DATA);

        assertThat(param, is(notNullValue()));
        assertThat(param.getData(), is(SAMPLE_DATA));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(param.getByteStream())),
                is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
        assertThat(param.getContentType(), is(nullValue()));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(nullValue()));
        assertThat(param.getName(), is(nullValue()));
    }

    @Test
    public void createWithCompleteDetails() throws IOException {
        final ByteArrayParameterData param = ByteArrayParameterData.create(SAMPLE_DATA, "describe me",
                "name here","text/plain", singletonMap("mykey", "myvalue"));

        assertThat(param, is(notNullValue()));
        assertThat(param.getData(), is(SAMPLE_DATA));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(param.getByteStream())),
                is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
        assertThat(param.getContentType(), is("text/plain"));
        assertThat(param.getDescription(), is("describe me"));
        assertThat(param.getMetadata(), is(singletonMap("mykey", "myvalue")));
        assertThat(param.getName(), is("name here"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenContentTypeIsReservedExist() throws IOException {
        ByteArrayParameterData.create(SAMPLE_DATA, null, null, PATH_UPLOAD_CONTENT_TYPE, null);
    }

}
