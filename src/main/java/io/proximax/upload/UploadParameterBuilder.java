package io.proximax.upload;

import io.proximax.exceptions.UploadParameterBuildFailureException;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy.SecretPart;
import io.proximax.utils.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This builder class creates the UploadParameter
 * <br>
 * <br>
 * The following are the fields to set:
 * <ul>
 *     <li><b>signerPrivateKey</b> - the private key of a blockchain account that will be used to create transaction for each upload</li>
 *     <li><b>recipientPublicKey</b> - the public key of a blockchain account that will receive the transactions being created</li>
 *     <li><b>description</b> - an optional short description for the upload</li>
 *     <li><b>privacyStrategy</b> - an optional privacy strategy that defines how the data will be encrypted</li>
 *     <li><b>computeDigest</b> - an optional flag that indicates if a digest is required to be calculated (true by default)</li>
 *     <li><b>dataList</b> - a list of UploadParameterData that contains the data and additional info
 * </ul>
 * @see UploadParameter
 */
public class UploadParameterBuilder {

    private String signerPrivateKey;
    private String recipientPublicKey;
    private String description;
    private PrivacyStrategy privacyStrategy;
    private Boolean computeDigest;
    private List<UploadParameterData> dataList;

    /**
     * Construct the builder class
     * @param signerPrivateKey the private key of a blockchain account that will be used to create transaction for each upload
     * @param recipientPublicKey the public key of a blockchain account that will receive the transactions being created
     */
    public UploadParameterBuilder(String signerPrivateKey, String recipientPublicKey) {
        checkParameter(signerPrivateKey != null, "signerPrivateKey is required");
        checkParameter(recipientPublicKey != null, "recipientPublicKey is required");

        this.signerPrivateKey = signerPrivateKey;
        this.recipientPublicKey = recipientPublicKey;
        this.dataList = new ArrayList<>();
    }

    /**
     * Add a file to upload by providing a FileParameterData
     * @param parameterData the parameter data with attributes of a file upload
     * @return the same instance of this builder
     */
    public UploadParameterBuilder addFile(FileParameterData parameterData) {
        checkParameter(parameterData != null, "parameterData is required");

        this.dataList.add(parameterData);
        return this;
    }

    /**
     * Add a file to upload by providing only the File
     * @param file the file to upload
     * @return the same instance of this builder
     * @throws IOException when file reading fails
     */
    public UploadParameterBuilder addFile(File file) throws IOException {
        checkParameter(file != null, "file is required");

        this.dataList.add(FileParameterData.create(file).build());
        return this;
    }

    /**
     * Add a file to upload by providing the File and additional info
     * @param file the file to upload
     * @param description a description for the file
     * @param name the name for the file
     * @param contentType the content type for the file
     * @param metadata an additional metadata for the file
     * @return the same instance of this builder
     * @throws IOException when file reading fails
     */
    public UploadParameterBuilder addFile(File file, String description, String name, String contentType,
                                          Map<String, String> metadata) throws IOException {
        this.dataList.add(new FileParameterData(file, description, name, contentType, metadata));
        return this;
    }

    /**
     * Add a list of files to upload as zip by providing a FilesAsZipParameterData
     * @param parameterData the parameter data with attributes of a zip upload
     * @return the same instance of this builder
     */
    public UploadParameterBuilder addFilesAsZip(FilesAsZipParameterData parameterData) {
        checkParameter(parameterData != null, "parameterData is required");

        this.dataList.add(parameterData);
        return this;
    }

    /**
     * Add a list of files to upload as zip by providing only the list of files
     * @param files the files to upload as zip
     * @return the same instance of this builder
     * @throws IOException when file reading fails
     */
    public UploadParameterBuilder addFilesAsZip(List<File> files) throws IOException {
        checkParameter(files != null, "files is required");

        this.dataList.add(FilesAsZipParameterData.create(files).build());
        return this;
    }

    /**
     * Add a list of files to upload as zip by providing the list of files and additional info
     * @param files the files to upload as zip
     * @param description a description for the zip file
     * @param name the name for the zip file
     * @param metadata an additional metadata for the zip file
     * @return the same instance of this builder
     * @throws IOException when file reading fails
     */
    public UploadParameterBuilder addFilesAsZip(List<File> files, String description, String name, Map<String, String> metadata) throws IOException {
        this.dataList.add(new FilesAsZipParameterData(files, description, name, metadata));
        return this;
    }

    /**
     * Add a byte array to upload by providing a ByteArrayParameterData
     * @param parameterData the parameter data with attributes of a byte array upload
     * @return the same instance of this builder
     */
    public UploadParameterBuilder addByteArray(ByteArrayParameterData parameterData) {
        checkParameter(parameterData != null, "parameterData is required");

        this.dataList.add(parameterData);
        return this;
    }

