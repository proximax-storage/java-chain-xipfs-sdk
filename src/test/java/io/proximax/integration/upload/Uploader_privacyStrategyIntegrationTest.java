package io.proximax.integration.upload;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.model.PrivacyType;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.integration.TestDataRepository.logAndSaveResult;
import static io.proximax.testsupport.Constants.TEST_PASSWORD;
import static io.proximax.testsupport.Constants.TEST_SHAMIR_SECRET_SHARES;
import static io.proximax.testsupport.Constants.TEST_SHAMIR_SECRET_THRESHOLD;
import static io.proximax.testsupport.Constants.TEST_SHAMIR_SECRET_TOTAL_SHARES;
import static io.proximax.testsupport.Constants.TEST_TEXT_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_privacyStrategyIntegrationTest {

    private Uploader unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new Uploader(ConnectionConfig.createWithLocalIpfsConnection(
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
    public void shouldUploadFileWithPlainPrivacyStrategy() {
        final UploadParameter param = UploadParameter
                .createForFileUpload(TEST_TEXT_FILE, IntegrationTestConfig.getPrivateKey1())
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getData().getDataHash(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithPlainPrivacyStrategy");
    }

    @Test
    public void shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy() {
        final UploadParameter param = UploadParameter
                .createForFileUpload(TEST_TEXT_FILE, IntegrationTestConfig.getPrivateKey1())
                .withNemKeysPrivacy(IntegrationTestConfig.getPrivateKey1(), IntegrationTestConfig.getPublicKey2())
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.NEMKEYS.getValue()));
        assertThat(result.getData().getDataHash(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy");
    }

    @Test
    public void shouldUploadFileWithSecuredWithPasswordPrivacyStrategy() {
        final UploadParameter param = UploadParameter
                .createForFileUpload(TEST_TEXT_FILE, IntegrationTestConfig.getPrivateKey1())
                .withPasswordPrivacy(TEST_PASSWORD)
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.PASSWORD.getValue()));
        assertThat(result.getData().getDataHash(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithSecuredWithPasswordPrivacyStrategy");
    }

    // TODO - revisit shamir secret sharing implementation that works cross-sdk
//    @Test
//    public void shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy() {
//        final UploadParameter param = UploadParameter
//                .createForFileUpload(TEST_TEXT_FILE, IntegrationTestConfig.getPrivateKey1())
//                .withShamirSecretSharing(
//                        TEST_SHAMIR_SECRET_TOTAL_SHARES,
//                        TEST_SHAMIR_SECRET_THRESHOLD,
//                        TEST_SHAMIR_SECRET_SHARES)
//                .build();
//
//        final UploadResult result = unitUnderTest.upload(param);
//
//        assertThat(result, is(notNullValue()));
//        assertThat(result.getTransactionHash(), is(notNullValue()));
//        assertThat(result.getPrivacyType(), is(PrivacyType.SHAMIR.getValue()));
//        assertThat(result.getData().getDataHash(), is(notNullValue()));
//
//        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy");
//    }

}
