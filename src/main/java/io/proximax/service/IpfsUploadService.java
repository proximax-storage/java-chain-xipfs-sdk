package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.service.client.IpfsClient;
import io.reactivex.Observable;

import java.io.File;
import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for uploading to IPFS
 */
public class IpfsUploadService {

    private final IpfsClient ipfsClient;

    /**
     * Construct this class
     * @param ipfsConnection the config class to connect to IPFS
     */
    public IpfsUploadService(final IpfsConnection ipfsConnection) {
        this.ipfsClient = new IpfsClient(ipfsConnection);
    }

    IpfsUploadService(final IpfsClient ipfsClient) {
        this.ipfsClient = ipfsClient;
    }

    /**
     * Upload byte stream
     * @param byteStream the byte stream
     * @return the IPFS upload response
     */
    public Observable<IpfsUploadResponse> uploadByteStream(final InputStream byteStream) {
        checkParameter(byteStream != null, "byteStream is required");

        return ipfsClient.addByteStream(byteStream).map(dataHash -> new IpfsUploadResponse(dataHash, System.currentTimeMillis()));
    }

    /**
     * Upload a path
     * @param path the data
     * @return the IPFS upload response
     */
    public Observable<IpfsUploadResponse> uploadPath(final File path) {
        checkParameter(path != null, "path is required");

        return ipfsClient.addPath(path).map(dataHash -> new IpfsUploadResponse(dataHash, System.currentTimeMillis()));
    }
}
