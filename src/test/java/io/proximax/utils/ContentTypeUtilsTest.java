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
package io.proximax.utils;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static io.proximax.testsupport.Constants.TEST_AUDIO_MP3_FILE;
import static io.proximax.testsupport.Constants.TEST_HTML_FILE;
import static io.proximax.testsupport.Constants.TEST_IMAGE_PNG_FILE;
import static io.proximax.testsupport.Constants.TEST_PDF_FILE_2;
import static io.proximax.testsupport.Constants.TEST_TEXT_FILE;
import static io.proximax.testsupport.Constants.TEST_VIDEO_MOV_FILE;
import static io.proximax.testsupport.Constants.TEST_VIDEO_MP4_FILE;
import static io.proximax.testsupport.Constants.TEST_ZIP_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ContentTypeUtilsTest {

    private ContentTypeUtils unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new ContentTypeUtils();
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenText() throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(TEST_TEXT_FILE);
        final String result = unitUnderTest.detectContentType(fileInputStream).blockingFirst();

        assertThat(result, is("text/plain"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenHtml() throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(TEST_HTML_FILE);
        final String result = unitUnderTest.detectContentType(fileInputStream).blockingFirst();

        assertThat(result, is("text/html"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenMov() throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(TEST_VIDEO_MOV_FILE);
        final String result = unitUnderTest.detectContentType(fileInputStream).blockingFirst();

        assertThat(result, is("video/quicktime"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenImage() throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(TEST_IMAGE_PNG_FILE);
        final String result = unitUnderTest.detectContentType(fileInputStream).blockingFirst();

        assertThat(result, is("image/png"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenPdf() throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(TEST_PDF_FILE_2);
        final String result = unitUnderTest.detectContentType(fileInputStream).blockingFirst();

        assertThat(result, is("application/pdf"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenZip() throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(TEST_ZIP_FILE);
        final String result = unitUnderTest.detectContentType(fileInputStream).blockingFirst();

        assertThat(result, is("application/zip"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenMp3() throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(TEST_AUDIO_MP3_FILE);
        final String result = unitUnderTest.detectContentType(fileInputStream).blockingFirst();

        assertThat(result, is("audio/mpeg"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenMp4() throws IOException {
        final FileInputStream fileInputStream = new FileInputStream(TEST_VIDEO_MP4_FILE);
        final String result = unitUnderTest.detectContentType(fileInputStream).blockingFirst();

        assertThat(result, is("video/mp4"));
    }
}