    /**
     * Add a byte array to upload by providing only the byte array
     * @param byteArray the byte array to upload
     * @return the same instance of this builder
     */
    public UploadParameterBuilder addByteArray(byte[] byteArray) {
        checkParameter(byteArray != null, "byteArray is required");

        this.dataList.add(ByteArrayParameterData.create(byteArray).build());
        return this;
    }

    /**
     * Add a byte array to upload by providing the byte array and additional info
     * @param byteArray the byte array to upload
     * @param description a description for the byte array
     * @param name the name for the byte array
     * @param contentType the content type for the byte array
     * @param metadata an additional metadata for the byte array
     * @return the same instance of this builder
     */
    public UploadParameterBuilder addByteArray(byte[] byteArray, String description, String name, String contentType, Map<String, String> metadata) {
        this.dataList.add(new ByteArrayParameterData(byteArray, description, name, contentType, metadata));
        return this;
    }

    /**
     * Add a string to upload by providing a StringParameterData
     * @param parameterData the parameter data with attributes of a string upload
     * @return the same instance of this builder
     */
    public UploadParameterBuilder addString(StringParameterData parameterData) {
        checkParameter(parameterData != null, "parameterData is required");

        this.dataList.add(parameterData);
        return this;
    }


    /**
     * Add a string to upload by providing only the string
     * @param string the string to upload
     * @return the same instance of this builder
     * @throws UnsupportedEncodingException when the conversion from string to byte array fails
     */
    public UploadParameterBuilder addString(String string) throws UnsupportedEncodingException {
        checkParameter(string != null, "string is required");

        this.dataList.add(StringParameterData.create(string).build());
        return this;
    }

    /**
     * Add a string to upload by providing the string and additional info
     * @param string the string to upload
     * @param encoding the encoding for the string
     * @param description a description for the string
     * @param name the name for the string
     * @param contentType the content type for the string
     * @param metadata an additional metadata for string
     * @return the same instance of this builder
     * @throws UnsupportedEncodingException when the conversion from string to byte array fails
     */
    public UploadParameterBuilder addString(String string, String encoding, String description, String name, String contentType,
                                            Map<String, String> metadata) throws UnsupportedEncodingException {
        this.dataList.add(new StringParameterData(string, encoding, description, name, contentType, metadata));
        return this;
    }

    /**
     * Add a URL resource to upload by providing a UrlResourceParameterData
     * @param parameterData the parameter data with attributes of a URL upload
     * @return the same instance of this builder
     */
    public UploadParameterBuilder addUrlResource(UrlResourceParameterData parameterData) {
        checkParameter(parameterData != null, "parameterData is required");

        this.dataList.add(parameterData);
        return this;
    }

    /**
     * Add a URL resource to upload by providing only the URL
     * @param url the URL to upload
     * @return the same instance of this builder
     * @throws IOException when URL resource reading fails
     */
    public UploadParameterBuilder addUrlResource(URL url) throws IOException {
        checkParameter(url != null, "url is required");

        this.dataList.add(UrlResourceParameterData.create(url).build());
        return this;
    }

    /**
     * Add a URL resource to upload by providing the URL and additional info
     * @param url the URL to upload
     * @param description a description for the URL
     * @param name the name for the URL
     * @param contentType the content type for the URL
     * @param metadata an additional metadata for URL
     * @return the same instance of this builder
     * @throws IOException when URL resource reading fails
     */
    public UploadParameterBuilder addUrlResource(URL url, String description, String name, String contentType,
                                                 Map<String, String> metadata) throws IOException {
        this.dataList.add(new UrlResourceParameterData(url, description, name, contentType, metadata));
        return this;
    }

    /**
     * Set a short description for the upload
     * @param description short description for the upload
     * @return the same instance of this builder
     */
    public UploadParameterBuilder description(String description) {
        checkParameter(description == null || description.length() <= 500, "root description cannot exceed 500 characters");

        this.description = description;
        return this;
    }

    /**
     * Set the compute digest flag
     * @param computeDigest flag that indicates if a digest is required to be calculated
     * @return the same instance of this builder
     */
    public UploadParameterBuilder computeDigest(boolean computeDigest) {
        this.computeDigest = computeDigest;
        return this;
    }

    /**
     * Set the privacy strategy
     * @param privacyStrategy strategy that defines how the data will be encrypted
     * @return the same instance of this builder
     */
    public UploadParameterBuilder privacyStrategy(PrivacyStrategy privacyStrategy) {
        this.privacyStrategy = privacyStrategy;
        return this;
    }

    /**
     * Set the privacy strategy as plain
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @return the same instance of this builder
     */
    public UploadParameterBuilder plainPrivacy() {
        this.privacyStrategy = PlainPrivacyStrategy.create(null);
        return this;
    }

