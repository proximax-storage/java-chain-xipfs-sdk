package io.proximax.upload;

import io.proximax.privacy.strategy.PrivacyStrategy;

import java.util.List;

import static io.proximax.model.Constants.SCHEMA_VERSION;
import static java.util.Collections.unmodifiableList;

/**
 * This model class is the input parameter when doing upload.
 * <br>
 * <br>
 * The following are its fields:
 * <ul>
 *     <li><b>signerPrivateKey</b> - the private key of a blockchain account that will be used to create transaction for each upload</li>
 *     <li><b>recipientPublicKey</b> - the public key of a blockchain account that will receive the transactions being created</li>
 *     <li><b>description</b> - an optional short description for the upload</li>
 *     <li><b>privacyStrategy</b> - an optional privacy strategy that defines how the data will be encrypted</li>
 *     <li><b>computeDigest</b> - an optional flag that indicates if a digest is required to be calculated (true by default)</li>
 *     <li><b>dataList</b> - a list of UploadParameterData that contains the data and additional info</li>
 *     <li><b>version</b> - the version of upload (always 1.0 - cannot be updated) </li>
 * </ul>
 * @see Upload#upload(UploadParameter)
 * @see UploadParameterBuilder
 */
public class UploadParameter {

    private final String signerPrivateKey;
    private final String recipientPublicKey;
    private final String description;
    private final PrivacyStrategy privacyStrategy;
    private final Boolean computeDigest;
    private final String version;
    private final List<UploadParameterData> dataList;

    UploadParameter(String signerPrivateKey, String recipientPublicKey, String description, PrivacyStrategy privacyStrategy,
                           Boolean computeDigest, List<UploadParameterData> dataList) {
        this.signerPrivateKey = signerPrivateKey;
        this.recipientPublicKey = recipientPublicKey;
        this.description = description;
        this.privacyStrategy = privacyStrategy;
        this.computeDigest = computeDigest;
        this.dataList = dataList;
        this.version = SCHEMA_VERSION;
    }

    /**
     * Get the Signer Private Key - the private key of a blockchain account that will be used to create transaction for each upload
     * @return the signer private key
     */
    public String getSignerPrivateKey() {
        return signerPrivateKey;
    }

    /**
     * Get the Recipient Public Key - the public key of a blockchain account that will receive the transactions being created
     * @return the recipient public key
     */
    public String getRecipientPublicKey() {
        return recipientPublicKey;
    }

    /**
     * Get the Description - the short description for the upload
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the Privacy Strategy - the privacy strategy that defines how the data will be encrypted
     * @return the privacy strategy
     */
    public PrivacyStrategy getPrivacyStrategy() {
        return privacyStrategy;
    }

    /**
     * Get the Compute Digest flag - the flag that indicates if a digest is required to be calculated
     * @return the compute digest flag
     */
    public Boolean getComputeDigest() {
        return computeDigest;
    }

    /**
     * Get the Version - the version of upload
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Get the Data List - the list of UploadParameterData that contains the data and additional info
     * @return the data list
     */
    public List<UploadParameterData> getDataList() {
        return unmodifiableList(dataList);
    }

    /**
     * Start creating an instance of this class using UploadParameterBuilder
     * @param signerPrivateKey the private key of a blockchain account that will be used to create transaction for each upload
     * @param recipientPublicKey the public key of a blockchain account that will receive the transactions being created
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder create(String signerPrivateKey, String recipientPublicKey) {
        return new UploadParameterBuilder(signerPrivateKey, recipientPublicKey);
    }
}
