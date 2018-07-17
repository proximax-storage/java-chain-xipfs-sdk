package io.proximax.model;

public final class ProximaxMessagePayloadModel {
    private final String digest;
    private final String rootDataHash;
    private final int privacyType;
    private final String privacySearchTag;
    private final String description;
    private final String version;

    ProximaxMessagePayloadModel(String digest, String rootDataHash, int privacyType, String privacySearchTag,
                                       String description, String version) {
        this.digest = digest;
        this.rootDataHash = rootDataHash;
        this.privacyType = privacyType;
        this.privacySearchTag = privacySearchTag;
        this.description = description;
        this.version = version;
    }

    public String getDigest() {
        return digest;
    }

    public String getRootDataHash() {
        return rootDataHash;
    }

    public Integer getPrivacyType() {
        return privacyType;
    }

    public String getPrivacySearchTag() {
        return privacySearchTag;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public static ProximaxMessagePayloadModel create(String rootDatahash, String digest, String description,
                                                     Integer privacyType, String privacySearchTag, String version) {
        return new ProximaxMessagePayloadModel(digest, rootDatahash, privacyType, privacySearchTag, description, version);
    }

}
