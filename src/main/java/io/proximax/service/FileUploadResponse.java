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

import java.io.File;
import java.io.InputStream;

/**
 * The model class that defines the result of uploading to IPFS
 *
 * @see FileUploadService#uploadByteStream(InputStream)
 * @see FileUploadService#uploadPath(File)
 */
public class FileUploadResponse {

    private final String dataHash;
    private final Long timestamp;

    FileUploadResponse(String dataHash, Long timestamp) {
        this.dataHash = dataHash;
        this.timestamp = timestamp;
    }

    /**
     * Get the data hash of the IPFS upload result
     *
     * @return the data hash
     */
    public String getDataHash() {
        return dataHash;
    }

    /**
     * Get the timestamp of upload
     *
     * @return the timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }
}
