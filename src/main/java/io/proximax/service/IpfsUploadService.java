package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.service.client.IpfsClient;
import io.reactivex.Observable;

import java.util.List;

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
     * Upload a list of data
     * @param dataList the list of data
     * @return the list of IPFS upload response
     */
    public Observable<List<IpfsUploadResponse>> uploadList(final List<byte[]> dataList) {
        checkArgument(dataList != null, "dataList is required");

        return Observable.fromIterable(dataList)
                .concatMapEager(this::upload)
                .toList()
                .toObservable();
    }

    /**
     * Upload a data
     * @param data the data
     * @return the IPFS upload response
     */
    public Observable<IpfsUploadResponse> upload(final byte[] data) {
        checkArgument(data != null, "data is required");

        return uploadData(data)
                .map(dataHash -> new IpfsUploadResponse(dataHash, System.currentTimeMillis()));
    }

    private Observable<String> uploadData(final byte[] data) {
        return ipfsClient.add(data);
    }
}
