package io.proximax.download;

import io.proximax.privacy.strategy.PrivacyStrategy;

/**
 * This model class is the input parameter when doing download.
 * <br>
 * <br>
 * The following are its fields:
 * <ul>
 *     <li><b>transactionHash</b> - the blockchain transaction hash to download</li>
 *     <li><b>rootDataHash</b> - the data hash to root data to download</li>
 *     <li><b>digest</b> - the digest to verify the downloaded root data</li>
 *     <li><b>privacyStrategy</b> - the privacy strategy to decrypt the data</li>
 * </ul>
 * @see Downloader#download(DownloadParameter)
 * @see DownloadParameterBuilder
 */
public class DownloadParameter {

    private final String transactionHash;
    private final String rootDataHash;
    private final PrivacyStrategy privacyStrategy;
    private final String digest;

    DownloadParameter(String transactionHash, String rootDataHash, PrivacyStrategy privacyStrategy, String digest) {
        this.transactionHash = transactionHash;
        this.rootDataHash = rootDataHash;
        this.privacyStrategy = privacyStrategy;
        this.digest = digest;
    }

    /**
     * Get the blockchain transaction hash
     * @return the blockchain transaction hash
     */
    public String getTransactionHash() {
        return transactionHash;
    }

    /**
     * Get the root data hash
     * @return the root data hash
     */
    public String getRootDataHash() {
        return rootDataHash;
    }

    /**
     * Get the privacy strategy to decrypt the data
     * @return the privacy strategy
     */
    public PrivacyStrategy getPrivacyStrategy() {
        return privacyStrategy;
    }

    /**
     * Get the digest of the root data
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Start creating instance of this class by providing transaction hash
     * @param transactionHash the blockchain transaction hash to download
     * @return the download parameter builder
     */
    public static DownloadParameterBuilder createWithTransactionHash(String transactionHash) {
        return new DownloadParameterBuilder(transactionHash);
    }

    /**
     * Start creating instance of this class by providing the root data hash and an optional digest
     * @param rootDataHash the data hash to root data to download
     * @param digest an optional digest to verify the downloaded root data
     * @return the download parameter builder
     */
    public static DownloadParameterBuilder createWithRootDataHash(String rootDataHash, String digest) {
        return new DownloadParameterBuilder(rootDataHash, digest);
    }
}
