package io.proximax.upload;

import io.proximax.connection.ConnectionConfig;
import io.proximax.exceptions.UploadFailureException;
import io.proximax.exceptions.UploadInitFailureException;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.model.ProximaxRootDataModel;
import io.proximax.service.BlockchainTransactionService;
import io.proximax.service.CreateProximaxMessagePayloadService;
import io.proximax.service.CreateProximaxRootDataService;
import io.reactivex.Observable;

import java.net.MalformedURLException;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class Upload {

    private final BlockchainTransactionService blockchainTransactionService;
    private final CreateProximaxRootDataService createProximaxRootDataService;
    private final CreateProximaxMessagePayloadService createProximaxMessagePayloadService;

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

    public UploadResult upload(UploadParameter uploadParam) {
        checkParameter(uploadParam != null, "uploadParam is required");

        return createProximaxRootDataService.createRootData(uploadParam).flatMap(rootData ->
                createProximaxMessagePayloadService.createMessagePayload(uploadParam, rootData).flatMap(messagePayload ->
                        blockchainTransactionService.createAndAnnounceTransaction(
                                uploadParam.getPrivacyStrategy(), uploadParam.getSignerPrivateKey(),
                                uploadParam.getRecipientPublicKey(), messagePayload
                        ).map(transactionHash -> createUploadResult(messagePayload, transactionHash, rootData))))
                .onErrorResumeNext((Throwable ex) ->
                        Observable.error(new UploadFailureException("Upload failed.", ex)))
                .blockingFirst();
    }

    private UploadResult createUploadResult(ProximaxMessagePayloadModel transactionMessagePayload, String transactionHash,
                                            ProximaxRootDataModel rootData) {
        return UploadResult.create(transactionHash, transactionMessagePayload.getDigest(),
                                transactionMessagePayload.getRootDataHash(), rootData);
    }

}
