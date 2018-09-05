package io.proximax.privacy.strategy;

import com.codahale.shamir.Scheme;
import io.proximax.exceptions.DecryptionFailureException;
import io.proximax.model.PrivacyType;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

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
    public void returnEncryptedWithAllSecretParts() {
        final SecuredWithShamirSecretSharingPrivacyStrategy unitUnderTest = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, SECRET_PARTS);

        final byte[] encrypted = unitUnderTest.encryptData(SAMPLE_DATA);

        assertThat(ArrayUtils.toObject(encrypted), not(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void returnEncryptedWithMinimumSecretParts() {
        final Map<Integer, byte[]> minimumSecretParts = new HashMap<>();
        minimumSecretParts.put(1, SECRET_PARTS.get(1));
        minimumSecretParts.put(3, SECRET_PARTS.get(3));
        minimumSecretParts.put(4, SECRET_PARTS.get(4));
        final SecuredWithShamirSecretSharingPrivacyStrategy unitUnderTest = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, minimumSecretParts);

        final byte[] encrypted = unitUnderTest.encryptData(SAMPLE_DATA);

        assertThat(ArrayUtils.toObject(encrypted), not(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void returnDecryptedWithMinimumSecretParts() {
        final SecuredWithShamirSecretSharingPrivacyStrategy allPartsStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, SECRET_PARTS);
        final byte[] encrypted = allPartsStrategy.encryptData(SAMPLE_DATA);
        final Map<Integer, byte[]> minimumSecretParts = new HashMap<>();
        minimumSecretParts.put(1, SECRET_PARTS.get(1));
        minimumSecretParts.put(2, SECRET_PARTS.get(2));
        minimumSecretParts.put(4, SECRET_PARTS.get(4));
        final SecuredWithShamirSecretSharingPrivacyStrategy unitUnderTest = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, minimumSecretParts);

        final byte[] decrypted = unitUnderTest.decryptData(encrypted);

        assertThat(ArrayUtils.toObject(decrypted), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void returnDecryptedWithDifferentSecretParts() {
        final Map<Integer, byte[]> firstSecretParts = new HashMap<>();
        firstSecretParts.put(2, SECRET_PARTS.get(2));
        firstSecretParts.put(3, SECRET_PARTS.get(3));
        firstSecretParts.put(5, SECRET_PARTS.get(5));
        final SecuredWithShamirSecretSharingPrivacyStrategy firstPartsStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, firstSecretParts);
        final byte[] encrypted = firstPartsStrategy.encryptData(SAMPLE_DATA);
        final Map<Integer, byte[]> secondSecretParts = new HashMap<>();
        secondSecretParts.put(1, SECRET_PARTS.get(1));
        secondSecretParts.put(2, SECRET_PARTS.get(2));
        secondSecretParts.put(4, SECRET_PARTS.get(4));
        final SecuredWithShamirSecretSharingPrivacyStrategy secondPartsStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, secondSecretParts);

        final byte[] decrypted = secondPartsStrategy.decryptData(encrypted);

        assertThat(ArrayUtils.toObject(decrypted), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test(expected = DecryptionFailureException.class)
    public void returnDecryptedWithWrongSecretParts() {
        final Map<Integer, byte[]> firstSecretParts = new HashMap<>();
        firstSecretParts.put(2, SECRET_PARTS.get(2));
        firstSecretParts.put(3, SECRET_PARTS.get(3));
        firstSecretParts.put(5, SECRET_PARTS.get(5));
        final SecuredWithShamirSecretSharingPrivacyStrategy firstPartsStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, firstSecretParts);
        final byte[] encrypted = firstPartsStrategy.encryptData(SAMPLE_DATA);
        final Map<Integer, byte[]> wrongSecretParts = new HashMap<>();
        wrongSecretParts.put(1, SECRET_PARTS.get(1));
        wrongSecretParts.put(3, SECRET_PARTS.get(3));
        wrongSecretParts.put(4, SECRET_PARTS.get(5));

        final SecuredWithShamirSecretSharingPrivacyStrategy unitUnderTest = SecuredWithShamirSecretSharingPrivacyStrategy.create(
                SECRET_TOTAL_PART_COUNT, SECRET_MINIMUM_PART_COUNT_TO_BUILD, wrongSecretParts);

        unitUnderTest.decryptData(encrypted);
    }

}
