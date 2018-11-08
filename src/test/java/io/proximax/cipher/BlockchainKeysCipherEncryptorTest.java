package io.proximax.cipher;

import io.nem.core.crypto.Hashes;
import io.nem.core.crypto.KeyPair;
import io.nem.core.crypto.PrivateKey;
import io.nem.core.crypto.PublicKey;
import io.nem.core.crypto.ed25519.Ed25519Utils;
import io.nem.core.crypto.ed25519.arithmetic.Ed25519EncodedGroupElement;
import io.nem.core.crypto.ed25519.arithmetic.Ed25519GroupElement;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.encoders.HexEncoder;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.proximax.testsupport.Constants.TEST_PDF_FILE_1;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;

public class BlockchainKeysCipherEncryptorTest {

    public static final byte[] SAMPLE_DATA = getSampleBytes();

    public static final String PRIVATE_KEY_1 = "8374B5915AEAB6308C34368B15ABF33C79FD7FEFC0DEAF9CC51BA57F120F1190";
    public static final String PUBLIC_KEY_1 = "9E7930144DA0845361F650BF78A36791ABF2577E251706ECA45480998FE61D18";

    public static final String PRIVATE_KEY_2 = "369CB3195F88A16F8326DABBD37DA5F8458B55AA5DA6F7E2F756A12BE6CAA546";
    public static final String PUBLIC_KEY_2 = "8E1A94D534EA6A3B02B0B967701549C21724C7644B2E4C20BF15D01D50097ACB";

    public static final String PRIVATE_KEY_3 = "49DCE5457D6D83983FB4C28F0E58668DA656F8BB46AAFEEC800EBD420E1FDED5";
    public static final String PUBLIC_KEY_3 = "353BCDAF409724F9E3F7D1246E91BFD3EC1782D9C04D72C0C162F4B346126E45";

