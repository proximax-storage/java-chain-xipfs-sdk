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
    public void testJSNemKeys() throws IOException {
        final String transactionHash = "06AC4209AAFC5B9FE5CE2C06DA16E21DE5E5753E24AE9B65A1CDB620FFCDA6DA";
        final DownloadParameter param = DownloadParameter.create(transactionHash)
                .withNemKeysPrivacy("97226FCCBD876D399BA2A70E640AD2C2C97AD5CE57A40EE9455C226D3C39AD49",
                        "D1869362F4FAA5F683AEF78FC0D6E04B976833000F3958862A09CC7B6DF347C2")
                .build();

        final DownloadResult result = unitUnderTest.download(param);

        System.out.println(new String(IOUtils.toByteArray(result.getData().getByteStream())));
    }


    @Test
    public void testJSPassword() throws IOException {
        final String transactionHash = "E853948733C1DD97641BEF6D7641FA70ECFDB63B11DE9EA50A2531CD2D0A165F";
        final DownloadParameter param = DownloadParameter.create(transactionHash)
                .withPasswordPrivacy("ProximaXIsLit")
                .build();

        final DownloadResult result = unitUnderTest.download(param);

        System.out.println(new String(IOUtils.toByteArray(result.getData().getByteStream())));
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
