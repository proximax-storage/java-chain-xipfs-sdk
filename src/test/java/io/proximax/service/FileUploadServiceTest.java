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
package io.proximax.service;

import io.proximax.service.client.IpfsClient;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;

import static io.proximax.testsupport.Constants.TEST_PATH_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;

public class FileUploadServiceTest {
    private static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    private static final String SAMPLE_DATAHASH = "QmTxpkEitAczbM5S4uZG3zoDToSDNQZQUV4vxBsW9Q1Nhh";

    private FileUploadService unitUnderTest;

    @Mock
    private IpfsClient mockIpfsClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new FileUploadService(mockIpfsClient);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnAddByteStreamWhenNullDataList() {
        unitUnderTest.uploadByteStream(null);
    }

    @Test
    public void shouldReturnDataHashOnAddByteStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(SAMPLE_DATA);
        given(mockIpfsClient.addByteStream(byteArrayInputStream))
                .willReturn(Observable.just(SAMPLE_DATAHASH));

        final FileUploadResponse ipfsUploadResponse =
                unitUnderTest.uploadByteStream(byteArrayInputStream).blockingFirst();

        assertThat(ipfsUploadResponse, is(notNullValue()));
        assertThat(ipfsUploadResponse.getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponse.getTimestamp(), is(notNullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnUploadPathWhenNullDataList() {
        unitUnderTest.uploadPath(null);
    }

    @Test
    public void shouldReturnDataHashOnUploadPath() {
        given(mockIpfsClient.addPath(TEST_PATH_FILE)).willReturn(Observable.just(SAMPLE_DATAHASH));

        final FileUploadResponse ipfsUploadResponse =
                unitUnderTest.uploadPath(TEST_PATH_FILE).blockingFirst();

        assertThat(ipfsUploadResponse, is(notNullValue()));
        assertThat(ipfsUploadResponse.getDataHash(), is(SAMPLE_DATAHASH));
        assertThat(ipfsUploadResponse.getTimestamp(), is(notNullValue()));
    }
}
