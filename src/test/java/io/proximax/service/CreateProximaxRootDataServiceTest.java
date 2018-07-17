package io.proximax.service;

import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.model.StoreType;
import io.proximax.upload.StringParameterData;
import io.proximax.upload.UploadParameter;
import io.proximax.utils.ContentTypeUtils;
import io.proximax.utils.DigestUtils;
import io.proximax.utils.PrivacyDataEncryptionUtils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class CreateProximaxRootDataServiceTest {

    private static final String DUMMY_ROOT_DESCRIPTION = "ewqeqwewqeqweqw";
    private static final String DUMMY_VERSION = "1.0";
    private static final String DUMMY_DATA_1 = "dopsaipdlsnlxnz,cn,zxnclznxlnldsaldslkaj;as.";
    private static final String DUMMY_DATA_2 = "oidpsaipdsakl;elwqnem,nq,mnjksahciuxhzkjcdsa";
    private static final String DUMMY_NAME_1 = "name 1";
    private static final String DUMMY_NAME_2 = "name 2";
    private static final String DUMMY_DESCRIPTION_1 = "data description 1";
    private static final String DUMMY_DESCRIPTION_2 = "data description 1";
    private static final Map<String, String> DUMMY_METADATA_1 = singletonMap("key1", "val1");
    private static final Map<String, String> DUMMY_METADATA_2 = singletonMap("key2", "val2");
    private static final byte[] DUMMY_ENCRYPTED_DATA_1 = "dsajhjdhaskhdksahkdsaljkjlxnzcm,nxz".getBytes();
    private static final byte[] DUMMY_ENCRYPTED_DATA_2 = "icjoiuewoiqueioqwjekwq,.eqwn,mnqwio".getBytes();
    private static final String DUMMY_DIGEST_1 = "Qmdsewquywqiyeiuqwyiueyqiuyeuiwyqid";
    private static final String DUMMY_DIGEST_2 = "ncm,xznm,czxkjhjdyeqwyuieywqkjhdkas";
    private static final String DUMMY_CONTENT_TYPE_1 = "text/plain";
    private static final String DUMMY_CONTENT_TYPE_2 = "application/pdf";
    private static final String DUMMY_ROOT_DATA_HASH_1 = "Qmdyueoqwoeuowqueowquioeuioqwuoi";
    private static final String DUMMY_ROOT_DATA_HASH_2 = "Qmuieowuqoieuiwoquioeqwm,dnmxczc";
    private static final Long DUMMY_TIMESTAMP_1 = 1000L;
    private static final Long DUMMY_TIMESTAMP_2 = 2000L;

    private CreateProximaxRootDataService unitUnderTest;

    @Mock
    private IpfsUploadService mockIpfsUploadService;

    @Mock
    private DigestUtils mockDigestUtils;

    @Mock
    private ContentTypeUtils mockContentTypeUtils;

    @Mock
    private PrivacyDataEncryptionUtils mockPrivacyDataEncryptionUtils;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new CreateProximaxRootDataService(mockIpfsUploadService, mockDigestUtils, mockContentTypeUtils,
                mockPrivacyDataEncryptionUtils);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullUploadParameter() {
        unitUnderTest.createRootData(null);
    }

    @Test
    public void shouldCreateDataWhenDigestTrue() throws UnsupportedEncodingException {
        given(mockPrivacyDataEncryptionUtils.encryptList(any(), any()))
                .willReturn(Observable.just(asList(DUMMY_ENCRYPTED_DATA_1, DUMMY_ENCRYPTED_DATA_2)));
        given(mockDigestUtils.digestForList(any()))
                .willReturn(Observable.just(asList(DUMMY_DIGEST_1, DUMMY_DIGEST_2)));
        given(mockContentTypeUtils.detectContentTypeForList(any(), any()))
                .willReturn(Observable.just(asList(DUMMY_CONTENT_TYPE_1, DUMMY_CONTENT_TYPE_2)));
        given(mockIpfsUploadService.uploadList(any(), any()))
                .willReturn(Observable.just(asList(
                        new IpfsUploadResponse(DUMMY_ROOT_DATA_HASH_1, DUMMY_TIMESTAMP_1),
                        new IpfsUploadResponse(DUMMY_ROOT_DATA_HASH_2, DUMMY_TIMESTAMP_2))));

        final ProximaxRootDataModel result = unitUnderTest.createRootData(sampleUploadParameterWithComputeDigestTrue()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDataList(), is(notNullValue()));
        assertThat(result.getDataList(), hasSize(2));
        assertThat(result.getDataList().get(0).getContentType(), is(DUMMY_CONTENT_TYPE_1));
        assertThat(result.getDataList().get(0).getDataHash(), is(DUMMY_ROOT_DATA_HASH_1));
        assertThat(result.getDataList().get(0).getDescription(), is(DUMMY_DESCRIPTION_1));
        assertThat(result.getDataList().get(0).getDigest(), is(DUMMY_DIGEST_1));
        assertThat(result.getDataList().get(0).getMetadata(), is(DUMMY_METADATA_1));
        assertThat(result.getDataList().get(0).getName(), is(DUMMY_NAME_1));
        assertThat(result.getDataList().get(0).getTimestamp(), is(DUMMY_TIMESTAMP_1));
        assertThat(result.getDataList().get(1).getContentType(), is(DUMMY_CONTENT_TYPE_2));
        assertThat(result.getDataList().get(1).getDataHash(), is(DUMMY_ROOT_DATA_HASH_2));
        assertThat(result.getDataList().get(1).getDescription(), is(DUMMY_DESCRIPTION_2));
        assertThat(result.getDataList().get(1).getDigest(), is(DUMMY_DIGEST_2));
        assertThat(result.getDataList().get(1).getMetadata(), is(DUMMY_METADATA_2));
        assertThat(result.getDataList().get(1).getName(), is(DUMMY_NAME_2));
        assertThat(result.getDataList().get(1).getTimestamp(), is(DUMMY_TIMESTAMP_2));
        assertThat(result.getDescription(), is(DUMMY_ROOT_DESCRIPTION));
        assertThat(result.getPrivacySearchTag(), is(nullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getStoreType(), is(StoreType.RESOURCE));
        assertThat(result.getVersion(), is(DUMMY_VERSION));
    }

    @Test
    public void shouldCreateDataWhenDigestFalse() throws UnsupportedEncodingException {
        given(mockPrivacyDataEncryptionUtils.encryptList(any(), any()))
                .willReturn(Observable.just(asList(DUMMY_ENCRYPTED_DATA_1, DUMMY_ENCRYPTED_DATA_2)));
        given(mockContentTypeUtils.detectContentTypeForList(any(), any()))
                .willReturn(Observable.just(asList(DUMMY_CONTENT_TYPE_1, DUMMY_CONTENT_TYPE_2)));
        given(mockIpfsUploadService.uploadList(any(), any()))
                .willReturn(Observable.just(asList(
                        new IpfsUploadResponse(DUMMY_ROOT_DATA_HASH_1, DUMMY_TIMESTAMP_1),
                        new IpfsUploadResponse(DUMMY_ROOT_DATA_HASH_2, DUMMY_TIMESTAMP_2))));

        final ProximaxRootDataModel result = unitUnderTest.createRootData(sampleUploadParameterWithComputeDigestFalse()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDataList(), is(notNullValue()));
        assertThat(result.getDataList(), hasSize(2));
        assertThat(result.getDataList().get(0).getContentType(), is(DUMMY_CONTENT_TYPE_1));
        assertThat(result.getDataList().get(0).getDataHash(), is(DUMMY_ROOT_DATA_HASH_1));
        assertThat(result.getDataList().get(0).getDescription(), is(DUMMY_DESCRIPTION_1));
        assertThat(result.getDataList().get(0).getDigest(), is(nullValue()));
        assertThat(result.getDataList().get(0).getMetadata(), is(DUMMY_METADATA_1));
        assertThat(result.getDataList().get(0).getName(), is(DUMMY_NAME_1));
        assertThat(result.getDataList().get(0).getTimestamp(), is(DUMMY_TIMESTAMP_1));
        assertThat(result.getDataList().get(1).getContentType(), is(DUMMY_CONTENT_TYPE_2));
        assertThat(result.getDataList().get(1).getDataHash(), is(DUMMY_ROOT_DATA_HASH_2));
        assertThat(result.getDataList().get(1).getDescription(), is(DUMMY_DESCRIPTION_2));
        assertThat(result.getDataList().get(1).getDigest(), is(nullValue()));
        assertThat(result.getDataList().get(1).getMetadata(), is(DUMMY_METADATA_2));
        assertThat(result.getDataList().get(1).getName(), is(DUMMY_NAME_2));
        assertThat(result.getDataList().get(1).getTimestamp(), is(DUMMY_TIMESTAMP_2));
        assertThat(result.getDescription(), is(DUMMY_ROOT_DESCRIPTION));
        assertThat(result.getPrivacySearchTag(), is(nullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getStoreType(), is(StoreType.RESOURCE));
        assertThat(result.getVersion(), is(DUMMY_VERSION));
    }

    private UploadParameter sampleUploadParameterWithComputeDigestTrue() throws UnsupportedEncodingException {
        return UploadParameter.create("dhsakhdkashkdsahkdsa", "ndsakjhdkjsahdasjhjkdsa")
                .addString(StringParameterData.create(DUMMY_DATA_1)
                        .description(DUMMY_DESCRIPTION_1)
                        .metadata(DUMMY_METADATA_1)
                        .name(DUMMY_NAME_1)
                        .build())
                .addString(StringParameterData.create(DUMMY_DATA_2)
                        .description(DUMMY_DESCRIPTION_2)
                        .metadata(DUMMY_METADATA_2)
                        .name(DUMMY_NAME_2)
                        .build())
                .description(DUMMY_ROOT_DESCRIPTION)
                .computeDigest(true)
                .build();
    }

    private UploadParameter sampleUploadParameterWithComputeDigestFalse() throws UnsupportedEncodingException {
        return UploadParameter.create("dhsakhdkashkdsahkdsa", "ndsakjhdkjsahdasjhjkdsa")
                .addString(StringParameterData.create(DUMMY_DATA_1)
                        .description(DUMMY_DESCRIPTION_1)
                        .metadata(DUMMY_METADATA_1)
                        .name(DUMMY_NAME_1)
                        .build())
                .addString(StringParameterData.create(DUMMY_DATA_2)
                        .description(DUMMY_DESCRIPTION_2)
                        .metadata(DUMMY_METADATA_2)
                        .name(DUMMY_NAME_2)
                        .build())
                .description(DUMMY_ROOT_DESCRIPTION)
                .computeDigest(false)
                .build();
    }
}
