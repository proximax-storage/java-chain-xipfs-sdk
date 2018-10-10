package io.proximax.utils;

import io.proximax.model.PrivacyType;
import io.proximax.model.ProximaxDataModel;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.upload.ByteArrayParameterData;
import org.junit.Test;

import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class JsonUtilsTest {

    private static final String SAMPLE_DATA_HASH = "QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf";
    private static final String SAMPLE_DIGEST = "eqwewqewqewqewqewq";

    @Test
    public void shouldSerializeCorrectly() {
        final String json = JsonUtils.toJson(messagePayload());

        assertThat(json, is(messagePayloadAsJson()));
    }

    @Test
    public void shouldDeserializeCorrectly() {
        final ProximaxMessagePayloadModel model = JsonUtils.fromJson(messagePayloadAsJson(), ProximaxMessagePayloadModel.class);

        assertThat(model, is(notNullValue()));
        assertThat(model.getPrivacyType(), is(1001));
        assertThat(model.getVersion(), is("1.0"));
        assertThat(model.getData().getDigest(), is(SAMPLE_DIGEST));
        assertThat(model.getData().getDataHash(), is(SAMPLE_DATA_HASH));
        assertThat(model.getData().getTimestamp(), is(1L));
        assertThat(model.getData().getDescription(), is("test description"));
        assertThat(model.getData().getMetadata(), is(singletonMap("testKey", "testValue")));
        assertThat(model.getData().getName(), is("test name"));
        assertThat(model.getData().getContentType(), is("text/plain"));

    }

    private ProximaxMessagePayloadModel messagePayload() {
        return ProximaxMessagePayloadModel.create(PrivacyType.PLAIN.getValue(), "1.0",
                ProximaxDataModel.create(
                        ByteArrayParameterData.create("test".getBytes(), "test description", "test name",
                                "text/plain+original", singletonMap("testKey", "testValue")),
                        SAMPLE_DATA_HASH, SAMPLE_DIGEST, "text/plain", 1L));
    }

    private String messagePayloadAsJson() {
        return "{" +
                "\"privacyType\":1001," +
                "\"version\":\"1.0\"," +
                "\"data\":{" +
                    "\"digest\":\"eqwewqewqewqewqewq\"," +
                    "\"dataHash\":\"QmXkGKuB74uVJijEjgmGa9jMiY3MBiziFQPnrzvTZ3DKJf\"," +
                    "\"timestamp\":1," +
                    "\"description\":\"test description\"," +
                    "\"metadata\":{" +
                        "\"testKey\":\"testValue\"}," +
                    "\"name\":\"test name\"," +
                    "\"contentType\":\"text/plain\"}}";
    }

}
