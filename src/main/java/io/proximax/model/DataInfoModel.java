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

import java.util.Collections;
import java.util.Map;

/**
 * This model class defines the details of a data
 */
public abstract class DataInfoModel {

    private final String description;
    private final Map<String, String> metadata;
    private final String name;
    private final String contentType;

    /**
     * Construct instance of this model
     *
     * @param description a description of the data
     * @param name        the name for the data
     * @param contentType the content type of the data
     * @param metadata    the string-to-string keypair metadata of the data
     */
    public DataInfoModel(String description, String name, String contentType, Map<String, String> metadata) {
        this.description = description;
        this.metadata = metadata == null ? null : Collections.unmodifiableMap(metadata);
        this.name = name;
        this.contentType = contentType;
    }

    /**
     * Get the description of the data
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the string-to-string keypair metadata of the data
     *
     * @return the metadata
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Get the name for the data
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the content type of the data
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }
}
