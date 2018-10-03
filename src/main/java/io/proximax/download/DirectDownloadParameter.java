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

import io.proximax.privacy.strategy.PrivacyStrategy;

/**
 * This model class is the input parameter of direct download.
 *
 * @see Downloader#directDownload(DirectDownloadParameter)
 * @see DirectDownloadParameterBuilder
 */
public class DirectDownloadParameter {

    private final String transactionHash;
    private final String accountPrivateKey;
    private final String dataHash;
    private final boolean validateDigest;
    private final PrivacyStrategy privacyStrategy;
    private final String digest;

    DirectDownloadParameter(String transactionHash, String accountPrivateKey, String dataHash, boolean validateDigest,
                            PrivacyStrategy privacyStrategy, String digest) {
        this.transactionHash = transactionHash;
        this.accountPrivateKey = accountPrivateKey;
        this.dataHash = dataHash;
        this.validateDigest = validateDigest;
        this.privacyStrategy = privacyStrategy;
        this.digest = digest;
    }

    /**
     * Get the transaction hash to download
     *
     * @return the transaction hash
     */
    public String getTransactionHash() {
        return transactionHash;
    }

    /**
     * Get the account private key of the sender or recipient of the transaction (applicable only with transactionHash). Required for secure messages
     *
     * @return the account private key
     */
    public String getAccountPrivateKey() {
        return accountPrivateKey;
    }

    /**
     * Get the data hash to download
     *
     * @return the data hash
     */
    public String getDataHash() {
        return dataHash;
    }

    /**
     * Get the the flag that indicates if to verify data with digest (applicable only with transactionHash)
     *
     * @return the validate digest flag
     */
    public boolean getValidateDigest() {
        return validateDigest;
    }

    /**
     * Get the digest to verify the downloaded data
     *
     * @return the digest
     */
    public PrivacyStrategy getPrivacyStrategy() {
        return privacyStrategy;
    }

    /**
     * Get the privacy strategy to decrypt the data
     *
     * @return the privacy strategy
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Start creating instance of this class from transaction hash using DirectDownloadParameterBuilder
     *
     * @param transactionHash the transaction hash of target download
     * @return the direct download parameter builder
     */
    public static DirectDownloadParameterBuilder createFromTransactionHash(String transactionHash) {
        return DirectDownloadParameterBuilder.createFromTransactionHash(transactionHash, null, null);
    }

    /**
     * Start creating instance of this class from transaction hash and account private key using DirectDownloadParameterBuilder
     *
     * @param transactionHash   the transaction hash of target download
     * @param accountPrivateKey the account private key
     * @return the direct download parameter builder
     */
    public static DirectDownloadParameterBuilder createFromTransactionHash(String transactionHash, String accountPrivateKey) {
        return DirectDownloadParameterBuilder.createFromTransactionHash(transactionHash, accountPrivateKey, null);
    }

    /**
     * Start creating instance of this class from transaction hash and validate digest flag sing DirectDownloadParameterBuilder
     *
     * @param transactionHash the transaction hash of target download
     * @param validateDigest  the validate digest flag as to whether to verify data with digest
     * @return the direct download parameter builder
     */
    public static DirectDownloadParameterBuilder createFromTransactionHash(String transactionHash, boolean validateDigest) {
        return DirectDownloadParameterBuilder.createFromTransactionHash(transactionHash, null, validateDigest);
    }

    /**
     * Start creating instance of this class from transaction hash, account private key and validate digest flag using DirectDownloadParameterBuilder
     *
     * @param transactionHash   the transaction hash of target download
     * @param accountPrivateKey the account private key
     * @param validateDigest    the validate digest flag as to whether to verify data with digest
     * @return the direct download parameter builder
     */
    public static DirectDownloadParameterBuilder createFromTransactionHash(String transactionHash, String accountPrivateKey, boolean validateDigest) {
        return DirectDownloadParameterBuilder.createFromTransactionHash(transactionHash, accountPrivateKey, validateDigest);
    }

    /**
     * Start creating instance of this class from data hash using DirectDownloadParameterBuilder
     *
     * @param dataHash the data hash to download
     * @return the direct download parameter builder
     */
    public static DirectDownloadParameterBuilder createFromDataHash(String dataHash) {
        return DirectDownloadParameterBuilder.createFromDataHash(dataHash, null);
    }

    /**
     * Start creating instance of this class from data hash and digest using DirectDownloadParameterBuilder
     *
     * @param dataHash the data hash to download
     * @param digest   the digest to verify download
     * @return the direct download parameter builder
     */
    public static DirectDownloadParameterBuilder createFromDataHash(String dataHash, String digest) {
        return DirectDownloadParameterBuilder.createFromDataHash(dataHash, digest);
    }
}
