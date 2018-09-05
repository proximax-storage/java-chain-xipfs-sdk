package io.proximax.service;

import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxDataModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.upload.ByteArrayParameterData;
import io.proximax.upload.PathParameterData;
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
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.BDDMockito.given;

public class CreateProximaxDataServiceTest {

    private static final byte[] DUMMY_DATA = "dopsaipdlsnlxnz,cn,zxnclznxlnldsaldslkaj;as.".getBytes();
    private static final String DUMMY_NAME = "name 1";
    private static final String DUMMY_DESCRIPTION = "data description 1";
    private static final Map<String, String> DUMMY_METADATA = singletonMap("key1", "val1");
    private static final byte[] DUMMY_ENCRYPTED_DATA = "dsajhjdhaskhdksahkdsaljkjlxnzcm,nxz".getBytes();
    private static final String DUMMY_DIGEST = "Qmdsewquywqiyeiuqwyiueyqiuyeuiwyqid";
    private static final String DUMMY_CONTENT_TYPE = "text/plain";
    private static final String DUMMY_DATA_HASH = "Qmdyueoqwoeuowqueowquioeuioqwuoi";
    private static final File DUMMY_PATH = new File("src//test//resources//test_path");
    private static final Long DUMMY_TIMESTAMP = 1000L;

    private CreateProximaxDataService unitUnderTest;

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

        unitUnderTest = new CreateProximaxDataService(mockIpfsUploadService, mockDigestUtils, mockContentTypeUtils);

