package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadDataParameter;
import io.proximax.download.DownloadParameter;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.utils.DigestUtils;
import io.proximax.utils.PrivacyDataEncryptionUtils;
import io.reactivex.Observable;

import java.util.List;
import java.util.Optional;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.util.stream.Collectors.toList;

/**
 * The service class responsible for retrieving data
 */
public class RetrieveProximaxDataService {

    private final IpfsDownloadService ipfsDownloadService;
    private final DigestUtils digestUtils;
    private final PrivacyDataEncryptionUtils privacyDataEncryptionUtils;

    /**
     * Construct this class
     * @param ipfsConnection the config class to connect to IPFS
     */
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

    /**
     * Retrieve data
     * @param downloadDataParameter the download data parameter
     * @return the data
     */
    public Observable<byte[]> getData(DownloadDataParameter downloadDataParameter) {
        checkParameter(downloadDataParameter != null, "downloadDataParameter is required");

        return doGetData(downloadDataParameter.getDataHash(), downloadDataParameter.getPrivacyStrategy(),
                downloadDataParameter.getDigest())
                .map(dataOpt -> dataOpt.orElse(null));
    }

    /**
     * Retrieve a list of data
     * @param downloadParam the download parameter
     * @param rootData the root data
     * @return the list of data
     */
    public Observable<List<byte[]>> getDataList(DownloadParameter downloadParam, ProximaxRootDataModel rootData) {
        checkParameter(downloadParam != null, "downloadParam is required");
        checkParameter(rootData != null, "rootData is required");

        return Observable.fromIterable(rootData.getDataList())
                .concatMapEager(data -> {
                    if (data.getContentType().equals(PATH_UPLOAD_CONTENT_TYPE)) { // path
                        return Observable.just(Optional.<byte[]>empty());
                    } else { // byte array
                        return doGetData(data.getDataHash(), downloadParam.getPrivacyStrategy(), data.getDigest());
                    }
                })
                .toList()
                .toObservable()
                .map(dataList -> dataList.stream().map(dataOpt -> dataOpt.orElse(null)).collect(toList()));
    }

    private Observable<Optional<byte[]>> doGetData(String dataHash, PrivacyStrategy privacyStrategy, String digest) {
        return ipfsDownloadService.download(dataHash)
                .flatMap(undecryptedData ->
                        digestUtils.validateDigest(undecryptedData, digest).map(result -> undecryptedData))
                .flatMap(undecryptedData ->
                        privacyDataEncryptionUtils.decrypt(privacyStrategy, undecryptedData))
                .map(Optional::of);
    }
}
