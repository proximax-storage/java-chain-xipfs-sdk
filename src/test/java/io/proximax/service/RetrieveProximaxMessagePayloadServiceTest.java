package io.proximax.service;

import io.proximax.sdk.model.transaction.TransferTransaction;
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

    private static final String SAMPLE_PRIVATE_KEY_1 = "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C";

    private RetrieveProximaxMessagePayloadService unitUnderTest;

    @Mock
    private TransferTransaction mockTransferTransaction;

    @Mock
    private BlockchainMessageService mockBlockchainMessageService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new RetrieveProximaxMessagePayloadService(mockBlockchainMessageService);
    }

    @Test
    public void shouldReturnPayload() {
        given(mockBlockchainMessageService.getMessagePayload(mockTransferTransaction, SAMPLE_PRIVATE_KEY_1))
                .willReturn(samplePayload());

        final ProximaxMessagePayloadModel result = unitUnderTest.getMessagePayload(mockTransferTransaction, SAMPLE_PRIVATE_KEY_1);

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

    private String samplePayload() {
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
