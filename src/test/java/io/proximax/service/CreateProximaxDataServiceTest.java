package io.proximax.service;

import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxDataModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.upload.ByteArrayParameterData;
import io.proximax.upload.PathParameterData;
import io.proximax.upload.UploadParameter;
import io.proximax.utils.ContentTypeUtils;
import io.reactivex.Observable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.testsupport.Constants.TEST_PATH_FILE;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

public class CreateProximaxDataServiceTest {

    private static final InputStream DUMMY_DATA_STREAM = new ByteArrayInputStream("dopsaipdlsnlxnz,cn,zxnclznxlnldsaldslkaj;as.".getBytes());
    private static final String DUMMY_NAME = "name 1";
    private static final String DUMMY_DESCRIPTION = "data description 1";
    private static final Map<String, String> DUMMY_METADATA = singletonMap("key1", "val1");
    private static final InputStream DUMMY_ENCRYPTED_DATA_STREAM = new ByteArrayInputStream("dsajhjdhaskhdksahkdsaljkjlxnzcm,nxz".getBytes());
    private static final String DUMMY_CONTENT_TYPE = "text/plain";
    private static final String DUMMY_DATA_HASH = "Qmdyueoqwoeuowqueowquioeuioqwuoi";
    private static final String DUMMY_DIGEST = "Qmdsewquywqiyeiuqwyiueyqiuyeuiwyqid";
    private static final File DUMMY_PATH = TEST_PATH_FILE;
    private static final Long DUMMY_TIMESTAMP = 1000L;

    private static final String SAMPLE_PRIVATE_KEY = "8374B5915AEAB6308C34368B15ABF33C79FD7FEFC0DEAF9CC51BA57F120F1190";

    private CreateProximaxDataService unitUnderTest;

    @Mock
    private FileUploadService mockFileUploadService;

    @Mock
    private ContentTypeUtils mockContentTypeUtils;

    @Mock
    private PrivacyStrategy mockPrivacyStrategy;

    @Mock
    private ByteArrayParameterData mockByteArrayParameterData;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        unitUnderTest = new CreateProximaxDataService(mockFileUploadService, mockContentTypeUtils);

        given(mockPrivacyStrategy.getPrivacyType()).willReturn(PrivacyType.PLAIN.getValue());
        given(mockByteArrayParameterData.getByteStream()).willReturn(DUMMY_DATA_STREAM);
        given(mockByteArrayParameterData.getDescription()).willReturn(DUMMY_DESCRIPTION);
        given(mockByteArrayParameterData.getName()).willReturn(DUMMY_NAME);
        given(mockByteArrayParameterData.getMetadata()).willReturn(DUMMY_METADATA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullUploadParameter() {
        unitUnderTest.createData(null);
    }

    @Test
    public void shouldCreateDataModelForByteArrayUpload() {
        given(mockPrivacyStrategy.encryptStream(DUMMY_DATA_STREAM)).willReturn(DUMMY_ENCRYPTED_DATA_STREAM);
        given(mockFileUploadService.uploadByteStream(any(), eq(mockPrivacyStrategy), eq(false)))
                .willReturn(Observable.just(new FileUploadResponse(DUMMY_DATA_HASH, DUMMY_TIMESTAMP, DUMMY_DIGEST)));

        final ProximaxDataModel result = unitUnderTest.createData(sampleByteArrayUploadParam()).blockingFirst();

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
    public void shouldCreateDataModelForPathUpload() {
        given(mockFileUploadService.uploadPath(DUMMY_PATH))
                .willReturn(Observable.just(new FileUploadResponse(DUMMY_DATA_HASH, DUMMY_TIMESTAMP, null)));
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
    public void shouldCreateDataModelForByteArrayUploadWithDetectContentTypeTrue() {
        given(mockPrivacyStrategy.encryptStream(DUMMY_DATA_STREAM)).willReturn(DUMMY_ENCRYPTED_DATA_STREAM);
        given(mockContentTypeUtils.detectContentType(DUMMY_DATA_STREAM)).willReturn(Observable.just(DUMMY_CONTENT_TYPE));
        given(mockFileUploadService.uploadByteStream(any(), eq(mockPrivacyStrategy), eq(false)))
                .willReturn(Observable.just(new FileUploadResponse(DUMMY_DATA_HASH, DUMMY_TIMESTAMP, null)));

        final ProximaxDataModel result = unitUnderTest.createData(sampleByteArrayUploadParamWithDetectContentTypeTrue()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getContentType(), is(DUMMY_CONTENT_TYPE));
    }

    @Test
    public void shouldCreateDataModelForByteArrayUploadWithDetectContentTypeFalse() {
        given(mockPrivacyStrategy.encryptStream(DUMMY_DATA_STREAM)).willReturn(DUMMY_ENCRYPTED_DATA_STREAM);
        given(mockFileUploadService.uploadByteStream(any(), eq(mockPrivacyStrategy), eq(false)))
                .willReturn(Observable.just(new FileUploadResponse(DUMMY_DATA_HASH, DUMMY_TIMESTAMP, null)));

        final ProximaxDataModel result = unitUnderTest.createData(sampleByteArrayUploadParamWithDetectContentTypeFalse()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getContentType(), is(nullValue()));
    }

    private UploadParameter sampleByteArrayUploadParam()  {
        given(mockByteArrayParameterData.getContentType()).willReturn(DUMMY_CONTENT_TYPE);

        return UploadParameter.createForByteArrayUpload(mockByteArrayParameterData, SAMPLE_PRIVATE_KEY)
                .withPrivacyStrategy(mockPrivacyStrategy)
                .build();
    }

    private UploadParameter sampleByteArrayUploadParamWithDetectContentTypeTrue()  {
        return UploadParameter.createForByteArrayUpload(mockByteArrayParameterData, SAMPLE_PRIVATE_KEY)
                .withDetectContentType(true)
                .withPrivacyStrategy(mockPrivacyStrategy)
                .build();
    }

    private UploadParameter sampleByteArrayUploadParamWithDetectContentTypeFalse()  {
        return UploadParameter.createForByteArrayUpload(mockByteArrayParameterData, SAMPLE_PRIVATE_KEY)
                .withDetectContentType(false)
                .withPrivacyStrategy(mockPrivacyStrategy)
                .build();
    }

    private UploadParameter samplePathUploadParam() {
        return UploadParameter.createForPathUpload(
                PathParameterData.create(DUMMY_PATH, DUMMY_DESCRIPTION, DUMMY_NAME, DUMMY_METADATA), SAMPLE_PRIVATE_KEY)
                .withPrivacyStrategy(mockPrivacyStrategy)
                .build();
    }
}
