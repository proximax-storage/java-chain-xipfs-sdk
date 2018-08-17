package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.service.client.IpfsClient;
import io.reactivex.Observable;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The service class responsible for download from IPFS
 */
public class IpfsDownloadService {

    private final IpfsClient ipfsClient;

    /**
     * Construct this class
     * @param ipfsConnection the config class to connect to IPFS
     */
    public IpfsDownloadService(final IpfsConnection ipfsConnection) {
        this.ipfsClient = new IpfsClient(ipfsConnection);
    }

    IpfsDownloadService(final IpfsClient ipfsClient) {
        this.ipfsClient = ipfsClient;
    }

    /**
     * Download a list of data
     * @param dataHashList the list of datah hash
     * @return the list of data
     */
    public Observable<List<byte[]>> downloadList(final List<String> dataHashList) {
        checkArgument(dataHashList != null, "dataHashList is required");

        return Observable.fromIterable(dataHashList)
                .concatMapEager(this::download)
                .toList()
                .toObservable();
    }

    /**
     * Download the data
     * @param dataHash the data hash
     * @return the data
     */
    public Observable<byte[]> download(final String dataHash) {
        checkArgument(dataHash != null, "dataHash is required");

        return downloadData(dataHash);
    }

    private Observable<byte[]> downloadData(final String dataHash) {
        return ipfsClient.get(dataHash);
    }


}
