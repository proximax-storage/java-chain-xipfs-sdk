/*
 * Copyright 2018 ProximaX Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.proximax.service;

import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.mosaic.MosaicId;
import io.nem.sdk.model.transaction.AggregateTransaction;
import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.TransactionType;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.exceptions.AnnounceBlockchainTransactionFailureException;
import io.proximax.exceptions.GetTransactionFailureException;
import io.proximax.exceptions.TransactionNotAllowedException;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.service.client.catapult.TransactionClient;
import io.proximax.utils.NemUtils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

import static io.proximax.service.client.catapult.TransactionClient.STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION;
import static org.exparity.hamcrest.date.LocalDateTimeMatchers.sameOrBefore;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

public class BlockchainTransactionServiceTest {

    private static final String SAMPLE_TRANSACTION_HASH = "F08E3C327DD5DE258EF20532F4D3C7638E9AC44885C34FDDC1A5740FD3C56EBB";
    private static final String SAMPLE_SIGNER_PRIVATE_KEY = "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C";
    private static final String SAMPLE_RECIPIENT_PUBLIC_KEY = "E9F6576AF9F05E6738CD4E55B875A823CC75B4E8AE8984747DF7B235685C1577";
    private static final Address SAMPLE_RECIPIENT_ADDRESS = Address.createFromRawAddress("SBRHESWCLX3VGQ6CHCZNKDN6DT7GLS6CZKJXCT5F");
    private static final Account SAMPLE_SIGNER_ACCOUNT = Account.createFromPrivateKey("CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C", NetworkType.MIJIN_TEST);

    private BlockchainTransactionService unitUnderTest;

    @Mock
    private BlockchainNetworkConnection mockBlockchainNetworkConnection;

    @Mock
    private TransactionClient mockTransactionClient;

    @Mock
    private AggregateTransaction mockAggregateTransaction;

    @Mock
    private TransferTransaction mockTransferTransaction;

    @Mock
    private NemUtils mockNemUtils;

    @Mock
    private BlockchainMessageService mockBlockchainMessageService;

    @Mock
    private Message mockMessage;

    @Mock
    private ProximaxMessagePayloadModel mockMessagePayload;

    @Mock
    private SignedTransaction mockSignedTransaction;

    @Captor
    private ArgumentCaptor<String> signerPrivateKeyArgumentCaptor;

    @Captor
    private ArgumentCaptor<TransferTransaction> transferTransactionArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new BlockchainTransactionService(mockBlockchainNetworkConnection, mockTransactionClient, mockNemUtils, mockBlockchainMessageService);

        given(mockBlockchainNetworkConnection.getNetworkType()).willReturn(NetworkType.MIJIN_TEST);
        given(mockTransferTransaction.getType()).willReturn(TransactionType.TRANSFER);
        given(mockAggregateTransaction.getType()).willReturn(TransactionType.AGGREGATE_COMPLETE);
        given(mockSignedTransaction.getHash()).willReturn(SAMPLE_TRANSACTION_HASH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnGetTransferTransactionWhenNullTransactionHash() {
        unitUnderTest.getTransferTransaction(null);
    }

    @Test(expected = GetTransactionFailureException.class)
    public void failOnGetTransferTransactionWhenInvalidTransactionHash() {
        given(mockTransactionClient.getTransaction(SAMPLE_TRANSACTION_HASH))
                .willReturn(Observable.error(new RuntimeException()));

        unitUnderTest.getTransferTransaction(SAMPLE_TRANSACTION_HASH).blockingFirst();
    }

    @Test(expected = TransactionNotAllowedException.class)
    public void failOnGetTransferTransactionWhenNotTransferTransaction() {
        given(mockTransactionClient.getTransaction(SAMPLE_TRANSACTION_HASH))
                .willReturn(Observable.just(mockAggregateTransaction));

        unitUnderTest.getTransferTransaction(SAMPLE_TRANSACTION_HASH).blockingFirst();
    }

    @Test
    public void shouldReturnTransferTransactionOnGetTransferTransaction() {
        given(mockTransactionClient.getTransaction(SAMPLE_TRANSACTION_HASH))
                .willReturn(Observable.just(mockTransferTransaction));

        final TransferTransaction transferTransaction = unitUnderTest.getTransferTransaction(SAMPLE_TRANSACTION_HASH).blockingFirst();

        assertThat(transferTransaction, is(mockTransferTransaction));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnCreateAndAnnounceTransactionWhenNullMessagePayload() {
        unitUnderTest.createAndAnnounceTransaction(null, SAMPLE_SIGNER_PRIVATE_KEY,
                null, null, 12, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnCreateAndAnnounceTransactionWhenNullSignerPrivateKey() {
        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, null,
                null, null, 12, false);
    }

    @Test(expected = RuntimeException.class)
    public void shouldBubbleUpExceptionsOnCreateAndAnnounceTransaction() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                null, false)).willReturn(mockMessage);
        given(mockNemUtils.getAddressFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY)).willThrow(new RuntimeException());

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                SAMPLE_RECIPIENT_PUBLIC_KEY, SAMPLE_RECIPIENT_ADDRESS.plain(), 12, false);
    }

    @Test(expected = AnnounceBlockchainTransactionFailureException.class)
    public void shouldFailWhenAnnouncementFailed() throws MalformedURLException {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                null, false)).willReturn(mockMessage);
        given(mockNemUtils.getAddressFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)).willThrow(new AnnounceBlockchainTransactionFailureException("failed"));

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                null, null, 12, false).blockingFirst();
    }

    @Test
    public void shouldSignTransactionWithCorrectDataOnCreateAndAnnounceTransaction() throws MalformedURLException {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                null, false)).willReturn(mockMessage);
        given(mockNemUtils.getAddressFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)).willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                null, null, 12, false).blockingFirst();

        assertThat(signerPrivateKeyArgumentCaptor.getValue(), is(SAMPLE_SIGNER_PRIVATE_KEY));
        assertThat(transferTransactionArgumentCaptor.getValue().getMessage(), is(mockMessage));
        assertThat(transferTransactionArgumentCaptor.getValue().getRecipient(), is(SAMPLE_RECIPIENT_ADDRESS));
        assertThat(transferTransactionArgumentCaptor.getValue().getDeadline().getLocalDateTime(), sameOrBefore(LocalDateTime.now().plusHours(12)));
        assertThat(transferTransactionArgumentCaptor.getValue().getMosaics(), hasSize(1));
        assertThat(transferTransactionArgumentCaptor.getValue().getMosaics().get(0).getId(),
                is(new MosaicId("prx:xpx")));
        assertThat(transferTransactionArgumentCaptor.getValue().getMosaics().get(0).getAmount(),
                is(BigInteger.valueOf(1)));
        assertThat(transferTransactionArgumentCaptor.getValue().getNetworkType(),
                is(NetworkType.MIJIN_TEST));
    }

    @Test
    public void shouldReturnTransactionHashOnCreateAndAnnounceTransaction() {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                null, false)).willReturn(mockMessage);
        given(mockNemUtils.getAddressFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)).willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);

        final String transactionHash =
                unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                        null, null, 12, false).blockingFirst();

        assertThat(transactionHash, is(SAMPLE_TRANSACTION_HASH));
    }

    @Test
    public void shouldUseSignerAddressWhenNullRecipientPublicKeyAndAddress() throws MalformedURLException {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                null, false)).willReturn(mockMessage);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)).willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);
        given(mockNemUtils.getAddressFromPrivateKey(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                null, null, 12, false).blockingFirst();
    }

    @Test
    public void shouldUseRecipientPublicKeyAddressWhenProvided() throws MalformedURLException {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY,
                SAMPLE_RECIPIENT_ADDRESS.plain(), false)).willReturn(mockMessage);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)).willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);
        given(mockNemUtils.getAddressFromPublicKey(SAMPLE_RECIPIENT_PUBLIC_KEY)).willReturn(SAMPLE_RECIPIENT_ADDRESS);

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                SAMPLE_RECIPIENT_PUBLIC_KEY, SAMPLE_RECIPIENT_ADDRESS.plain(), 12, false).blockingFirst();
    }

    @Test
    public void shouldUseRecipientAddressWhenNullRecipientPublicKeyButAddressProvided() throws MalformedURLException {
        given(mockBlockchainMessageService.createMessage(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY, null,
                SAMPLE_RECIPIENT_ADDRESS.plain(), false)).willReturn(mockMessage);
        given(mockNemUtils.getAccount(SAMPLE_SIGNER_PRIVATE_KEY)).willReturn(SAMPLE_SIGNER_ACCOUNT);
        given(mockNemUtils.signTransaction(signerPrivateKeyArgumentCaptor.capture(), transferTransactionArgumentCaptor.capture()))
                .willReturn(mockSignedTransaction);
        given(mockTransactionClient.announce(mockSignedTransaction, SAMPLE_RECIPIENT_ADDRESS)).willReturn(STATUS_FOR_SUCCESSFUL_UNCONFIRMED_TRANSACTION);
        given(mockNemUtils.getAddress(SAMPLE_RECIPIENT_ADDRESS.plain())).willReturn(SAMPLE_RECIPIENT_ADDRESS);

        unitUnderTest.createAndAnnounceTransaction(mockMessagePayload, SAMPLE_SIGNER_PRIVATE_KEY,
                null, SAMPLE_RECIPIENT_ADDRESS.plain(), 12, false).blockingFirst();
    }
}
