package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadDataParameter;
import io.proximax.download.DownloadParameter;
import io.proximax.model.ProximaxDataModel;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.utils.DigestUtils;
import io.proximax.utils.PrivacyDataEncryptionUtils;
import io.reactivex.Observable;

import java.util.List;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class RetrieveProximaxDataService {

    private final IpfsDownloadService ipfsDownloadService;
    private final DigestUtils digestUtils;
    private final PrivacyDataEncryptionUtils privacyDataEncryptionUtils;

    public RetrieveProximaxDataService(IpfsConnection ipfsConnection) {
        this.ipfsDownloadService = new IpfsDownloadService(ipfsConnection);
        this.digestUtils = new DigestUtils();
        this.privacyDataEncryptionUtils = new PrivacyDataEncryptionUtils();
    }

    RetrieveProximaxDataService(IpfsDownloadService ipfsDownloadService, DigestUtils digestUtils,
                                PrivacyDataEncryptionUtils privacyDataEncryptionUtils) {
        this.ipfsDownloadService = ipfsDownloadService;
        this.digestUtils = digestUtils;
        this.privacyDataEncryptionUtils = privacyDataEncryptionUtils;
    }

    public Observable<byte[]> getData(DownloadDataParameter downloadDataParameter) {
        checkParameter(downloadDataParameter != null, "downloadDataParameter is required");

        final List<String> dataHashList = singletonList(downloadDataParameter.getDataHash());
        final List<String> digestList = singletonList(downloadDataParameter.getDigest());
        return getDataList(dataHashList, digestList, downloadDataParameter.getPrivacyStrategy())
                .map(dataList -> dataList.get(0));
    }

    public Observable<List<byte[]>> getDataList(DownloadParameter downloadParam, ProximaxRootDataModel rootData) {
        checkParameter(downloadParam != null, "downloadParam is required");
        checkParameter(rootData != null, "rootData is required");

        final List<String> dataHashList = getDataHashList(rootData.getDataList());
        final List<String> digestList = getDigestList(rootData.getDataList());
        return getDataList(dataHashList, digestList, downloadParam.getPrivacyStrategy());
    }

    private Observable<List<byte[]>> getDataList(List<String> dataHashList, List<String> digestList,
                                                 PrivacyStrategy privacyStrategy) {
        final Observable<List<byte[]>> undecryptedDataListOb = downloadDataList(dataHashList);
        final Observable<List<byte[]>> undecryptedAndVerifiedDataListOb = verifyDataListWithDigest(digestList, undecryptedDataListOb);

        return decryptDataList(privacyStrategy, undecryptedAndVerifiedDataListOb);
    }

    private List<String> getDigestList(List<ProximaxDataModel> dataList) {
        return dataList.stream().map(ProximaxDataModel::getDigest).collect(toList());
    }

    private List<String> getDataHashList(List<ProximaxDataModel> dataList) {
        return dataList.stream().map(ProximaxDataModel::getDataHash).collect(toList());
    }

    private Observable<List<byte[]>> downloadDataList(List<String> dataHashList) {
        return ipfsDownloadService.downloadList(dataHashList);
    }

    private Observable<List<byte[]>> verifyDataListWithDigest(List<String> digestList, Observable<List<byte[]>> undecryptedDataListOb) {
        return undecryptedDataListOb.flatMap(undecryptedDataList ->
                digestUtils.validateDigestList(undecryptedDataList, digestList).map(result -> undecryptedDataList));
    }

    private Observable<List<byte[]>> decryptDataList(PrivacyStrategy privacyStrategy,
                                                     Observable<List<byte[]>> undecryptedAndVerifiedDataListOb) {
        return undecryptedAndVerifiedDataListOb
                .flatMap(undecryptedDataList -> privacyDataEncryptionUtils.decryptList(privacyStrategy, undecryptedDataList));
    }
}
