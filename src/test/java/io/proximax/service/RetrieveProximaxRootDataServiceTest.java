package io.proximax.service;

import io.proximax.download.DownloadParameter;
import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.model.StoreType;
import io.proximax.utils.DigestUtils;
import io.proximax.utils.PrivacyDataEncryptionUtils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class RetrieveProximaxRootDataServiceTest {

    public static final String DUMMY_ROOT_DATAHASH = "Qmdjsaldjasljdlaewq";
    public static final byte[] DUMMY_ENCRYPTED_DATA = "eqwudkhaskdhsabnasgdsaui hcixuzhciuzxhiuysauidyiuyqiwuyieq".getBytes();
    public static final String DUMMY_ROOT_DIGEST = "Qmdsjadhksahkdashkj";

    private RetrieveProximaxRootDataService unitUnderTest;

    @Mock
    private IpfsDownloadService mockIpfsDownloadService;

    @Mock
    private DigestUtils mockDigestUtils;

    @Mock
    private PrivacyDataEncryptionUtils mockPrivacyDataEncryptionUtils;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new RetrieveProximaxRootDataService(mockIpfsDownloadService, mockDigestUtils, mockPrivacyDataEncryptionUtils);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullDownloadParameter() {
        unitUnderTest.getRootData(null, Optional.empty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullMessagePayloadOpt() {
        unitUnderTest.getRootData(sampleDownloadParam(), null);
    }

    @Test
    public void shouldReturnRootDataWhenEmptyMessagePayload() {
        given(mockIpfsDownloadService.download(DUMMY_ROOT_DATAHASH, StoreType.RESOURCE)).willReturn(Observable.just(DUMMY_ENCRYPTED_DATA));
        given(mockDigestUtils.validateDigest(any(), any())).willReturn(Observable.just(true));
        given(mockPrivacyDataEncryptionUtils.decrypt(any(), any())).willReturn(Observable.just(sampleRootDataJson().getBytes()));

        final ProximaxRootDataModel result = unitUnderTest.getRootData(sampleDownloadParam(), Optional.empty()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getVersion(), is("1.0"));
        assertThat(result.getStoreType(), is(StoreType.RESOURCE));
        assertThat(result.getPrivacyType(), is(1001));
        assertThat(result.getPrivacySearchTag(), is("test"));
        assertThat(result.getDescription(), is("ewqeqwewqeqweqw"));
        assertThat(result.getDataList(), is(notNullValue()));
        assertThat(result.getDataList(), hasSize(2));
        assertThat(result.getDataList().get(0).getTimestamp(), is(1000L));
        assertThat(result.getDataList().get(0).getName(), is("data name 1"));
        assertThat(result.getDataList().get(0).getMetadata().get("key1"), is("value1"));
        assertThat(result.getDataList().get(0).getDigest(), is("iowuqoieuqowueoiqw"));
        assertThat(result.getDataList().get(0).getDataHash(), is("Qmdahdksadjksahjk"));
        assertThat(result.getDataList().get(0).getContentType(), is("text/plain"));
        assertThat(result.getDataList().get(1).getTimestamp(), is(2000L));
        assertThat(result.getDataList().get(1).getName(), is("data name 2"));
        assertThat(result.getDataList().get(1).getMetadata().get("key2"), is("value2"));
        assertThat(result.getDataList().get(1).getDigest(), is("sadasdsadsadasdads"));
        assertThat(result.getDataList().get(1).getDataHash(), is("Qmcxzczxczxczxcxz"));
        assertThat(result.getDataList().get(1).getContentType(), is("text/html"));

    }

    @Test
    public void shouldReturnRootDataWhenMessagePayloadProvided() {
        given(mockIpfsDownloadService.download(DUMMY_ROOT_DATAHASH, StoreType.RESOURCE)).willReturn(Observable.just(DUMMY_ENCRYPTED_DATA));
        given(mockDigestUtils.validateDigest(any(), any())).willReturn(Observable.just(true));
        given(mockPrivacyDataEncryptionUtils.decrypt(any(), any())).willReturn(Observable.just(sampleRootDataJson().getBytes()));

        final ProximaxRootDataModel result = unitUnderTest.getRootData(sampleDownloadParam(), Optional.of(sampleMessagePayload())).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getVersion(), is("1.0"));
        assertThat(result.getStoreType(), is(StoreType.RESOURCE));
        assertThat(result.getPrivacyType(), is(1001));
        assertThat(result.getPrivacySearchTag(), is("test"));
        assertThat(result.getDescription(), is("ewqeqwewqeqweqw"));
        assertThat(result.getDataList(), is(notNullValue()));
        assertThat(result.getDataList(), hasSize(2));
        assertThat(result.getDataList().get(0).getTimestamp(), is(1000L));
        assertThat(result.getDataList().get(0).getName(), is("data name 1"));
        assertThat(result.getDataList().get(0).getMetadata().get("key1"), is("value1"));
        assertThat(result.getDataList().get(0).getDigest(), is("iowuqoieuqowueoiqw"));
        assertThat(result.getDataList().get(0).getDataHash(), is("Qmdahdksadjksahjk"));
        assertThat(result.getDataList().get(0).getContentType(), is("text/plain"));
        assertThat(result.getDataList().get(1).getTimestamp(), is(2000L));
        assertThat(result.getDataList().get(1).getName(), is("data name 2"));
        assertThat(result.getDataList().get(1).getMetadata().get("key2"), is("value2"));
        assertThat(result.getDataList().get(1).getDigest(), is("sadasdsadsadasdads"));
        assertThat(result.getDataList().get(1).getDataHash(), is("Qmcxzczxczxczxcxz"));
        assertThat(result.getDataList().get(1).getContentType(), is("text/html"));
    }

    private DownloadParameter sampleDownloadParam() {
        return DownloadParameter.createWithRootDataHash(DUMMY_ROOT_DATAHASH, DUMMY_ROOT_DIGEST)
                .build();
    }

    private ProximaxMessagePayloadModel sampleMessagePayload() {
        return ProximaxMessagePayloadModel.create(DUMMY_ROOT_DATAHASH, DUMMY_ROOT_DIGEST, null,
                PrivacyType.PLAIN.getValue(), null, "1.0");
    }

    private String sampleRootDataJson() {
        return "{\"privacyType\":1001," +
                        "\"privacySearchTag\":\"test\"," +
                        "\"description\":\"ewqeqwewqeqweqw\"," +
                        "\"storeType\":\"RESOURCE\"," +
                        "\"version\":\"1.0\"," +
                        "\"dataList\":[" +
                            "{" +
                                "\"digest\":\"iowuqoieuqowueoiqw\"," +
                                "\"dataHash\":\"Qmdahdksadjksahjk\"," +
                                "\"description\":\"data 1\"," +
                                "\"metadata\":{\"key1\":\"value1\"}," +
                                "\"timestamp\":1000," +
                                "\"name\":\"data name 1\"," +
                                "\"contentType\":\"text/plain\"" +
                            "}," +
                            "{" +
                                "\"digest\":\"sadasdsadsadasdads\"," +
                                "\"dataHash\":\"Qmcxzczxczxczxcxz\"," +
                                "\"description\":\"data 2\"," +
                                "\"metadata\":{\"key2\":\"value2\"}," +
                                "\"timestamp\":2000" +
                                ",\"name\":\"data name 2\"," +
                                "\"contentType\":\"text/html\"" +
                            "}" +
                        "]" +
                        "}";
    }
}
