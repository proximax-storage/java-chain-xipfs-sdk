package io.proximax.service;

import io.proximax.core.crypto.PrivateKey;
import io.proximax.core.crypto.PublicKey;
import io.proximax.sdk.model.account.Address;
import io.proximax.sdk.model.account.PublicAccount;
import io.proximax.sdk.model.blockchain.NetworkType;
import io.proximax.sdk.model.transaction.Message;
import io.proximax.sdk.model.transaction.PlainMessage;
import io.proximax.sdk.model.transaction.Recipient;
import io.proximax.sdk.model.transaction.SecureMessage;
import io.proximax.sdk.model.transaction.TransferTransaction;
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

    private static final String SAMPLE_PRIVATE_KEY_1 = "322EB09F00E1F6AE6ABA96977E7676575E315CBDF79A83164FFA03B7CAE88927";
    private static final String SAMPLE_PUBLIC_KEY_1 = "2EEDB614740F60B11F3E4EC387388D8826CDFF33C0B0DACB9BA0BB8793DEBF6E";
    private static final String SAMPLE_PRIVATE_KEY_2 = "E678B975F1AC5C8D88A9673287EE60840161B0117CF320CF2EBF384C17C71F9C";
    private static final String SAMPLE_PUBLIC_KEY_2 = "44AFFBEFF7EE8AFDE907EAD933C88374DE22F359CCBB6575BB14A8CB96B11C90";
    private static final String SAMPLE_ADDRESS_2 = "VC6LV4EP2MT3I5AV76IKVTPVAZRJUQDMKSABLE2M";
    private static final String SAMPLE_PRIVATE_KEY_3 = "3C5FE45A711448245203832295523623A5D09A7B49F354B54933E4D5564D50F7";

    private BlockchainMessageService unitUnderTest;

    @Mock
    private TransferTransaction mockTransferTransaction;

    @Mock
    private AccountClient mockAccountClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new BlockchainMessageService(NetworkType.TEST_NET, mockAccountClient);
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
        given(mockTransferTransaction.getSigner()).willReturn(Optional.of(PublicAccount.createFromPublicKey(SAMPLE_PUBLIC_KEY_1, NetworkType.TEST_NET)));
        given(mockTransferTransaction.getRecipient()).willReturn(Recipient.from(Address.createFromRawAddress(SAMPLE_ADDRESS_2)));

        final String result = unitUnderTest.getMessagePayload(mockTransferTransaction, SAMPLE_PRIVATE_KEY_2);

        assertThat(result, is(expectedPayload()));
    }

    @Test
    public void shouldReturnPayloadWhenSecureMessageAndRetrieverIsTheSender() {
        given(mockTransferTransaction.getMessage()).willReturn(SecureMessage.create(
                PrivateKey.fromHexString(SAMPLE_PRIVATE_KEY_1), PublicKey.fromHexString(SAMPLE_PUBLIC_KEY_2),
                expectedPayload()));
        given(mockTransferTransaction.getSigner()).willReturn(Optional.of(PublicAccount.createFromPublicKey(SAMPLE_PUBLIC_KEY_1, NetworkType.TEST_NET)));
        given(mockTransferTransaction.getRecipient()).willReturn(Recipient.from(Address.createFromRawAddress(SAMPLE_ADDRESS_2)));
        given(mockAccountClient.getPublicKey(SAMPLE_ADDRESS_2)).willReturn(PublicKey.fromHexString(SAMPLE_PUBLIC_KEY_2));

        final String result = unitUnderTest.getMessagePayload(mockTransferTransaction, SAMPLE_PRIVATE_KEY_1);

        assertThat(result, is(expectedPayload()));
    }

    @Test(expected = InvalidPrivateKeyOnDownloadException.class)
    public void failWhenSecureMessageAndAccountPrivateKeyIsNeitherSenderOrRecipient() {
        given(mockTransferTransaction.getMessage()).willReturn(SecureMessage.create(
                PrivateKey.fromHexString(SAMPLE_PRIVATE_KEY_1), PublicKey.fromHexString(SAMPLE_PUBLIC_KEY_2),
                expectedPayload()));
        given(mockTransferTransaction.getSigner()).willReturn(Optional.of(PublicAccount.createFromPublicKey(SAMPLE_PUBLIC_KEY_1, NetworkType.TEST_NET)));
        given(mockTransferTransaction.getRecipient()).willReturn(Recipient.from(Address.createFromRawAddress(SAMPLE_ADDRESS_2)));

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
                "\"dataHash\":\"QmNZqVc7tBvjs1rohmcBbRUVUNmijEzexTQ9vi7435DTys\"," +
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
