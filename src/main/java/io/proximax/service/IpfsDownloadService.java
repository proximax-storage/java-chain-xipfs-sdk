package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.model.StoreType;
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

    public Observable<List<byte[]>> downloadList(final List<String> dataHashList, final StoreType storeType) {
        checkArgument(dataHashList != null, "dataHashList is required");
        checkArgument(storeType != null, "storeType is required");

        return Observable.fromIterable(dataHashList)
                .concatMapEager(dataHash -> download(dataHash, storeType))
                .toList()
                .toObservable();
    }

    public Observable<byte[]> download(final String dataHash, final StoreType storeType) {
        checkArgument(dataHash != null, "dataHash is required");
        checkArgument(storeType != null, "storeType is required");

        return downloadUsingStoreType(dataHash, storeType);
    }

    private Observable<byte[]> downloadUsingStoreType(final String dataHash, final StoreType storeType) {
        if (storeType == StoreType.BLOCK) {
            return ipfsClient.getBlock(dataHash);
        }
        return ipfsClient.get(dataHash);
    }


}
