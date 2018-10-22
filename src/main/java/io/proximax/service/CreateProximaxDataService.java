package io.proximax.service;

import io.proximax.connection.ConnectionConfig;
import io.proximax.exceptions.UploadParameterDataNotSupportedException;
import io.proximax.model.ProximaxDataModel;
import io.proximax.upload.AbstractByteStreamParameterData;
import io.proximax.upload.PathParameterData;
import io.proximax.upload.UploadParameter;
import io.proximax.utils.ContentTypeUtils;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;

import java.io.InputStream;
import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for creating the uploaded data object
 */
public class CreateProximaxDataService {

    private final FileUploadService fileUploadService;
    private final DigestUtils digestUtils;
    private final ContentTypeUtils contentTypeUtils;

    /**
     * Construct this class
     *
     * @param connectionConfig the connection config
     */
    public CreateProximaxDataService(ConnectionConfig connectionConfig) {
        this.fileUploadService = new FileUploadService(connectionConfig);
        this.digestUtils = new DigestUtils();
        this.contentTypeUtils = new ContentTypeUtils();
    }

    CreateProximaxDataService(FileUploadService fileUploadService, DigestUtils digestUtils, ContentTypeUtils contentTypeUtils) {
        this.fileUploadService = fileUploadService;
        this.digestUtils = digestUtils;
        this.contentTypeUtils = contentTypeUtils;
    }

    /**
     * Creates the uploaded data object
     *
     * @param uploadParam the upload parameter
     * @return the uploaded data object
     */
    public Observable<ProximaxDataModel> createData(UploadParameter uploadParam) {
        checkParameter(uploadParam != null, "uploadParam is required");

        if (uploadParam.getData() instanceof AbstractByteStreamParameterData) { // when byte stream upload
            return uploadByteStream(uploadParam, (AbstractByteStreamParameterData) uploadParam.getData());
        } else if (uploadParam.getData() instanceof PathParameterData) { // when path upload
            return uploadPath((PathParameterData) uploadParam.getData());
        } else { // when unknown data
            throw new UploadParameterDataNotSupportedException(String.format("Uploading of %s is not supported",
                    uploadParam.getData().getClass().getName()));
        }
    }

    private Observable<ProximaxDataModel> uploadByteStream(UploadParameter uploadParam, AbstractByteStreamParameterData byteStreamParamData) {
        final Observable<Optional<String>> detectedContentTypeOb = detectContentType(uploadParam, byteStreamParamData);
        final InputStream encryptedByteStream = encryptByteStream(uploadParam, byteStreamParamData);
        final Observable<Optional<String>> digestOb = computeDigest(uploadParam, byteStreamParamData);
        final Observable<FileUploadResponse> ipfsUploadResponseOb = fileUploadService.uploadByteStream(encryptedByteStream);

        return Observable.zip(ipfsUploadResponseOb, digestOb, detectedContentTypeOb,
                (ipfsUploadResponse, digest, contentTypeOpt) ->
                        ProximaxDataModel.create(byteStreamParamData, ipfsUploadResponse.getDataHash(),
                                digest.orElse(null), contentTypeOpt.orElse(null), ipfsUploadResponse.getTimestamp()));
    }

    private Observable<Optional<String>> detectContentType(UploadParameter uploadParam, AbstractByteStreamParameterData byteStreamParamData) {
        return uploadParam.getDetectContentType() && byteStreamParamData.getContentType() == null
                ? contentTypeUtils.detectContentType(byteStreamParamData.getByteStream()).map(Optional::of)
                : Observable.just(Optional.ofNullable(byteStreamParamData.getContentType()));
    }

    private InputStream encryptByteStream(UploadParameter uploadParam, AbstractByteStreamParameterData byteStreamParamData) {
        return uploadParam.getPrivacyStrategy().encryptStream(byteStreamParamData.getByteStream());
    }

    private Observable<Optional<String>> computeDigest(UploadParameter uploadParam, AbstractByteStreamParameterData byteStreamParamData) {
        return uploadParam.getComputeDigest() ? digestUtils.digest(encryptByteStream(uploadParam, byteStreamParamData)).map(Optional::of) : Observable.just(Optional.empty());
    }

    private Observable<ProximaxDataModel> uploadPath(PathParameterData pathParamData) {
        return fileUploadService.uploadPath(pathParamData.getPath()).map(ipfsUploadResponse ->
                ProximaxDataModel.create(pathParamData, ipfsUploadResponse.getDataHash(),
                        null, pathParamData.getContentType(), ipfsUploadResponse.getTimestamp()));
    }
}
