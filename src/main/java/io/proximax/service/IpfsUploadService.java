package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.service.client.IpfsClient;
import io.reactivex.Observable;

import java.io.File;

import static com.google.common.base.Preconditions.checkArgument;

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
     * Upload a byte array
     * @param data the byte array data
     * @return the IPFS upload response
     */
    public Observable<IpfsUploadResponse> uploadByteArray(final byte[] data) {
        checkArgument(data != null, "data is required");

        return ipfsClient.addByteArray(data).map(dataHash -> new IpfsUploadResponse(dataHash, System.currentTimeMillis()));
    }

    /**
     * Upload a path
     * @param path the data
     * @return the IPFS upload response
     */
    public Observable<IpfsUploadResponse> uploadPath(final File path) {
        checkArgument(path != null, "path is required");

        return ipfsClient.addPath(path).map(dataHash -> new IpfsUploadResponse(dataHash, System.currentTimeMillis()));
    }
}
