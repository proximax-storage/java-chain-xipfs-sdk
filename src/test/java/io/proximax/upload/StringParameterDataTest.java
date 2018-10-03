/*
 * Copyright 2018 ProximaX Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.proximax.upload;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
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
    public void createWithStringOnly() throws IOException {
        final StringParameterData param = StringParameterData.create(SAMPLE_DATA);

        assertThat(param, is(notNullValue()));
        assertThat(param.getString(), is(SAMPLE_DATA));
        assertThat(new String(IOUtils.toByteArray(param.getByteStream())), is(SAMPLE_DATA));
        assertThat(param.getContentType(), is(nullValue()));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(nullValue()));
        assertThat(param.getName(), is(nullValue()));
    }

    @Test
    public void createWithCompleteDetails() throws IOException {
        final StringParameterData param = StringParameterData.create(SAMPLE_DATA, "UTF-8",
                "describe me", "name here", "text/plain", singletonMap("mykey", "myvalue"));

        assertThat(param, is(notNullValue()));
        assertThat(param.getString(), is(SAMPLE_DATA));
        assertThat(new String(IOUtils.toByteArray(param.getByteStream()), "UTF-8"), is(SAMPLE_DATA));
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
