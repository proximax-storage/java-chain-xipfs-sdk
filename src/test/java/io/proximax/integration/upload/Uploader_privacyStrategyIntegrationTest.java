package io.proximax.integration.upload;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.model.PrivacyType;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.PASSWORD;
import static io.proximax.testsupport.Constants.PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.PUBLIC_KEY_2;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_PARTS;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_TOTAL_PART_COUNT;
import static io.proximax.testsupport.Constants.SMALL_FILE;
import static io.proximax.testsupport.TestHelper.logAndSaveResult;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_privacyStrategyIntegrationTest {

    private Uploader unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new Uploader(ConnectionConfig.create(
                new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
                new IpfsConnection(IPFS_MULTI_ADDRESS)));
    }

    @Test
    public void shouldUploadFileWithPlainPrivacyStrategy() throws Exception {
        final UploadParameter param = UploadParameter.createForFileUpload(SMALL_FILE, PRIVATE_KEY_1)
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
        final UploadParameter param = UploadParameter.createForFileUpload(SMALL_FILE, PRIVATE_KEY_1)
                .securedWithNemKeysPrivacy(PRIVATE_KEY_1, PUBLIC_KEY_2)
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
        final UploadParameter param = UploadParameter.createForFileUpload(SMALL_FILE, PRIVATE_KEY_1)
                .securedWithPasswordPrivacy(PASSWORD)
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
        final UploadParameter param = UploadParameter.createForFileUpload(SMALL_FILE, PRIVATE_KEY_1)
                .securedWithShamirSecretSharing(
                        SHAMIR_SECRET_TOTAL_PART_COUNT,
                        SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        SHAMIR_SECRET_PARTS)
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.SHAMIR.getValue()));
        assertThat(result.getData().getDataHash(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy");
    }

}
