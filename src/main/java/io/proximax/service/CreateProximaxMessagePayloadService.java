package io.proximax.service;

import io.proximax.model.ProximaxDataModel;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.upload.UploadParameter;
import io.reactivex.Observable;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for creating a message payload
 */
public class CreateProximaxMessagePayloadService {

    /**
     * Creates a message payload
     * @param uploadParameter the upload parameter
     * @param uploadedData an uploaded data object
     * @return the created message payload
     */
    public Observable<ProximaxMessagePayloadModel> createMessagePayload(UploadParameter uploadParameter, ProximaxDataModel uploadedData) {
        checkParameter(uploadParameter != null, "uploadParameter is required");
        checkParameter(uploadedData != null, "uploadedData is required");

        return Observable.just(ProximaxMessagePayloadModel.create(uploadParameter.getPrivacyStrategy().getPrivacyType(),
                uploadParameter.getVersion(), uploadedData));
    }
}