    /**
     * Set the privacy strategy as plain (overloaded method with argument to specify search tag)
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param searchTag search tag to optimize searching for the upload
     * @return the same instance of this builder
     */
    public UploadParameterBuilder plainPrivacy(String searchTag) {
        this.privacyStrategy = PlainPrivacyStrategy.create(searchTag);
        return this;
    }

    /**
     * Set the privacy strategy as secured with nem keys
     * <br>
     * <br>
     * This strategy will use the required signer private key and recipient public key
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @return the same instance of this builder
     */
    public UploadParameterBuilder securedWithNemKeysPrivacyStrategy() {
        this.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(
                this.signerPrivateKey, this.recipientPublicKey, null);
        return this;
    }

    /**
     * Set the privacy strategy as secured with nem keys (overloaded method with argument to specify search tag)
     * <br>
     * <br>
     * This strategy will use the required signer private key and recipient public key
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param searchTag search tag to optimize searching for the upload
     * @return the same instance of this builder
     */
    public UploadParameterBuilder securedWithNemKeysPrivacyStrategy(String searchTag) {
        this.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(
                this.signerPrivateKey, this.recipientPublicKey, searchTag);
        return this;
    }

    /**
     * Set the privacy strategy as secured with password
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param password a 50-character minimum password
     * @return the same instance of this builder
     */
    public UploadParameterBuilder securedWithPasswordPrivacyStrategy(String password) {
        this.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password, null);
        return this;
    }

    /**
     * Set the privacy strategy as secured with password (overloaded method with argument to specify search tag)
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param password the password
     * @param searchTag search tag to optimize searching for the upload
     * @return the same instance of this builder
     */
    public UploadParameterBuilder securedWithPasswordPrivacyStrategy(String password, String searchTag) {
        this.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password, searchTag);
        return this;
    }

    /**
     * Set the privacy strategy as secured with shamir secret sharing
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param secretTotalPartCount the total count of parts of the secret
     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
     * @param secretParts the array of secret parts composed of the part index and the secret part
     * @return the same instance of this builder
     */
    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                SecretPart... secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                null, secretParts);
        return this;
    }

    /**
     * Set the privacy strategy as secured with shamir secret sharing (overloaded method with argument to specify search tag)
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param secretTotalPartCount the total count of parts of the secret
     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
     * @param searchTag search tag to optimize searching for the upload
     * @param secretParts the array of secret parts composed of the part index and the secret part
     * @return the same instance of this builder
     */
    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                String searchTag,
                                                                                SecretPart... secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                searchTag, secretParts);
        return this;
    }

    /**
     * Set the privacy strategy as secured with shamir secret sharing
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param secretTotalPartCount the total count of parts of the secret
     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
     * @param secretParts the list of secret parts composed of the part index and the secret part
     * @return the same instance of this builder
     */
    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                List<SecretPart> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    /**
     * Set the privacy strategy as secured with shamir secret sharing (overloaded method with argument to specify search tag)
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param secretTotalPartCount the total count of parts of the secret
     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
     * @param searchTag search tag to optimize searching for the upload
     * @param secretParts the list of secret parts composed of the part index and the secret part
     * @return the same instance of this builder
     */
    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                String searchTag,
                                                                                List<SecretPart> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, searchTag);
        return this;
    }

    /**
     * Set the privacy strategy as secured with shamir secret sharing
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param secretTotalPartCount the total count of parts of the secret
     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
     * @param secretParts the map containing part index and secret part pairs
     * @return the same instance of this builder
     */
    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                Map<Integer, byte[]> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    /**
     * Set the privacy strategy as secured with shamir secret sharing (overloaded method with argument to specify search tag)
     * <br>
     * Privacy strategy defines how the data will be encrypted
     * @param secretTotalPartCount the total count of parts of the secret
     * @param secretMinimumPartCountToBuild the minimum count of parts of the secret
     * @param searchTag search tag to optimize searching for the upload
     * @param secretParts the map containing part index and secret part pairs
     * @return the same instance of this builder
     */
    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                String searchTag,
                                                                                Map<Integer, byte[]> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, searchTag);
        return this;
    }

    /**
     * Builds the UploadParameter
     * <br>
     * Throws an UploadParameterBuildFailureException if no data to upload
     * <br>
     * Defaults the following if not provided
     * <ul>
     *     <li><b>privacyStrategy</b> - to plain privacy strategy</li>
     *     <li><b>computeDigest</b> - to true)</li>
     * </ul>
     * @return the upload parameter
     */
    public UploadParameter build() {
        if (CollectionUtils.isEmpty(this.dataList))
            throw new UploadParameterBuildFailureException("A parameter data should be provided. Considering using one of the add***() methods");

        if (this.computeDigest == null)
            this.computeDigest = true;
        if (this.privacyStrategy == null)
            this.privacyStrategy = PlainPrivacyStrategy.create(null);

        return new UploadParameter(signerPrivateKey, recipientPublicKey, description, privacyStrategy, computeDigest, dataList);
    }

}
