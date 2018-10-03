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

import java.util.Map;

/**
 * This model class represents the details of an uploaded data
 */
public final class ProximaxDataModel extends DataInfoModel {

    private final String digest;
    private final String dataHash;
    private final long timestamp;

    /**
     * Construct an instance
     *
     * @param digest      the computed digest for the data (already encrypted data by privacy strategy)
     * @param dataHash    the data hash to access the data (already encrypted data by privacy strategy)
     * @param description a description of the data
     * @param metadata    an additional metadata for the data
     * @param timestamp   the timestamp when the upload occurred
     * @param name        the name for the data (file name by default if a file upload)
     * @param contentType the content type of the data (detected if none specified)
     */
    public ProximaxDataModel(final String digest,
                             final String dataHash,
                             final String description,
                             final Map<String, String> metadata,
                             final Long timestamp,
                             final String name,
                             final String contentType) {
        super(description, name, contentType, metadata);
        this.digest = digest;
        this.dataHash = dataHash;
        this.timestamp = timestamp;
    }

    /**
     * Get the computed digest for the data
     *
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Get the data hash to access the data
     *
     * @return the data hash
     */
    public String getDataHash() {
        return dataHash;
    }

    /**
     * Get the upload timestamp
     *
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Creates an instance of this model
     *
     * @param parameterData an upload param data containing the description, metadata and name
     * @param dataHash      the data hash
     * @param digest        the computed digest
     * @param contentType   the content type
     * @param timestamp     the timestamp uploaded
     * @return the instance of this model
     */
    public static ProximaxDataModel create(DataInfoModel parameterData, String dataHash, String digest, String contentType, Long timestamp) {
        return new ProximaxDataModel(digest, dataHash, parameterData.getDescription(), parameterData.getMetadata(),
                timestamp, parameterData.getName(), contentType);
    }
}
