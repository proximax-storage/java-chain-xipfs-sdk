package io.proximax.utils;

import io.proximax.sdk.model.account.Account;
import io.proximax.sdk.model.account.Address;
import io.proximax.sdk.model.blockchain.NetworkType;
import io.proximax.sdk.model.mosaic.Mosaic;
import io.proximax.sdk.model.mosaic.MosaicId;
import io.proximax.sdk.model.transaction.Deadline;
import io.proximax.sdk.model.transaction.PlainMessage;
import io.proximax.sdk.model.transaction.SignedTransaction;
import io.proximax.sdk.model.transaction.TransferTransaction;
import org.junit.Before;
import org.junit.Test;

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
        final Account account = unitUnderTest.generateAccount();

        assertThat(account, is(notNullValue()));
        assertThat(account.getAddress(), is(notNullValue()));
        assertThat(account.getPrivateKey(), is(notNullValue()));
        assertThat(account.getPublicKey(), is(notNullValue()));

        System.out.println("Address: " + account.getAddress().plain());
        System.out.println("Private Key: " + account.getPrivateKey());
        System.out.println("Public Key: " + account.getPublicKey());
    }

    private TransferTransaction sampleTransferTransaction() {        
        MosaicId ID = new MosaicId(new BigInteger("0DC67FBE1CAD29E3", 16));
        return TransferTransaction.create(
                Deadline.create(24, ChronoUnit.HOURS),
                SAMPLE_ADDRESS,
                Collections.singletonList(new Mosaic(ID, BigInteger.valueOf(1))),
                new PlainMessage("test"),
                NetworkType.MIJIN_TEST);
    }

}
