package io.proximax.upload;

import io.proximax.exceptions.UploadParameterBuildFailureException;
import io.proximax.model.StoreType;
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

public class UploadParameterBuilder {

    private String signerPrivateKey;
    private String recipientPublicKey;
    private String description;
    private PrivacyStrategy privacyStrategy;
    private Boolean computeDigest;
    private StoreType storeType;
    private String version;
    private List<UploadParameterData> dataList;

    public UploadParameterBuilder(String signerPrivateKey, String recipientPublicKey) {
        checkParameter(signerPrivateKey != null, "signerPrivateKey is required");
        checkParameter(recipientPublicKey != null, "recipientPublicKey is required");

        this.signerPrivateKey = signerPrivateKey;
        this.recipientPublicKey = recipientPublicKey;
        this.dataList = new ArrayList<>();
    }

    public UploadParameterBuilder addFile(FileParameterData parameterData) {
        checkParameter(parameterData != null, "parameterData is required");

        this.dataList.add(parameterData);
        return this;
    }

    public UploadParameterBuilder addFile(File file) throws IOException {
        checkParameter(file != null, "file is required");

        this.dataList.add(FileParameterData.create(file).build());
        return this;
    }

    public UploadParameterBuilder addFile(File file, String description, String name, String contentType,
                                          Map<String, String> metadata) throws IOException {
        this.dataList.add(new FileParameterData(file, description, name, contentType, metadata));
        return this;
    }

    public UploadParameterBuilder addFilesAsZip(FilesAsZipParameterData parameterData) {
        checkParameter(parameterData != null, "parameterData is required");

        this.dataList.add(parameterData);
        return this;
    }

    public UploadParameterBuilder addFilesAsZip(List<File> files) throws IOException {
        checkParameter(files != null, "files is required");

        this.dataList.add(FilesAsZipParameterData.create(files).build());
        return this;
    }

    public UploadParameterBuilder addFilesAsZip(List<File> files, String description, String name, Map<String, String> metadata) throws IOException {
        this.dataList.add(new FilesAsZipParameterData(files, description, name, metadata));
        return this;
    }

    public UploadParameterBuilder addByteArray(ByteArrayParameterData parameterData) {
        checkParameter(parameterData != null, "parameterData is required");

        this.dataList.add(parameterData);
        return this;
    }

    public UploadParameterBuilder addByteArray(byte[] byteArray) {
        checkParameter(byteArray != null, "byteArray is required");

        this.dataList.add(ByteArrayParameterData.create(byteArray).build());
        return this;
    }

    public UploadParameterBuilder addByteArray(byte[] byteArray, String description, String name, String contentType, Map<String, String> metadata) {
        this.dataList.add(new ByteArrayParameterData(byteArray, description, name, contentType, metadata));
        return this;
    }

    public UploadParameterBuilder addString(StringParameterData parameterData) {
        checkParameter(parameterData != null, "parameterData is required");

        this.dataList.add(parameterData);
        return this;
    }

    public UploadParameterBuilder addString(String string, String encoding, String description, String name, String contentType,
                                            Map<String, String> metadata) throws UnsupportedEncodingException {
        this.dataList.add(new StringParameterData(string, encoding, description, name, contentType, metadata));
        return this;
    }

    public UploadParameterBuilder addString(String string) throws UnsupportedEncodingException {
        checkParameter(string != null, "string is required");

        this.dataList.add(StringParameterData.create(string).build());
        return this;
    }

    public UploadParameterBuilder addUrlResource(UrlResourceParameterData parameterData) {
        checkParameter(parameterData != null, "parameterData is required");

        this.dataList.add(parameterData);
        return this;
    }

    public UploadParameterBuilder addUrlResource(URL url) throws IOException {
        checkParameter(url != null, "url is required");

        this.dataList.add(UrlResourceParameterData.create(url).build());
        return this;
    }

    public UploadParameterBuilder addUrlResource(URL url, String description, String name, String contentType,
                                                 Map<String, String> metadata) throws IOException {
        this.dataList.add(new UrlResourceParameterData(url, description, name, contentType, metadata));
        return this;
    }

    public UploadParameterBuilder description(String description) {
        checkParameter(description == null || description.length() <= 500, "root description cannot exceed 500 characters");

        this.description = description;
        return this;
    }

    public UploadParameterBuilder storeType(StoreType storeType) {
        this.storeType = storeType;
        return this;
    }

    public UploadParameterBuilder computeDigest(boolean computeDigest) {
        this.computeDigest = computeDigest;
        return this;
    }

    public UploadParameterBuilder privacyStrategy(PrivacyStrategy privacyStrategy) {
        this.privacyStrategy = privacyStrategy;
        return this;
    }

    public UploadParameterBuilder plainPrivacy() {
        this.privacyStrategy = PlainPrivacyStrategy.create(null);
        return this;
    }

    public UploadParameterBuilder plainPrivacy(String searchTag) {
        this.privacyStrategy = PlainPrivacyStrategy.create(searchTag);
        return this;
    }

    public UploadParameterBuilder securedWithNemKeysPrivacyStrategy() {
        this.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(
                this.signerPrivateKey, this.recipientPublicKey, null);
        return this;
    }

    public UploadParameterBuilder securedWithNemKeysPrivacyStrategy(String searchTag) {
        this.privacyStrategy = SecuredWithNemKeysPrivacyStrategy.create(
                this.signerPrivateKey, this.recipientPublicKey, searchTag);
        return this;
    }

    public UploadParameterBuilder securedWithPasswordPrivacyStrategy(String password) {
        this.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password, null);
        return this;
    }

    public UploadParameterBuilder securedWithPasswordPrivacyStrategy(String password, String searchTag) {
        this.privacyStrategy = SecuredWithPasswordPrivacyStrategy.create(password, searchTag);
        return this;
    }

    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                SecretPart... secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                null, secretParts);
        return this;
    }

    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                String searchTag,
                                                                                SecretPart... secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                searchTag, secretParts);
        return this;
    }

    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                List<SecretPart> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                String searchTag,
                                                                                List<SecretPart> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, searchTag);
        return this;
    }

    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                Map<Integer, byte[]> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, null);
        return this;
    }

    public UploadParameterBuilder securedWithShamirSecretSharingPrivacyStrategy(int secretTotalPartCount,
                                                                                int secretMinimumPartCountToBuild,
                                                                                String searchTag,
                                                                                Map<Integer, byte[]> secretParts) {
        this.privacyStrategy = SecuredWithShamirSecretSharingPrivacyStrategy.create(secretTotalPartCount, secretMinimumPartCountToBuild,
                secretParts, searchTag);
        return this;
    }

    public UploadParameter build() {
        if (CollectionUtils.isEmpty(this.dataList))
            throw new UploadParameterBuildFailureException("A parameter data should be provided");

        if (this.computeDigest == null)
            this.computeDigest = true;
        if (this.privacyStrategy == null)
            this.privacyStrategy = PlainPrivacyStrategy.create(null);
        if (this.storeType == null)
            this.storeType = StoreType.RESOURCE;

        return new UploadParameter(signerPrivateKey, recipientPublicKey, description, privacyStrategy, computeDigest,
                storeType, dataList);
    }

}
