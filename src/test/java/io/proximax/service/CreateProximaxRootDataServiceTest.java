package io.proximax.service;

import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.upload.ByteArrayParameterData;
import io.proximax.upload.PathParameterData;
import io.proximax.upload.StringParameterData;
import io.proximax.upload.UploadParameter;
import io.proximax.utils.ContentTypeUtils;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.Map;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.BDDMockito.given;

public class CreateProximaxRootDataServiceTest {

    private static final String DUMMY_ROOT_DESCRIPTION = "ewqeqwewqeqweqw";
    private static final String DUMMY_VERSION = "1.0";
    private static final byte[] DUMMY_DATA_1 = "dopsaipdlsnlxnz,cn,zxnclznxlnldsaldslkaj;as.".getBytes();
    private static final byte[] DUMMY_DATA_2 = "oidpsaipdsakl;elwqnem,nq,mnjksahciuxhzkjcdsa".getBytes();
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
    private static final String DUMMY_DATA_HASH_1 = "Qmdyueoqwoeuowqueowquioeuioqwuoi";
    private static final String DUMMY_DATA_HASH_2 = "Qmuieowuqoieuiwoquioeqwm,dnmxczc";
    private static final File DUMMY_PATH_1 = new File("src//test//resources//test_path");
    private static final File DUMMY_PATH_2 = new File("src//test//resources");

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
    private PrivacyStrategy mockPrivacyStrategy;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new CreateProximaxRootDataService(mockIpfsUploadService, mockDigestUtils, mockContentTypeUtils);

        given(mockPrivacyStrategy.getPrivacyType()).willReturn(1001);
        given(mockPrivacyStrategy.getPrivacySearchTag()).willReturn("test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullUploadParameter() {
        unitUnderTest.createRootData(null);
    }

