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

import org.junit.Test;

import java.io.IOException;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.testsupport.Constants.TEST_HTML_FILE;
import static io.proximax.testsupport.Constants.TEST_PATH_FILE;
import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class PathParameterDataTest {

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPath() {
        PathParameterData.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNotDirectory() {
        PathParameterData.create(TEST_HTML_FILE);
    }

    @Test
    public void createWithPathOnly() {
        final PathParameterData param = PathParameterData.create(TEST_PATH_FILE);

        assertThat(param, is(notNullValue()));
        assertThat(param.getPath(), is(TEST_PATH_FILE));
        assertThat(param.getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(nullValue()));
        assertThat(param.getName(), is(nullValue()));
    }

    @Test
    public void createWithCompleteDetails() throws IOException {
        final PathParameterData param = PathParameterData.create(TEST_PATH_FILE, "describe me",
                "name here", singletonMap("mykey", "myvalue"));

        assertThat(param, is(notNullValue()));
        assertThat(param.getPath(), is(TEST_PATH_FILE));
        assertThat(param.getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
        assertThat(param.getDescription(), is("describe me"));
        assertThat(param.getMetadata(), is(singletonMap("mykey", "myvalue")));
        assertThat(param.getName(), is("name here"));
    }
}
