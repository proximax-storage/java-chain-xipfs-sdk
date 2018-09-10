package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.service.client.IpfsClient;
import io.reactivex.Observable;

import java.io.InputStream;

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
     * Download the data byte strean
     * @param dataHash the data hash
     * @return the data byte stream
     */
    public Observable<InputStream> download(final String dataHash) {
        checkArgument(dataHash != null, "dataHash is required");

        return downloadData(dataHash);
    }

    private Observable<InputStream> downloadData(final String dataHash) {
        return ipfsClient.getByteStream(dataHash);
    }


}
