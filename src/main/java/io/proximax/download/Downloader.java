package io.proximax.download;

import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.async.AsyncCallbacks;
import io.proximax.async.AsyncTask;
import io.proximax.connection.ConnectionConfig;
import io.proximax.exceptions.DirectDownloadFailureException;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.exceptions.DownloadInitFailureException;
import io.proximax.model.ProximaxDataModel;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.service.BlockchainTransactionService;
import io.proximax.service.RetrieveProximaxDataService;
import io.proximax.service.RetrieveProximaxMessagePayloadService;
import io.proximax.utils.AsyncUtils;
import io.reactivex.Observable;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Optional;
import java.util.function.Supplier;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The Downloader class that handles the download functionality
 * <br>
 * <br>
 * The Downloader creation requires a ConnectionConfig that defines generally where the download will be done.
 * The instance of the class can be reused to download multiple times.
 * <br>
 * <br>
 * Downloads can be done by providing the blockchain transaction hash or the data hash.
 * A complete download can be done to get the data and its accompanying details,
 * and a direct download can be done to retrieve the data only.
 *
 * @see ConnectionConfig
 * @see DownloadParameter
 * @see DirectDownloadParameter
 */
public class Downloader {
    private final BlockchainTransactionService blockchainTransactionService;
    private final RetrieveProximaxMessagePayloadService retrieveProximaxMessagePayloadService;
    private final RetrieveProximaxDataService retrieveProximaxDataService;

    /**
     * Construct the class with a ConnectionConfig
     *
     * @param connectionConfig the connection config that defines generally where the download will be sent
     */
    public Downloader(ConnectionConfig connectionConfig) {
        this.retrieveProximaxMessagePayloadService = new RetrieveProximaxMessagePayloadService();
        this.retrieveProximaxDataService = new RetrieveProximaxDataService(connectionConfig);

        try {
            this.blockchainTransactionService = new BlockchainTransactionService(connectionConfig.getBlockchainNetworkConnection());
        } catch (MalformedURLException e) {
            throw new DownloadInitFailureException("Failed to initialize", e);
        }
    }

    Downloader(BlockchainTransactionService blockchainTransactionService,
               RetrieveProximaxMessagePayloadService retrieveProximaxMessagePayloadService,
               RetrieveProximaxDataService retrieveProximaxDataService) {
        this.blockchainTransactionService = blockchainTransactionService;
        this.retrieveProximaxMessagePayloadService = retrieveProximaxMessagePayloadService;
        this.retrieveProximaxDataService = retrieveProximaxDataService;
    }

    /**
     * Retrieve synchronously the data and its accompanying details.
     * This would use the blockchain transaction hash to retrieve the data's byte stream and its details.
     * <br>
     *
     * @param downloadParam the download parameter
     * @return the download result
     */
    public DownloadResult download(final DownloadParameter downloadParam) {
        checkParameter(downloadParam != null, "downloadParam is required");

        return doCompleteDownload(downloadParam).blockingFirst();
    }

    /**
     * Retrieve asynchronously the data and its accompanying details.
     * This would use the blockchain transaction hash to retrieve the data's byte stream and its details.
     * <br>
     *
     * @param downloadParam  the download parameter
     * @param asyncCallbacks an optional callbacks when succeeded or failed
     * @return the download result containing the list of data
     */
    public AsyncTask downloadAsync(DownloadParameter downloadParam, AsyncCallbacks<DownloadResult> asyncCallbacks) {
        checkParameter(downloadParam != null, "downloadParam is required");

        final AsyncTask asyncTask = new AsyncTask();

        AsyncUtils.processFirstItem(this.doCompleteDownload(downloadParam), asyncCallbacks, asyncTask);

        return asyncTask;
    }

    /**
     * Retrieve synchronously the data
     *
     * @param directDownloadParameter the direct download data parameter
     * @return the data
     */
    public InputStream directDownload(final DirectDownloadParameter directDownloadParameter) {
        checkParameter(directDownloadParameter != null, "directDownloadParameter is required");

        return doDirectDownload(directDownloadParameter).blockingFirst();
    }

