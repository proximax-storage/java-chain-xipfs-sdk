package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.model.ProximaxDataModel;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadParameterData;
import io.proximax.utils.ContentTypeUtils;
import io.proximax.utils.DigestUtils;
import io.proximax.utils.PrivacyDataEncryptionUtils;
import io.reactivex.Observable;

import java.util.Collections;
import java.util.List;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.util.stream.Collectors.toList;

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

        final List<byte[]> dataList = getDataList(uploadParam.getDataList());
        final List<String> contentTypeListFromParams = getContentTypeList(uploadParam.getDataList());

        final Observable<List<byte[]>> encryptedDataListOb = encryptDataList(uploadParam.getPrivacyStrategy(), dataList).cache();
        final Observable<List<String>> digestListOb = encryptedDataListOb.flatMap(encryptedDataList ->
                computeDigestsOfDataList(uploadParam.getComputeDigest(), dataList, encryptedDataList));
        final Observable<List<String>> contentTypeListOb = detectContentTypesForDataList(dataList, contentTypeListFromParams);
        final Observable<List<IpfsUploadResponse>> dataUploadResponseListOb =
                encryptedDataListOb.flatMap(ipfsUploadService::uploadList);

        return Observable.zip(dataUploadResponseListOb, digestListOb, contentTypeListOb,
                (dataUploadResponseList, digestList, contentTypeList) ->
                        ProximaxDataModel.createList(uploadParam.getDataList(), dataUploadResponseList, digestList, contentTypeList))
                .map(dataModeList -> createRootData(uploadParam, dataModeList));
    }

    private List<byte[]> getDataList(List<UploadParameterData> dataList) {
        return dataList.stream().map(UploadParameterData::getData).collect(toList());
    }

    private List<String> getContentTypeList(List<UploadParameterData> dataList) {
        return dataList.stream().map(UploadParameterData::getContentType).collect(toList());
    }

    private Observable<List<byte[]>> encryptDataList(PrivacyStrategy privacyStrategy, List<byte[]> dataList) {
        return privacyDataEncryptionUtils.encryptList(privacyStrategy, dataList);
    }

    private Observable<List<String>> computeDigestsOfDataList(boolean computeDigest, List<byte[]> dataList,
                                                              List<byte[]> encryptedDataList) {
        return computeDigest ? digestUtils.digestForList(encryptedDataList) :
                Observable.just(Collections.nCopies(dataList.size(), null));
    }

    private Observable<List<String>> detectContentTypesForDataList(List<byte[]> dataList, List<String> contentTypeList) {
        return contentTypeUtils.detectContentTypeForList(dataList, contentTypeList );
    }

    private ProximaxRootDataModel createRootData(UploadParameter uploadParam, List<ProximaxDataModel> dataModeList) {
        return new ProximaxRootDataModel(uploadParam.getPrivacyStrategy().getPrivacyType(),
                uploadParam.getPrivacyStrategy().getPrivacySearchTag(),
                uploadParam.getDescription(),
                uploadParam.getVersion(), dataModeList);
    }
}
