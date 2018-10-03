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

import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.proximax.testsupport.Constants.TEST_HTML_FILE;
import static io.proximax.testsupport.Constants.TEST_IMAGE_PNG_FILE;
import static io.proximax.testsupport.Constants.TEST_PATH_FILE;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class FilesAsZipParameterDataTest {

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullFiles() throws IOException {
        FilesAsZipParameterData.create((List<File>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenEmptyFiles() throws IOException {
        FilesAsZipParameterData.create(emptyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenSomeFilesIsDirectoryFiles() throws IOException {
        FilesAsZipParameterData.create(asList(TEST_IMAGE_PNG_FILE, TEST_HTML_FILE, TEST_PATH_FILE));
    }

    @Test
    public void createWithFilesOnly() throws IOException {
        final FilesAsZipParameterData param = FilesAsZipParameterData.create(singletonList(TEST_IMAGE_PNG_FILE));

        assertThat(param, is(notNullValue()));
        assertThat(param.getFiles(), is(singletonList(TEST_IMAGE_PNG_FILE)));
        assertThat(param.getByteStream(), is(notNullValue()));
        assertThat(param.getContentType(), is("application/zip"));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(nullValue()));
        assertThat(param.getName(), is(nullValue()));
    }

    @Test
    public void createWithCompleteDetails() throws IOException {
        final FilesAsZipParameterData param = FilesAsZipParameterData.create(singletonList(TEST_IMAGE_PNG_FILE),
                "describe me", "name here", singletonMap("mykey", "myvalue"));

        assertThat(param, is(notNullValue()));
        assertThat(param.getFiles(), is(singletonList(TEST_IMAGE_PNG_FILE)));
        assertThat(param.getByteStream(), is(notNullValue()));
        assertThat(param.getContentType(), is("application/zip"));
        assertThat(param.getDescription(), is("describe me"));
        assertThat(param.getMetadata(), is(singletonMap("mykey", "myvalue")));
        assertThat(param.getName(), is("name here"));
    }
}
