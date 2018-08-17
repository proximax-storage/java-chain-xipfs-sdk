package io.proximax.service.factory;

import io.nem.sdk.model.transaction.Message;
import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class BlockchainMessageFactoryTest {

    private static final String SAMPLE_ROOT_DATA_HASH = "QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf";
    private static final String SAMPLE_DIGEST = "eqwewqewqewqewqewq";

    private BlockchainMessageFactory unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new BlockchainMessageFactory();

    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullPrivacyStrategy() {
        unitUnderTest.createMessage(null, messagePayload());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullMessagePayload() {
        unitUnderTest.createMessage(plainPrivacyStrategy(), null);
    }

    @Test
    public void shouldCreatePlainMessage() {
        final Message result = unitUnderTest.createMessage(plainPrivacyStrategy(), messagePayload());

        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is("{" +
                "\"digest\":\"eqwewqewqewqewqewq\"," +
                "\"rootDataHash\":\"QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf\"," +
                "\"privacyType\":1001," +
                "\"privacySearchTag\":\"test\"," +
                "\"description\":\"root description\"," +
                "\"version\":\"1.0\"}"));
    }

    @Test
    public void shouldCreatePlainMessageWithMinimalDetails() {
        final Message result = unitUnderTest.createMessage(plainPrivacyStrategy(), messagePayloadWithMinimumDetails());

        assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is("{" +
                "\"rootDataHash\":\"QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf\"," +
                "\"privacyType\":1001," +
                "\"version\":\"1.0\"}"));
    }

    private PrivacyStrategy plainPrivacyStrategy() {
        return PlainPrivacyStrategy.create("test");
    }

    private ProximaxMessagePayloadModel messagePayload() {
        return ProximaxMessagePayloadModel.create(SAMPLE_ROOT_DATA_HASH, SAMPLE_DIGEST, "root description",
                PrivacyType.PLAIN.getValue(), "test", "1.0");
    }

    private ProximaxMessagePayloadModel messagePayloadWithMinimumDetails() {
        return ProximaxMessagePayloadModel.create(SAMPLE_ROOT_DATA_HASH, null, null,
                PrivacyType.PLAIN.getValue(), null, "1.0");
    }
}
