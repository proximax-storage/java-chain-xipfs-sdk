package io.proximax.privacy.strategy;

import io.nem.core.crypto.CryptoEngines;
import io.nem.core.crypto.KeyPair;
import io.nem.core.crypto.PrivateKey;
import io.proximax.exceptions.DecryptionFailureException;
import io.proximax.model.PrivacyType;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;

public class SecuredWithNemKeysPrivacyStrategyTest {

    public static final byte[] SAMPLE_DATA = "the quick brown fox jumps over the lazy dog".getBytes();

    public static final KeyPair SENDER_KEYPAIR = new KeyPair(PrivateKey.fromHexString("08871D3EB4CF3D6695A61E8E1B60DC64DCC9EED40F33D4848BF9079168CCD4A4"));

    public static final KeyPair RECEIVER_KEYPAIR = new KeyPair(PrivateKey.fromHexString("1A5B81AE8830B8A79232CD366552AF6496FE548B4A23D4173FEEBA41B8ABA81F"));

    public static final KeyPair ANOTHER_KEYPAIR = new KeyPair(PrivateKey.fromHexString("CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C"));

    @Test
    public void shouldReturnCorrectPrivacyType() {
        final int privacyType = SecuredWithNemKeysPrivacyStrategy.create(SENDER_KEYPAIR.getPrivateKey().toString(),
                RECEIVER_KEYPAIR.getPublicKey().toString()).getPrivacyType();

        assertThat(privacyType, is(PrivacyType.NEMKEYS.getValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failInitWithoutPrivateKey() {
        SecuredWithNemKeysPrivacyStrategy.create(null, RECEIVER_KEYPAIR.getPublicKey().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failInitWithoutPublicKey() {
        SecuredWithNemKeysPrivacyStrategy.create(SENDER_KEYPAIR.getPrivateKey().toString(), null);
    }

    @Test
    public void shouldReturnEncryptedWithKeys() {
        final SecuredWithNemKeysPrivacyStrategy unitUnderTest =
                SecuredWithNemKeysPrivacyStrategy.create(SENDER_KEYPAIR.getPrivateKey().toString(),
                        RECEIVER_KEYPAIR.getPublicKey().toString());

        final byte[] encrypted = unitUnderTest.encryptData(SAMPLE_DATA);

        assertThat(ArrayUtils.toObject(encrypted), not(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test(expected = DecryptionFailureException.class)
    public void failDecryptWhenPrivateKeyIsNeitherSenderOrReceiver() {
        final SecuredWithNemKeysPrivacyStrategy unitUnderTest =
                SecuredWithNemKeysPrivacyStrategy.create(ANOTHER_KEYPAIR.getPrivateKey().toString(),
                        RECEIVER_KEYPAIR.getPublicKey().toString());

        unitUnderTest.decryptData(sampleEncryptedData());
    }

    @Test
    public void shouldReturnDecryptedWithKeysWherePrivateKeyIsSender() {
        final SecuredWithNemKeysPrivacyStrategy unitUnderTest =
                SecuredWithNemKeysPrivacyStrategy.create(SENDER_KEYPAIR.getPrivateKey().toString(),
                        RECEIVER_KEYPAIR.getPublicKey().toString());

        final byte[] decrypted = unitUnderTest.decryptData(sampleEncryptedData());

        assertThat(ArrayUtils.toObject(decrypted), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void shouldReturnDecryptedWithKeysWherePrivateKeyIsReceiver() {
        final SecuredWithNemKeysPrivacyStrategy unitUnderTest =
                SecuredWithNemKeysPrivacyStrategy.create(RECEIVER_KEYPAIR.getPrivateKey().toString(),
                        SENDER_KEYPAIR.getPublicKey().toString());

        final byte[] decrypted = unitUnderTest.decryptData(sampleEncryptedData());

        assertThat(ArrayUtils.toObject(decrypted), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    private byte[] sampleEncryptedData() {
        return CryptoEngines.defaultEngine().createBlockCipher(
                new KeyPair(SENDER_KEYPAIR.getPrivateKey()),
                new KeyPair(RECEIVER_KEYPAIR.getPublicKey())).encrypt(SAMPLE_DATA);
    }
}
