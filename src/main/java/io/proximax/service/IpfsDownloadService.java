package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.service.client.IpfsClient;
import io.reactivex.Observable;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class IpfsDownloadService {

    private final IpfsClient ipfsClient;

    public IpfsDownloadService(final IpfsConnection ipfsConnection) {
        this.ipfsClient = new IpfsClient(ipfsConnection);
    }

    IpfsDownloadService(final IpfsClient ipfsClient) {
        this.ipfsClient = ipfsClient;
    }

    public Observable<List<byte[]>> downloadList(final List<String> dataHashList) {
        checkArgument(dataHashList != null, "dataHashList is required");

        return Observable.fromIterable(dataHashList)
                .concatMapEager(this::download)
                .toList()
                .toObservable();
    }

    public Observable<byte[]> download(final String dataHash) {
        checkArgument(dataHash != null, "dataHash is required");

        return downloadData(dataHash);
    }

    private Observable<byte[]> downloadData(final String dataHash) {
        return ipfsClient.get(dataHash);
    }


}
