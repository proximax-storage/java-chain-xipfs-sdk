package io.proximax.service;

import io.proximax.connection.FileStorageConnection;
import io.proximax.exceptions.UploadParameterDataNotSupportedException;
import io.proximax.model.ProximaxDataModel;
import io.proximax.service.factory.FileRepositoryFactory;
import io.proximax.upload.AbstractByteStreamParameterData;
import io.proximax.upload.PathParameterData;
import io.proximax.upload.UploadParameter;
import io.proximax.utils.ContentTypeUtils;
import io.reactivex.Observable;

import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for creating the uploaded data object
 */
public class CreateProximaxDataService {

    private final FileUploadService fileUploadService;
    private final ContentTypeUtils contentTypeUtils;

    /**
     * Construct this class
     *
     * @param fileStorageConnection the connection to file storage
     */
    public CreateProximaxDataService(FileStorageConnection fileStorageConnection) {
        this.fileUploadService = new FileUploadService(FileRepositoryFactory.create(fileStorageConnection));
        this.contentTypeUtils = new ContentTypeUtils();
    }

    CreateProximaxDataService(FileUploadService fileUploadService, ContentTypeUtils contentTypeUtils) {
        this.fileUploadService = fileUploadService;
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
        final Observable<FileUploadResponse> ipfsUploadResponseOb = fileUploadService.uploadByteStream(
                byteStreamParamData::getByteStream, uploadParam.getPrivacyStrategy(), uploadParam.getComputeDigest());

        return Observable.zip(ipfsUploadResponseOb, detectedContentTypeOb, (ipfsUploadResponse, contentTypeOpt) ->
                ProximaxDataModel.create(byteStreamParamData, ipfsUploadResponse.getDataHash(),
                        ipfsUploadResponse.getDigest(), contentTypeOpt.orElse(null), ipfsUploadResponse.getTimestamp()));
    }

    private Observable<Optional<String>> detectContentType(UploadParameter uploadParam, AbstractByteStreamParameterData byteStreamParamData) {
        return uploadParam.getDetectContentType() && byteStreamParamData.getContentType() == null
                ? contentTypeUtils.detectContentType(byteStreamParamData.getByteStream()).map(Optional::of)
                : Observable.just(Optional.ofNullable(byteStreamParamData.getContentType()));
    }

    private Observable<ProximaxDataModel> uploadPath(PathParameterData pathParamData) {
        return fileUploadService.uploadPath(pathParamData.getPath()).map(ipfsUploadResponse ->
                ProximaxDataModel.create(pathParamData, ipfsUploadResponse.getDataHash(),
                        null, pathParamData.getContentType(), ipfsUploadResponse.getTimestamp()));
    }
}
