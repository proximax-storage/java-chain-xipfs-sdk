package io.proximax.privacy.strategy;

import io.proximax.core.crypto.CryptoEngines;
import io.proximax.core.crypto.KeyPair;
import io.proximax.core.crypto.PrivateKey;
import io.proximax.cipher.BlockchainKeysCipherEncryptor;
import io.proximax.model.PrivacyType;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class NemKeysPrivacyStrategyTest {

    private static final byte[] SAMPLE_RAW_DATA = "the quick brown fox jumps over the lazy dog".getBytes();
    private static final byte[] SAMPLE_ENCRYPTED_DATA = "DIASYUOIDHKJckxhzkhkahdhsao".getBytes();

    private static final KeyPair SENDER_KEYPAIR = new KeyPair(PrivateKey.fromHexString("08871D3EB4CF3D6695A61E8E1B60DC64DCC9EED40F33D4848BF9079168CCD4A4"));

    private static final KeyPair RECEIVER_KEYPAIR = new KeyPair(PrivateKey.fromHexString("1A5B81AE8830B8A79232CD366552AF6496FE548B4A23D4173FEEBA41B8ABA81F"));

    private static final KeyPair ANOTHER_KEYPAIR = new KeyPair(PrivateKey.fromHexString("CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C"));

    @Mock
    private BlockchainKeysCipherEncryptor encryptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnCorrectPrivacyType() {
        final int privacyType = new NemKeysPrivacyStrategy(encryptor, SENDER_KEYPAIR.getPrivateKey().toString(),
                RECEIVER_KEYPAIR.getPublicKey().toString()).getPrivacyType();

        assertThat(privacyType, is(PrivacyType.NEMKEYS.getValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failInitWithoutPrivateKey() {
        new NemKeysPrivacyStrategy(encryptor, null, RECEIVER_KEYPAIR.getPublicKey().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failInitWithoutPublicKey() {
        new NemKeysPrivacyStrategy(encryptor, SENDER_KEYPAIR.getPrivateKey().toString(), null);
    }

    @Test
    public void shouldReturnEncryptedWithKeys() {
        InputStream dummyEncryptedStream = new ByteArrayInputStream(SAMPLE_ENCRYPTED_DATA);
        given(encryptor.encryptStream(any(), any(), any()))
                .willReturn(dummyEncryptedStream);

        final NemKeysPrivacyStrategy unitUnderTest =
                new NemKeysPrivacyStrategy(encryptor, SENDER_KEYPAIR.getPrivateKey().toString(),
                        RECEIVER_KEYPAIR.getPublicKey().toString());

        final InputStream encrypted = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_RAW_DATA));

        assertThat(encrypted, is(dummyEncryptedStream));
    }

    @Test
    public void shouldReturnDecryptedWithKeys() throws IOException {
        InputStream dummyDecryptedStream = new ByteArrayInputStream(SAMPLE_RAW_DATA);
        given(encryptor.decryptStream(any(), any(), any()))
                .willReturn(dummyDecryptedStream);

        final NemKeysPrivacyStrategy unitUnderTest =
                new NemKeysPrivacyStrategy(encryptor, SENDER_KEYPAIR.getPrivateKey().toString(),
                        RECEIVER_KEYPAIR.getPublicKey().toString());

        final InputStream decrypted = unitUnderTest.decryptStream(new ByteArrayInputStream(SAMPLE_ENCRYPTED_DATA));

        assertThat(decrypted, is(dummyDecryptedStream));
    }

    @Test
    public void shouldReturnSameEncrypted() throws IOException {
        final NemKeysPrivacyStrategy unitUnderTest =
                new NemKeysPrivacyStrategy(new BlockchainKeysCipherEncryptor(), SENDER_KEYPAIR.getPrivateKey().toString(),
                        RECEIVER_KEYPAIR.getPublicKey().toString());

        final InputStream encrypted = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_RAW_DATA));

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(encrypted)), not(arrayContaining(ArrayUtils.toObject(SAMPLE_RAW_DATA))));
    }

    @Test
    public void shouldReturnSameDecrypted() throws IOException {
        final NemKeysPrivacyStrategy unitUnderTest =
                new NemKeysPrivacyStrategy(new BlockchainKeysCipherEncryptor(), SENDER_KEYPAIR.getPrivateKey().toString(),
                        RECEIVER_KEYPAIR.getPublicKey().toString());

        final InputStream decrypted = unitUnderTest.decryptStream(new ByteArrayInputStream(sampleEncryptedData()));

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(decrypted)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_RAW_DATA))));
    }

    private byte[] sampleEncryptedData() {
        return CryptoEngines.defaultEngine().createBlockCipher(
                new KeyPair(SENDER_KEYPAIR.getPrivateKey()),
                new KeyPair(RECEIVER_KEYPAIR.getPublicKey())).encrypt(SAMPLE_RAW_DATA);
    }
}
