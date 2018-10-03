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
package io.proximax.download;

import io.proximax.model.DataInfoModel;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The model class that defines the downloaded data
 * @see DownloadResult
 */
public class DownloadResultData extends DataInfoModel {

    private final Supplier<InputStream> byteStreamSupplier;
    private final String digest;
    private final String dataHash;
    private final long timestamp;

    DownloadResultData(Supplier<InputStream> byteStreamSupplier, String digest, String dataHash, long timestamp, String description, String name,
                       String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata == null ? Collections.emptyMap() : Collections.unmodifiableMap(metadata));
        this.byteStreamSupplier = byteStreamSupplier;
        this.digest = digest;
        this.dataHash = dataHash;
        this.timestamp = timestamp;
    }

    /**
     * Get the byte stream
     * @return the byte stream
     */
    public InputStream getByteStream() {
        return byteStreamSupplier.get();
    }

    /**
     * Get the digest
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Get the data hash
     * @return the data hash
     */
    public String getDataHash() {
        return dataHash;
    }

    /**
     * Get the timestamp of upload
     * @return the timestamp of upload
     */
    public long getTimestamp() {
        return timestamp;
    }
}
