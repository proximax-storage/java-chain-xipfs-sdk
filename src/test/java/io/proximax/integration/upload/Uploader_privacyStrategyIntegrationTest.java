package io.proximax.integration.upload;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.model.PrivacyType;
import io.proximax.testsupport.IntegrationTestProperties;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.TEST_PASSWORD;
import static io.proximax.testsupport.Constants.TEST_SHAMIR_SECRET_SHARES;
import static io.proximax.testsupport.Constants.TEST_SHAMIR_SECRET_THRESHOLD;
import static io.proximax.testsupport.Constants.TEST_SHAMIR_SECRET_TOTAL_SHARES;
import static io.proximax.testsupport.Constants.TEST_TEXT_FILE;
import static io.proximax.testsupport.TestDataRepository.logAndSaveResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_privacyStrategyIntegrationTest {

    private Uploader unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new Uploader(ConnectionConfig.create(
                new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST,
                        IntegrationTestProperties.getBlockchainRestUrl()),
                new IpfsConnection(IntegrationTestProperties.getIpfsMultiAddress())));
    }

    @Test
    public void shouldUploadFileWithPlainPrivacyStrategy() throws Exception {
        final UploadParameter param = UploadParameter
                .createForFileUpload(TEST_TEXT_FILE, IntegrationTestProperties.getPrivateKey1())
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getData().getDataHash(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithPlainPrivacyStrategy");
    }

    @Test
    public void shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy() throws Exception {
        final UploadParameter param = UploadParameter
                .createForFileUpload(TEST_TEXT_FILE, IntegrationTestProperties.getPrivateKey1())
                .withNemKeysPrivacy(IntegrationTestProperties.getPrivateKey1(), IntegrationTestProperties.getPublicKey2())
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.NEMKEYS.getValue()));
        assertThat(result.getData().getDataHash(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy");
    }

    @Test
    public void shouldUploadFileWithSecuredWithPasswordPrivacyStrategy() throws Exception {
        final UploadParameter param = UploadParameter
                .createForFileUpload(TEST_TEXT_FILE, IntegrationTestProperties.getPrivateKey1())
                .withPasswordPrivacy(TEST_PASSWORD)
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.PASSWORD.getValue()));
        assertThat(result.getData().getDataHash(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithSecuredWithPasswordPrivacyStrategy");
    }

    @Test
    public void shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy() throws Exception {
        final UploadParameter param = UploadParameter
                .createForFileUpload(TEST_TEXT_FILE, IntegrationTestProperties.getPrivateKey1())
                .withShamirSecretSharing(
                        TEST_SHAMIR_SECRET_TOTAL_SHARES,
                        TEST_SHAMIR_SECRET_THRESHOLD,
                        TEST_SHAMIR_SECRET_SHARES)
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.SHAMIR.getValue()));
        assertThat(result.getData().getDataHash(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy");
    }

}
