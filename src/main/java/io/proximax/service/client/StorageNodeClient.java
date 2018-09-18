package io.proximax.service.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.proximax.connection.HttpProtocol;
import io.proximax.connection.StorageConnection;
import io.proximax.exceptions.StorageNodeConnectionFailureException;
import io.proximax.exceptions.UploadPathNotSupportedException;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.service.api.FileStorageClientApi;
import io.reactivex.Observable;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The client class that directly interface with Storage Node API
 * <br>
 * <br>
 * This client is responsible for the following.
 * <ul>
 * <li>adding of file(represented as byte arrays) and returning the hash for it</li>
 * <li>retrieving of file given a hash</li>
 * </ul>
 */
public class StorageNodeClient implements FileStorageClientApi {

    public static final String HEADER_CREDENTIALS = "HeaderCredentials";

    private final String apiUrl;
    private final String headerCredentials;
    private final ObjectMapper objectMapper;

    /**
     * Construct the class with Storage connection
     *
     * @param storageConnection the storage connection
     */
    public StorageNodeClient(StorageConnection storageConnection) {
        checkParameter(storageConnection != null, "storageConnection is required");

        this.apiUrl = storageConnection.getApiUrl();
        this.headerCredentials = String.format("NemAddress=%s; Bearer %s", storageConnection.getNemAddress(), storageConnection.getBearerToken());
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Add/Upload a file (represented as byte stream) to storage node
     *
     * @param byteStream the byte stream to upload
     * @return the hash (base58) for the data uploaded
     */
    public Observable<String> addByteStream(InputStream byteStream) {
        checkParameter(byteStream != null, "byteStream is required");

        return Observable.fromCallable(() -> {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                final HttpEntity file = MultipartEntityBuilder.create()
                        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                        .addBinaryBody("file", byteStream,
                                ContentType.DEFAULT_BINARY, "file").build();
                final HttpPost httpPost = new HttpPost(apiUrl + "/upload/file");
                httpPost.setEntity(file);
                httpPost.setHeader(HEADER_CREDENTIALS, headerCredentials);

                final CloseableHttpResponse response = httpClient.execute(httpPost);
                final UploadFileResponse uploadFileResponse = objectMapper.readValue(response.getEntity().getContent(), UploadFileResponse.class);
                return uploadFileResponse.dataHash;
            } catch (IOException e) {
                throw new StorageNodeConnectionFailureException("Failed to upload", e);
            }
        });
    }

    /**
     * The response model when calling storage API GET /upload/file
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UploadFileResponse {
        private final String dataHash;

        // TODO update to dataHash once changed on platform

        /**
         * Constructor for this class
         *
         * @param dataHash the ipfs data hash
         */
        public UploadFileResponse(@JsonProperty("DataHash") String dataHash) {
            this.dataHash = dataHash;
        }

    }

    /**
     * Add/Upload a path (not supported)
     *
     * @param path the path being added
     * @return the hash (base58) for the data uploaded
     */
    public Observable<String> addPath(File path) {
        return Observable.error(new UploadPathNotSupportedException("Path upload is not supported on storage"));
    }

    /**
     * Retrieves the file stream from storage node given a hash
     *
     * @param dataHash the hash (base58) of an IPFS file
     * @return the file (represented as byte stream)
     */
    public Observable<InputStream> getByteStream(String dataHash) {
        checkParameter(dataHash != null, "dataHash is required");

        return Observable.fromCallable(() -> {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            final HttpGet httpGet = new HttpGet(new URIBuilder(apiUrl + "/download/file")
                    .addParameter("dataHash", dataHash).build());
            httpGet.setHeader(HEADER_CREDENTIALS, headerCredentials);

            final CloseableHttpResponse response = httpClient.execute(httpGet);
            return response.getEntity().getContent();
        });
    }

    /**
     * Retrieves node info of the storage
     *
     * @return the node info of the storage
     */
    public Observable<NodeInfoResponse> getNodeInfo() {
        return Observable.fromCallable(() -> {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                final HttpGet httpGet = new HttpGet(apiUrl + "/node/info");
                httpGet.setHeader(HEADER_CREDENTIALS, headerCredentials);

                final CloseableHttpResponse response = httpClient.execute(httpGet);
                return objectMapper.readValue(response.getEntity().getContent(), NodeInfoResponse.class);
            }
        });
    }

    /**
     * The response model when calling storage API GET /node/info
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NodeInfoResponse {
        private final NodeInfoResponseBlockchainNetwork blockchainNetwork;

        /**
         * Constructor for this class
         *
         * @param blockchainNetwork the blockchain network details
         */
        public NodeInfoResponse(@JsonProperty("blockchainNetwork") NodeInfoResponseBlockchainNetwork blockchainNetwork) {
            this.blockchainNetwork = blockchainNetwork;
        }

        /**
         * Get the blockchain network details
         *
         * @return the blockchain network details
         */
        public NodeInfoResponseBlockchainNetwork getBlockchainNetwork() {
            return blockchainNetwork;
        }
    }

    /**
     * The model for blockchain network details of NodeInfoResponse
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NodeInfoResponseBlockchainNetwork {
        private final HttpProtocol protocol;
        private final int port;
        private final String host;
        private final BlockchainNetworkType networkType;

        /**
         * Constructor for this class
         *
         * @param protocol    the blockchain protocol
         * @param port        the blockchain port
         * @param host        the blockchain host
         * @param networkType the blockchain network type
         */
        public NodeInfoResponseBlockchainNetwork(@JsonProperty("protocol") String protocol,
                                                 @JsonProperty("port") int port,
                                                 @JsonProperty("host") String host,
                                                 @JsonProperty("network") String networkType) {
            this.protocol = HttpProtocol.fromString(protocol);
            this.port = port;
            this.host = host;
            this.networkType = BlockchainNetworkType.fromString(networkType);
        }

        /**
         * Get the blockchain protocol
         *
         * @return the blockchain protocol
         */
        public HttpProtocol getProtocol() {
            return protocol;
        }

        /**
         * Get the blockchain port
         *
         * @return the blockchain port
         */
        public int getPort() {
            return port;
        }

        /**
         * Get the blockchain host
         *
         * @return the blockchain host
         */
        public String getHost() {
            return host;
        }

        /**
         * Get the blockchain network type
         *
         * @return the blockchain network type
         */
        public BlockchainNetworkType getNetworkType() {
            return networkType;
        }
    }
}

