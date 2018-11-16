/*
 *
 *  * Copyright 2018 ProximaX Limited
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package io.proximax.download;

import io.proximax.exceptions.RetrievalTimeoutException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;

public class DownloadResultDataTest {

    private DownloadResultData unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new DownloadResultData(
                () -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                    return new ByteArrayInputStream("hello there, old friend".getBytes());
                },
                null, null, 1L, null, null, null, null
        );
    }

    @Test
    public void shouldGetContentAsString() {
        final String result = unitUnderTest.getContentAsString(null);

        assertThat(result, is("hello there, old friend"));
    }

    @Test
    public void shouldGetContentAsStringWithTimeout() {
        final String result = unitUnderTest.getContentAsString(null, 200, TimeUnit.MILLISECONDS);

        assertThat(result, is("hello there, old friend"));
    }

    @Test(expected = RetrievalTimeoutException.class)
    public void failGetContentAsStringOnTimeout() {
        unitUnderTest.getContentAsString(null, 50, TimeUnit.MILLISECONDS);
    }

    @Test
    public void shouldGetContentAsByteArray() {
        final byte[] result = unitUnderTest.getContentAsByteArray();

        assertThat(ArrayUtils.toObject(result), is(arrayContaining(ArrayUtils.toObject("hello there, old friend".getBytes()))));
    }

    @Test
    public void shouldGetContentAsByteArrayWithTimeout() {
        final byte[] result = unitUnderTest.getContentAsByteArray(200, TimeUnit.MILLISECONDS);

        assertThat(ArrayUtils.toObject(result), is(arrayContaining(ArrayUtils.toObject("hello there, old friend".getBytes()))));
    }

    @Test(expected = RetrievalTimeoutException.class)
    public void failGetContentAsByteArrayOnTimeout() {
        unitUnderTest.getContentAsByteArray(50, TimeUnit.MILLISECONDS);
    }

    @Test
    public void shouldSaveToFile() throws IOException {
        final File tempFile = File.createTempFile("tmp" + System.currentTimeMillis(), "tmp");

        unitUnderTest.saveToFile(tempFile);

        assertThat(ArrayUtils.toObject(FileUtils.readFileToByteArray(tempFile)), is(arrayContaining(ArrayUtils.toObject("hello there, old friend".getBytes()))));
    }

    @Test
    public void shouldSaveToFileWithTimeout() throws IOException {
        final File tempFile = File.createTempFile("tmp" + System.currentTimeMillis(), "tmp");

        unitUnderTest.saveToFile(tempFile, 200, TimeUnit.MILLISECONDS);

        assertThat(ArrayUtils.toObject(FileUtils.readFileToByteArray(tempFile)), is(arrayContaining(ArrayUtils.toObject("hello there, old friend".getBytes()))));
    }

    @Test(expected = RetrievalTimeoutException.class)
    public void failSaveToFileOnTimeout() throws IOException {
        final File tempFile = File.createTempFile("tmp" + System.currentTimeMillis(), "tmp");

        unitUnderTest.saveToFile(tempFile, 50, TimeUnit.MILLISECONDS);
    }
}
