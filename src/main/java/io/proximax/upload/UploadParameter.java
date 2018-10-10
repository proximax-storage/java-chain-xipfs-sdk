package io.proximax.upload;

import io.proximax.privacy.strategy.PrivacyStrategy;

import java.io.File;
import java.net.URL;
import java.util.List;

import static io.proximax.model.Constants.SCHEMA_VERSION;

/**
 * This model class is the input parameter of upload.
 * @see Uploader#upload(UploadParameter)
 * @see UploadParameterBuilder
 */
public class UploadParameter {

    private final UploadParameterData data;
    private final String signerPrivateKey;
    private final String recipientPublicKey;
    private final String recipientAddress;
    private final boolean computeDigest;
    private final boolean detectContentType;
    private final int transactionDeadline;
    private final boolean useBlockchainSecureMessage;
    private final PrivacyStrategy privacyStrategy;
    private final String version;

    UploadParameter(UploadParameterData data, String signerPrivateKey, String recipientPublicKey, String recipientAddress,
                    boolean computeDigest, boolean detectContentType, int transactionDeadline,
                    boolean useBlockchainSecureMessage, PrivacyStrategy privacyStrategy) {
        this.data = data;
        this.signerPrivateKey = signerPrivateKey;
        this.recipientPublicKey = recipientPublicKey;
        this.recipientAddress = recipientAddress;
        this.computeDigest = computeDigest;
        this.detectContentType = detectContentType;
        this.transactionDeadline = transactionDeadline;
        this.useBlockchainSecureMessage = useBlockchainSecureMessage;
        this.privacyStrategy = privacyStrategy;
        this.version = SCHEMA_VERSION;
    }

    /**
     * Get the data which contains the data and its details
     * @return the data
     */
    public UploadParameterData getData() {
        return data;
    }

    /**
     * Get the private key of a blockchain account that will be used to create transaction for each upload
     * @return the signer private key
     */
    public String getSignerPrivateKey() {
        return signerPrivateKey;
    }

    /**
     * Get the public key of a blockchain account that will receive the transactions being created (if different from signer)
     * @return the recipient public key
     */
    public String getRecipientPublicKey() {
        return recipientPublicKey;
    }

    /**
     * Get the address of a blockchain account that will receive the transactions being created (if different from signer)
     * @return the recipient public key
     */
    public String getRecipientAddress() {
        return recipientAddress;
    }

    /**
     * Get the flag that indicates if a digest is required to be calculated
     * @return the compute digest flag
     */
    public boolean getComputeDigest() {
        return computeDigest;
    }

    /**
     * Get the that indicates if a content type is to be derived from data
     * @return the detect content type flag
     */
    public boolean getDetectContentType() {
        return detectContentType;
    }

    /**
     * Get the transaction deadline (duration) of the blockchain transaction to be created. This value is in hours.
     * @return the transaction deadline
     */
    public int getTransactionDeadline() {
        return transactionDeadline;
    }

    /**
     * Get the flag that indicates if transaction's message is to be secured
     * @return the use blockchain secure message flag
     */
    public boolean getUseBlockchainSecureMessage() {
        return useBlockchainSecureMessage;
    }

    /**
     * Get the privacy strategy that defines how the data will be encrypted
     * @return the privacy strategy
     */
    public PrivacyStrategy getPrivacyStrategy() {
        return privacyStrategy;
    }

    /**
     * Get the schema version of upload
     * @return the schema version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Start creating parameter for a file upload using UploadParameterBuilder
     * @param file the file to upload
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForFileUpload(File file, String signerPrivateKey) {
        return createForFileUpload(FileParameterData.create(file), signerPrivateKey);
    }

    /**
     * Start creating parameter for a file upload using UploadParameterBuilder
     * @param parameterData the parameter data containing the file and additional details
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForFileUpload(FileParameterData parameterData, String signerPrivateKey) {
        return new UploadParameterBuilder(parameterData, signerPrivateKey);
    }

    /**
     * Start creating parameter for a byte array upload using UploadParameterBuilder
     * @param bytes the byte array to upload
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForByteArrayUpload(byte[] bytes, String signerPrivateKey) {
        return createForByteArrayUpload(ByteArrayParameterData.create(bytes), signerPrivateKey);
    }

    /**
     * Start creating parameter for a byte array upload using UploadParameterBuilder
     * @param parameterData the parameter data containing the byte array and additional details
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForByteArrayUpload(ByteArrayParameterData parameterData, String signerPrivateKey) {
        return new UploadParameterBuilder(parameterData, signerPrivateKey);
    }

    /**
     * Start creating parameter for a string upload using UploadParameterBuilder
     * @param string the string to upload
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForStringUpload(String string, String signerPrivateKey) {
        return createForStringUpload(StringParameterData.create(string), signerPrivateKey);
    }

    /**
     * Start creating parameter for a string upload using UploadParameterBuilder
     * @param parameterData the parameter data containing the string and additional details
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForStringUpload(StringParameterData parameterData, String signerPrivateKey) {
        return new UploadParameterBuilder(parameterData, signerPrivateKey);
    }

    /**
     * Start creating parameter for a URL resource upload using UploadParameterBuilder
     * @param url the URL resource to upload
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForUrlResourceUpload(URL url, String signerPrivateKey) {
        return createForUrlResourceUpload(UrlResourceParameterData.create(url), signerPrivateKey);
    }

    /**
     * Start creating parameter for a URL resource upload using UploadParameterBuilder
     * @param parameterData the parameter data containing the URL resource and additional details
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForUrlResourceUpload(UrlResourceParameterData parameterData, String signerPrivateKey) {
        return new UploadParameterBuilder(parameterData, signerPrivateKey);
    }

    /**
     * Start creating parameter for files to upload as zip using UploadParameterBuilder
     * @param files the files to zip and upload
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForFilesAsZipUpload(List<File> files, String signerPrivateKey) {
        return createForFilesAsZipUpload(FilesAsZipParameterData.create(files), signerPrivateKey);
    }

    /**
     * Start creating parameter for a zipped files upload using UploadParameterBuilder
     * @param parameterData the parameter data containing the zipped files and additional details
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForFilesAsZipUpload(FilesAsZipParameterData parameterData, String signerPrivateKey) {
        return new UploadParameterBuilder(parameterData, signerPrivateKey);
    }

    /**
     * Start creating parameter for a path upload using UploadParameterBuilder
     * @param path the path to upload
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForPathUpload(File path, String signerPrivateKey) {
        return createForPathUpload(PathParameterData.create(path), signerPrivateKey);
    }

    /**
     * Start creating parameter for a path upload using UploadParameterBuilder
     * @param parameterData the parameter data containing the path and additional details
     * @param signerPrivateKey the private key of the signer of the blockchain transaction
     * @return the upload parameter builder
     */
    public static UploadParameterBuilder createForPathUpload(PathParameterData parameterData, String signerPrivateKey) {
        return new UploadParameterBuilder(parameterData, signerPrivateKey);
    }
}
