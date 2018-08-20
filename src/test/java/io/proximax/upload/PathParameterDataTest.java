package io.proximax.upload;

import org.junit.Test;

import java.io.IOException;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.testsupport.Constants.HTML_FILE;
import static io.proximax.testsupport.Constants.PATH_FILE;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class PathParameterDataTest {

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPath() {
        PathParameterData.create(null).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNotDirectory() {
        PathParameterData.create(HTML_FILE).build();
    }

    @Test
    public void createWithPathOnly() {
        final PathParameterData param = PathParameterData.create(PATH_FILE).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getPath(), is(PATH_FILE));
        assertThat(param.getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(emptyMap()));
        assertThat(param.getName(), is(nullValue()));
    }

    @Test
    public void createWithCompleteDetails() throws IOException {
        final PathParameterData param = PathParameterData.create(PATH_FILE)
                .description("describe me")
                .metadata(singletonMap("mykey", "myvalue"))
                .name("name here")
                .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getPath(), is(PATH_FILE));
        assertThat(param.getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
        assertThat(param.getDescription(), is("describe me"));
        assertThat(param.getMetadata(), is(singletonMap("mykey", "myvalue")));
        assertThat(param.getName(), is("name here"));
    }
}
