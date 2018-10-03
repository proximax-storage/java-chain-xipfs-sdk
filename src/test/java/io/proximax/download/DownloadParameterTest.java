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
package io.proximax.download;

import io.proximax.model.PrivacyType;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import org.junit.Test;

import static io.proximax.testsupport.Constants.TEST_PRIVATE_KEY_1;
import static io.proximax.testsupport.Constants.TEST_PUBLIC_KEY_2;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class DownloadParameterTest {

    private static final String SAMPLE_TRANSACTION_HASH = "F08E3C327DD5DE258EF20532F4D3C7638E9AC44885C34FDDC1A5740FD3C56EBB";

    @Test(expected = IllegalArgumentException.class)
    public void failWhenCreatingWithNullTransactionHash() {
        DownloadParameter.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenInvalidAccountPrivateKey() {
        DownloadParameter.create(SAMPLE_TRANSACTION_HASH)
                .withAccountPrivateKey("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test
    public void canBuildParamWithOnlyTransactionHash() {
        final DownloadParameter param = DownloadParameter.create(SAMPLE_TRANSACTION_HASH).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getTransactionHash(), is(SAMPLE_TRANSACTION_HASH));
        assertThat(param.getAccountPrivateKey(), is(nullValue()));
        assertThat(param.getValidateDigest(), is(false));
        assertThat(param.getPrivacyStrategy(), is(notNullValue()));
        assertThat(param.getPrivacyStrategy().getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
    }

    @Test
    public void buildParamWithPrivacyStrategy() {
        final DownloadParameter param = DownloadParameter.create(SAMPLE_TRANSACTION_HASH)
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
        final DownloadParameter param = DownloadParameter.create(SAMPLE_TRANSACTION_HASH)
                .withPlainPrivacy()
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(PlainPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithNemKeysPrivacy() {
        final DownloadParameter param = DownloadParameter.create(SAMPLE_TRANSACTION_HASH)
                .withNemKeysPrivacy(TEST_PRIVATE_KEY_1, TEST_PUBLIC_KEY_2)
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithNemKeysPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithPasswordPrivacy() {
        final DownloadParameter param = DownloadParameter.create(SAMPLE_TRANSACTION_HASH)
                .withPasswordPrivacy("hdksahjkdhsakjhdsajhdkjhsajkdsbajjdhsajkhdjksahjkdahjkhdkjsahjdsadasdsadas")
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithPasswordPrivacyStrategy.class));
    }

    // TODO - revisit shamir secret sharing implementation that works cross-sdk
//    @Test
//    public void shouldCreateWithSecuredWithShamirSecretSharingMapStrategy() {
//        final Map<Integer, byte[]> minimumSecretParts = new HashMap<>();
//        minimumSecretParts.put(1, TEST_SHAMIR_SECRET_SHARES.get(1));
//        minimumSecretParts.put(3, TEST_SHAMIR_SECRET_SHARES.get(3));
//        minimumSecretParts.put(5, TEST_SHAMIR_SECRET_SHARES.get(5));
//        final DownloadParameter param = DownloadParameter.create(SAMPLE_TRANSACTION_HASH)
//                .withShamirSecretSharingPrivacy(TEST_SHAMIR_SECRET_TOTAL_SHARES, TEST_SHAMIR_SECRET_THRESHOLD,
//                        minimumSecretParts)
//                .build();
//
//        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
//    }
//
//    @Test
//    public void shouldCreateWithSecuredWithShamirSecretSharingArrayStrategy() {
//        final DownloadParameter param = DownloadParameter.create(SAMPLE_TRANSACTION_HASH)
//                .withShamirSecretSharingPrivacy(TEST_SHAMIR_SECRET_TOTAL_SHARES, TEST_SHAMIR_SECRET_THRESHOLD,
//                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(1, TEST_SHAMIR_SECRET_SHARES.get(1)),
//                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(3, TEST_SHAMIR_SECRET_SHARES.get(3)),
//                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(5, TEST_SHAMIR_SECRET_SHARES.get(5)))
//                .build();
//
//        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
//    }
//
//    @Test
//    public void shouldCreateWithSecuredWithShamirSecretSharingListStrategy() {
//        final DownloadParameter param = DownloadParameter.create(SAMPLE_TRANSACTION_HASH)
//                .withShamirSecretSharingPrivacy(TEST_SHAMIR_SECRET_TOTAL_SHARES, TEST_SHAMIR_SECRET_THRESHOLD,
//                        asList(
//                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(1, TEST_SHAMIR_SECRET_SHARES.get(1)),
//                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(3, TEST_SHAMIR_SECRET_SHARES.get(3)),
//                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(5, TEST_SHAMIR_SECRET_SHARES.get(5))))
//                .build();
//
//        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
//    }
}
