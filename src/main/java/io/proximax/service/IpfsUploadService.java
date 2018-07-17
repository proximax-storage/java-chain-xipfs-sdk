package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.model.StoreType;
import io.proximax.service.client.IpfsClient;
import io.reactivex.Observable;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class IpfsUploadService {

    private final IpfsClient ipfsClient;

    public IpfsUploadService(final IpfsConnection ipfsConnection) {
        this.ipfsClient = new IpfsClient(ipfsConnection);
    }

    IpfsUploadService(final IpfsClient ipfsClient) {
        this.ipfsClient = ipfsClient;
    }

    public Observable<List<IpfsUploadResponse>> uploadList(final List<byte[]> dataList, final StoreType storeType) {
        checkArgument(dataList != null, "dataList is required");
        checkArgument(storeType != null, "storeType is required");

        return Observable.fromIterable(dataList)
                .concatMapEager(data -> upload(data, storeType))
                .toList()
                .toObservable();
    }

    public Observable<IpfsUploadResponse> upload(final byte[] data, final StoreType storeType) {
        checkArgument(data != null, "data is required");
        checkArgument(storeType != null, "storeType is required");

        return uploadUsingStoreType(data, storeType)
                .map(dataHash -> new IpfsUploadResponse(dataHash, System.currentTimeMillis()));
    }

    private Observable<String> uploadUsingStoreType(final byte[] data, final StoreType storeType) {
        if (storeType == StoreType.BLOCK) {
          return ipfsClient.addBlock(data);
          // if added as block, data cannot be pinned
          //.flatMap(dataHash -> ipfsClient.pin(dataHash).map(pinned -> dataHash))
        }
        return ipfsClient.add(data);
    }
}
