package io.proximax.service.api;

import io.reactivex.Observable;

import java.io.File;
import java.io.InputStream;

/**
 * This interface defines the methods that a file storage client API must implement
 */
public interface FileStorageClientApi {

    /**
     * Add/Upload a file (represented as byte stream) to storage node
     *
     * @param byteStream the byte stream to upload
     * @return the hash (base58) for the data uploaded
     */
    Observable<String> addByteStream(InputStream byteStream);

    /**
     * Add/Upload a path
     *
     * @param path the path being added
     * @return the hash (base58) for the data uploaded
     */
    Observable<String> addPath(File path);

    /**
     * Retrieves the file stream given a hash
     *
     * @param dataHash the hash (base58) of an IPFS file
     * @return the file (represented as byte stream)
     */
    Observable<InputStream> getByteStream(String dataHash);
}
