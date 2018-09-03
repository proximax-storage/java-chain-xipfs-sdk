package io.proximax.model;

import io.proximax.privacy.strategy.PrivacyStrategy;

/**
 * This model class represents the transaction message
 * <br>
 * <br>
 * The following are its fields:
 * <ul>
 *     <li><b>digest</b> - the digest for the root data that refers to the upload instance</li>
 *     <li><b>rootDataHash</b> - the data hash for the root data that refers to the upload instance</li>
 *     <li><b>privacyType</b> - the privacy type from privacy strategy used to encrypt data</li>
 *     <li><b>description</b> - the short description for the upload</li>
 *     <li><b>version</b> - the version of upload</li>
 * </ul>
 * @see PrivacyType
 * @see PrivacyStrategy
 */
public final class ProximaxMessagePayloadModel {
    private final String digest;
    private final String rootDataHash;
    private final int privacyType;
    private final String description;
    private final String version;

    private ProximaxMessagePayloadModel(String digest, String rootDataHash, int privacyType, String description, String version) {
        this.digest = digest;
        this.rootDataHash = rootDataHash;
        this.privacyType = privacyType;
        this.description = description;
        this.version = version;
    }

    /**
     * Get the digest for the root data that refers to the upload instance
     * @return the digest
     */
    public String getDigest() {
        return digest;
    }

    /**
     * Get the data hash for the root data that refers to the upload instance
     * @return the root data hash
     */
    public String getRootDataHash() {
        return rootDataHash;
    }

    /**
     * Get the privacy type from privacy strategy used to encrypt data
     * @return the privacy type
     */
    public int getPrivacyType() {
        return privacyType;
    }

    /**
     * Get the short description for the upload
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the version of upload
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Construct instance of this model
     * @param rootDatahash the data hash for the root data
     * @param digest the digest for the root data
     * @param description the short description for the upload
     * @param privacyType the privacy type
     * @param version the version of upload
     * @return instance of this model
     */
    public static ProximaxMessagePayloadModel create(String rootDatahash, String digest, String description,
                                                     Integer privacyType, String version) {
        return new ProximaxMessagePayloadModel(digest, rootDatahash, privacyType, description, version);
    }

}
