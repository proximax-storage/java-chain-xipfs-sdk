package io.proximax.service;

import io.nem.sdk.model.transaction.Message;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.model.ProximaxMessagePayloadModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;

public class RetrieveProximaxMessagePayloadServiceTest {

    private static final String SAMPLE_PRIVATE_KEY = "1A5B81AE8830B8A79232CD366552AF6496FE548B4A23D4173FEEBA41B8ABA81F";

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
        unitUnderTest.getMessagePayload(null, SAMPLE_PRIVATE_KEY);
    }

    @Test
    public void shouldReturnMessagePayload() {
        given(mockMessage.getPayload()).willReturn("{" +
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
                "}}");

        final ProximaxMessagePayloadModel result = unitUnderTest.getMessagePayload(mockTransferTransaction, SAMPLE_PRIVATE_KEY);

        assertThat(result, is(notNullValue()));
        assertThat(result.getVersion(), is("1.0"));
        assertThat(result.getPrivacyType(), is(1001));
        assertThat(result.getData().getDigest(), is("eqwewqewqewqewqewq"));
        assertThat(result.getData().getDataHash(), is("QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf"));
        assertThat(result.getData().getTimestamp(), is(1L));
        assertThat(result.getData().getDescription(), is("test description"));
        assertThat(result.getData().getName(), is("test name"));
        assertThat(result.getData().getContentType(), is("text/plain"));
        assertThat(result.getData().getMetadata(), is(singletonMap("testKey", "testValue")));
    }

}
