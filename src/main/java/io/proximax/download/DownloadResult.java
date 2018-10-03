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

/**
 * The model class that defines the result of a download
 *
 * @see Downloader#download(DownloadParameter)
 */
public final class DownloadResult {

    private final String transactionHash;
    private final int privacyType;
    private final String version;
    private final DownloadResultData data;

    private DownloadResult(String transactionHash, int privacyType, String version, DownloadResultData data) {
        this.transactionHash = transactionHash;
        this.privacyType = privacyType;
        this.version = version;
        this.data = data;
    }

    /**
     * Get the blockchain transaction hash of the download
     *
     * @return the blockchain transaction hash
     */
    public String getTransactionHash() {
        return transactionHash;
    }

    /**
     * Get the the privacy type of privacy strategy used to encrypt data
     *
     * @return the privacy type
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
     * Get the downloaded data
     *
     * @return the downloaded data
     */
    public DownloadResultData getData() {
        return data;
    }

    static DownloadResult create(String transactionHash, int privacyType, String version, DownloadResultData data) {
        return new DownloadResult(transactionHash, privacyType, version, data);
    }
}
