package io.proximax.upload;

import org.junit.Test;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class ByteArrayParameterDataTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullByteArray() {
        ByteArrayParameterData.create(null).build();
    }

    @Test
    public void createWithByteArrayOnly() {
        final ByteArrayParameterData param = ByteArrayParameterData.create(SAMPLE_DATA).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getData(), is(SAMPLE_DATA));
        assertThat(param.getContentType(), is(nullValue()));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(emptyMap()));
        assertThat(param.getName(), is(nullValue()));
    }

    @Test
    public void createWithCompleteDetails() {
        final ByteArrayParameterData param = ByteArrayParameterData.create(SAMPLE_DATA)
                .description("describe me")
                .metadata(singletonMap("mykey", "myvalue"))
                .name("name here")
                .contentType("text/plain")
                .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getData(), is(SAMPLE_DATA));
        assertThat(param.getContentType(), is("text/plain"));
        assertThat(param.getDescription(), is("describe me"));
        assertThat(param.getMetadata(), is(singletonMap("mykey", "myvalue")));
        assertThat(param.getName(), is("name here"));
    }
}
