package io.proximax.service.client;

import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.IpfsClientFailureException;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
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
        checkArgument(ipfsConnection != null, "ipfsConnection is required");

        this.ipfsConnection = ipfsConnection;
    }

    /**
     * Add/Upload a file (represented as byte arrays) to IPFS
     * <br>
     * <br>
     * This method is equivalent to `ipfs add` CLI command
     * @param data the byte array being added
     * @return the hash (base58) for the data uploaded
     */
    public Observable<String> add(byte[] data) {
        checkArgument(data != null, "data is required");

        return Observable.just(data)
                .observeOn(Schedulers.io())
                .map(this::addByteArray)
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new IpfsClientFailureException(String.format("Failed to add resource"), ex)))
                .observeOn(Schedulers.computation())
                .map(merkleNodes -> merkleNodes.get(0).hash.toBase58());
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
        checkArgument(dataHash != null, "dataHash is required");

        return Observable.just(dataHash)
                .observeOn(Schedulers.computation())
                .map(hash -> Multihash.fromBase58(dataHash))
                .observeOn(Schedulers.io())
                .map(this::pin)
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new IpfsClientFailureException(String.format("Failed to pin for %s", dataHash), ex)))
                .observeOn(Schedulers.computation())
                .map(list -> list.stream().map(Multihash::toBase58).collect(toList()));
    }

    /**
     * Retrieves the file from IPFS given a hash
     * <br>
     * <br>
     * This method is equivalent to `ipfs cat` CLI command
     * @param dataHash the hash (base58) of an IPFS file
     * @return the file (represented as byte arrays)
     */
    public Observable<byte[]> get(String dataHash) {
        checkArgument(dataHash != null, "dataHash is required");

        return Observable.just(dataHash)
                .observeOn(Schedulers.computation())
                .map(hash -> Multihash.fromBase58(dataHash))
                .observeOn(Schedulers.io())
                .map(this::getByteArray)
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new IpfsClientFailureException(String.format("Failed to get resource for %s", dataHash), ex)));
    }

    private List<MerkleNode> addByteArray(byte[] data) throws IOException {
        return ipfsConnection.getIpfs().add(new NamedStreamable.ByteArrayWrapper(data));
    }

    private List<Multihash> pin(Multihash multihash) throws IOException {
        return ipfsConnection.getIpfs().pin.add(multihash);
    }

    private byte[] getByteArray(Multihash multihash) throws IOException {
        return ipfsConnection.getIpfs().cat(multihash);
    }
}
