package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.UploadParameterDataNotSupportedException;
import io.proximax.model.ProximaxDataModel;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.upload.ByteArrayParameterData;
import io.proximax.upload.PathParameterData;
import io.proximax.upload.UploadParameter;
import io.proximax.utils.ContentTypeUtils;
import io.proximax.utils.DigestUtils;
import io.proximax.utils.PrivacyDataEncryptionUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;

import java.util.List;
import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for creating a root data
 */
public class CreateProximaxRootDataService {

    private final IpfsUploadService ipfsUploadService;
    private final DigestUtils digestUtils;
    private final ContentTypeUtils contentTypeUtils;
    private final PrivacyDataEncryptionUtils privacyDataEncryptionUtils;

    /**
     * Construct this class
     * @param ipfsConnection the config class to connect to IPFS
     */
    public CreateProximaxRootDataService(IpfsConnection ipfsConnection) {
        this.ipfsUploadService = new IpfsUploadService(ipfsConnection);
        this.digestUtils = new DigestUtils();
        this.contentTypeUtils = new ContentTypeUtils();
        this.privacyDataEncryptionUtils = new PrivacyDataEncryptionUtils();
    }

    CreateProximaxRootDataService(IpfsUploadService ipfsUploadService, DigestUtils digestUtils,
                                  ContentTypeUtils contentTypeUtils, PrivacyDataEncryptionUtils privacyDataEncryptionUtils) {
        this.ipfsUploadService = ipfsUploadService;
        this.digestUtils = digestUtils;
        this.contentTypeUtils = contentTypeUtils;
        this.privacyDataEncryptionUtils = privacyDataEncryptionUtils;
    }

    /**
     * Creates a root data
     * @param uploadParam the upload parameter
     * @return the created root data
     */
    public Observable<ProximaxRootDataModel> createRootData(UploadParameter uploadParam) {
        checkParameter(uploadParam != null, "uploadParam is required");

        return Observable.fromIterable(uploadParam.getDataList())
                .concatMapEager(paramData -> {
                    if (paramData instanceof ByteArrayParameterData) { // byte array data
                        return uploadData(uploadParam, (ByteArrayParameterData) paramData);
                    } if (paramData instanceof PathParameterData) { // path
                        return uploadPath((PathParameterData) paramData);
                    } else { // unknown parameter type
                        throw new UploadParameterDataNotSupportedException(String.format("Uploading of %s is not supported", paramData.getClass().getName()));
                    }
                })
                .toList()
                .toObservable()
                .map(dataModeList -> createRootData(uploadParam, dataModeList));
    }

    private ObservableSource<? extends ProximaxDataModel> uploadData(UploadParameter uploadParam, ByteArrayParameterData byteArrParamData) {
        final Observable<String> detectedContentTypeOb =
                contentTypeUtils.detectContentType(byteArrParamData.getData(), byteArrParamData.getContentType());
        final Observable<byte[]> encryptedDataOb =
                privacyDataEncryptionUtils.encrypt(uploadParam.getPrivacyStrategy(), byteArrParamData.getData()).cache();
        final Observable<Optional<String>> digestOb = encryptedDataOb.flatMap(encryptedData ->
                computeDigest(uploadParam.getComputeDigest(), encryptedData));
        final Observable<IpfsUploadResponse> ipfsUploadResponseOb = encryptedDataOb.flatMap(ipfsUploadService::uploadByteArray);

        return Observable.zip(ipfsUploadResponseOb, digestOb, detectedContentTypeOb,
                (ipfsUploadResponse, digest, contentType) ->
                        ProximaxDataModel.create(byteArrParamData, ipfsUploadResponse.getDataHash(),
                                digest.orElse(null), contentType, ipfsUploadResponse.getTimestamp()));
    }

    private Observable<Optional<String>> computeDigest(boolean computeDigest, byte[] encryptedData) {
        return computeDigest ? digestUtils.digest(encryptedData).map(Optional::of) : Observable.just(Optional.empty());
    }

    private Observable<ProximaxDataModel> uploadPath(PathParameterData pathParamData) {
        return ipfsUploadService.uploadPath(pathParamData.getPath()).map(ipfsUploadResponse ->
                ProximaxDataModel.create(pathParamData, ipfsUploadResponse.getDataHash(),
                        null,  pathParamData.getContentType(), ipfsUploadResponse.getTimestamp()));
    }

    private ProximaxRootDataModel createRootData(UploadParameter uploadParam, List<ProximaxDataModel> dataModeList) {
        return new ProximaxRootDataModel(uploadParam.getPrivacyStrategy().getPrivacyType(),
                uploadParam.getPrivacyStrategy().getPrivacySearchTag(),
                uploadParam.getDescription(),
                uploadParam.getVersion(), dataModeList);
    }
}
