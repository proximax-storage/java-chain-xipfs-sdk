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

package io.proximax.service;

import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.service.client.IpfsClient;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

public class FileDownloadServiceTest {

    private static final String DUMMY_DATA_HASH = "Qmdahdksadjksahjk";
    private static final InputStream DUMMY_DOWNLOADED_DATA_STREAM =
            new ByteArrayInputStream("dopsaipdlsnlxnz,cn,zxnclznxlnldsaldslkaj;as.".getBytes());
    private static final String DUMMY_DIGEST = "iowuqoieuqowueoiqw";
    private static final InputStream DUMMY_DECRYPTED_DATA_STREAM =
            new ByteArrayInputStream("dsajhjdhaskhdksahkdsaljkjlxnzcm,nxz".getBytes());

    private FileDownloadService unitUnderTest;

    @Mock
    private IpfsClient mockIpfsClient;

    @Mock
    private DigestUtils mockDigestUtils;

    @Mock
    private PrivacyStrategy mockPrivacyStrategy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new FileDownloadService(mockIpfsClient, mockDigestUtils);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullDataHash() {
        unitUnderTest.getByteStream(null, mockPrivacyStrategy, null);
    }

    @Test
    public void shouldGetByteStream() {
        given(mockIpfsClient.getByteStream(DUMMY_DATA_HASH)).willReturn(Observable.just(DUMMY_DOWNLOADED_DATA_STREAM));

        final InputStream result =
                unitUnderTest.getByteStream(DUMMY_DATA_HASH, null, null)
                        .blockingFirst();

        assertThat(result, is(DUMMY_DOWNLOADED_DATA_STREAM));
    }

    @Test
    public void shouldGetByteStreamWithPrivacyStrategy() {
        given(mockIpfsClient.getByteStream(DUMMY_DATA_HASH)).willReturn(Observable.just(DUMMY_DOWNLOADED_DATA_STREAM));
        given(mockPrivacyStrategy.decryptStream(DUMMY_DOWNLOADED_DATA_STREAM)).willReturn(DUMMY_DECRYPTED_DATA_STREAM);

        final InputStream result =
                unitUnderTest.getByteStream(DUMMY_DATA_HASH, mockPrivacyStrategy, null)
                        .blockingFirst();

        assertThat(result, is(DUMMY_DECRYPTED_DATA_STREAM));
    }

    @Test
    public void shouldGetByteStreamWithDigest() {
        given(mockIpfsClient.getByteStream(DUMMY_DATA_HASH)).willReturn(Observable.just(DUMMY_DOWNLOADED_DATA_STREAM));
        given(mockDigestUtils.validateDigest(DUMMY_DOWNLOADED_DATA_STREAM, DUMMY_DIGEST)).willReturn(Observable.just(true));

        final InputStream result =
                unitUnderTest.getByteStream(DUMMY_DATA_HASH, null, DUMMY_DIGEST)
                        .blockingFirst();

        assertThat(result, is(DUMMY_DOWNLOADED_DATA_STREAM));
    }

    @Test(expected = RuntimeException.class)
    public void failWhenDigestDoNotMatch() {
        given(mockIpfsClient.getByteStream(DUMMY_DATA_HASH)).willReturn(Observable.just(DUMMY_DOWNLOADED_DATA_STREAM));
        given(mockDigestUtils.validateDigest(DUMMY_DOWNLOADED_DATA_STREAM, DUMMY_DIGEST)).willThrow(RuntimeException.class);

        unitUnderTest.getByteStream(DUMMY_DATA_HASH, null, DUMMY_DIGEST)
                .blockingFirst();
    }
}
