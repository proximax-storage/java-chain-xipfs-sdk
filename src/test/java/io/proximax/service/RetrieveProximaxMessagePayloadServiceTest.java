package io.proximax.service;

import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.model.ProximaxMessagePayloadModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;

public class RetrieveProximaxMessagePayloadServiceTest {

    private RetrieveProximaxMessagePayloadService unitUnderTest;

    @Mock
    private TransferTransaction mockTransferTransaction;

    @Mock
    private Message mockMessage;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new RetrieveProximaxMessagePayloadService();

        given(mockTransferTransaction.getMessage()).willReturn(mockMessage);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullTransferTransaction() {
        unitUnderTest.getMessagePayload(null);
    }

    @Test
    public void shouldReturnMessagePayload() {
        given(mockMessage.getPayload()).willReturn("{" +
                "\"digest\":\"eqwewqewqewqewqewq\"," +
                "\"rootDataHash\":\"QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf\"," +
                "\"privacyType\":1001," +
                "\"description\":\"root description\"," +
                "\"version\":\"1.0\"}");

        final ProximaxMessagePayloadModel messagePayload = unitUnderTest.getMessagePayload(mockTransferTransaction);

        assertThat(messagePayload, is(notNullValue()));
        assertThat(messagePayload.getVersion(), is("1.0"));
        assertThat(messagePayload.getDigest(), is("eqwewqewqewqewqewq"));
        assertThat(messagePayload.getRootDataHash(), is("QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf"));
        assertThat(messagePayload.getPrivacyType(), is(1001));
        assertThat(messagePayload.getDescription(), is("root description"));
    }

}
