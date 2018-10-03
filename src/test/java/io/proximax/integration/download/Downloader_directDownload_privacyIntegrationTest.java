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
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.proximax.testsupport.Constants.TEST_PASSWORD;
import static io.proximax.testsupport.Constants.TEST_TEXT_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_directDownload_privacyIntegrationTest {

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
    public void shouldDirectDownloadUsingTransactionHashWithPlainPrivacy() throws IOException {
        final String transactionHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithPlainPrivacyStrategy",
                "transactionHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromTransactionHash(transactionHash)
                        .withPlainPrivacy()
                        .build();

        final InputStream result = unitUnderTest.directDownload(param);

        assertThat(result, is(notNullValue()));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
    }

    @Test
    public void shouldDirectDownloadUsingTransactionHashWithNemKeysPrivacy() throws IOException {
        final String transactionHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy",
                "transactionHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromTransactionHash(transactionHash)
                        .withNemKeysPrivacy(IntegrationTestConfig.getPrivateKey1(), IntegrationTestConfig.getPublicKey2())
                        .build();

        final InputStream result = unitUnderTest.directDownload(param);

        assertThat(result, is(notNullValue()));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
                is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
    }

    @Test(expected = IOException.class)
    public void failDirectDownloadUsingTransactionHashWithIncorrectNemKeys() throws IOException {
        final String transactionHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy",
                "transactionHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromTransactionHash(transactionHash)
                        .withNemKeysPrivacy(IntegrationTestConfig.getPrivateKey1(), IntegrationTestConfig.getPublicKey1())
                        .build();

        final InputStream result = unitUnderTest.directDownload(param);

        IOUtils.toByteArray(result);
    }

    @Test
    public void shouldDirectDownloadUsingTransactionHashWithPasswordPrivacy() throws IOException {
        final String transactionHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
                "transactionHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromTransactionHash(transactionHash)
                        .withPasswordPrivacy(TEST_PASSWORD)
                        .build();

        final InputStream result = unitUnderTest.directDownload(param);

        assertThat(result, is(notNullValue()));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
    }

    @Test(expected = IOException.class)
    public void failDirectDownloadUsingTransactionHashWithIncorrectPassword() throws IOException {
        final String transactionHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
                "transactionHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromTransactionHash(transactionHash)
                        .withPasswordPrivacy(TEST_PASSWORD + "dummy")
                        .build();

        final InputStream result = unitUnderTest.directDownload(param);

        IOUtils.toByteArray(result);
    }

    // TODO - revisit shamir secret sharing implementation that works cross-sdk
//	@Test
//	public void shouldDirectDownloadUsingTransactionHashWithShamirSecretSharingPrivacy() throws IOException {
//		final String transactionHash = TestDataRepository.getData(
//				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy",
//				"transactionHash");
//		final DirectDownloadParameter param =
//				DirectDownloadParameter.createFromTransactionHash(transactionHash)
//						.withShamirSecretSharingPrivacy(
//								TEST_SHAMIR_SECRET_TOTAL_SHARES,
//								TEST_SHAMIR_SECRET_THRESHOLD,
//								TEST_SHAMIR_SECRET_SHARES)
//						.build();
//
//		final InputStream result = unitUnderTest.directDownload(param);
//
//		assertThat(result, is(notNullValue()));
//		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
//	}

    @Test
    public void shouldDirectDownloadUsingDataHashWithPlainPrivacy() throws IOException {
        final String dataHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithPlainPrivacyStrategy",
                "dataHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromDataHash(dataHash)
                        .withPlainPrivacy()
                        .build();

        final InputStream result = unitUnderTest.directDownload(param);

        assertThat(result, is(notNullValue()));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
    }

    @Test
    public void shouldDirectDownloadUsingDataHashWithNemKeysPrivacy() throws IOException {
        final String dataHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy",
                "dataHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromDataHash(dataHash)
                        .withNemKeysPrivacy(IntegrationTestConfig.getPrivateKey1(), IntegrationTestConfig.getPublicKey2())
                        .build();

        final InputStream result = unitUnderTest.directDownload(param);

        assertThat(result, is(notNullValue()));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)),
                is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
    }

    @Test(expected = IOException.class)
    public void failDirectDownloadUsingDataHashWhenIncorrectNemKeys() throws IOException {
        final String dataHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
                "dataHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromDataHash(dataHash)
                        .withNemKeysPrivacy(IntegrationTestConfig.getPrivateKey1(), IntegrationTestConfig.getPublicKey1())
                        .build();

        final InputStream result = unitUnderTest.directDownload(param);

        IOUtils.toByteArray(result);
    }


    @Test
    public void shouldDirectDownloadUsingDataHashWithPasswordPrivacy() throws IOException {
        final String dataHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
                "dataHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromDataHash(dataHash)
                        .withPasswordPrivacy(TEST_PASSWORD)
                        .build();

        final InputStream result = unitUnderTest.directDownload(param);

        assertThat(result, is(notNullValue()));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
    }

    @Test(expected = IOException.class)
    public void failDirectDownloadUsingDataHashWhenIncorrectPassword() throws IOException {
        final String dataHash = TestDataRepository.getData(
                "Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithPasswordPrivacyStrategy",
                "dataHash");
        final DirectDownloadParameter param =
                DirectDownloadParameter.createFromDataHash(dataHash)
                        .withPasswordPrivacy(TEST_PASSWORD + "dummy")
                        .build();

        final InputStream result = unitUnderTest.directDownload(param);

        IOUtils.toByteArray(result);
    }

    // TODO - revisit shamir secret sharing implementation that works cross-sdk
//	@Test
//	public void shouldDirectDownloadUsingDataHashWithShamirSecretSharingPrivacy() throws IOException {
//		final String dataHash = TestDataRepository.getData(
//				"Uploader_privacyStrategyIntegrationTest.shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy",
//				"dataHash");
//		final DirectDownloadParameter param =
//				DirectDownloadParameter.createFromDataHash(dataHash)
//						.withShamirSecretSharingPrivacy(
//								TEST_SHAMIR_SECRET_TOTAL_SHARES,
//								TEST_SHAMIR_SECRET_THRESHOLD,
//								TEST_SHAMIR_SECRET_SHARES)
//						.build();
//
//		final InputStream result = unitUnderTest.directDownload(param);
//
//		assertThat(result, is(notNullValue()));
//		assertThat(ArrayUtils.toObject(IOUtils.toByteArray(result)), is(arrayContaining(ArrayUtils.toObject((FileUtils.readFileToByteArray(TEST_TEXT_FILE))))));
//	}

}
