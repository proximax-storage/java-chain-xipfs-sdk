package io.proximax.service;

import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxDataModel;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.upload.UploadParameter;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static io.proximax.model.Constants.SCHEMA_VERSION;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class CreateProximaxMessagePayloadServiceTest {

    private CreateProximaxMessagePayloadService unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new CreateProximaxMessagePayloadService();
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullUploadParameter() {
        unitUnderTest.createMessagePayload(null, sampleUploadedData());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullRootData() throws UnsupportedEncodingException {
        unitUnderTest.createMessagePayload(sampleUploadParameter(), null);
    }

    @Test
    public void shouldReturnMessagePayload() throws UnsupportedEncodingException {
        final ProximaxMessagePayloadModel result =
                unitUnderTest.createMessagePayload(sampleUploadParameter(), sampleUploadedData()).blockingFirst();

        assertThat(result, is(notNullValue()));
        assertThat(result.getPrivacyType(), is(PrivacyType.PLAIN.getValue()));
        assertThat(result.getVersion(), is(SCHEMA_VERSION));
        assertThat(result.getData().getDataHash(), is("sample data hash"));
        assertThat(result.getData().getDigest(), is("sample digest"));
        assertThat(result.getData().getTimestamp(), is(1L));
        assertThat(result.getData().getDescription(), is("sample description"));
        assertThat(result.getData().getName(), is("sample name"));
        assertThat(result.getData().getContentType(), is("text/plain"));
        assertThat(result.getData().getMetadata(), is(singletonMap("samplekey", "samplevalue")));
    }

    private ProximaxDataModel sampleUploadedData() {
        return new ProximaxDataModel("sample digest", "sample data hash", "sample description",
                singletonMap("samplekey", "samplevalue"), 1L, "sample name", "text/plain");
    }

    private UploadParameter sampleUploadParameter() throws UnsupportedEncodingException {
        return UploadParameter.createForStringUpload("sample", "sample private key")
                .plainPrivacy()
                .build();
    }

}
