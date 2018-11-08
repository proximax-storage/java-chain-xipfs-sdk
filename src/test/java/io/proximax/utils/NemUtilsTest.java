package io.proximax.utils;

import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.mosaic.Mosaic;
import io.nem.sdk.model.mosaic.XEM;
import io.nem.sdk.model.transaction.Deadline;
import io.nem.sdk.model.transaction.PlainMessage;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.cipher.PBECipherEncryptor;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.HexEncoder;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class NemUtilsTest {

    private static final String SAMPLE_PRIVATE_KEY = "1A5B81AE8830B8A79232CD366552AF6496FE548B4A23D4173FEEBA41B8ABA81F";
    private static final String SAMPLE_PUBLIC_KEY = "E9F6576AF9F05E6738CD4E55B875A823CC75B4E8AE8984747DF7B235685C1577";
    private static final Address SAMPLE_ADDRESS = Address.createFromRawAddress("SBRHESWCLX3VGQ6CHCZNKDN6DT7GLS6CZKJXCT5F");

    private NemUtils unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new NemUtils(NetworkType.MIJIN_TEST);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnGetAddressWhenNullAddress() {
        unitUnderTest.getAddress(null);
    }

    @Test
    public void shouldReturnAddressOnGetAddress() {
        final Address address = unitUnderTest.getAddress(SAMPLE_ADDRESS.plain());

        assertThat(address, is(SAMPLE_ADDRESS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnGetAddressFromPublicKeyWhenNullPublicKey() {
        unitUnderTest.getAddressFromPublicKey(null);
    }

    @Test
    public void shouldReturnAddressOnGetAddressFromPublicKey() {
        final Address address = unitUnderTest.getAddressFromPublicKey(SAMPLE_PUBLIC_KEY);

        assertThat(address, is(SAMPLE_ADDRESS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnGetAddressFromPublicKeyWhenNullPrivateKey() {
        unitUnderTest.getAddressFromPrivateKey(null);
    }

    @Test
    public void shouldReturnAddressOnGetAddressFromPrivateKey() {
        final Address address = unitUnderTest.getAddressFromPrivateKey(SAMPLE_PRIVATE_KEY);

        assertThat(address, is(SAMPLE_ADDRESS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnGetAccountWhenNullPrivateKey() {
        unitUnderTest.getAccount(null);
    }

    @Test
    public void shouldReturnAccountOnGetAccount() {
        final Account account = unitUnderTest.getAccount(SAMPLE_PRIVATE_KEY);

        assertThat(account, is(notNullValue()));
        assertThat(account.getPrivateKey(), is(SAMPLE_PRIVATE_KEY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnSignTransactionWhenNullSignerPrivateKey() {
        unitUnderTest.signTransaction(null, sampleTransferTransaction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnSignTransactionWhenNullTransferTransaction() {
        unitUnderTest.signTransaction(SAMPLE_PRIVATE_KEY, null);
    }

    @Test
    public void shouldReturnSignedTransactionOnSignTransaction() {
        final SignedTransaction signedTransaction = unitUnderTest.signTransaction(SAMPLE_PRIVATE_KEY, sampleTransferTransaction());

        assertThat(signedTransaction, is(notNullValue()));;
    }

    @Test
    public void shouldGenerateAccount() {
        final Account account = new NemUtils(NetworkType.MIJIN).generateAccount();

        assertThat(account, is(notNullValue()));
        assertThat(account.getAddress(), is(notNullValue()));
        assertThat(account.getPrivateKey(), is(notNullValue()));
        assertThat(account.getPublicKey(), is(notNullValue()));

        System.out.println("Address: " + account.getAddress().plain());
        System.out.println("Private Key: " + account.getPrivateKey());
        System.out.println("Public Key: " + account.getPublicKey());

        System.out.println("Address: " +
                Address.createFromPublicKey("1b835c72da29399067e4e6eaaeb06912227db3849d15978d4321b307389660a2",
                        NetworkType.MIJIN).plain());
        System.out.println("Address: " +
                Address.createFromPublicKey("1b835c72da29399067e4e6eaaeb06912227db3849d15978d4321b307389660a2",
                        NetworkType.MIJIN_TEST).plain());
        System.out.println("Address: " +
                Address.createFromPublicKey("1b835c72da29399067e4e6eaaeb06912227db3849d15978d4321b307389660a2",
                        NetworkType.MAIN_NET).plain());
        System.out.println("Address: " +
                Address.createFromPublicKey("1b835c72da29399067e4e6eaaeb06912227db3849d15978d4321b307389660a2",
                        NetworkType.TEST_NET).plain());

    }

    @Test
    public void testAddress() {
        final String publicKey = Account.createFromPrivateKey("2618090794e9c9682f2ac6504369a2f4fb9fe7ee7746f9560aca228d355b1cb9", NetworkType.MIJIN).getPublicKey();

        System.out.println("Public Key: " + publicKey);
        System.out.println("Address (MIJIN): " + Address.createFromPublicKey(publicKey, NetworkType.MIJIN).plain());
        System.out.println("Address (MAINNET): " + Address.createFromPublicKey(publicKey, NetworkType.MAIN_NET).plain());
        System.out.println("Address (TESTNET): " + Address.createFromPublicKey(publicKey, NetworkType.TEST_NET).plain());
    }


    @Test
    public void testSha3() {
        String input = "Hello world !";
        SHA3.DigestSHA3 digestSHA3_512 = new SHA3.Digest512();
        byte[] digest512 = digestSHA3_512.digest(input.getBytes());
        System.out.println("SHA3-512 = " + Hex.encodeHexString(digest512));
        SHA3.DigestSHA3 digestSHA3_256 = new SHA3.Digest256();
        byte[] digest256 = digestSHA3_256.digest(input.getBytes());
        System.out.println("SHA3-256 = " + Hex.encodeHexString(digest256));
    }

    @Test
    public void testEncryptToHex() throws IOException {
        final String cipherAsHex = encryptToHex("quick brown fox".getBytes(), "password".toCharArray());
        System.out.println(cipherAsHex);
    }

    public static String encryptToHex(byte[] binary, char[] password) throws IOException {
        PBECipherEncryptor basicBinaryEncryptor = new PBECipherEncryptor();
        String cipherTextAsHex = Hex.encodeHexString(IOUtils.toByteArray(basicBinaryEncryptor.encryptStream(new ByteArrayInputStream(binary), password)));
        return cipherTextAsHex;
    }

    private TransferTransaction sampleTransferTransaction() {
        return TransferTransaction.create(
                Deadline.create(24, ChronoUnit.HOURS),
                SAMPLE_ADDRESS,
                Collections.singletonList(new Mosaic(XEM.createRelative(BigInteger.valueOf(1)).getId(), BigInteger.valueOf(1))),
                new PlainMessage("test".getBytes()),
                NetworkType.MIJIN_TEST);
    }

}
