package io.proximax.service;

import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxDataModel;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.upload.StringParameterData;
import io.proximax.upload.UploadParameter;
import io.proximax.utils.DigestUtils;
import io.proximax.utils.PrivacyDataEncryptionUtils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.UnsupportedEncodingException;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class CreateProximaxMessagePayloadServiceTest {

    private static final byte[] DUMMY_ENCRYPTED_DATA = "djhsaklhdkshzkjdhsakjdhjksahkjdsakljdsa".getBytes();
    private static final String DUMMY_DIGEST = "eqeqweqweqweqweqweqw";
    private static final String DUMMY_ROOT_DATA_HASH = "Qmcjkzxhkjchxzkjhcjkxznbcjkxz";
    private static final String DUMMY_ROOT_DESCRIPTION = "ewqeqwewqeqweqw";
    private static final String DUMMY_VERSION = "1.0";
    private static final PrivacyStrategy DUMMY_PRIVACY_STRATEGY = PlainPrivacyStrategy.create(null);

    private CreateProximaxMessagePayloadService unitUnderTest;

    @Mock
    private IpfsUploadService mockIpfsUploadService;

    @Mock
    private DigestUtils mockDigestUtils;

    @Mock
    private PrivacyDataEncryptionUtils mockPrivacyDataEncryptionUtils;

    @Captor
    private ArgumentCaptor<byte[]> rootDataByteArgumentCaptor;

    @Captor
    private ArgumentCaptor<PrivacyStrategy> privacyStrategyArgumentCaptor;

    @Captor
    private ArgumentCaptor<byte[]> digestArgumentCaptor;

    @Captor
    private ArgumentCaptor<byte[]> uploadArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        unitUnderTest = new CreateProximaxMessagePayloadService(mockIpfsUploadService, mockDigestUtils, mockPrivacyDataEncryptionUtils);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullUploadParameter() {
        unitUnderTest.createMessagePayload(null, sampleRootData());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullRootData() throws UnsupportedEncodingException {
        unitUnderTest.createMessagePayload(sampleUploadParameterWithComputeDigestTrue(), null);
    }

    @Test
    public void shouldReturnMessagePayloadWhenComputeDigestTrue() throws UnsupportedEncodingException {
        given(mockPrivacyDataEncryptionUtils.encrypt(any(), any()))
                .willReturn(Observable.just(DUMMY_ENCRYPTED_DATA));
        given(mockDigestUtils.digest(any())).willReturn(Observable.just(DUMMY_DIGEST));
        given(mockIpfsUploadService.uploadByteArray(any()))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_ROOT_DATA_HASH, 9999L)));

        final ProximaxMessagePayloadModel result =
                unitUnderTest.createMessagePayload(sampleUploadParameterWithComputeDigestTrue(), sampleRootData()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDigest(), is(DUMMY_DIGEST));
        assertThat(result.getRootDataHash(), is(DUMMY_ROOT_DATA_HASH));
        assertThat(result.getDescription(), is(DUMMY_ROOT_DESCRIPTION));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getPrivacySearchTag(), is(nullValue()));
        assertThat(result.getVersion(), is(DUMMY_VERSION));
    }

    @Test
    public void shouldReturnMessagePayloadWhenComputeDigestFalse() throws UnsupportedEncodingException {
        given(mockPrivacyDataEncryptionUtils.encrypt(any(), any()))
                .willReturn(Observable.just(DUMMY_ENCRYPTED_DATA));
        given(mockIpfsUploadService.uploadByteArray(any()))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_ROOT_DATA_HASH, 9999L)));

        final ProximaxMessagePayloadModel result =
                unitUnderTest.createMessagePayload(sampleUploadParameterWithComputeDigestFalse(), sampleRootData()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDigest(), is(nullValue()));
        assertThat(result.getRootDataHash(), is(DUMMY_ROOT_DATA_HASH));
        assertThat(result.getDescription(), is(DUMMY_ROOT_DESCRIPTION));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getPrivacySearchTag(), is(nullValue()));
        assertThat(result.getVersion(), is(DUMMY_VERSION));
    }

    @Test
    public void shouldDelegateCorrectly() throws UnsupportedEncodingException {
        given(mockPrivacyDataEncryptionUtils.encrypt(privacyStrategyArgumentCaptor.capture(), rootDataByteArgumentCaptor.capture()))
                .willReturn(Observable.just(DUMMY_ENCRYPTED_DATA));
        given(mockDigestUtils.digest(digestArgumentCaptor.capture())).willReturn(Observable.just(DUMMY_DIGEST));
        given(mockIpfsUploadService.uploadByteArray(uploadArgumentCaptor.capture()))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_ROOT_DATA_HASH, 9999L)));

        final ProximaxMessagePayloadModel result =
                unitUnderTest.createMessagePayload(sampleUploadParameterWithComputeDigestTrue(), sampleRootData()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(privacyStrategyArgumentCaptor.getValue(), instanceOf(PlainPrivacyStrategy.class));
        assertThat(new String(rootDataByteArgumentCaptor.getValue()), is(
                "{\"privacyType\":1001," +
                        "\"privacySearchTag\":\"test\"," +
                        "\"description\":\"ewqeqwewqeqweqw\"," +
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
                        "}"));
        assertThat(digestArgumentCaptor.getValue(), is(DUMMY_ENCRYPTED_DATA));
        assertThat(uploadArgumentCaptor.getValue(), is(DUMMY_ENCRYPTED_DATA));
    }

    private ProximaxRootDataModel sampleRootData() {
        return new ProximaxRootDataModel(PrivacyType.PLAIN.getValue(), "test", DUMMY_ROOT_DESCRIPTION,
                DUMMY_VERSION, asList(
                        new ProximaxDataModel("iowuqoieuqowueoiqw", "Qmdahdksadjksahjk", "data 1",
                                singletonMap("key1", "value1"), 1000L, "data name 1", "text/plain"),
                        new ProximaxDataModel("sadasdsadsadasdads", "Qmcxzczxczxczxcxz", "data 2",
                                singletonMap("key2", "value2"), 2000L, "data name 2", "text/html")
        ));
    }

    private UploadParameter sampleUploadParameterWithComputeDigestTrue() throws UnsupportedEncodingException {
        return UploadParameter.create("dhsakhdkashkdsahkdsa", "ndsakjhdkjsahdasjhjkdsa")
                .addString(StringParameterData.create("dashkdhsakjdhask").build())
                .description(DUMMY_ROOT_DESCRIPTION)
                .computeDigest(true)
                .build();
    }

    private UploadParameter sampleUploadParameterWithComputeDigestFalse() throws UnsupportedEncodingException {
        return UploadParameter.create("dhsakhdkashkdsahkdsa", "ndsakjhdkjsahdasjhjkdsa")
                .addString(StringParameterData.create("dashkdhsakjdhask").build())
                .description(DUMMY_ROOT_DESCRIPTION)
                .computeDigest(false)
                .build();
    }
}
