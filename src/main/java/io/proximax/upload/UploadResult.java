package io.proximax.upload;

import io.proximax.model.ProximaxRootDataModel;

/**
 * The model class that defines the result of an upload.
 * <br>
 * <br>
 * The following are its fields:
 * <ul>
 *     <li><b>transactionHash</b> - the blockchain transaction hash that refers to the upload instance and its data</li>
 *     <li><b>digest</b> - the digest for the root data that refers to the upload instance</li>
 *     <li><b>rootDataHash</b> - the data hash for the root data that refers to the upload instance</li>
 *     <li><b>rootData</b> - the root data (ProximaxRootDataModel) that describes the upload instance</li>
 * </ul>
 * @see Upload
 */
public class UploadResult {

    private String transactionHash;
    private String digest;
    private String rootDataHash;
    private ProximaxRootDataModel rootData;

    UploadResult(String transactionHash, String digest, String rootDataHash, ProximaxRootDataModel rootData) {
        this.transactionHash = transactionHash;
        this.digest = digest;
        this.rootDataHash = rootDataHash;
        this.rootData = rootData;
    }

    /**
     * Get the blockchain transaction hash that refers to the upload instance and its data
     * @return the blockchain transaction hash
     */
    public String getTransactionHash() {
        return transactionHash;
    }

    /**
     * Get the the digest for the root data that refers to the upload instance
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Get the data hash for the root data that refers to the upload instance
     * @return the root data hash
     */
    public String getRootDataHash() {
        return rootDataHash;
    }

    /**
     * Get the root data that describes the upload instance
     * @return the root data
     */
    public ProximaxRootDataModel getRootData() {
        return rootData;
    }

    static UploadResult create(String transactionHash, String digest, String rootDataHash,
                                      ProximaxRootDataModel rootData) {
        return new UploadResult(transactionHash, digest, rootDataHash, rootData);
    }
}
