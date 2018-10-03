/*
 * Copyright 2018 ProximaX Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
     *
     * @param uploadParameter the upload parameter
     * @param uploadedData    an uploaded data object
     * @return the created message payload
     */
    public Observable<ProximaxMessagePayloadModel> createMessagePayload(UploadParameter uploadParameter, ProximaxDataModel uploadedData) {
        checkParameter(uploadParameter != null, "uploadParameter is required");
        checkParameter(uploadedData != null, "uploadedData is required");

        return Observable.just(ProximaxMessagePayloadModel.create(uploadParameter.getPrivacyStrategy().getPrivacyType(),
                uploadParameter.getVersion(), uploadedData));
    }
}
