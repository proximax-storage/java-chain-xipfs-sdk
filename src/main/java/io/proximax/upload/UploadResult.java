package io.proximax.upload;

import io.proximax.model.ProximaxRootDataModel;

public class UploadResult {

    private String transactionHash;
    private String digest;
    private String rootDataHash;
    private ProximaxRootDataModel rootData;


    public UploadResult(String transactionHash, String digest, String rootDataHash, ProximaxRootDataModel rootData) {
        this.transactionHash = transactionHash;
        this.digest = digest;
        this.rootDataHash = rootDataHash;
        this.rootData = rootData;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public String getDigest() {
        return digest;
    }

    public String getRootDataHash() {
        return rootDataHash;
    }

    public ProximaxRootDataModel getRootData() {
        return rootData;
    }

    public static UploadResult create(String transactionHash, String digest, String rootDataHash,
                                      ProximaxRootDataModel rootData) {
        return new UploadResult(transactionHash, digest, rootDataHash, rootData);
    }
}
