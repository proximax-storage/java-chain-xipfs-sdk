package io.proximax.service;

import io.nem.core.crypto.PrivateKey;
import io.nem.core.crypto.PublicKey;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.account.PublicAccount;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.PlainMessage;
import io.nem.sdk.model.transaction.SecureMessage;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.exceptions.DownloadForMessageTypeNotSupportedException;
import io.proximax.exceptions.InvalidPrivateKeyOnDownloadException;
import io.proximax.exceptions.MissingPrivateKeyOnDownloadException;
import io.proximax.exceptions.MissingSignerOnTransferTransactionException;
import io.proximax.service.client.catapult.AccountClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

public class BlockchainMessageService_getMessagePayloadTest {

    private static final String SAMPLE_PRIVATE_KEY_1 = "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C";
    private static final String SAMPLE_PUBLIC_KEY_1 = "0BB0FC937EF6C10AD16ABCC3FF4A2848F16BF360A98D64140E7674F31702903B";
    private static final String SAMPLE_PRIVATE_KEY_2 = "1A5B81AE8830B8A79232CD366552AF6496FE548B4A23D4173FEEBA41B8ABA81F";
    private static final String SAMPLE_PUBLIC_KEY_2 = "E9F6576AF9F05E6738CD4E55B875A823CC75B4E8AE8984747DF7B235685C1577";
    private static final String SAMPLE_ADDRESS_2 = "SBRHESWCLX3VGQ6CHCZNKDN6DT7GLS6CZKJXCT5F";
    private static final String SAMPLE_PRIVATE_KEY_3 = "49DCE5457D6D83983FB4C28F0E58668DA656F8BB46AAFEEC800EBD420E1FDED5";

    private BlockchainMessageService unitUnderTest;

    @Mock
    private TransferTransaction mockTransferTransaction;

    @Mock
    private AccountClient mockAccountClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new BlockchainMessageService(NetworkType.MIJIN_TEST, mockAccountClient);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullTransferTransaction() {
        unitUnderTest.getMessagePayload(null, null);
    }

    @Test
    public void shouldReturnPayloadWhenPlainMessage() {
        given(mockTransferTransaction.getMessage()).willReturn(PlainMessage.create(expectedPayload()));

        final String result = unitUnderTest.getMessagePayload(mockTransferTransaction, null);

        assertThat(result, is(expectedPayload()));
    }

    @Test(expected = MissingPrivateKeyOnDownloadException.class)
    public void failWhenSecureMessageAndNoPrivateKey() {
        given(mockTransferTransaction.getMessage()).willReturn(SecureMessage.create(
                PrivateKey.fromHexString(SAMPLE_PRIVATE_KEY_1), PublicKey.fromHexString(SAMPLE_PUBLIC_KEY_2),
                expectedPayload()));

        unitUnderTest.getMessagePayload(mockTransferTransaction, null);
    }

    @Test(expected = MissingSignerOnTransferTransactionException.class)
    public void failWhenSecureMessageAndTxnHasNoSigner() {
        given(mockTransferTransaction.getMessage()).willReturn(SecureMessage.create(
                PrivateKey.fromHexString(SAMPLE_PRIVATE_KEY_1), PublicKey.fromHexString(SAMPLE_PUBLIC_KEY_2),
                expectedPayload()));
        given(mockTransferTransaction.getSigner()).willReturn(Optional.empty());

        unitUnderTest.getMessagePayload(mockTransferTransaction, SAMPLE_PRIVATE_KEY_1);
    }

    @Test
    public void shouldReturnPayloadWhenSecureMessageAndRetrieverIsTheRecipient() {
        given(mockTransferTransaction.getMessage()).willReturn(SecureMessage.create(
                PrivateKey.fromHexString(SAMPLE_PRIVATE_KEY_1), PublicKey.fromHexString(SAMPLE_PUBLIC_KEY_2),
                expectedPayload()));
        given(mockTransferTransaction.getSigner()).willReturn(Optional.of(PublicAccount.createFromPublicKey(SAMPLE_PUBLIC_KEY_1, NetworkType.MIJIN_TEST)));
        given(mockTransferTransaction.getRecipient()).willReturn(Address.createFromRawAddress(SAMPLE_ADDRESS_2));

        final String result = unitUnderTest.getMessagePayload(mockTransferTransaction, SAMPLE_PRIVATE_KEY_2);

        assertThat(result, is(expectedPayload()));
    }

    @Test
    public void shouldReturnPayloadWhenSecureMessageAndRetrieverIsTheSender() {
        given(mockTransferTransaction.getMessage()).willReturn(SecureMessage.create(
                PrivateKey.fromHexString(SAMPLE_PRIVATE_KEY_1), PublicKey.fromHexString(SAMPLE_PUBLIC_KEY_2),
                expectedPayload()));
        given(mockTransferTransaction.getSigner()).willReturn(Optional.of(PublicAccount.createFromPublicKey(SAMPLE_PUBLIC_KEY_1, NetworkType.MIJIN_TEST)));
        given(mockTransferTransaction.getRecipient()).willReturn(Address.createFromRawAddress(SAMPLE_ADDRESS_2));
        given(mockAccountClient.getPublicKey(SAMPLE_ADDRESS_2)).willReturn(PublicKey.fromHexString(SAMPLE_PUBLIC_KEY_2));

        final String result = unitUnderTest.getMessagePayload(mockTransferTransaction, SAMPLE_PRIVATE_KEY_1);

        assertThat(result, is(expectedPayload()));
    }

    @Test(expected = InvalidPrivateKeyOnDownloadException.class)
    public void failWhenSecureMessageAndAccountPrivateKeyIsNeitherSenderOrRecipient() {
        given(mockTransferTransaction.getMessage()).willReturn(SecureMessage.create(
                PrivateKey.fromHexString(SAMPLE_PRIVATE_KEY_1), PublicKey.fromHexString(SAMPLE_PUBLIC_KEY_2),
                expectedPayload()));
        given(mockTransferTransaction.getSigner()).willReturn(Optional.of(PublicAccount.createFromPublicKey(SAMPLE_PUBLIC_KEY_1, NetworkType.MIJIN_TEST)));
        given(mockTransferTransaction.getRecipient()).willReturn(Address.createFromRawAddress(SAMPLE_ADDRESS_2));

        unitUnderTest.getMessagePayload(mockTransferTransaction, SAMPLE_PRIVATE_KEY_3);
    }

    @Test(expected = DownloadForMessageTypeNotSupportedException.class)
    public void failWhenMessageTypeIsUnknown() {
        given(mockTransferTransaction.getMessage()).willReturn(new Message(999, null, null){});

        unitUnderTest.getMessagePayload(mockTransferTransaction, null);
    }

    private String expectedPayload() {
        return "{" +
            "\"privacyType\":1001," +
            "\"version\":\"1.0\"," +
            "\"data\":{" +
                "\"digest\":\"eqwewqewqewqewqewq\"," +
                "\"dataHash\":\"QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf\"," +
                "\"timestamp\":1," +
                "\"description\":\"test description\"," +
                "\"metadata\":{" +
                    "\"testKey\":\"testValue\"" +
                "}," +
                "\"name\":\"test name\"," +
                "\"contentType\":\"text/plain\"" +
            "}}";
    }

}
