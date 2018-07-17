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
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class NemUtilsTest {

    private static final String SAMPLE_SIGNER_PRIVATE_KEY = "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C";
    private static final String SAMPLE_RECIPIENT_PUBLIC_KEY = "E9F6576AF9F05E6738CD4E55B875A823CC75B4E8AE8984747DF7B235685C1577";
    private static final Address SAMPLE_RECIPIENT_ADDRESS = Address.createFromRawAddress("SBRHESWCLX3VGQ6CHCZNKDN6DT7GLS6CZKJXCT5F");

    private NemUtils unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new NemUtils(NetworkType.MIJIN_TEST);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnToAddressWhenNullRecipientPublicKey() {
        unitUnderTest.toAddress(null);
    }

    @Test
    public void shouldReturnAddressOnToAddress() {
        final Address address = unitUnderTest.toAddress(SAMPLE_RECIPIENT_PUBLIC_KEY);

        assertThat(address, is(SAMPLE_RECIPIENT_ADDRESS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnToAccountWhenNullSignerPrivateKey() {
        unitUnderTest.toAccount(null);
    }

    @Test
    public void shouldReturnAccountOnToAccount() {
        final Account account = unitUnderTest.toAccount(SAMPLE_SIGNER_PRIVATE_KEY);

        assertThat(account, is(notNullValue()));
        assertThat(account.getPrivateKey(), is(SAMPLE_SIGNER_PRIVATE_KEY));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnSignTransactionWhenNullSignerPrivateKey() {
        unitUnderTest.signTransaction(null, sampleTransferTransaction());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnSignTransactionWhenNullTransferTransaction() {
        unitUnderTest.signTransaction(SAMPLE_SIGNER_PRIVATE_KEY, null);
    }

    @Test
    public void shouldReturnSignedTransactionOnSignTransaction() {
        final SignedTransaction signedTransaction = unitUnderTest.signTransaction(SAMPLE_SIGNER_PRIVATE_KEY, sampleTransferTransaction());

        assertThat(signedTransaction, is(notNullValue()));;
    }

    private TransferTransaction sampleTransferTransaction() {
        return TransferTransaction.create(
                Deadline.create(24, ChronoUnit.HOURS),
                SAMPLE_RECIPIENT_ADDRESS,
                Collections.singletonList(new Mosaic(XEM.createRelative(BigInteger.valueOf(1)).getId(), BigInteger.valueOf(1))),
                new PlainMessage("test"),
                NetworkType.MIJIN_TEST);
    }

}
