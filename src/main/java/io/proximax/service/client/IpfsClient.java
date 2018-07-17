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
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;

/**
 * The client class that directly interface with IPFS
 *
 * This class is responsible for uploading byte arrays to IPFS as either blocks or resource (also referred as objects).
 * In addition, class also allows downloading of byte arrays already uploaded on the IPFS using their data hashes.
 */
public class IpfsClient {

    private final IpfsConnection ipfsConnection;

    /**
     * Construct the class using IPFS connection
     * @param ipfsConnection
     */
    public IpfsClient(IpfsConnection ipfsConnection) {
        this.ipfsConnection = ipfsConnection;

        checkArgument(ipfsConnection != null, "ipfsConnection is required");
    }

    /**
     * Uploads byte arrays to IPFS equivalent to the `add` command
     * This uploads the byte arrays as a resource which means it can be split into smaller blocks.
     * @param data the byte array being uploaded
     * @return the data hash (base58) to access the data uploaded
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
     * Uploads byte arrays to IPFS equivalent to the `block put` command.
     * This uploads the byte arrays as a single block which means the byte array should not be split into smaller blocks.
     * @param data the byte array being uploaded
     * @return the data hash (base58) to access the data uploaded
     */
    public Observable<String> addBlock(byte[] data) {
        checkArgument(data != null, "data is required");

        return Observable.just(data)
                .observeOn(Schedulers.io())
                .map(this::addByteArrayAsBlock)
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new IpfsClientFailureException(String.format("Failed to add block"), ex)))
                .observeOn(Schedulers.computation())
                .map(merkleNodes -> merkleNodes.hash.toBase58());
    }

    /**
     * Pin the data associated with the IPFS data hash
     * @param dataHash the data hash (base58) of an IPFS content
     * @return list of data hashes pinned
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
     * Retrieves the byte arrays stored as resource (also referred as objects) from IPFS of a given data hash
     * This is equivalent to 'cat' command on IPFS
     * @param dataHash the data hash (base58) of an IPFS content
     * @return the content associated with the data hash
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

    /**
     * Retrieves the byte arrays stored as block from IPFS of a given data hash
     * This is equivalent to 'block get' command on IPFS
     * @param dataHash the data hash (base58) of an IPFS content
     * @return the content associated with the data hash
     */
    public Observable<byte[]> getBlock(String dataHash) {
        checkArgument(dataHash != null, "dataHash is required");

        return Observable.just(dataHash)
                .observeOn(Schedulers.computation())
                .map(hash -> Multihash.fromBase58(dataHash))
                .observeOn(Schedulers.io())
                .map(this::getByteArrayAsBlock)
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new IpfsClientFailureException(String.format("Failed to get block for %s", dataHash), ex)));
    }

    private List<MerkleNode> addByteArray(byte[] data) throws IOException {
        return ipfsConnection.getIpfs().add(new NamedStreamable.ByteArrayWrapper(data));
    }

    private MerkleNode addByteArrayAsBlock(byte[] data) {
        return ipfsConnection.getIpfs().block.put(data, Optional.empty());
    }

    private List<Multihash> pin(Multihash multihash) throws IOException {
        return ipfsConnection.getIpfs().pin.add(multihash);
    }

    private byte[] getByteArray(Multihash multihash) throws IOException {
        return ipfsConnection.getIpfs().cat(multihash);
    }

    private byte[] getByteArrayAsBlock(Multihash multihash) throws IOException {
        return ipfsConnection.getIpfs().block.get(multihash);
    }

}
