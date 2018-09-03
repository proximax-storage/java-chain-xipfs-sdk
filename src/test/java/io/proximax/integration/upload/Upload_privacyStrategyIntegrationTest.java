package io.proximax.integration.upload;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.model.BlockchainNetwork;
import io.proximax.model.PrivacyType;
import io.proximax.upload.FileParameterData;
import io.proximax.upload.Upload;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
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
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Upload_privacyStrategyIntegrationTest {

    private Upload unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new Upload(ConnectionConfig.create(
                new BlockchainNetworkConnection(BlockchainNetwork.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
                new IpfsConnection(IPFS_MULTI_ADDRESS)));
    }

    @Test
    public void shouldUploadFileWithPlainPrivacyStrategy() throws Exception {
        final UploadParameter param = UploadParameter.create(PRIVATE_KEY_1, PUBLIC_KEY_2)
                .addFile(FileParameterData.create(SMALL_FILE).build())
                .plainPrivacy("plain")
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getDigest(), is(notNullValue()));
        assertThat(result.getRootDataHash(), is(notNullValue()));
        assertThat(result.getRootData().getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getRootData().getPrivacySearchTag(), is("plain"));
        assertThat(result.getRootData().getDataList(), hasSize(1));
        assertThat(result.getRootData().getDataList().get(0).getDataHash(), is(notNullValue()));
        assertThat(result.getRootData().getDataList().get(0).getDigest(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithPlainPrivacyStrategy");
    }

    @Test
    public void shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy() throws Exception {
        final UploadParameter param = UploadParameter.create(PRIVATE_KEY_1, PUBLIC_KEY_2)
                .addFile(FileParameterData.create(SMALL_FILE).build())
                .securedWithNemKeysPrivacyStrategy("nem")
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getDigest(), is(notNullValue()));
        assertThat(result.getRootDataHash(), is(notNullValue()));
        assertThat(result.getRootData(), is(notNullValue()));
        assertThat(result.getRootData().getPrivacyType(), is(PrivacyType.NEMKEYS.getValue()));
        assertThat(result.getRootData().getPrivacySearchTag(), is("nem"));
        assertThat(result.getRootData().getDataList(), hasSize(1));
        assertThat(result.getRootData().getDataList().get(0).getDataHash(), is(notNullValue()));
        assertThat(result.getRootData().getDataList().get(0).getDigest(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithSecuredWithNemKeysPrivacyStrategy");
    }

    @Test
    public void shouldUploadFileWithSecuredWithPasswordPrivacyStrategy() throws Exception {
        final UploadParameter param = UploadParameter.create(PRIVATE_KEY_1, PUBLIC_KEY_2)
                .addFile(FileParameterData.create(SMALL_FILE).build())
                .securedWithPasswordPrivacyStrategy(PASSWORD, "pass")
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getDigest(), is(notNullValue()));
        assertThat(result.getRootDataHash(), is(notNullValue()));
        assertThat(result.getRootData(), is(notNullValue()));
        assertThat(result.getRootData().getPrivacyType(), is(PrivacyType.PASSWORD.getValue()));
        assertThat(result.getRootData().getPrivacySearchTag(), is("pass"));
        assertThat(result.getRootData().getDataList(), hasSize(1));
        assertThat(result.getRootData().getDataList().get(0).getDataHash(), is(notNullValue()));
        assertThat(result.getRootData().getDataList().get(0).getDigest(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithSecuredWithPasswordPrivacyStrategy");
    }

    @Test
    public void shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy() throws Exception {
        final UploadParameter param = UploadParameter.create(PRIVATE_KEY_1, PUBLIC_KEY_2)
                .addFile(FileParameterData.create(SMALL_FILE).build())
                .securedWithShamirSecretSharingPrivacyStrategy(
                        SHAMIR_SECRET_TOTAL_PART_COUNT,
                        SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        "shamir",
                        SHAMIR_SECRET_PARTS)
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getDigest(), is(notNullValue()));
        assertThat(result.getRootDataHash(), is(notNullValue()));
        assertThat(result.getRootData(), is(notNullValue()));
        assertThat(result.getRootData().getPrivacyType(), is(PrivacyType.SHAMIR.getValue()));
        assertThat(result.getRootData().getPrivacySearchTag(), is("shamir"));
        assertThat(result.getRootData().getDataList(), hasSize(1));
        assertThat(result.getRootData().getDataList().get(0).getDataHash(), is(notNullValue()));
        assertThat(result.getRootData().getDataList().get(0).getDigest(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadFileWithSecuredWithShamirSecretSharingPrivacyStrategy");
    }

}