        given(mockPrivacyStrategy.getPrivacyType()).willReturn(PrivacyType.PLAIN.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullUploadParameter() {
        unitUnderTest.createData(null);
    }

    @Test
    public void shouldCreateRootDataForByteArrayUpload() {
        given(mockPrivacyStrategy.encryptData(DUMMY_DATA)).willReturn(DUMMY_ENCRYPTED_DATA);
        given(mockDigestUtils.digest(DUMMY_ENCRYPTED_DATA)).willReturn(Observable.just(DUMMY_DIGEST));
        given(mockIpfsUploadService.uploadByteArray(DUMMY_ENCRYPTED_DATA))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH, DUMMY_TIMESTAMP)));

        final ProximaxDataModel result = unitUnderTest.createData(sampleByteArrayUploadParamWithComputeDigestTrue()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getContentType(), is(DUMMY_CONTENT_TYPE));
        assertThat(result.getDataHash(), is(DUMMY_DATA_HASH));
        assertThat(result.getDescription(), is(DUMMY_DESCRIPTION));
        assertThat(result.getDigest(), is(DUMMY_DIGEST));
        assertThat(result.getMetadata(), is(DUMMY_METADATA));
        assertThat(result.getName(), is(DUMMY_NAME));
        assertThat(result.getTimestamp(), is(DUMMY_TIMESTAMP));
    }

    @Test
    public void shouldCreateRootDataForPathUpload() {
        given(mockIpfsUploadService.uploadPath(DUMMY_PATH))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH, DUMMY_TIMESTAMP)));

        final ProximaxDataModel result = unitUnderTest.createData(samplePathUploadParam()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getContentType(), is(PATH_UPLOAD_CONTENT_TYPE));
        assertThat(result.getDataHash(), is(DUMMY_DATA_HASH));
        assertThat(result.getDescription(), is(DUMMY_DESCRIPTION));
        assertThat(result.getDigest(), is(nullValue()));
        assertThat(result.getMetadata(), is(DUMMY_METADATA));
        assertThat(result.getName(), is(DUMMY_NAME));
        assertThat(result.getTimestamp(), is(DUMMY_TIMESTAMP));
    }

    @Test
    public void shouldCreateRootDataForByteArrayUploadAndWhenComputeDigestTrue() {
        given(mockPrivacyStrategy.encryptData(DUMMY_DATA)).willReturn(DUMMY_ENCRYPTED_DATA);
        given(mockDigestUtils.digest(DUMMY_ENCRYPTED_DATA)).willReturn(Observable.just(DUMMY_DIGEST));
        given(mockIpfsUploadService.uploadByteArray(DUMMY_ENCRYPTED_DATA))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH, DUMMY_TIMESTAMP)));

        final ProximaxDataModel result = unitUnderTest.createData(sampleByteArrayUploadParamWithComputeDigestTrue()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDigest(), is(DUMMY_DIGEST));
    }

    @Test
    public void shouldCreateRootDataForByteArrayUploadAndWhenComputeDigestFalse() {
        given(mockPrivacyStrategy.encryptData(DUMMY_DATA)).willReturn(DUMMY_ENCRYPTED_DATA);
        given(mockIpfsUploadService.uploadByteArray(DUMMY_ENCRYPTED_DATA))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH, DUMMY_TIMESTAMP)));

        final ProximaxDataModel result = unitUnderTest.createData(sampleByteArrayUploadParamWithComputeDigestFalse()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getDigest(), is(nullValue()));
    }

    @Test
    public void shouldCreateRootDataForByteArrayUploadAndWhenDetectContentTypeTrue() {
        given(mockPrivacyStrategy.encryptData(DUMMY_DATA)).willReturn(DUMMY_ENCRYPTED_DATA);
        given(mockContentTypeUtils.detectContentType(DUMMY_DATA)).willReturn(Observable.just(DUMMY_CONTENT_TYPE));
        given(mockIpfsUploadService.uploadByteArray(DUMMY_ENCRYPTED_DATA))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH, DUMMY_TIMESTAMP)));

        final ProximaxDataModel result = unitUnderTest.createData(sampleByteArrayUploadParamWithDetectContentTypeTrue()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getContentType(), is(DUMMY_CONTENT_TYPE));
    }

    @Test
    public void shouldCreateRootDataForByteArrayUploadAndWhenDetectContentTypeFalse() {
        given(mockPrivacyStrategy.encryptData(DUMMY_DATA)).willReturn(DUMMY_ENCRYPTED_DATA);
        given(mockIpfsUploadService.uploadByteArray(DUMMY_ENCRYPTED_DATA))
                .willReturn(Observable.just(new IpfsUploadResponse(DUMMY_DATA_HASH, DUMMY_TIMESTAMP)));

        final ProximaxDataModel result = unitUnderTest.createData(sampleByteArrayUploadParamWithDetectContentTypeFalse()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getContentType(), is(nullValue()));
    }

    private UploadParameter sampleByteArrayUploadParamWithComputeDigestTrue()  {
        return UploadParameter.createForByteArrayUpload(
                ByteArrayParameterData.create(DUMMY_DATA, DUMMY_DESCRIPTION, DUMMY_NAME, DUMMY_CONTENT_TYPE, DUMMY_METADATA),
                "ndsakjhdkjsahdasjhjkdsa")
                .computeDigest(true)
                .privacyStrategy(mockPrivacyStrategy)
                .build();
    }

    private UploadParameter sampleByteArrayUploadParamWithComputeDigestFalse()  {
        return UploadParameter.createForByteArrayUpload(
                ByteArrayParameterData.create(DUMMY_DATA, DUMMY_DESCRIPTION, DUMMY_NAME, DUMMY_CONTENT_TYPE, DUMMY_METADATA),
                "ndsakjhdkjsahdasjhjkdsa")
                .computeDigest(false)
                .privacyStrategy(mockPrivacyStrategy)
                .build();
    }

    private UploadParameter sampleByteArrayUploadParamWithDetectContentTypeTrue()  {
        return UploadParameter.createForByteArrayUpload(
                ByteArrayParameterData.create(DUMMY_DATA, DUMMY_DESCRIPTION, DUMMY_NAME, null, DUMMY_METADATA),
                "ndsakjhdkjsahdasjhjkdsa")
                .detectContentType(true)
                .privacyStrategy(mockPrivacyStrategy)
                .build();
    }

    private UploadParameter sampleByteArrayUploadParamWithDetectContentTypeFalse()  {
        return UploadParameter.createForByteArrayUpload(
                ByteArrayParameterData.create(DUMMY_DATA, DUMMY_DESCRIPTION, DUMMY_NAME, null, DUMMY_METADATA),
                "ndsakjhdkjsahdasjhjkdsa")
                .detectContentType(false)
                .privacyStrategy(mockPrivacyStrategy)
                .build();
    }

    private UploadParameter samplePathUploadParam() {
        return UploadParameter.createForPathUpload(
                PathParameterData.create(DUMMY_PATH, DUMMY_DESCRIPTION, DUMMY_NAME, DUMMY_METADATA),
                "ndsakjhdkjsahdasjhjkdsa")
                .privacyStrategy(mockPrivacyStrategy)
                .build();
    }
}
