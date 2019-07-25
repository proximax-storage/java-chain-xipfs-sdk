package io.proximax.upload;

import io.proximax.async.AsyncCallbacks;
import io.proximax.async.AsyncTask;
import io.proximax.connection.ConnectionConfig;
import io.proximax.exceptions.UploadFailureException;
import io.proximax.exceptions.UploadInitFailureException;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.service.BlockchainTransactionService;
import io.proximax.service.CreateProximaxDataService;
import io.proximax.service.CreateProximaxMessagePayloadService;
import io.proximax.utils.AsyncUtils;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.net.MalformedURLException;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The Uploader class that handles the upload functionality
 * <br>
 * <br>
 * The Uploader creation requires a ConnectionConfig that defines generally
 * where and how the upload will be done. The instance of the class can be
 * reused to upload multiple times.
 * <br>
 * <br>
 * Each upload requires an UploadParameter that contains what is being uploaded
 * along with additional details.
 * <br>
 * <br>
 * Each upload can be any of the following type:
 * <ul>
 * <li>byte array</li>
 * <li>file</li>
 * <li>url resource</li>
 * <li>list of files to be compressed as a zip</li>
 * <li>string</li>
 * <li>path or directory</li>
 * </ul>
 * <br>
 *
 * @see ConnectionConfig
 * @see UploadParameter
 */
public class Uploader {

    private final BlockchainTransactionService blockchainTransactionService;
    private final CreateProximaxDataService createProximaxDataService;
    private final CreateProximaxMessagePayloadService createProximaxMessagePayloadService;

    /**
     * Construct the class with a ConnectionConfig
     *
     * @param connectionConfig the connection config that defines generally
     *                         where the upload will be sent
     */
    public Uploader(ConnectionConfig connectionConfig) {
        this.createProximaxDataService = new CreateProximaxDataService(connectionConfig.getFileStorageConnection());
        this.createProximaxMessagePayloadService = new CreateProximaxMessagePayloadService();

        try {
            this.blockchainTransactionService = new BlockchainTransactionService(connectionConfig.getBlockchainNetworkConnection());
        } catch (MalformedURLException e) {
            throw new UploadInitFailureException("Failed to initialize", e);
        }
    }

    Uploader(BlockchainTransactionService blockchainTransactionService, CreateProximaxDataService createProximaxDataService,
             CreateProximaxMessagePayloadService createProximaxMessagePayloadService) {
        this.blockchainTransactionService = blockchainTransactionService;
        this.createProximaxDataService = createProximaxDataService;
        this.createProximaxMessagePayloadService = createProximaxMessagePayloadService;
    }

    /**
     * Upload a data synchronously and attach it on a blockchain transaction.
     * This upload returns result once the blockchain transaction is validated
     * and already set with `unconfirmed` status
     * <br>
     * The upload throws an UploadFailureException runtime exception if does not
     * succeed.
     *
     * @param uploadParam the upload parameter
     * @return the upload result
     */
    public UploadResult upload(UploadParameter uploadParam) {
        checkParameter(uploadParam != null, "uploadParam is required");

        return doUpload(uploadParam).blockingFirst();
    }

    /**
     * Upload a data asynchronously and attach it on a blockchain transaction.
     * This upload returns result once the blockchain transaction is validated
     * and already set with `unconfirmed` status
     * <br>
     * The upload throws an UploadFailureException runtime exception if does not
     * succeed.
     *
     * @param uploadParam    the upload parameter
     * @param asyncCallbacks an optional callbacks when succeeded or failed
     * @return the upload result
     */
    public AsyncTask uploadAsync(UploadParameter uploadParam, AsyncCallbacks<UploadResult> asyncCallbacks) {
        checkParameter(uploadParam != null, "uploadParam is required");

        final AsyncTask asyncTask = new AsyncTask();

        AsyncUtils.processFirstItem(this.doUpload(uploadParam).subscribeOn(Schedulers.newThread()), asyncCallbacks, asyncTask);

        return asyncTask;
    }

    private Observable<UploadResult> doUpload(UploadParameter uploadParam) {
        return Observable.fromCallable(
                () -> {
                    try {
                        final UploadResult result = createProximaxDataService.createData(uploadParam).flatMap(uploadedData
                                -> createProximaxMessagePayloadService.createMessagePayload(uploadParam, uploadedData)
                                .flatMap(messagePayload
                                        -> createAndAnnounceTransaction(uploadParam, messagePayload)
                                        .map(transactionHash
                                                -> createUploadResult(messagePayload, transactionHash)))).blockingFirst();
                        return result;
                    } catch (RuntimeException ex) {
                        throw new UploadFailureException("Upload failed.", ex);
                    }

                }
        );
    }

    private Observable<String> createAndAnnounceTransaction(UploadParameter uploadParam, ProximaxMessagePayloadModel messagePayload) {
        return blockchainTransactionService.createAndAnnounceTransaction(
                messagePayload, uploadParam.getSignerPrivateKey(), uploadParam.getRecipientPublicKey(), uploadParam.getRecipientAddress(),
                uploadParam.getTransactionDeadline(), uploadParam.getTransactionMosaics(), uploadParam.getUseBlockchainSecureMessage());
    }

    private UploadResult createUploadResult(ProximaxMessagePayloadModel messagePayload, String transactionHash) {
        return UploadResult.create(transactionHash, messagePayload.getPrivacyType(), messagePayload.getVersion(),
                messagePayload.getData());
    }

}
