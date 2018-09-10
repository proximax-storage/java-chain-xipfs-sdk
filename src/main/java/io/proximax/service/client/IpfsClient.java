package io.proximax.service.client;

import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.IpfsClientFailureException;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.util.stream.Collectors.toList;

/**
 * The client class that directly interface with IPFS using their SDK
 * <br>
 * <br>
 * This class delegates to IPFS the following:
 * <ul>
 *     <li>adding of file(represented as byte arrays) and returning the hash for it</li>
 *     <li>retrieving of file given a hash</li>
 *     <li>pinning a file given a hash to ensure it is not garbage collected</li>
 * </ul>
 */
public class IpfsClient {

    private final IpfsConnection ipfsConnection;

    /**
     * Construct the class with IPFSConnection
     * @param ipfsConnection the Ipfs connection
     */
    public IpfsClient(IpfsConnection ipfsConnection) {
        checkParameter(ipfsConnection != null, "ipfsConnection is required");

        this.ipfsConnection = ipfsConnection;
    }

    /**
     * Add/Upload a file (represented as byte stream) to IPFS
     * <br>
     * <br>
     * This method is equivalent to `ipfs add` CLI command
     * @param byteStream the byte stream to upload
     * @return the hash (base58) for the data uploaded
     */
    public Observable<String> addByteStream(InputStream byteStream) {
        checkParameter(byteStream != null, "byteStream is required");

        return Observable.just(byteStream)
                .observeOn(Schedulers.io())
                .map(stream -> ipfsConnection.getIpfs().add(new NamedStreamable.InputStreamWrapper(stream)))
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new IpfsClientFailureException(String.format("Failed to add resource"), ex)))
                .observeOn(Schedulers.computation())
                .map(merkleNodes -> merkleNodes.get(0).hash.toBase58());
    }

    /**
     * Add/Upload a path (directory or folder) to IPFS
     * <br>
     * <br>
     * This method is equivalent to `ipfs add` CLI command
     * @param path the path being added
     * @return the hash (base58) for the data uploaded
     */
    public Observable<String> addPath(File path) {
        checkParameter(path != null, "path is required");
        checkParameter(path.isDirectory(), "path should be directory/folder");

        return Observable.just(path)
                .observeOn(Schedulers.io())
                .map(pathInput -> ipfsConnection.getIpfs().add(new NamedStreamable.FileWrapper(pathInput)))
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new IpfsClientFailureException(String.format("Failed to add path"), ex)))
                .observeOn(Schedulers.computation())
                .map(merkleNodes -> merkleNodes.get(merkleNodes.size()-1).hash.toBase58());
    }

    /**
     * Pin a file on IPFS given a hash
     * <br>
     * <br>
     * This method is equivalent to `ipfs pin add` CLI command
     * @param dataHash the hash (base58) of an IPFS file
     * @return list of hashes pinned (includes children if hash used is a directory)
     */
    public Observable<List<String>> pin(String dataHash) {
        checkParameter(dataHash != null, "dataHash is required");

        return Observable.just(dataHash)
                .observeOn(Schedulers.computation())
                .map(hash -> Multihash.fromBase58(dataHash))
                .observeOn(Schedulers.io())
                .map(hash -> ipfsConnection.getIpfs().pin.add(hash))
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new IpfsClientFailureException(String.format("Failed to pin for %s", dataHash), ex)))
                .observeOn(Schedulers.computation())
                .map(list -> list.stream().map(Multihash::toBase58).collect(toList()));
    }

    /**
     * Retrieves the file stream from IPFS given a hash
     * <br>
     * <br>
     * This method is equivalent to `ipfs cat` CLI command
     * @param dataHash the hash (base58) of an IPFS file
     * @return the file (represented as byte stream)
     */
    public Observable<InputStream> getByteStream(String dataHash) {
        checkParameter(dataHash != null, "dataHash is required");

        return Observable.just(dataHash)
                .observeOn(Schedulers.computation())
                .map(hash -> Multihash.fromBase58(dataHash))
                .observeOn(Schedulers.io())
                .map(hash -> ipfsConnection.getIpfs().catStream(hash))
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new IpfsClientFailureException(String.format("Failed to getByteStream resource for %s", dataHash), ex)));
    }
}
