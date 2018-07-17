package io.proximax.download;

import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.connection.ConnectionConfig;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.exceptions.UploadInitFailureException;
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

public class Download {
    private final BlockchainTransactionService blockchainTransactionService;
    private final RetrieveProximaxMessagePayloadService retrieveProximaxMessagePayloadService;
    private final RetrieveProximaxRootDataService retrieveProximaxRootDataService;
    private final RetrieveProximaxDataService retrieveProximaxDataService;

    public Download(ConnectionConfig connectionConfig) {
        this.retrieveProximaxMessagePayloadService = new RetrieveProximaxMessagePayloadService();
        this.retrieveProximaxRootDataService = new RetrieveProximaxRootDataService(connectionConfig.getIpfsConnection());
        this.retrieveProximaxDataService = new RetrieveProximaxDataService(connectionConfig.getIpfsConnection());

        try {
            this.blockchainTransactionService = new BlockchainTransactionService(connectionConfig.getBlockchainNetworkConnection());
        } catch (MalformedURLException e) {
            throw new UploadInitFailureException("Failed to initialize", e);
        }
    }

    Download(BlockchainTransactionService blockchainTransactionService, RetrieveProximaxMessagePayloadService retrieveProximaxMessagePayloadService,
             RetrieveProximaxRootDataService retrieveProximaxRootDataService, RetrieveProximaxDataService retrieveProximaxDataService) {
        this.blockchainTransactionService = blockchainTransactionService;
        this.retrieveProximaxMessagePayloadService = retrieveProximaxMessagePayloadService;
        this.retrieveProximaxRootDataService = retrieveProximaxRootDataService;
        this.retrieveProximaxDataService = retrieveProximaxDataService;
    }

    public DownloadResult download(final DownloadParameter downloadParam) {
        checkParameter(downloadParam != null, "downloadParam is required");

        return getBlockchainTransaction(downloadParam.transactionHash)
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
