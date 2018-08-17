package io.proximax.download;

import io.proximax.privacy.strategy.PrivacyStrategy;

/**
 * This model class is the input parameter when doing download data.
 * <br>
 * <br>
 * The following are its fields:
 * <ul>
 *     <li><b>dataHash</b> - the data hash to download</li>
 *     <li><b>digest</b> - the digest to verify the downloaded data</li>
 *     <li><b>privacyStrategy</b> - the privacy strategy to decrypt the data</li>
 * </ul>
 * @see Download#downloadData(DownloadDataParameter)
 * @see DownloadDataParameterBuilder
 */
public class DownloadDataParameter {

    private final String dataHash;
    private final PrivacyStrategy privacyStrategy;
    private final String digest;

    DownloadDataParameter(String dataHash, PrivacyStrategy privacyStrategy, String digest) {
        this.dataHash = dataHash;
        this.privacyStrategy = privacyStrategy;
        this.digest = digest;
    }

    /**
     * Get the data hash to download
     * @return the data hash
     */
    public String getDataHash() {
        return dataHash;
    }

    /**
     * Get the digest to verify the downloaded data
     * @return the digest
     */
    public PrivacyStrategy getPrivacyStrategy() {
        return privacyStrategy;
    }

    /**
     * Get the privacy strategy to decrypt the data
     * @return the privacy strategy
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Start creating instance of this class using DownloadDataParameterBuilder
     * @param dataHash the data hash to download
     * @return the download data parameter builder
     */
    public static DownloadDataParameterBuilder create(String dataHash) {
        return new DownloadDataParameterBuilder(dataHash);
    }
}
