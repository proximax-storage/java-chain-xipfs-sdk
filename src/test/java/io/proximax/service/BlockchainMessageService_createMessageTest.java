package io.proximax.service;

import io.proximax.core.crypto.KeyPair;
import io.proximax.core.crypto.PrivateKey;
import io.proximax.core.crypto.PublicKey;
import io.proximax.sdk.model.blockchain.NetworkType;
import io.proximax.sdk.model.transaction.Message;
import io.proximax.sdk.model.transaction.SecureMessage;
import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxDataModel;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.service.client.catapult.AccountClient;
import io.proximax.upload.ByteArrayParameterData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;

public class BlockchainMessageService_createMessageTest {

    private static final String SAMPLE_SENDER_PRIVATE_KEY = "3C5FE45A711448245203832295523623A5D09A7B49F354B54933E4D5564D50F7";
    private static final String SAMPLE_SENDER_PUBLIC_KEY = "F100416F5831943979A7D5F53BEBA7CBD9535D9CF920A88074D698C08E9FB42B";
    private static final String SAMPLE_SENDER_ADDRESS = "VBJPBI23LGXD5FEPZRLFSZ3WJPG5NVKYAIWVBQV5";
    private static final String SAMPLE_RECIPIENT_PUBLIC_KEY = "2EEDB614740F60B11F3E4EC387388D8826CDFF33C0B0DACB9BA0BB8793DEBF6E";
    private static final String SAMPLE_RECIPIENT_ADDRESS = "VAFHIY4GYEAQUZK4ECTAZD5R3JU2KFWEPW54ZBHA";
    private static final String SAMPLE_DATA_HASH = "QmNZqVc7tBvjs1rohmcBbRUVUNmijEzexTQ9vi7435DTys";
    private static final String SAMPLE_DIGEST = "eqwewqewqewqewqewq";

    private BlockchainMessageService unitUnderTest;

    @Mock
    private AccountClient mockAccountClient;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new BlockchainMessageService(NetworkType.TEST_NET, mockAccountClient);

    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullMessagePayload() {
        unitUnderTest.createMessage(null, SAMPLE_SENDER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY,
                SAMPLE_RECIPIENT_ADDRESS, false);
    }

    @Test
    public void shouldCreatePlainMessage() {
        final Message result = unitUnderTest.createMessage(messagePayload(), SAMPLE_SENDER_PRIVATE_KEY,
                SAMPLE_RECIPIENT_PUBLIC_KEY, SAMPLE_RECIPIENT_ADDRESS, false);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is("{" +
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
                "}}"));
    }

    @Test
    public void shouldCreatePlainMessageWithMinimalDetails() {
        final Message result = unitUnderTest.createMessage(messagePayloadWithMinimumDetails(), SAMPLE_SENDER_PRIVATE_KEY,
                SAMPLE_RECIPIENT_PUBLIC_KEY, SAMPLE_RECIPIENT_ADDRESS, false);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is("{" +
                "\"privacyType\":1001," +
                "\"version\":\"1.0\"," +
                "\"data\":{" +
                    "\"dataHash\":\"QmNZqVc7tBvjs1rohmcBbRUVUNmijEzexTQ9vi7435DTys\"," +
                    "\"timestamp\":1" +
                "}}"));
    }

    @Test
    public void shouldCreateSecureMessageWithOnlySenderPrivateKey() {
        final Message result = unitUnderTest.createMessage(messagePayload(), SAMPLE_SENDER_PRIVATE_KEY,
                null, null, true);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(SecureMessage.class)));
        assertThat(((SecureMessage) result).decrypt(
                new KeyPair(PrivateKey.fromHexString(SAMPLE_SENDER_PRIVATE_KEY)),
                new KeyPair(PublicKey.fromHexString(SAMPLE_SENDER_PUBLIC_KEY))
        ), is("{" +
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
                "}}"));
    }

    @Test
    public void shouldCreateSecureMessageWithRecipientPublicKey() {
        final Message result = unitUnderTest.createMessage(messagePayload(), SAMPLE_SENDER_PRIVATE_KEY,
                SAMPLE_RECIPIENT_PUBLIC_KEY, null, true);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(SecureMessage.class)));
        assertThat(((SecureMessage) result).decrypt(
                new KeyPair(PrivateKey.fromHexString(SAMPLE_SENDER_PRIVATE_KEY)),
                new KeyPair(PublicKey.fromHexString(SAMPLE_RECIPIENT_PUBLIC_KEY))
        ), is("{" +
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
                "}}"));
    }

    @Test
    public void shouldCreateSecureMessageWithRecipientAddressButSameWithSender() {
        final Message result = unitUnderTest.createMessage(messagePayload(), SAMPLE_SENDER_PRIVATE_KEY,
                null, SAMPLE_SENDER_ADDRESS, true);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(SecureMessage.class)));
        assertThat(((SecureMessage) result).decrypt(
                new KeyPair(PrivateKey.fromHexString(SAMPLE_SENDER_PRIVATE_KEY)),
                new KeyPair(PublicKey.fromHexString(SAMPLE_SENDER_PUBLIC_KEY))
        ), is("{" +
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
                "}}"));
    }

    @Test
    public void shouldCreateSecureMessageWithAnotherRecipientAddress() {
        given(mockAccountClient.getPublicKey(SAMPLE_RECIPIENT_ADDRESS))
                .willReturn(PublicKey.fromHexString(SAMPLE_RECIPIENT_PUBLIC_KEY));

        final Message result = unitUnderTest.createMessage(messagePayload(), SAMPLE_SENDER_PRIVATE_KEY,
                null, SAMPLE_RECIPIENT_ADDRESS, true);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(SecureMessage.class)));
        assertThat(((SecureMessage) result).decrypt(
                new KeyPair(PrivateKey.fromHexString(SAMPLE_SENDER_PRIVATE_KEY)),
                new KeyPair(PublicKey.fromHexString(SAMPLE_RECIPIENT_PUBLIC_KEY))
        ), is("{" +
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
                "}}"));
    }

    private ProximaxMessagePayloadModel messagePayload() {
        return ProximaxMessagePayloadModel.create(PrivacyType.PLAIN.getValue(), "1.0",
                ProximaxDataModel.create(
                        ByteArrayParameterData.create("test".getBytes(), "test description", "test name",
                                "text/plain+original", singletonMap("testKey", "testValue")),
                        SAMPLE_DATA_HASH, SAMPLE_DIGEST, "text/plain", 1L));
    }

    private ProximaxMessagePayloadModel messagePayloadWithMinimumDetails() {
        return ProximaxMessagePayloadModel.create(PrivacyType.PLAIN.getValue(), "1.0",
                ProximaxDataModel.create(
                        ByteArrayParameterData.create("test".getBytes()),
                        SAMPLE_DATA_HASH, null, null, 1L));
    }
}
