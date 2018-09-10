package io.proximax.upload;

import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.model.Constants.SCHEMA_VERSION;
import static io.proximax.testsupport.Constants.IMAGE_FILE;
import static io.proximax.testsupport.Constants.PATH_FILE;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_PARTS;
import static io.proximax.testsupport.Constants.SHAMIR_SECRET_TOTAL_PART_COUNT;
import static io.proximax.testsupport.Constants.STRING_TEST;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class UploadParameterTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();

    private static final String SAMPLE_SIGNER_PRIVATE_KEY = "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C";
    private static final String SAMPLE_RECIPIENT_PUBLIC_KEY = "E9F6576AF9F05E6738CD4E55B875A823CC75B4E8AE8984747DF7B235685C1577";
    private static final String SAMPLE_RECIPIENT_ADDRESS = "SBRHESWCLX3VGQ6CHCZNKDN6DT7GLS6CZKJXCT5F";

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullByteArrayOnCreateForByteArrayUpload() {
        UploadParameter.createForByteArrayUpload((byte[]) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullByteArrayParameterDataOnCreateForByteArrayUpload() {
        UploadParameter.createForByteArrayUpload((ByteArrayParameterData) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPrivateKeyOnCreateForByteArrayUpload() {
        UploadParameter.createForByteArrayUpload(SAMPLE_DATA, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenInvalidPrivateKeyOnCreateForByteArrayUpload() {
        UploadParameter.createForByteArrayUpload(SAMPLE_DATA, "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test
    public void shouldCreateParamWithByteArray() {
        final UploadParameter param = UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(ByteArrayParameterData.class)));
        assertThat(((ByteArrayParameterData) param.getData()).getData(), is(SAMPLE_DATA));
        assertThat(param.getData().getDescription(), is(nullValue()));
        assertThat(param.getData().getName(), is(nullValue()));
        assertThat(param.getData().getContentType(), is(nullValue()));
        assertThat(param.getData().getMetadata(), is(nullValue()));
    }

    @Test
    public void shouldCreateParamWithByteArrayParameterData() {
        final UploadParameter param =
                UploadParameter.createForByteArrayUpload(
                        ByteArrayParameterData.create(SAMPLE_DATA, "test description", "test name",
                                "text/plain", singletonMap("testkey", "testvalue")),
                        SAMPLE_SIGNER_PRIVATE_KEY)
                        .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(ByteArrayParameterData.class)));
        assertThat(((ByteArrayParameterData) param.getData()).getData(), is(SAMPLE_DATA));
        assertThat(param.getData().getDescription(), is("test description"));
        assertThat(param.getData().getName(), is("test name"));
        assertThat(param.getData().getContentType(), is("text/plain"));
        assertThat(param.getData().getMetadata(), is(singletonMap("testkey", "testvalue")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullFileOnCreateForFileUpload() throws IOException {
        UploadParameter.createForFileUpload((File) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullFileParameterDataOnCreateForFileUpload() {
        UploadParameter.createForFileUpload((FileParameterData) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPrivateKeyOnCreateForFileUpload() throws IOException {
        UploadParameter.createForFileUpload(IMAGE_FILE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenInvalidPrivateKeyOnCreateForFileUpload() throws IOException {
        UploadParameter.createForFileUpload(IMAGE_FILE, "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test
    public void shouldCreateParamWithFile() throws IOException {
        final UploadParameter param = UploadParameter.createForFileUpload(IMAGE_FILE, SAMPLE_SIGNER_PRIVATE_KEY).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(FileParameterData.class)));
        assertThat(((FileParameterData) param.getData()).getFile(), is(IMAGE_FILE));
        assertThat(((FileParameterData) param.getData()).getByteStream(), is(notNullValue()));
        assertThat(param.getData().getDescription(), is(nullValue()));
        assertThat(param.getData().getName(), is("test_image.png"));
        assertThat(param.getData().getContentType(), is(nullValue()));
        assertThat(param.getData().getMetadata(), is(nullValue()));
    }

    @Test
    public void shouldCreateParamWithFileParameterData() throws IOException {
        final UploadParameter param =
                UploadParameter.createForFileUpload(
                        FileParameterData.create(IMAGE_FILE, "test description", "test name",
                                "text/plain", singletonMap("testkey", "testvalue")),
                        SAMPLE_SIGNER_PRIVATE_KEY)
                        .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(FileParameterData.class)));
        assertThat(((FileParameterData) param.getData()).getFile(), is(IMAGE_FILE));
        assertThat(((FileParameterData) param.getData()).getByteStream(), is(notNullValue()));
        assertThat(param.getData().getDescription(), is("test description"));
        assertThat(param.getData().getName(), is("test name"));
        assertThat(param.getData().getContentType(), is("text/plain"));
        assertThat(param.getData().getMetadata(), is(singletonMap("testkey", "testvalue")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullFilesOnCreateForFilesAsZipUpload() throws IOException {
        UploadParameter.createForFilesAsZipUpload((List<File>) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullFilesAsZipParameterDataOnCreateForFileAsZipUpload() {
        UploadParameter.createForFilesAsZipUpload((FilesAsZipParameterData) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPrivateKeyOnCreateForFilesAsZipUpload() throws IOException {
        UploadParameter.createForFilesAsZipUpload(singletonList(IMAGE_FILE), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenInvalidPrivateKeyOnCreateForFilesAsZipUpload() throws IOException {
        UploadParameter.createForFilesAsZipUpload(singletonList(IMAGE_FILE), "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test
    public void shouldCreateParamWithFilesAsZip() throws IOException {
        final UploadParameter param = UploadParameter.createForFilesAsZipUpload(singletonList(IMAGE_FILE), SAMPLE_SIGNER_PRIVATE_KEY).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(FilesAsZipParameterData.class)));
        assertThat(((FilesAsZipParameterData) param.getData()).getFiles(), is(singletonList(IMAGE_FILE)));
        assertThat(((FilesAsZipParameterData) param.getData()).getByteStream(), is(notNullValue()));
        assertThat(param.getData().getDescription(), is(nullValue()));
        assertThat(param.getData().getName(), is(nullValue()));
        assertThat(param.getData().getContentType(), is("application/zip"));
        assertThat(param.getData().getMetadata(), is(nullValue()));
    }

    @Test
    public void shouldCreateParamWithFileAsZipParameterData() throws IOException {
        final UploadParameter param =
                UploadParameter.createForFilesAsZipUpload(
                        FilesAsZipParameterData.create(singletonList(IMAGE_FILE), "test description", "test name",
                                singletonMap("testkey", "testvalue")),
                        SAMPLE_SIGNER_PRIVATE_KEY)
                        .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(FilesAsZipParameterData.class)));
        assertThat(((FilesAsZipParameterData) param.getData()).getFiles(), is(singletonList(IMAGE_FILE)));
        assertThat(((FilesAsZipParameterData) param.getData()).getByteStream(), is(notNullValue()));
        assertThat(param.getData().getDescription(), is("test description"));
        assertThat(param.getData().getName(), is("test name"));
        assertThat(param.getData().getContentType(), is("application/zip"));
        assertThat(param.getData().getMetadata(), is(singletonMap("testkey", "testvalue")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPathOnCreateForPathUpload() throws IOException {
        UploadParameter.createForPathUpload((File) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPathParameterDataOnCreateForPathUpload() {
        UploadParameter.createForPathUpload((PathParameterData) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPrivateKeyOnCreateForPathUpload() throws IOException {
        UploadParameter.createForPathUpload(PATH_FILE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenInvalidPrivateKeyOnCreateForPathUpload() throws IOException {
        UploadParameter.createForPathUpload(PATH_FILE, "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test
    public void shouldCreateParamWithPath() throws IOException {
        final UploadParameter param = UploadParameter.createForPathUpload(PATH_FILE, SAMPLE_SIGNER_PRIVATE_KEY).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(PathParameterData.class)));
        assertThat(((PathParameterData) param.getData()).getPath(), is(PATH_FILE));
        assertThat(param.getData().getDescription(), is(nullValue()));
        assertThat(param.getData().getName(), is(nullValue()));
        assertThat(param.getData().getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
        assertThat(param.getData().getMetadata(), is(nullValue()));
    }

    @Test
    public void shouldCreateParamWithPathParameterData() throws IOException {
        final UploadParameter param =
                UploadParameter.createForPathUpload(
                        PathParameterData.create(PATH_FILE, "test description", "test name",
                                singletonMap("testkey", "testvalue")),
                        SAMPLE_SIGNER_PRIVATE_KEY)
                        .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(PathParameterData.class)));
        assertThat(((PathParameterData) param.getData()).getPath(), is(PATH_FILE));
        assertThat(param.getData().getDescription(), is("test description"));
        assertThat(param.getData().getName(), is("test name"));
        assertThat(param.getData().getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
        assertThat(param.getData().getMetadata(), is(singletonMap("testkey", "testvalue")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullStringOnCreateForStringUpload() throws IOException {
        UploadParameter.createForStringUpload((String) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullStringParameterDataOnCreateForStringUpload() {
        UploadParameter.createForStringUpload((StringParameterData) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPrivateKeyOnCreateForStringUpload() throws IOException {
        UploadParameter.createForStringUpload(STRING_TEST, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenInvalidPrivateKeyOnCreateForStringUpload() throws IOException {
        UploadParameter.createForStringUpload(STRING_TEST, "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test
    public void shouldCreateParamWithString() throws IOException {
        final UploadParameter param = UploadParameter.createForStringUpload(STRING_TEST, SAMPLE_SIGNER_PRIVATE_KEY).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(StringParameterData.class)));
        assertThat(((StringParameterData) param.getData()).getString(), is(STRING_TEST));
        assertThat(new String(IOUtils.toByteArray(((StringParameterData) param.getData()).getByteStream())), is(STRING_TEST));
        assertThat(param.getData().getDescription(), is(nullValue()));
        assertThat(param.getData().getName(), is(nullValue()));
        assertThat(param.getData().getContentType(), is(nullValue()));
        assertThat(param.getData().getMetadata(), is(nullValue()));
    }

    @Test
    public void shouldCreateParamWithStringParameterData() throws IOException {
        final UploadParameter param =
                UploadParameter.createForStringUpload(
                        StringParameterData.create(STRING_TEST, "UTF-8", "test description", "test name",
                                "text/plain", singletonMap("testkey", "testvalue")),
                        SAMPLE_SIGNER_PRIVATE_KEY)
                        .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(StringParameterData.class)));
        assertThat(((StringParameterData) param.getData()).getString(), is(STRING_TEST));
        assertThat(new String(IOUtils.toByteArray(((StringParameterData) param.getData()).getByteStream())), is(STRING_TEST));
        assertThat(param.getData().getDescription(), is("test description"));
        assertThat(param.getData().getName(), is("test name"));
        assertThat(param.getData().getContentType(), is("text/plain"));
        assertThat(param.getData().getMetadata(), is(singletonMap("testkey", "testvalue")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullUrlOnCreateForUrlResourceUpload() throws IOException {
        UploadParameter.createForUrlResourceUpload((URL) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullUrlParameterDataOnCreateForUrlResourceUpload() {
        UploadParameter.createForUrlResourceUpload((UrlResourceParameterData) null, SAMPLE_RECIPIENT_PUBLIC_KEY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPrivateKeyOnCreateForUrlResourceUpload() throws IOException {
        UploadParameter.createForUrlResourceUpload(IMAGE_FILE.toURI().toURL(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenInvalidPrivateKeyOnCreateForUrlResourceUpload() throws IOException {
        UploadParameter.createForUrlResourceUpload(IMAGE_FILE.toURI().toURL(), "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test
    public void shouldCreateParamWithUrl() throws IOException {
        final UploadParameter param = UploadParameter.createForUrlResourceUpload(IMAGE_FILE.toURI().toURL(), SAMPLE_SIGNER_PRIVATE_KEY).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(UrlResourceParameterData.class)));
        assertThat(((UrlResourceParameterData) param.getData()).getUrl(), is(IMAGE_FILE.toURI().toURL()));
        assertThat(((UrlResourceParameterData) param.getData()).getByteStream(), is(notNullValue()));
        assertThat(param.getData().getDescription(), is(nullValue()));
        assertThat(param.getData().getName(), is(nullValue()));
        assertThat(param.getData().getContentType(), is(nullValue()));
        assertThat(param.getData().getMetadata(), is(nullValue()));
    }

    @Test
    public void shouldCreateParamWithUrlResourceParameterData() throws IOException {
        final UploadParameter param =
                UploadParameter.createForUrlResourceUpload(
                        UrlResourceParameterData.create(IMAGE_FILE.toURI().toURL(), "test description", "test name",
                                "text/plain", singletonMap("testkey", "testvalue")),
                        SAMPLE_SIGNER_PRIVATE_KEY)
                        .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getSignerPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(param.getData(), is(instanceOf(UrlResourceParameterData.class)));
        assertThat(((UrlResourceParameterData) param.getData()).getUrl(), is(IMAGE_FILE.toURI().toURL()));
        assertThat(((UrlResourceParameterData) param.getData()).getByteStream(), is(notNullValue()));
        assertThat(param.getData().getDescription(), is("test description"));
        assertThat(param.getData().getName(), is("test name"));
        assertThat(param.getData().getContentType(), is("text/plain"));
        assertThat(param.getData().getMetadata(), is(singletonMap("testkey", "testvalue")));
    }

    @Test
    public void shouldCreateWithPlainPrivacy() {
        final UploadParameter param = UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY)
                .plainPrivacy()
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(PlainPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithNemKeysPrivacy() {
        final UploadParameter param = UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY)
                .securedWithNemKeysPrivacy(SAMPLE_SIGNER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY)
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithNemKeysPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithPasswordPrivacy() {
        final UploadParameter param = UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY)
                .securedWithPasswordPrivacy("hdksahjkdhsakjhdsajhdkjhsajkdsbajjdhsajkhdjksahjkdahjkhdkjsahjdsadasdsadas")
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithPasswordPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithShamirSecretSharingMapStrategy() {
        final Map<Integer, byte[]> minimumSecretParts = new HashMap<>();
        minimumSecretParts.put(1, SHAMIR_SECRET_PARTS.get(1));
        minimumSecretParts.put(3, SHAMIR_SECRET_PARTS.get(3));
        minimumSecretParts.put(5, SHAMIR_SECRET_PARTS.get(5));
        final UploadParameter param = UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY)
                .securedWithShamirSecretSharing(SHAMIR_SECRET_TOTAL_PART_COUNT, SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        minimumSecretParts)
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithShamirSecretSharingArrayStrategy() throws UnsupportedEncodingException {
        final UploadParameter param = UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY)
                .securedWithShamirSecretSharing(SHAMIR_SECRET_TOTAL_PART_COUNT, SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(1, SHAMIR_SECRET_PARTS.get(1)),
                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(3, SHAMIR_SECRET_PARTS.get(3)),
                        new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(5, SHAMIR_SECRET_PARTS.get(5)))
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithSecuredWithShamirSecretSharingListStrategy() throws UnsupportedEncodingException {
        final UploadParameter param = UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY)
                .securedWithShamirSecretSharing(SHAMIR_SECRET_TOTAL_PART_COUNT, SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                        asList(
                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(1, SHAMIR_SECRET_PARTS.get(1)),
                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(3, SHAMIR_SECRET_PARTS.get(3)),
                                new SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart(5, SHAMIR_SECRET_PARTS.get(5))))
                .build();

        assertThat(param.getPrivacyStrategy(), instanceOf(SecuredWithShamirSecretSharingPrivacyStrategy.class));
    }

    @Test
    public void shouldCreateWithDefaults() {
        final UploadParameter param = UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY).build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getComputeDigest(), is(false));
        assertThat(param.getVersion(), is(SCHEMA_VERSION));
        assertThat(param.getRecipientPublicKey(), is(nullValue()));
        assertThat(param.getRecipientAddress(), is(nullValue()));
        assertThat(param.getPrivacyStrategy(), is(instanceOf(PlainPrivacyStrategy.class)));
        assertThat(param.getDetectContentType(), is(false));
        assertThat(param.getTransactionDeadline(), is(12));
        assertThat(param.getUseBlockchainSecureMessage(), is(false));
    }

    @Test
    public void shouldCreateWithAllParametersConfigured() {
        final UploadParameter param = UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY)
                .computeDigest(true)
                .recipientPublicKey(SAMPLE_RECIPIENT_PUBLIC_KEY)
                .recipientAddress(SAMPLE_RECIPIENT_ADDRESS)
                .securedWithPasswordPrivacy("passwordaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .detectContentType(true)
                .transactionDeadline(5)
                .useBlockchainSecureMessage(true)
                .build();

        assertThat(param, is(notNullValue()));
        assertThat(param.getComputeDigest(), is(true));
        assertThat(param.getRecipientPublicKey(), is(SAMPLE_RECIPIENT_PUBLIC_KEY));
        assertThat(param.getRecipientAddress(), is(SAMPLE_RECIPIENT_ADDRESS));
        assertThat(param.getPrivacyStrategy(), is(instanceOf(SecuredWithPasswordPrivacyStrategy.class)));
        assertThat(param.getDetectContentType(), is(true));
        assertThat(param.getTransactionDeadline(), is(5));
        assertThat(param.getUseBlockchainSecureMessage(), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenInvalidRecipientPublicKey() {
        UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY)
                .recipientPublicKey("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenInvalidRecipientPublicAddress() {
        UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY)
                .recipientAddress("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenInvalidTransactionDeadline() {
        UploadParameter.createForByteArrayUpload(SAMPLE_DATA, SAMPLE_SIGNER_PRIVATE_KEY)
                .transactionDeadline(48);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failCreateParameterDataWhenDescriptionExceedsLimit() {
        ByteArrayParameterData.create(SAMPLE_DATA, RandomStringUtils.random(201), "test name",
                "text/plain", singletonMap("testkey", "testvalue"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failCreateParameterDataWhenNameExceedsLimit() {
        ByteArrayParameterData.create(SAMPLE_DATA, "test description", RandomStringUtils.random(201),
                "text/plain", singletonMap("testkey", "testvalue"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failCreateParameterDataWhenMetadataJsonExceedsLimit() {
        ByteArrayParameterData.create(SAMPLE_DATA, "test description", "test name",
                "text/plain", singletonMap(RandomStringUtils.random(250), RandomStringUtils.random(250)));
    }

}
