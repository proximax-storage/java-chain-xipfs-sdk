package io.proximax.privacy.strategy;

import io.proximax.model.PrivacyType;

public final class PlainPrivacyStrategy extends AbstractPlainMessagePrivacyStrategy {

    PlainPrivacyStrategy(String searchTag) {
        super(searchTag);
    }

    @Override
    public int getPrivacyType() {
        return PrivacyType.PLAIN.getValue();
    }

    @Override
    public final byte[] encryptData(byte[] data) {
        return data;
    }

    @Override
    public final byte[] decryptData(byte[] data) {
        return data;
    }

    public static PlainPrivacyStrategy create(String searchTag) {
        return new PlainPrivacyStrategy(searchTag);
    }
}
