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
package io.proximax.model;

import io.proximax.privacy.strategy.PrivacyStrategy;

/**
 * This model class represents the transaction message
 *
 * @see PrivacyType
 * @see PrivacyStrategy
 */
public final class ProximaxMessagePayloadModel {

    private final int privacyType;
    private final String version;
    private ProximaxDataModel data;

    private ProximaxMessagePayloadModel(int privacyType, String version, ProximaxDataModel data) {
        this.privacyType = privacyType;
        this.version = version;
        this.data = data;
    }

    /**
     * Get the privacy type from privacy strategy used to encrypt data
     *
     * @return the privacy type
     * @see io.proximax.model.PrivacyType
     */
    public int getPrivacyType() {
        return privacyType;
    }

    /**
     * Get the schema version of the upload
     *
     * @return the schema version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Get the data object containing the data hash
     *
     * @return the data object
     */
    public ProximaxDataModel getData() {
        return data;
    }

    /**
     * Construct instance of this model
     *
     * @param privacyType the privacy type from privacy strategy used to encrypt data
     * @param version     the schema version of the upload
     * @param data        the data object containing the data hash
     * @return instance of this model
     */
    public static ProximaxMessagePayloadModel create(int privacyType, String version, ProximaxDataModel data) {
        return new ProximaxMessagePayloadModel(privacyType, version, data);
    }

}
