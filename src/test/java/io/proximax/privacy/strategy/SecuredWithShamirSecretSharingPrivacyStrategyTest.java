package io.proximax.privacy.strategy;

import com.codahale.shamir.Scheme;
import io.proximax.model.PrivacyType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;

public class SecuredWithShamirSecretSharingPrivacyStrategyTest {

    private static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();

    private static final byte[] SECRET = ("dhsakdhsauihcxznmneywquidkjsanjcbnxztyduisyaheqkwjncbxzcgyuiagsdiujasodjk" +
            "wqehqtsyiudhsandmbsamnbcxzygcahgisudhasnmbdmnasbcysagciugxziuhkjsbdamndnmsabfgaduishadshakdnsakdbsajbeh" +
            "wqyuieyqwiueyqwohdsanlnalsfjkhgkzgmnbcmnxzbhjgdsajgduisayiuyewquehwqkjbeqwnbdmnabsdabjshgdasudhgsuakghk" +
            "cbxzcbajsbdkasjgkjhwgquegqwbzmcbmzxn").getBytes();

    private static final int SECRET_TOTAL_PART_COUNT = 5;

    private static final int SECRET_MINIMUM_PART_COUNT_TO_BUILD = 3;

    private static final Scheme SCHEME = Scheme.of(SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD);

    private static final Map<Integer, byte[]> SECRET_PARTS = SCHEME.split(SECRET);

    @Test
    public void shouldReturnCorrectPrivacyType() {
        final int privacyType = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, SECRET_PARTS).getPrivacyType();

        assertThat(privacyType, is(PrivacyType.SHAMIR.getValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWithNegativeSecretTotalPartCount() {
        SecuredWithShamirSecretSharingPrivacyStrategy.create(-1, SECRET_MINIMUM_PART_COUNT_TO_BUILD, SECRET_PARTS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWithNegativeSecretMinimumPartCountToBuild() {
        SecuredWithShamirSecretSharingPrivacyStrategy.create(SECRET_TOTAL_PART_COUNT, -1, SECRET_PARTS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWithSecretMinimumPartCountToBuildBeingGreaterThanTotalPartCount() {
        SecuredWithShamirSecretSharingPrivacyStrategy.create(SECRET_TOTAL_PART_COUNT, SECRET_TOTAL_PART_COUNT + 1, SECRET_PARTS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWithSecretPartsEmpty() {
        SecuredWithShamirSecretSharingPrivacyStrategy.create(SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                Collections.emptyMap());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWithSecretPartsNotMeetingMinimumPartCountToBuild() {
        SecuredWithShamirSecretSharingPrivacyStrategy.create(SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD,
                Collections.singletonMap(1, SECRET_PARTS.get(1)));
    }

    @Test
    public void returnEncryptedWithAllSecretParts() throws IOException {
        final SecuredWithShamirSecretSharingPrivacyStrategy unitUnderTest = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, SECRET_PARTS);

        final InputStream encrypted = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA));

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(encrypted)), not(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void returnEncryptedWithMinimumSecretParts() throws IOException {
        final Map<Integer, byte[]> minimumSecretParts = new HashMap<>();
        minimumSecretParts.put(1, SECRET_PARTS.get(1));
        minimumSecretParts.put(3, SECRET_PARTS.get(3));
        minimumSecretParts.put(4, SECRET_PARTS.get(4));
        final SecuredWithShamirSecretSharingPrivacyStrategy unitUnderTest = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, minimumSecretParts);

        final InputStream encrypted = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA));

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(encrypted)), not(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void returnDecryptedWithMinimumSecretParts() throws IOException {
        final SecuredWithShamirSecretSharingPrivacyStrategy allPartsStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, SECRET_PARTS);
        final InputStream encryptedStream = allPartsStrategy.encryptStream(new ByteArrayInputStream(SAMPLE_DATA));
        final Map<Integer, byte[]> minimumSecretParts = new HashMap<>();
        minimumSecretParts.put(1, SECRET_PARTS.get(1));
        minimumSecretParts.put(2, SECRET_PARTS.get(2));
        minimumSecretParts.put(4, SECRET_PARTS.get(4));
        final SecuredWithShamirSecretSharingPrivacyStrategy unitUnderTest = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, minimumSecretParts);

        final InputStream decrypted = unitUnderTest.decryptStream(encryptedStream);

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(decrypted)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void returnDecryptedWithDifferentSecretParts() throws IOException {
        final Map<Integer, byte[]> firstSecretParts = new HashMap<>();
        firstSecretParts.put(2, SECRET_PARTS.get(2));
        firstSecretParts.put(3, SECRET_PARTS.get(3));
        firstSecretParts.put(5, SECRET_PARTS.get(5));
        final SecuredWithShamirSecretSharingPrivacyStrategy firstPartsStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, firstSecretParts);
        final InputStream encryptedStream = firstPartsStrategy.encryptStream(new ByteArrayInputStream(SAMPLE_DATA));
        final Map<Integer, byte[]> secondSecretParts = new HashMap<>();
        secondSecretParts.put(1, SECRET_PARTS.get(1));
        secondSecretParts.put(2, SECRET_PARTS.get(2));
        secondSecretParts.put(4, SECRET_PARTS.get(4));
        final SecuredWithShamirSecretSharingPrivacyStrategy secondPartsStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, secondSecretParts);

        final InputStream decrypted = secondPartsStrategy.decryptStream(encryptedStream);

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(decrypted)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test(expected = IOException.class)
    public void returnDecryptedWithWrongSecretParts() throws IOException {
        final Map<Integer, byte[]> firstSecretParts = new HashMap<>();
        firstSecretParts.put(2, SECRET_PARTS.get(2));
        firstSecretParts.put(3, SECRET_PARTS.get(3));
        firstSecretParts.put(5, SECRET_PARTS.get(5));
        final SecuredWithShamirSecretSharingPrivacyStrategy firstPartsStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, firstSecretParts);
        final InputStream encryptedStream = firstPartsStrategy.encryptStream(new ByteArrayInputStream(SAMPLE_DATA));
        final Map<Integer, byte[]> wrongSecretParts = new HashMap<>();
        wrongSecretParts.put(1, SECRET_PARTS.get(1));
        wrongSecretParts.put(3, SECRET_PARTS.get(3));
        wrongSecretParts.put(4, SECRET_PARTS.get(5));

        final SecuredWithShamirSecretSharingPrivacyStrategy unitUnderTest = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, wrongSecretParts);

        IOUtils.toByteArray(unitUnderTest.decryptStream(encryptedStream));
    }

}
