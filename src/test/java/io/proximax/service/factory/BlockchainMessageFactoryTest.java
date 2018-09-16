package io.proximax.service.factory;

import io.nem.sdk.model.transaction.Message;
import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxDataModel;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.upload.ByteArrayParameterData;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class BlockchainMessageFactoryTest {

    private static final String SAMPLE_SIGNER_PRIVATE_KEY = "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C";
    private static final String SAMPLE_RECIPIENT_PUBLIC_KEY = "E9F6576AF9F05E6738CD4E55B875A823CC75B4E8AE8984747DF7B235685C1577";
    private static final String SAMPLE_RECIPIENT_ADDRESS = "SBRHESWCLX3VGQ6CHCZNKDN6DT7GLS6CZKJXCT5F";
    private static final String SAMPLE_DATA_HASH = "QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf";
    private static final String SAMPLE_DIGEST = "eqwewqewqewqewqewq";

    private BlockchainMessageFactory unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new BlockchainMessageFactory();
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullMessagePayload() {
        unitUnderTest.createMessage(null, SAMPLE_SIGNER_PRIVATE_KEY, SAMPLE_RECIPIENT_PUBLIC_KEY,
                SAMPLE_RECIPIENT_ADDRESS, false);
    }

    @Test
    public void shouldCreatePlainMessage() {
        final Message result = unitUnderTest.createMessage(messagePayload(), SAMPLE_SIGNER_PRIVATE_KEY,
                SAMPLE_RECIPIENT_PUBLIC_KEY, SAMPLE_RECIPIENT_ADDRESS, false);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is("{" +
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
                "}}"));
    }

    @Test
    public void shouldCreatePlainMessageWithMinimalDetails() {
        final Message result = unitUnderTest.createMessage(messagePayloadWithMinimumDetails(), SAMPLE_SIGNER_PRIVATE_KEY,
                SAMPLE_RECIPIENT_PUBLIC_KEY, SAMPLE_RECIPIENT_ADDRESS, false);

        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is("{" +
                "\"privacyType\":1001," +
                "\"version\":\"1.0\"," +
                "\"data\":{" +
                    "\"dataHash\":\"QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf\"," +
                    "\"timestamp\":1" +
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
