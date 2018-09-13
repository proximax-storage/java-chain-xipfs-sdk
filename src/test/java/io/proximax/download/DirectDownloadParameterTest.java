package io.proximax.download;

import io.proximax.model.PrivacyType;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.proximax.testsupport.Constants.PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.PUBLIC_KEY_2;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_PARTS;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_TOTAL_PART_COUNT;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class DirectDownloadParameterTest {

    private static final String SAMPLE_TRANSACTION_HASH = "F08E3C327DD5DE258EF20532F4D3C7638E9AC44885C34FDDC1A5740FD3C56EBB";
    private static final String SAMPLE_PRIVATE_KEY = "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C";
    private static final String SAMPLE_DATA_HASH = "QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf";
    private static final String SAMPLE_DIGEST = "eqwewqewqewqewqewq";

    @Test(expected = IllegalArgumentException.class)
    public void failWhenCreatingWithNullDataHash() {
        DirectDownloadParameter.createFromDataHash(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenCreatingWithInvalidDataHash() {
        DirectDownloadParameter.createFromDataHash("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test
    public void canBuildParamWithOnlyDataHash() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(SAMPLE_DATA_HASH).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getTransactionHash(), is(nullValue()));
        assertThat(param.getAccountPrivateKey(), is(nullValue()));
        assertThat(param.getDigest(), is(nullValue()));
        assertThat(param.getValidateDigest(), is(true));
        assertThat(param.getDataHash(), is(SAMPLE_DATA_HASH));
        assertThat(param.getPrivacyStrategy(), is(notNullValue()));
        assertThat(param.getPrivacyStrategy().getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
    }

    @Test
    public void canBuildParamWithDataHashAndDigest() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(SAMPLE_DATA_HASH, SAMPLE_DIGEST).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getTransactionHash(), is(nullValue()));
        assertThat(param.getAccountPrivateKey(), is(nullValue()));
        assertThat(param.getDigest(), is(SAMPLE_DIGEST));
        assertThat(param.getValidateDigest(), is(true));
        assertThat(param.getDataHash(), is(SAMPLE_DATA_HASH));
        assertThat(param.getPrivacyStrategy(), is(notNullValue()));
        assertThat(param.getPrivacyStrategy().getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenCreatingWithNullTransactionHash() {
        DirectDownloadParameter.createFromTransactionHash(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenInvalidAccountPrivateKey() {
        DirectDownloadParameter.createFromTransactionHash(SAMPLE_TRANSACTION_HASH, "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test
    public void canBuildParamWithOnlyTransactionHash() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(SAMPLE_TRANSACTION_HASH).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getTransactionHash(), is(SAMPLE_TRANSACTION_HASH));
        assertThat(param.getAccountPrivateKey(), is(nullValue()));
        assertThat(param.getDigest(), is(nullValue()));
        assertThat(param.getValidateDigest(), is(false));
        assertThat(param.getDataHash(), is(nullValue()));
        assertThat(param.getPrivacyStrategy(), is(notNullValue()));
        assertThat(param.getPrivacyStrategy().getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
    }

    @Test
    public void canBuildParamWithTransactionHashAndPrivateKey() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(SAMPLE_TRANSACTION_HASH, SAMPLE_PRIVATE_KEY).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getTransactionHash(), is(SAMPLE_TRANSACTION_HASH));
        assertThat(param.getAccountPrivateKey(), is(SAMPLE_PRIVATE_KEY));
        assertThat(param.getDigest(), is(nullValue()));
        assertThat(param.getValidateDigest(), is(false));
        assertThat(param.getDataHash(), is(nullValue()));
        assertThat(param.getPrivacyStrategy(), is(notNullValue()));
        assertThat(param.getPrivacyStrategy().getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
    }

    @Test
    public void canBuildParamWithTransactionHashAndPrivateKeyAndValidateDigest() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(SAMPLE_TRANSACTION_HASH, SAMPLE_PRIVATE_KEY, true).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getTransactionHash(), is(SAMPLE_TRANSACTION_HASH));
        assertThat(param.getAccountPrivateKey(), is(SAMPLE_PRIVATE_KEY));
        assertThat(param.getDigest(), is(nullValue()));
        assertThat(param.getValidateDigest(), is(true));
        assertThat(param.getDataHash(), is(nullValue()));
        assertThat(param.getPrivacyStrategy(), is(notNullValue()));
        assertThat(param.getPrivacyStrategy().getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
    }

    @Test
    public void canBuildParamWithTransactionHashAndValidateDigest() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(SAMPLE_TRANSACTION_HASH, true).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getTransactionHash(), is(SAMPLE_TRANSACTION_HASH));
        assertThat(param.getAccountPrivateKey(), is(nullValue()));
        assertThat(param.getDigest(), is(nullValue()));
        assertThat(param.getValidateDigest(), is(true));
        assertThat(param.getDataHash(), is(nullValue()));
        assertThat(param.getPrivacyStrategy(), is(notNullValue()));
        assertThat(param.getPrivacyStrategy().getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
    }

    @Test
    public void buildParamWithPrivacyStrategy() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(SAMPLE_DATA_HASH)
                .withPrivacyStrategy(SecuredWithNemKeysPrivacyStrategy.create(
                        "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C",
                        "E9F6576AF9F05E6738CD4E55B875A823CC75B4E8AE8984747DF7B235685C1577"
                ))
                .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getPrivacyStrategy(), is(notNullValue()));
        assertThat(param.getPrivacyStrategy().getPrivacyType(), is(PrivacyType.NEMKEYS.getValue()));
    }

    @Test
    public void shouldCreateWithPlainPrivacy() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(SAMPLE_DATA_HASH)
                .withPlainPrivacy()
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(PlainPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithNemKeysPrivacy() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(SAMPLE_DATA_HASH)
                .withNemKeysPrivacy(PRIVATE_KEY_1, PUBLIC_KEY_2)
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithNemKeysPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithPasswordPrivacy() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(SAMPLE_DATA_HASH)
                .withPasswordPrivacy("hdksahjkdhsakjhdsajhdkjhsajkdsbajjdhsajkhdjksahjkdahjkhdkjsahjdsadasdsadas")
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithPasswordPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithShamirSecretSharingMapStrategy() {
        final Map<Integer, byte[]> minimumSecretParts = new HashMap<>();
        minimumSecretParts.put(1, SHAMIR_SECRET_PARTS.get(1));
        minimumSecretParts.put(3, SHAMIR_SECRET_PARTS.get(3));
        minimumSecretParts.put(5, SHAMIR_SECRET_PARTS.get(5));
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(SAMPLE_DATA_HASH)
                .withShamirSecretSharingPrivacy(SHAMIR_SECRET_TOTAL_PART_COUNT, SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        minimumSecretParts)
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithShamirSecretSharingArrayStrategy() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(SAMPLE_DATA_HASH)
                .withShamirSecretSharingPrivacy(SHAMIR_SECRET_TOTAL_PART_COUNT, SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(1, SHAMIR_SECRET_PARTS.get(1)),
                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(3, SHAMIR_SECRET_PARTS.get(3)),
                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(5, SHAMIR_SECRET_PARTS.get(5)))
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithShamirSecretSharingListStrategy() {
        final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(SAMPLE_DATA_HASH)
                .withShamirSecretSharingPrivacy(SHAMIR_SECRET_TOTAL_PART_COUNT, SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        asList(
                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(1, SHAMIR_SECRET_PARTS.get(1)),
                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(3, SHAMIR_SECRET_PARTS.get(3)),
                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(5, SHAMIR_SECRET_PARTS.get(5))))
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
    }

}
