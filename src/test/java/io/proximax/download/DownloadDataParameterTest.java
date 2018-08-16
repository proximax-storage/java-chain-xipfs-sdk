package io.proximax.download;

import io.proximax.model.PrivacyType;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategyTest.SECRET_MINIMUM_PART_COUNT_TO_BUILD;
import static io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategyTest.SECRET_PARTS;
import static io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategyTest.SECRET_TOTAL_PART_COUNT;
import static io.proximax.testsupport.Constants.PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.PUBLIC_KEY_2;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class DownloadDataParameterTest {

    private static final String SAMPLE_DATA_HASH = "QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf";
    private static final String SAMPLE_DIGEST = "eqwewqewqewqewqewq";

    @Test(expected = IllegalArgumentException.class)
    public void failWhenCreatingWithNullDataHash() {
        DownloadDataParameter.create(null);
    }

    @Test
    public void canBuildParamWithOnlyDataHash() {
        final DownloadDataParameter param = DownloadDataParameter.create(SAMPLE_DATA_HASH).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getDigest(), is(nullValue()));
        assertThat(param.getDataHash(), is(SAMPLE_DATA_HASH));
        assertThat(param.getPrivacyStrategy(), is(notNullValue()));
        assertThat(param.getPrivacyStrategy().getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
    }

    @Test
    public void buildParamWithAllData() {
        final DownloadDataParameter param = DownloadDataParameter.create(SAMPLE_DATA_HASH)
                .digest(SAMPLE_DIGEST)
                .privacyStrategy(new SecuredWithNemKeysPrivacyStrategy(
                        "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C",
                        "E9F6576AF9F05E6738CD4E55B875A823CC75B4E8AE8984747DF7B235685C1577",
                        "test"
                ))
                .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getDigest(), is(SAMPLE_DIGEST));
        assertThat(param.getDataHash(), is(SAMPLE_DATA_HASH));
        assertThat(param.getPrivacyStrategy(), is(notNullValue()));
        assertThat(param.getPrivacyStrategy().getPrivacyType(), is(PrivacyType.NEMKEYS.getValue()));
    }

    @Test
    public void shouldCreateWithPlainPrivacy() throws UnsupportedEncodingException {
        final DownloadDataParameter param = DownloadDataParameter.create(SAMPLE_DATA_HASH)
                .plainPrivacy()
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(PlainPrivacyStrategy.class));
        assertThat(param.getPrivacyStrategy().getPrivacySearchTag(), is(nullValue()));
    }

    @Test
    public void shouldCreateWithSecuredWithNemKeysPrivacy() throws UnsupportedEncodingException {
        final DownloadDataParameter param = DownloadDataParameter.create(SAMPLE_DATA_HASH)
                .securedWithNemKeysPrivacyStrategy(PRIVATE_KEY_1, PUBLIC_KEY_2)
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithNemKeysPrivacyStrategy.class));
        assertThat(param.getPrivacyStrategy().getPrivacySearchTag(), is(nullValue()));
    }

    @Test
    public void shouldCreateWithSecuredWithPasswordPrivacy() throws UnsupportedEncodingException {
        final DownloadDataParameter param = DownloadDataParameter.create(SAMPLE_DATA_HASH)
                .securedWithPasswordPrivacyStrategy("hdksahjkdhsakjhdsajhdkjhsajkdsbajjdhsajkhdjksahjkdahjkhdkjsahjdsadasdsadas")
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithPasswordPrivacyStrategy.class));
        assertThat(param.getPrivacyStrategy().getPrivacySearchTag(), is(nullValue()));
    }

    @Test
    public void shouldCreateWithSecuredWithShamirSecretSharingMapStrategy() throws UnsupportedEncodingException {
        final Map<Integer, byte[]> minimumSecretParts = new HashMap<>();
        minimumSecretParts.put(1, SECRET_PARTS.get(1));
        minimumSecretParts.put(3, SECRET_PARTS.get(3));
        minimumSecretParts.put(5, SECRET_PARTS.get(5));
        final DownloadDataParameter param = DownloadDataParameter.create(SAMPLE_DATA_HASH)
                .securedWithShamirSecretSharingPrivacyStrategy(SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        minimumSecretParts)
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
        assertThat(param.getPrivacyStrategy().getPrivacySearchTag(), is(nullValue()));
    }

    @Test
    public void shouldCreateWithSecuredWithShamirSecretSharingArrayStrategy() throws UnsupportedEncodingException {
        final DownloadDataParameter param = DownloadDataParameter.create(SAMPLE_DATA_HASH)
                .securedWithShamirSecretSharingPrivacyStrategy(SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(1, SECRET_PARTS.get(1)),
                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(3, SECRET_PARTS.get(3)),
                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(5, SECRET_PARTS.get(5)))
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
        assertThat(param.getPrivacyStrategy().getPrivacySearchTag(), is(nullValue()));
    }

    @Test
    public void shouldCreateWithSecuredWithShamirSecretSharingListStrategy() throws UnsupportedEncodingException {
        final DownloadDataParameter param = DownloadDataParameter.create(SAMPLE_DATA_HASH)
                .securedWithShamirSecretSharingPrivacyStrategy(SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        asList(
                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(1, SECRET_PARTS.get(1)),
                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(3, SECRET_PARTS.get(3)),
                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(5, SECRET_PARTS.get(5))))
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
        assertThat(param.getPrivacyStrategy().getPrivacySearchTag(), is(nullValue()));
    }

}
