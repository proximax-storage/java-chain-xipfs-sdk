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
 * This model class is the input parameter of download.
 *
 * @see Downloader#download(DownloadParameter)
 * @see DownloadParameterBuilder
 */
public class DownloadParameter {

    private final String transactionHash;
    private final String accountPrivateKey;
    private final PrivacyStrategy privacyStrategy;
    private final boolean validateDigest;

    DownloadParameter(String transactionHash, String accountPrivateKey, PrivacyStrategy privacyStrategy, boolean validateDigest) {
        this.transactionHash = transactionHash;
        this.accountPrivateKey = accountPrivateKey;
        this.privacyStrategy = privacyStrategy;
        this.validateDigest = validateDigest;
    }

    /**
     * Get the blockchain transaction hash to download
     *
     * @return the blockchain transaction hash
     */
    public String getTransactionHash() {
        return transactionHash;
    }

    /**
     * Get the account the private key of the sender or recipient of the transaction (required for secure messages)
     *
     * @return the account private key
     */
    public String getAccountPrivateKey() {
        return accountPrivateKey;
    }

    /**
     * Get the privacy strategy to decrypt the data
     *
     * @return the privacy strategy
     */
    public PrivacyStrategy getPrivacyStrategy() {
        return privacyStrategy;
    }

    /**
     * Get the flag that indicates if need to verify digest
     *
     * @return the validate digest flag
     */
    public boolean getValidateDigest() {
        return validateDigest;
    }

    /**
     * Start creating instance of this class by providing transaction hash
     *
     * @param transactionHash the blockchain transaction hash to download
     * @return the download parameter builder
     */
    public static DownloadParameterBuilder create(String transactionHash) {
        return new DownloadParameterBuilder(transactionHash);
    }
}
