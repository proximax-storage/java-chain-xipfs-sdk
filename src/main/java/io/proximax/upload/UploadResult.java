package io.proximax.upload;

import io.proximax.model.ProximaxDataModel;

/**
 * The model class that defines the result of an upload.
 * @see Uploader#upload(UploadParameter)
 */
public final class UploadResult {

    private String transactionHash;
    private final int privacyType;
    private final String version;
    private ProximaxDataModel data;

    private UploadResult(String transactionHash, int privacyType, String version, ProximaxDataModel data) {
        this.transactionHash = transactionHash;
        this.privacyType = privacyType;
        this.version = version;
        this.data = data;
    }

    /**
     * Get the blockchain transaction hash of the upload
     * @return the blockchain transaction hash
     */
    public String getTransactionHash() {
        return transactionHash;
    }

    /**
     * Get the privacy type of privacy strategy used to encrypt data
     * @return the privacy type
     * @see io.proximax.model.PrivacyType
     */
    public int getPrivacyType() {
        return privacyType;
    }

    /**
     * Get the schema version of upload
     * @return the schema version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Get the uploaded data object
     * @return the uploaded data object
     */
    public ProximaxDataModel getData() {
        return data;
    }

    static UploadResult create(String transactionHash, int privacyType, String version, ProximaxDataModel data) {
        return new UploadResult(transactionHash, privacyType, version, data);
    }
}
