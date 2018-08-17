package io.proximax.download;

import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.connection.ConnectionConfig;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.exceptions.DownloadInitFailureException;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.service.BlockchainTransactionService;
import io.proximax.service.RetrieveProximaxDataService;
import io.proximax.service.RetrieveProximaxMessagePayloadService;
import io.proximax.service.RetrieveProximaxRootDataService;
import io.reactivex.Observable;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The Download class that handles the download functionality
 * <br>
 * <br>
 * The Upload creation requires a ConnectionConfig that defines generally where the download will be done.
 * The instance of the class can be reused to download multiple times.
 * <br>
 * <br>
 * Downloads can be done by providing the blockchain transaction hash and the root data hash to download the whole upload instance.
 * <br>
 * Downloads can also download data directly by providing the data hash.
 * @see ConnectionConfig
 * @see DownloadParameter
 * @see DownloadDataParameter
 */
public class Download {
    private final BlockchainTransactionService blockchainTransactionService;
    private final RetrieveProximaxMessagePayloadService retrieveProximaxMessagePayloadService;
    private final RetrieveProximaxRootDataService retrieveProximaxRootDataService;
    private final RetrieveProximaxDataService retrieveProximaxDataService;

    /**
     * Construct the class with a ConnectionConfig
     * @param connectionConfig the connection config that defines generally where the download will be sent
     */
    public Download(ConnectionConfig connectionConfig) {
        this.retrieveProximaxMessagePayloadService = new RetrieveProximaxMessagePayloadService();
        this.retrieveProximaxRootDataService = new RetrieveProximaxRootDataService(connectionConfig.getIpfsConnection());
        this.retrieveProximaxDataService = new RetrieveProximaxDataService(connectionConfig.getIpfsConnection());

        try {
            this.blockchainTransactionService = new BlockchainTransactionService(connectionConfig.getBlockchainNetworkConnection());
        } catch (MalformedURLException e) {
            throw new DownloadInitFailureException("Failed to initialize", e);
        }
    }

    Download(BlockchainTransactionService blockchainTransactionService, RetrieveProximaxMessagePayloadService retrieveProximaxMessagePayloadService,
             RetrieveProximaxRootDataService retrieveProximaxRootDataService, RetrieveProximaxDataService retrieveProximaxDataService) {
        this.blockchainTransactionService = blockchainTransactionService;
        this.retrieveProximaxMessagePayloadService = retrieveProximaxMessagePayloadService;
        this.retrieveProximaxRootDataService = retrieveProximaxRootDataService;
        this.retrieveProximaxDataService = retrieveProximaxDataService;
    }

    /**
     * Download the upload instance by providing the blockchain transaction hash or root data hash
     * <br>
     * By downloading the upload instance, it automatically fetches all data included on that upload instance.
     * @param downloadParam the download parameter
     * @return the download result containing the list of data
     */
    public DownloadResult download(final DownloadParameter downloadParam) {
        checkParameter(downloadParam != null, "downloadParam is required");

        return getBlockchainTransaction(downloadParam.getTransactionHash())
                .map(transferTransactionOpt ->
                        transferTransactionOpt.map(transferTransaction ->
                                retrieveProximaxMessagePayloadService.getMessagePayload(downloadParam.getPrivacyStrategy(), transferTransaction)))
                .flatMap(messagePayloadOpt -> retrieveProximaxRootDataService.getRootData(downloadParam, messagePayloadOpt))
                .flatMap(rootData -> retrieveProximaxDataService.getDataList(downloadParam, rootData)
                        .map(decryptedDataList -> createDownloadResult(rootData, decryptedDataList)))
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new DownloadFailureException("Download failed.", ex)))
                .blockingFirst();
    }

    /**
     * Download a data by providing the data hash
     * @param downloadDataParameter the download data parameter
     * @return the download result containing the data
     */
    public DownloadDataResult downloadData(final DownloadDataParameter downloadDataParameter) {
        checkParameter(downloadDataParameter != null, "downloadDataParameter is required");

        return retrieveProximaxDataService.getData(downloadDataParameter)
                .map(DownloadDataResult::new)
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new DownloadFailureException("Download failed.", ex)))
                .blockingFirst();
    }

    private Observable<Optional<TransferTransaction>> getBlockchainTransaction(String transactionHash) {
        return Optional.ofNullable(transactionHash)
                .map(hash -> blockchainTransactionService.getTransferTransaction(hash).map(Optional::of))
                .orElse(Observable.just(Optional.empty()));
    }

    private DownloadResult createDownloadResult(ProximaxRootDataModel rootData, List<byte[]> decryptedDataList) {
        return DownloadResult.create(rootData, decryptedDataList);
    }
}
