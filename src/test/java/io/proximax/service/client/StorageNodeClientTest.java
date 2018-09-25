package io.proximax.service.client;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.proximax.connection.HttpProtocol;
import io.proximax.connection.StorageConnection;
import io.proximax.exceptions.UploadPathNotSupportedException;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.service.client.StorageNodeClient.NodeInfoResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.proximax.service.client.StorageNodeClient.HEADER_CREDENTIALS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class StorageNodeClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().dynamicPort());

    private StorageNodeClient unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new StorageNodeClient(
                new StorageConnection(
                        "localhost",
                        wireMockRule.port(),
                        HttpProtocol.HTTP,
                        "11111",
                        "nem:test"
                )
        );
    }

    @Test
    public void shouldFetchNodeInfo() {
        givenNodeInfoResponse();

        final NodeInfoResponse nodeInfoResponse = unitUnderTest.getNodeInfo().blockingFirst();

        assertThat(nodeInfoResponse, is(notNullValue()));
        assertThat(nodeInfoResponse.getBlockchainNetwork(), is(notNullValue()));
        assertThat(nodeInfoResponse.getBlockchainNetwork().getHost(), is("52.221.231.207"));
        assertThat(nodeInfoResponse.getBlockchainNetwork().getNetworkType(), is(BlockchainNetworkType.MIJIN_TEST));
        assertThat(nodeInfoResponse.getBlockchainNetwork().getPort(), is(3000));
        assertThat(nodeInfoResponse.getBlockchainNetwork().getProtocol(), is(HttpProtocol.HTTP));
    }

    @Test
    public void shouldDownloadFile() throws IOException {
        givenDownloadFileResponse();

        final InputStream sample = unitUnderTest.getByteStream("SAMPLE").blockingFirst();

        assertThat(sample, is(notNullValue()));
        assertThat(new String(IOUtils.toByteArray(sample)), is("test body"));
    }

    @Test(expected = UploadPathNotSupportedException.class)
    public void failOnAddPath() {
        unitUnderTest.addPath(new File("\\test_path")).blockingFirst();
    }

    @Test
    public void shouldUploadFile() throws IOException {
        givenUploadFileResponse();

        final String result =
                unitUnderTest.addByteStream(new ByteArrayInputStream("test body".getBytes())).blockingFirst();

        assertThat(result, is("SAMPLE"));
    }


    private void givenNodeInfoResponse() {
        stubFor(get(urlEqualTo("/node/info"))
                .withHeader(HEADER_CREDENTIALS, equalTo("NemAddress=nem:test; Bearer 11111"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody("{\n" +
                                "    \"identity\": {\n" +
                                "        \"name\": \"test\",\n" +
                                "        \"peerId\": \"xxx\"\n" +
                                "    },\n" +
                                "    \"blockchainNetwork\": {\n" +
                                "        \"protocol\": \"http\",\n" +
                                "        \"port\": 3000,\n" +
                                "        \"host\": \"52.221.231.207\",\n" +
                                "        \"network\": \"MIJIN_TEST\"\n" +
                                "    }\n" +
                                "}")));
    }

    private void givenDownloadFileResponse() {
        stubFor(get(urlEqualTo("/download/file?dataHash=SAMPLE"))
                .withHeader(HEADER_CREDENTIALS, equalTo("NemAddress=nem:test; Bearer 11111"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("test body")));
    }

    private void givenUploadFileResponse() {
        stubFor(put(urlEqualTo("/upload/file"))
                .withHeader(HEADER_CREDENTIALS, equalTo("NemAddress=nem:test; Bearer 11111"))
                .withHeader("Content-Type", containing("multipart/form-data"))
                .withHeader("Content-Type", containing("boundary="))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{\"DataHash\":\"SAMPLE\"}")));
    }
}