    private BlockchainKeysCipherEncryptor unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new BlockchainKeysCipherEncryptor();
    }

    @Test
    public void testJsLibrary() throws IOException {
        final InputStream decryptedStream = unitUnderTest.decryptStream(new ByteArrayInputStream(
                        Hex.decode("eefaec79a5167582b032099247b7cabc80055c0a6e097c687954e5d7baf630a33726925ed54d8f92ce54be66cc2485fcc841f4ad18a696b2e5cc178b32f5da5ec3673485c26376e104f475822040fe20")
                ),
                new KeyPair(PrivateKey.fromHexString("2a91e1d5c110a8d0105aad4683f962c2a56663a3cad46666b16d243174673d90")),
                new KeyPair(PublicKey.fromHexString("1671038b892f9fcca2122cd455a6c084bf3451a126bad1f6001e26d38735751a")));

        System.out.println(new String(IOUtils.toByteArray(decryptedStream)));
    }

    @Test
    public void testJsSdk() throws IOException {
        final InputStream decryptedStream = unitUnderTest.decryptStream(new ByteArrayInputStream(
                        Hex.decode("aaa7fb05dd8c3f46611010e098240f059ab8dad7d9d8be793f2999086b5508d7a5be577ce024a46b5932a3445ae6d06ea3a814fa75494f688f8a4e51d6fe788f8eb6c27a248d552201b2ae400b771340c7a98a0d6b787008f78e6f8c3d73b26afd9551226b2cbc401ae777f6994d0f9fc1e2623571cfbab8520a8ae9c164e811478a3448f2395b77e4d8f6a2ede206359f86efff5a7c7a1a7af4856eb4b73b1f2b1c38e9e4bd2bf9f6b9113422fab01c0a183a2ce4887ed9a37b85173fd016dc")
                ),
                new KeyPair(PrivateKey.fromHexString("4F03FDC5BCF3AE004FBEBF23D321DFE600FBB1B6739A7DAC45C58834EAD48193")),
                new KeyPair(PublicKey.fromHexString("633FD217940F7FEB21FF6477DEBF6ADECDA2891561276AFC393D979E14470B39")));

        System.out.println(new String(IOUtils.toByteArray(decryptedStream)));
    }

    @Test
    public void testSwiftSdk() throws IOException {
        final InputStream decryptedStream = unitUnderTest.decryptStream(new ByteArrayInputStream(
                        Hex.decode("C5B63458BDDE656AC3F76351AB501BEEACC3F8DEA0843F8100F87BB44D2B15F789B2E1D30CACFF4916C7258CFAD2E24B0AA3295BAEA2161E15B6F12592CD8234")
                ),
                new KeyPair(PrivateKey.fromHexString("8374B5915AEAB6308C34368B15ABF33C79FD7FEFC0DEAF9CC51BA57F120F1190")),
                new KeyPair(PublicKey.fromHexString("8E1A94D534EA6A3B02B0B967701549C21724C7644B2E4C20BF15D01D50097ACB")));

        System.out.println(new String(IOUtils.toByteArray(decryptedStream)));
    }

    @Test
    public void testCSharp() throws IOException {
        final InputStream decryptedStream = unitUnderTest.decryptStream(new ByteArrayInputStream(
                        Hex.decode("39a4e7c931720c6ff86d834af7ac1de7b4742e1c859567e6de59d45c0c106dc6fd0a2f01f2b9f3917a1030846fd6e88ed860ec7b30a0325422c6645de075e71f")
                ),
                new KeyPair(PrivateKey.fromHexString("2a91e1d5c110a8d0105aad4683f962c2a56663a3cad46666b16d243174673d90")),
                new KeyPair(PublicKey.fromHexString("1671038b892f9fcca2122cd455a6c084bf3451a126bad1f6001e26d38735751a")));

        System.out.println(new String(IOUtils.toByteArray(decryptedStream)));
    }

    @Test
    public void test2() throws IOException {
        final InputStream encryptedStream = unitUnderTest.encryptStream(new ByteArrayInputStream(
                        "ProximaX is an advanced extension of the Blockchain and Distributed Ledger Technology (DLT) with utility-rich services and protocols".getBytes()
                ),
                new KeyPair(PrivateKey.fromHexString("4F03FDC5BCF3AE004FBEBF23D321DFE600FBB1B6739A7DAC45C58834EAD48193")),
                new KeyPair(PublicKey.fromHexString("633FD217940F7FEB21FF6477DEBF6ADECDA2891561276AFC393D979E14470B39")));

        final byte[] bytes = IOUtils.toByteArray(encryptedStream);

        System.out.println(bytes.length);
        System.out.println(Hex.toHexString(bytes));
        System.out.println("6J85Os1THbCmCK476OnrTuBG4seqf1".toUpperCase());
    }

    @Test
    public void testCsharpKeyDerivation() throws IOException {

        String privateKey1 = "2a91e1d5c110a8d0105aad4683f962c2a56663a3cad46666b16d243174673d90";
        String privateKey2 = "2618090794e9c9682f2ac6504369a2f4fb9fe7ee7746f9560aca228d355b1cb9";

        System.out.println(new KeyPair(PrivateKey.fromHexString(privateKey1)).getPublicKey());
        System.out.println(new KeyPair(PrivateKey.fromHexString(privateKey2)).getPublicKey());
        System.out.println();

        byte[] salt = Hex.decode("0a5103646209c911468723ea67095ed325e5faa35a319d338edcfc7e32c5e30c");
        Ed25519GroupElement senderA = (new Ed25519EncodedGroupElement(
                new KeyPair(
                        PrivateKey.fromHexString(privateKey1))
                        .getPublicKey().getRaw()))
                .decode();
        senderA.precomputeForScalarMultiplication();
        byte[] sharedKey = senderA.scalarMultiply(
                Ed25519Utils.prepareForScalarMultiply(
                        PrivateKey.fromHexString(privateKey2)))
                .encode().getRaw();

        for(int i = 0; i < 32; ++i) {
            sharedKey[i] ^= salt[i];
        }

        System.out.println("shared key before salt " + Hex.toHexString(sharedKey));

        final byte[] derivedKey = Hashes.sha3_256(new byte[][]{sharedKey});

        final SecretKeySpec secreKey = new SecretKeySpec(derivedKey, "AES");

        System.out.println("salted shared key " + Hex.toHexString(secreKey.getEncoded()));
    }

    @Test
    public void testJsKeyDerivation() throws IOException {

        String privateKey1 = "2a91e1d5c110a8d0105aad4683f962c2a56663a3cad46666b16d243174673d90";
        String privateKey2 = "2618090794e9c9682f2ac6504369a2f4fb9fe7ee7746f9560aca228d355b1cb9";

        System.out.println(new KeyPair(PrivateKey.fromHexString(privateKey1)).getPublicKey());
        System.out.println(new KeyPair(PrivateKey.fromHexString(privateKey2)).getPublicKey());
        System.out.println();

        byte[] salt = Hex.decode("0a5103646209c911468723ea67095ed325e5faa35a319d338edcfc7e32c5e30c");
        Ed25519GroupElement senderA = (new Ed25519EncodedGroupElement(
                new KeyPair(
                        PrivateKey.fromHexString(privateKey1))
                        .getPublicKey().getRaw()))
                .decode();
        senderA.precomputeForScalarMultiplication();
        byte[] sharedKey = senderA.scalarMultiply(
                Ed25519Utils.prepareForScalarMultiply(
                        PrivateKey.fromHexString(privateKey2)))
                .encode().getRaw();

        for(int i = 0; i < 32; ++i) {
            sharedKey[i] ^= salt[i];
        }

        System.out.println("shared key before salt " + Hex.toHexString(sharedKey));

        final byte[] derivedKey = Hashes.sha3_256(new byte[][]{sharedKey});

        final SecretKeySpec secreKey = new SecretKeySpec(derivedKey, "AES");

        System.out.println("salted shared key " + Hex.toHexString(secreKey.getEncoded()));
    }



    @Test
    public void shouldReturnEncryptedDataOnEncrypt() throws IOException {
        final InputStream encryptedStream = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA),
                new KeyPair(PrivateKey.fromHexString(PRIVATE_KEY_1)),
                new KeyPair(PublicKey.fromHexString(PUBLIC_KEY_2)));

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(encryptedStream)), not(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void shouldReturnDecryptedDataOnDecryptForSameKeys() throws IOException {
        final InputStream encryptedStream = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA),
                new KeyPair(PrivateKey.fromHexString(PRIVATE_KEY_1)),
                new KeyPair(PublicKey.fromHexString(PUBLIC_KEY_2)));

        final InputStream decrypted = unitUnderTest.decryptStream(encryptedStream,
                new KeyPair(PrivateKey.fromHexString(PRIVATE_KEY_1)),
                new KeyPair(PublicKey.fromHexString(PUBLIC_KEY_2)));

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(decrypted)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test
    public void shouldReturnDecryptedDataOnDecryptForOppositeKeys() throws IOException {
        final InputStream encryptedStream = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA),
                new KeyPair(PrivateKey.fromHexString(PRIVATE_KEY_1)),
                new KeyPair(PublicKey.fromHexString(PUBLIC_KEY_2)));

        final InputStream decrypted = unitUnderTest.decryptStream(encryptedStream,
                new KeyPair(PrivateKey.fromHexString(PRIVATE_KEY_2)),
                new KeyPair(PublicKey.fromHexString(PUBLIC_KEY_1)));

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(decrypted)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test(expected = IOException.class)
    public void failOnDecryptWhenIncorrectPrivateKey() throws IOException {
        final InputStream encryptedStream = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA),
                new KeyPair(PrivateKey.fromHexString(PRIVATE_KEY_1)),
                new KeyPair(PublicKey.fromHexString(PUBLIC_KEY_2)));

        final InputStream decrypted = unitUnderTest.decryptStream(encryptedStream,
                new KeyPair(PrivateKey.fromHexString(PRIVATE_KEY_3)),
                new KeyPair(PublicKey.fromHexString(PUBLIC_KEY_1)));

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(decrypted)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    @Test(expected = IOException.class)
    public void failOnDecryptWhenIncorrectPublicKey() throws IOException {
        final InputStream encryptedStream = unitUnderTest.encryptStream(new ByteArrayInputStream(SAMPLE_DATA),
                new KeyPair(PrivateKey.fromHexString(PRIVATE_KEY_1)),
                new KeyPair(PublicKey.fromHexString(PUBLIC_KEY_2)));

        final InputStream decrypted = unitUnderTest.decryptStream(encryptedStream,
                new KeyPair(PrivateKey.fromHexString(PRIVATE_KEY_1)),
                new KeyPair(PublicKey.fromHexString(PUBLIC_KEY_3)));

        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(decrypted)), is(arrayContaining(ArrayUtils.toObject(SAMPLE_DATA))));
    }

    private static byte[] getSampleBytes() {
        try {
            return FileUtils.readFileToByteArray(TEST_PDF_FILE_1);
        } catch (IOException e) {
            return null;
        }
    }
}
