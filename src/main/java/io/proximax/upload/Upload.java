package io.proximax.upload;

import io.proximax.async.AsyncCallback;
import io.proximax.async.AsyncTask;
import io.proximax.connection.ConnectionConfig;
import io.proximax.exceptions.UploadFailureException;
import io.proximax.exceptions.UploadInitFailureException;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.service.BlockchainTransactionService;
import io.proximax.service.CreateProximaxMessagePayloadService;
import io.proximax.service.CreateProximaxRootDataService;
import io.proximax.utils.AsyncUtils;
import io.reactivex.Observable;

import java.net.MalformedURLException;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The Upload class that handles the upload functionality
 * <br>
 * <br>
 * The Upload creation requires a ConnectionConfig that defines generally where and how the upload will be done.
 * The instance of the class can be reused to upload multiple times.
 * <br>
 * <br>
 * Each upload requires an UploadParameter that contains what is being uploaded along with additional details.
 * <br>
 * <br>
 * Each upload can have multiple data which can be provided as any of the following:
 * <ul>
 *     <li>byte array</li>
 *     <li>file</li>
 *     <li>url resource</li>
 *     <li>list of files to be compressed as a zip</li>
 *     <li>string</li>
 * </ul>
 * <br>
 * @see ConnectionConfig
 * @see UploadParameter
 */
public class Upload {

    private final BlockchainTransactionService blockchainTransactionService;
    private final CreateProximaxRootDataService createProximaxRootDataService;
    private final CreateProximaxMessagePayloadService createProximaxMessagePayloadService;

    /**
     * Construct the class with a ConnectionConfig
     * @param connectionConfig the connection config that defines generally where the upload will be sent
     */
    public Upload(ConnectionConfig connectionConfig) {
        this.createProximaxRootDataService = new CreateProximaxRootDataService(connectionConfig.getIpfsConnection());
        this.createProximaxMessagePayloadService = new CreateProximaxMessagePayloadService(connectionConfig.getIpfsConnection());

        try {
            this.blockchainTransactionService = new BlockchainTransactionService(connectionConfig.getBlockchainNetworkConnection());
        } catch (MalformedURLException e) {
            throw new UploadInitFailureException("Failed to initialize", e);
        }
    }

    Upload(BlockchainTransactionService blockchainTransactionService, CreateProximaxRootDataService createProximaxRootDataService,
           CreateProximaxMessagePayloadService createProximaxMessagePayloadService) {
        this.blockchainTransactionService = blockchainTransactionService;
        this.createProximaxRootDataService = createProximaxRootDataService;
        this.createProximaxMessagePayloadService = createProximaxMessagePayloadService;
    }

    /**
     * Upload data or list of data synchronously and attach it on a blockchain transaction.
     * This upload returns result once the blockchain transaction is validated and already set with `unconfirmed` status
     * <br>
     * The upload throws an UploadFailureException runtime exception if does not succeed.
     * @param uploadParam the upload parameter that contains what is being uploaded along with additional details
     * @return the upload result containing the hashes to get the uploaded content
     */
    public UploadResult upload(UploadParameter uploadParam) {
        checkParameter(uploadParam != null, "uploadParam is required");

        return doUpload(uploadParam).blockingFirst();
    }

    /**
     * Upload data or list of data asynchronously and attach it on a blockchain transaction.
     * This upload returns result once the blockchain transaction is validated and already set with `unconfirmed` status
     * <br>
     * The upload throws an UploadFailureException runtime exception if does not succeed.
     * @param uploadParam the upload parameter that contains what is being uploaded along with additional details
     * @return the upload result containing the hashes to get the uploaded content
     */
    public AsyncTask uploadAsync(UploadParameter uploadParam, AsyncCallback<UploadResult> asyncCallback) {
        checkParameter(uploadParam != null, "uploadParam is required");

        final AsyncTask asyncTask = new AsyncTask();

        AsyncUtils.processFirstItem(this.doUpload(uploadParam), asyncCallback, asyncTask);

        return asyncTask;
    }

    private Observable<UploadResult> doUpload(UploadParameter uploadParam) {
        return createProximaxRootDataService.createRootData(uploadParam)
                .flatMap(rootData ->
                        createProximaxMessagePayloadService.createMessagePayload(uploadParam, rootData)
                                .flatMap(messagePayload ->
                                        blockchainTransactionService.createAndAnnounceTransaction(
                                                uploadParam.getPrivacyStrategy(), uploadParam.getSignerPrivateKey(),
                                                uploadParam.getRecipientPublicKey(), messagePayload)
                                                .map(transactionHash ->
                                                        createUploadResult(messagePayload, transactionHash, rootData))))
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new UploadFailureException("Upload failed.", ex)));
    }

    private UploadResult createUploadResult(ProximaxMessagePayloadModel transactionMessagePayload, String transactionHash,
                                            ProximaxRootDataModel rootData) {
        return UploadResult.create(transactionHash, transactionMessagePayload.getDigest(),
                                transactionMessagePayload.getRootDataHash(), rootData);
    }

}