    /**
     * Retrieve asynchronously the data
     *
     * @param directDownloadParameter the direct download data parameter
     * @param asyncCallbacks          an optional callbacks when succeeded or failed
     * @return the data
     */
    public AsyncTask directDownloadAsync(DirectDownloadParameter directDownloadParameter, AsyncCallbacks<InputStream> asyncCallbacks) {
        checkParameter(directDownloadParameter != null, "directDownloadParameter is required");

        final AsyncTask asyncTask = new AsyncTask();

        AsyncUtils.processFirstItem(this.doDirectDownload(directDownloadParameter), asyncCallbacks, asyncTask);

        return asyncTask;
    }

    private Observable<DownloadResult> doCompleteDownload(DownloadParameter downloadParam) {
        try {
            final DownloadResult downloadResult = blockchainTransactionService.getTransferTransaction(downloadParam.getTransactionHash())
                    .map(transferTransaction -> retrieveProximaxMessagePayloadService.getMessagePayload(transferTransaction,
                            downloadParam.getAccountPrivateKey()))
                    .map(messagePayload -> createCompleteDownloadResult(messagePayload,
                            () -> getDataByteStream(Optional.of(messagePayload), null, downloadParam.getPrivacyStrategy(),
                                    downloadParam.getValidateDigest(), null).blockingFirst(),
                            downloadParam.getTransactionHash())).blockingFirst();
            return Observable.just(downloadResult);
        } catch (RuntimeException ex) {
            return Observable.error(new DownloadFailureException("Download failed.", ex));
        }
    }

    private DownloadResult createCompleteDownloadResult(ProximaxMessagePayloadModel messagePayload,
                                                        Supplier<InputStream> byteStreamSupplier, String transactionHash) {
        final ProximaxDataModel data = messagePayload.getData();
        return DownloadResult.create(transactionHash, messagePayload.getPrivacyType(), messagePayload.getVersion(),
                new DownloadResultData(byteStreamSupplier, data.getDigest(), data.getDataHash(), data.getTimestamp(),
                        data.getDescription(), data.getName(), data.getContentType(), data.getMetadata()));
    }

    private Observable<InputStream> doDirectDownload(DirectDownloadParameter downloadParam) {
        try {
            final InputStream inputStream = getOptionalBlockchainTransaction(downloadParam.getTransactionHash())
                    .map(transferTransactionOpt -> transferTransactionOpt.map(transferTransaction ->
                            retrieveProximaxMessagePayloadService.getMessagePayload(transferTransaction, downloadParam.getAccountPrivateKey())))
                    .flatMap(messagePayload -> getDataByteStream(messagePayload, downloadParam.getDataHash(), downloadParam.getPrivacyStrategy(),
                            downloadParam.getValidateDigest(), downloadParam.getDigest())).blockingFirst();
            return Observable.just(inputStream);
        } catch (RuntimeException ex) {
            return Observable.error(new DirectDownloadFailureException("Direct download failed.", ex));
        }
    }

    private Observable<Optional<TransferTransaction>> getOptionalBlockchainTransaction(String transactionHash) {
        return Optional.ofNullable(transactionHash)
                .map(hash -> blockchainTransactionService.getTransferTransaction(hash).map(Optional::of))
                .orElse(Observable.just(Optional.empty()));
    }

    private Observable<InputStream> getDataByteStream(Optional<ProximaxMessagePayloadModel> messagePayload, String dataHash, PrivacyStrategy privacyStrategy,
                                                      boolean validateDigest, String digest) {
        final String resolvedDataHash = messagePayload.map(payload -> payload.getData().getDataHash()).orElse(dataHash);
        final String resolvedDigest = messagePayload.map(payload -> payload.getData().getDigest()).orElse(digest);
        final String resolvedContentType = messagePayload.map(payload -> payload.getData().getContentType()).orElse(null);

        return retrieveProximaxDataService.getDataByteStream(resolvedDataHash, privacyStrategy, validateDigest, resolvedDigest, resolvedContentType);
    }
}
