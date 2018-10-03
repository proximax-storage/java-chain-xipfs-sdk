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
package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.download.Downloader;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import io.proximax.model.PrivacyType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.proximax.testsupport.Constants.TEST_PASSWORD;
import static io.proximax.testsupport.Constants.TEST_TEXT_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_download_privacyStrategyIntegrationTest {

    private Downloader unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new Downloader(ConnectionConfig.createWithLocalIpfsConnection(
                new BlockchainNetworkConnection(
                        IntegrationTestConfig.getBlockchainNetworkType(),
                        IntegrationTestConfig.getBlockchainApiHost(),
                        IntegrationTestConfig.getBlockchainApiPort(),
                        IntegrationTestConfig.getBlockchainApiProtocol()),
                new IpfsConnection(
                        IntegrationTestConfig.getIpfsApiHost(),
                        IntegrationTestConfig.getIpfsApiPort())));
    }

    @Test
    public void shouldDownloadWithPlainPrivacy() throws IOException {
        final String transactionHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithPlainPrivacyStrategy",
                "transactionHash");
        final DownloadParameter param = DownloadParameter.create(transactionHash)
                .withPlainPrivacy()
                .build();

        final DownloadResult result = unitUnderTest.download(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result.getData().getByteStream())),
                is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
    }

    @Test
    public void shouldDownloadWithNemKeysPrivacy() throws IOException {
        final String transactionHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy",
                "transactionHash");
        final DownloadParameter param = DownloadParameter.create(transactionHash)
                .withNemKeysPrivacy(IntegrationTestConfig.getPrivateKey1(), IntegrationTestConfig.getPublicKey2())
                .build();

        final DownloadResult result = unitUnderTest.download(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.NEMKEYS.getValue()));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result.getData().getByteStream())),
                is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
    }

    @Test(expected = IOException.class)
    public void failDownloadWithIncorrectNemKeys() throws IOException {
        final String transactionHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy",
                "transactionHash");
        final DownloadParameter param = DownloadParameter.create(transactionHash)
                .withNemKeysPrivacy(IntegrationTestConfig.getPrivateKey1(), IntegrationTestConfig.getPublicKey1())
                .build();

        final DownloadResult result = unitUnderTest.download(param);

        IOUtils.toByteArray(result.getData().getByteStream());
    }

    @Test
    public void shouldDownloadWithPasswordPrivacy() throws IOException {
        final String transactionHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
                "transactionHash");
        final DownloadParameter param = DownloadParameter.create(transactionHash)
                .withPasswordPrivacy(TEST_PASSWORD)
                .build();

        final DownloadResult result = unitUnderTest.download(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.PASSWORD.getValue()));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result.getData().getByteStream())),
                is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
    }

    @Test(expected = IOException.class)
    public void failDownloadWithIncorrectPassword() throws IOException {
        final String transactionHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
                "transactionHash");
        final DownloadParameter param = DownloadParameter.create(transactionHash)
                .withPasswordPrivacy(TEST_PASSWORD + "dummy")
                .build();

        final DownloadResult result = unitUnderTest.download(param);

        IOUtils.toByteArray(result.getData().getByteStream());
    }

    // TODO - revisit shamir secret sharing implementation that works cross-sdk
//	@Test
//	public void shouldDownloadWithShamirSecretSharingPrivacy() throws IOException {
//		final String transactionHash = TestDataRepository.getData(
//				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy",
//				"transactionHash");
//		final DownloadParameter param = DownloadParameter.create(transactionHash)
//				.withShamirSecretSharingPrivacy(
//						TEST_SHAMIR_SECRET_TOTAL_SHARES,
//						TEST_SHAMIR_SECRET_THRESHOLD,
//						TEST_SHAMIR_SECRET_SHARES)
//				.build();
//
//		final DownloadResult result = unitUnderTest.download(param);
//
//		assertThat(result, is(notNullValue()));
//		assertThat(result.getPrivacyType(), is(PrivacyType.SHAMIR.getValue()));
//		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result.getData().getByteStream())),
//				is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
//	}
}