    @Test
    public void shouldCreateRootDataForByteArrayUploadAndWhenDigestTrue() {
        given(mockPrivacyStrategy.encryptData(DUMMY_DATA_1)).willReturn(DUMMY_ENCRYPTED_DATA_1);
        given(mockPrivacyStrategy.encryptData(DUMMY_DATA_2)).willReturn(DUMMY_ENCRYPTED_DATA_2);
        given(mockDigestUtils.digest(DUMMY_ENCRYPTED_DATA_1)).willReturn(Observable.just(DUMMY_DIGEST_1));
        given(mockDigestUtils.digest(DUMMY_ENCRYPTED_DATA_2)).willReturn(Observable.just(DUMMY_DIGEST_2));
        given(mockContentTypeUtils.detectContentType(DUMMY_DATA_1, null))
                .willReturn(Observable.just(DUMMY_CONTENT_TYPE_1));
        given(mockContentTypeUtils.detectContentType(DUMMY_DATA_2, null))
                .willReturn(Observable.just(DUMMY_CONTENT_TYPE_2));
        given(mockIpfsUploadService.uploadByteArray(DUMMY_ENCRYPTED_DATA_1))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH_1, DUMMY_TIMESTAMP_1)));
        given(mockIpfsUploadService.uploadByteArray(DUMMY_ENCRYPTED_DATA_2))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH_2, DUMMY_TIMESTAMP_2)));

        final ProximaxRootDataModel result = unitUnderTest.createRootData(sampleByteArrayUploadParamWithComputeDigestTrue()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDataList(), is(notNullValue()));
        assertThat(result.getDataList(), hasSize(2));
        assertThat(result.getDataList().get(0).getContentType(), is(DUMMY_CONTENT_TYPE_1));
        assertThat(result.getDataList().get(0).getDataHash(), is(DUMMY_DATA_HASH_1));
        assertThat(result.getDataList().get(0).getDescription(), is(DUMMY_DESCRIPTION_1));
        assertThat(result.getDataList().get(0).getDigest(), is(DUMMY_DIGEST_1));
        assertThat(result.getDataList().get(0).getMetadata(), is(DUMMY_METADATA_1));
        assertThat(result.getDataList().get(0).getName(), is(DUMMY_NAME_1));
        assertThat(result.getDataList().get(0).getTimestamp(), is(DUMMY_TIMESTAMP_1));
        assertThat(result.getDataList().get(1).getContentType(), is(DUMMY_CONTENT_TYPE_2));
        assertThat(result.getDataList().get(1).getDataHash(), is(DUMMY_DATA_HASH_2));
        assertThat(result.getDataList().get(1).getDescription(), is(DUMMY_DESCRIPTION_2));
        assertThat(result.getDataList().get(1).getDigest(), is(DUMMY_DIGEST_2));
        assertThat(result.getDataList().get(1).getMetadata(), is(DUMMY_METADATA_2));
        assertThat(result.getDataList().get(1).getName(), is(DUMMY_NAME_2));
        assertThat(result.getDataList().get(1).getTimestamp(), is(DUMMY_TIMESTAMP_2));
        assertThat(result.getDescription(), is(DUMMY_ROOT_DESCRIPTION));
        assertThat(result.getPrivacySearchTag(), is("test"));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getVersion(), is(DUMMY_VERSION));
    }

    @Test
    public void shouldCreateRootDataForByteArrayUploadAndWhenDigestFalse() {
        given(mockPrivacyStrategy.encryptData(DUMMY_DATA_1)).willReturn(DUMMY_ENCRYPTED_DATA_1);
        given(mockPrivacyStrategy.encryptData(DUMMY_DATA_2)).willReturn(DUMMY_ENCRYPTED_DATA_2);
        given(mockContentTypeUtils.detectContentType(DUMMY_DATA_1, null))
                .willReturn(Observable.just(DUMMY_CONTENT_TYPE_1));
        given(mockContentTypeUtils.detectContentType(DUMMY_DATA_2, null))
                .willReturn(Observable.just(DUMMY_CONTENT_TYPE_2));
        given(mockIpfsUploadService.uploadByteArray(DUMMY_ENCRYPTED_DATA_1))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH_1, DUMMY_TIMESTAMP_1)));
        given(mockIpfsUploadService.uploadByteArray(DUMMY_ENCRYPTED_DATA_2))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH_2, DUMMY_TIMESTAMP_2)));

        final ProximaxRootDataModel result = unitUnderTest.createRootData(sampleByteArrayUploadParamWithComputeDigestFalse()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDataList(), is(notNullValue()));
        assertThat(result.getDataList(), hasSize(2));
        assertThat(result.getDataList().get(0).getContentType(), is(DUMMY_CONTENT_TYPE_1));
        assertThat(result.getDataList().get(0).getDataHash(), is(DUMMY_DATA_HASH_1));
        assertThat(result.getDataList().get(0).getDescription(), is(DUMMY_DESCRIPTION_1));
        assertThat(result.getDataList().get(0).getDigest(), is(nullValue()));
        assertThat(result.getDataList().get(0).getMetadata(), is(DUMMY_METADATA_1));
        assertThat(result.getDataList().get(0).getName(), is(DUMMY_NAME_1));
        assertThat(result.getDataList().get(0).getTimestamp(), is(DUMMY_TIMESTAMP_1));
        assertThat(result.getDataList().get(1).getContentType(), is(DUMMY_CONTENT_TYPE_2));
        assertThat(result.getDataList().get(1).getDataHash(), is(DUMMY_DATA_HASH_2));
        assertThat(result.getDataList().get(1).getDescription(), is(DUMMY_DESCRIPTION_2));
        assertThat(result.getDataList().get(1).getDigest(), is(nullValue()));
        assertThat(result.getDataList().get(1).getMetadata(), is(DUMMY_METADATA_2));
        assertThat(result.getDataList().get(1).getName(), is(DUMMY_NAME_2));
        assertThat(result.getDataList().get(1).getTimestamp(), is(DUMMY_TIMESTAMP_2));
        assertThat(result.getDescription(), is(DUMMY_ROOT_DESCRIPTION));
        assertThat(result.getPrivacySearchTag(), is("test"));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getVersion(), is(DUMMY_VERSION));
    }

    @Test
    public void shouldCreateRootDataForPathUpload() {
        given(mockIpfsUploadService.uploadPath(DUMMY_PATH_1))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH_1, DUMMY_TIMESTAMP_1)));
        given(mockIpfsUploadService.uploadPath(DUMMY_PATH_2))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH_2, DUMMY_TIMESTAMP_2)));

        final ProximaxRootDataModel result = unitUnderTest.createRootData(samplePathUploadParam()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDataList(), is(notNullValue()));
        assertThat(result.getDataList(), hasSize(2));
        assertThat(result.getDataList().get(0).getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
        assertThat(result.getDataList().get(0).getDataHash(), is(DUMMY_DATA_HASH_1));
        assertThat(result.getDataList().get(0).getDescription(), is(DUMMY_DESCRIPTION_1));
        assertThat(result.getDataList().get(0).getDigest(), is(nullValue()));
        assertThat(result.getDataList().get(0).getMetadata(), is(DUMMY_METADATA_1));
        assertThat(result.getDataList().get(0).getName(), is(DUMMY_NAME_1));
        assertThat(result.getDataList().get(0).getTimestamp(), is(DUMMY_TIMESTAMP_1));
        assertThat(result.getDataList().get(1).getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
        assertThat(result.getDataList().get(1).getDataHash(), is(DUMMY_DATA_HASH_2));
        assertThat(result.getDataList().get(1).getDescription(), is(DUMMY_DESCRIPTION_2));
        assertThat(result.getDataList().get(1).getDigest(), is(nullValue()));
        assertThat(result.getDataList().get(1).getMetadata(), is(DUMMY_METADATA_2));
        assertThat(result.getDataList().get(1).getName(), is(DUMMY_NAME_2));
        assertThat(result.getDataList().get(1).getTimestamp(), is(DUMMY_TIMESTAMP_2));
        assertThat(result.getDescription(), is(DUMMY_ROOT_DESCRIPTION));
        assertThat(result.getPrivacySearchTag(), is("test"));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getVersion(), is(DUMMY_VERSION));
    }

    @Test
    public void shouldCreateRootDataForPathAndByteArrayUpload() {
        given(mockPrivacyStrategy.encryptData(DUMMY_DATA_1)).willReturn(DUMMY_ENCRYPTED_DATA_1);
        given(mockContentTypeUtils.detectContentType(DUMMY_DATA_1, null))
                .willReturn(Observable.just(DUMMY_CONTENT_TYPE_1));
        given(mockDigestUtils.digest(DUMMY_ENCRYPTED_DATA_1)).willReturn(Observable.just(DUMMY_DIGEST_1));
        given(mockIpfsUploadService.uploadByteArray(DUMMY_ENCRYPTED_DATA_1))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH_1, DUMMY_TIMESTAMP_1)));
        given(mockIpfsUploadService.uploadPath(DUMMY_PATH_2))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH_2, DUMMY_TIMESTAMP_2)));

        final ProximaxRootDataModel result = unitUnderTest.createRootData(samplePathAndByteArrayUploadParam()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDataList(), is(notNullValue()));
        assertThat(result.getDataList(), hasSize(2));
        assertThat(result.getDataList().get(0).getContentType(), is(DUMMY_CONTENT_TYPE_1));
        assertThat(result.getDataList().get(0).getDataHash(), is(DUMMY_DATA_HASH_1));
        assertThat(result.getDataList().get(0).getDescription(), is(DUMMY_DESCRIPTION_1));
        assertThat(result.getDataList().get(0).getDigest(), is(DUMMY_DIGEST_1));
        assertThat(result.getDataList().get(0).getMetadata(), is(DUMMY_METADATA_1));
        assertThat(result.getDataList().get(0).getName(), is(DUMMY_NAME_1));
        assertThat(result.getDataList().get(0).getTimestamp(), is(DUMMY_TIMESTAMP_1));
        assertThat(result.getDataList().get(1).getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
        assertThat(result.getDataList().get(1).getDataHash(), is(DUMMY_DATA_HASH_2));
        assertThat(result.getDataList().get(1).getDescription(), is(DUMMY_DESCRIPTION_2));
        assertThat(result.getDataList().get(1).getDigest(), is(nullValue()));
        assertThat(result.getDataList().get(1).getMetadata(), is(DUMMY_METADATA_2));
        assertThat(result.getDataList().get(1).getName(), is(DUMMY_NAME_2));
        assertThat(result.getDataList().get(1).getTimestamp(), is(DUMMY_TIMESTAMP_2));
        assertThat(result.getDescription(), is(DUMMY_ROOT_DESCRIPTION));
        assertThat(result.getPrivacySearchTag(), is("test"));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getVersion(), is(DUMMY_VERSION));
    }

    private UploadParameter sampleByteArrayUploadParamWithComputeDigestTrue()  {
        return UploadParameter.create("dhsakhdkashkdsahkdsa", "ndsakjhdkjsahdasjhjkdsa")
                .addByteArray(ByteArrayParameterData.create(DUMMY_DATA_1)
                        .description(DUMMY_DESCRIPTION_1)
                        .metadata(DUMMY_METADATA_1)
                        .name(DUMMY_NAME_1)
                        .build())
                .addByteArray(ByteArrayParameterData.create(DUMMY_DATA_2)
                        .description(DUMMY_DESCRIPTION_2)
                        .metadata(DUMMY_METADATA_2)
                        .name(DUMMY_NAME_2)
                        .build())
                .description(DUMMY_ROOT_DESCRIPTION)
                .computeDigest(true)
                .privacyStrategy(mockPrivacyStrategy)
                .build();
    }

    private UploadParameter sampleByteArrayUploadParamWithComputeDigestFalse()  {
        return UploadParameter.create("dhsakhdkashkdsahkdsa", "ndsakjhdkjsahdasjhjkdsa")
                .addByteArray(StringParameterData.create(DUMMY_DATA_1)
                        .description(DUMMY_DESCRIPTION_1)
                        .metadata(DUMMY_METADATA_1)
                        .name(DUMMY_NAME_1)
                        .build())
                .addByteArray(ByteArrayParameterData.create(DUMMY_DATA_2)
                        .description(DUMMY_DESCRIPTION_2)
                        .metadata(DUMMY_METADATA_2)
                        .name(DUMMY_NAME_2)
                        .build())
                .description(DUMMY_ROOT_DESCRIPTION)
                .computeDigest(false)
                .privacyStrategy(mockPrivacyStrategy)
                .build();
    }

    private UploadParameter samplePathUploadParam()  {
        return UploadParameter.create("dhsakhdkashkdsahkdsa", "ndsakjhdkjsahdasjhjkdsa")
                .addPath(PathParameterData.create(DUMMY_PATH_1)
                        .description(DUMMY_DESCRIPTION_1)
                        .metadata(DUMMY_METADATA_1)
                        .name(DUMMY_NAME_1)
                        .build())
                .addPath(PathParameterData.create(DUMMY_PATH_2)
                        .description(DUMMY_DESCRIPTION_2)
                        .metadata(DUMMY_METADATA_2)
                        .name(DUMMY_NAME_2)
                        .build())
                .description(DUMMY_ROOT_DESCRIPTION)
                .computeDigest(true)
                .privacyStrategy(mockPrivacyStrategy)
                .build();
    }

    private UploadParameter samplePathAndByteArrayUploadParam()  {
        return UploadParameter.create("dhsakhdkashkdsahkdsa", "ndsakjhdkjsahdasjhjkdsa")
                .addByteArray(StringParameterData.create(DUMMY_DATA_1)
                        .description(DUMMY_DESCRIPTION_1)
                        .metadata(DUMMY_METADATA_1)
                        .name(DUMMY_NAME_1)
                        .build())
                .addPath(PathParameterData.create(DUMMY_PATH_2)
                        .description(DUMMY_DESCRIPTION_2)
                        .metadata(DUMMY_METADATA_2)
                        .name(DUMMY_NAME_2)
                        .build())
                .description(DUMMY_ROOT_DESCRIPTION)
                .computeDigest(true)
                .privacyStrategy(mockPrivacyStrategy)
                .build();
    }

}
