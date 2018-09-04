package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.UploadParameterDataNotSupportedException;
import io.proximax.model.ProximaxDataModel;
import io.proximax.upload.ByteArrayParameterData;
import io.proximax.upload.PathParameterData;
import io.proximax.upload.UploadParameter;
import io.proximax.utils.ContentTypeUtils;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;

import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for creating the uploaded data object
 */
public class CreateProximaxDataService {

    private final IpfsUploadService ipfsUploadService;
    private final DigestUtils digestUtils;
    private final ContentTypeUtils contentTypeUtils;

    /**
     * Construct this class
     * @param ipfsConnection the config class to connect to IPFS
     */
    public CreateProximaxDataService(IpfsConnection ipfsConnection) {
        this.ipfsUploadService = new IpfsUploadService(ipfsConnection);
        this.digestUtils = new DigestUtils();
        this.contentTypeUtils = new ContentTypeUtils();
    }

    CreateProximaxDataService(IpfsUploadService ipfsUploadService, DigestUtils digestUtils, ContentTypeUtils contentTypeUtils) {
        this.ipfsUploadService = ipfsUploadService;
        this.digestUtils = digestUtils;
        this.contentTypeUtils = contentTypeUtils;
    }

    /**
     * Creates the uploaded data object
     * @param uploadParam the upload parameter
     * @return the uploaded data object
     */
    public Observable<ProximaxDataModel> createData(UploadParameter uploadParam) {
        checkParameter(uploadParam != null, "uploadParam is required");

        if (uploadParam.getData() instanceof ByteArrayParameterData) { // when byte array data
            return uploadData(uploadParam, (ByteArrayParameterData) uploadParam.getData());
        } if (uploadParam.getData() instanceof PathParameterData) { // when path
            return uploadPath((PathParameterData) uploadParam.getData());
        } else { // when unknown data
            throw new UploadParameterDataNotSupportedException(String.format("Uploading of %s is not supported",
                    uploadParam.getData().getClass().getName()));
        }
    }

    private Observable<ProximaxDataModel> uploadData(UploadParameter uploadParam, ByteArrayParameterData byteArrParamData) {
        final Observable<Optional<String>> detectedContentTypeOb = detectContentType(uploadParam, byteArrParamData);
        final byte[] encryptedData = uploadParam.getPrivacyStrategy().encryptData(byteArrParamData.getData());
        final Observable<Optional<String>> digestOb = computeDigest(uploadParam.getComputeDigest(), encryptedData);
        final Observable<IpfsUploadResponse> ipfsUploadResponseOb = ipfsUploadService.uploadByteArray(encryptedData);

        return Observable.zip(ipfsUploadResponseOb, digestOb, detectedContentTypeOb,
                (ipfsUploadResponse, digest, contentTypeOpt) ->
                        ProximaxDataModel.create(byteArrParamData, ipfsUploadResponse.getDataHash(),
                                digest.orElse(null), contentTypeOpt.orElse(null), ipfsUploadResponse.getTimestamp()));
    }

    private Observable<Optional<String>> detectContentType(UploadParameter uploadParam, ByteArrayParameterData byteArrParamData) {
        return uploadParam.getDetectContentType() && byteArrParamData.getContentType() == null
                ? contentTypeUtils.detectContentType(byteArrParamData.getData()).map(Optional::of)
                : Observable.just(Optional.ofNullable(byteArrParamData.getContentType()));
    }

    private Observable<Optional<String>> computeDigest(boolean computeDigest, byte[] encryptedData) {
        return computeDigest ? digestUtils.digest(encryptedData).map(Optional::of) : Observable.just(Optional.empty());
    }

    private Observable<ProximaxDataModel> uploadPath(PathParameterData pathParamData) {
        return ipfsUploadService.uploadPath(pathParamData.getPath()).map(ipfsUploadResponse ->
                ProximaxDataModel.create(pathParamData, ipfsUploadResponse.getDataHash(),
                        null,  pathParamData.getContentType(), ipfsUploadResponse.getTimestamp()));
    }
}
