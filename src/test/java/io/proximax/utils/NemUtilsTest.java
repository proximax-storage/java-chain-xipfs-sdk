package io.proximax.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

import java.math.BigInteger;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import io.proximax.sdk.model.account.Account;
import io.proximax.sdk.model.account.Address;
import io.proximax.sdk.model.blockchain.NetworkType;
import io.proximax.sdk.model.mosaic.NetworkCurrencyMosaic;
import io.proximax.sdk.model.transaction.Deadline;
import io.proximax.sdk.model.transaction.PlainMessage;
import io.proximax.sdk.model.transaction.SignedTransaction;
import io.proximax.sdk.model.transaction.TransferTransaction;

public class NemUtilsTest {

    private static final String NETWORK_GENERATION_HASH = "122EB09F00E1F6AE6ABA96977E7676575E315CBDF79A83164FFA03B7CAE88927";
    private static final String SAMPLE_PRIVATE_KEY = "322EB09F00E1F6AE6ABA96977E7676575E315CBDF79A83164FFA03B7CAE88927";
    private static final String SAMPLE_PUBLIC_KEY = "2EEDB614740F60B11F3E4EC387388D8826CDFF33C0B0DACB9BA0BB8793DEBF6E";
    private static final Address SAMPLE_ADDRESS = Address.createFromRawAddress("VAFHIY4GYEAQUZK4ECTAZD5R3JU2KFWEPW54ZBHA");

    private NemUtils unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new NemUtils(NetworkType.TEST_NET);
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
        unitUnderTest.signTransaction(null, sampleTransferTransaction(), NETWORK_GENERATION_HASH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnSignTransactionWhenNullTransferTransaction() {
        unitUnderTest.signTransaction(SAMPLE_PRIVATE_KEY, null, NETWORK_GENERATION_HASH);
    }

    @Test
    public void shouldReturnSignedTransactionOnSignTransaction() {
        final SignedTransaction signedTransaction = unitUnderTest.signTransaction(SAMPLE_PRIVATE_KEY, sampleTransferTransaction(), NETWORK_GENERATION_HASH);

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
        return TransferTransaction.create(
                Deadline.create(24, ChronoUnit.HOURS),
                SAMPLE_ADDRESS,
                Collections.singletonList(NetworkCurrencyMosaic.createAbsolute(BigInteger.ONE)),
                new PlainMessage("test"),
                NetworkType.TEST_NET);
    }

}
