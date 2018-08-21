package io.proximax.privacy.strategy;

import io.proximax.model.PrivacyType;

/**
 * The abstract class to be used when creating custom privacy strategy
 * <br>
 * <br>
 * This fixes the privacy type as CUSTOM
 * @see PrivacyType
 */
public abstract class CustomPrivacyStrategy extends AbstractPlainMessagePrivacyStrategy {

    /**
     * Create instance by providing a privacy search tag
     * @param privacySearchTag the privacy search tag
     */
    public CustomPrivacyStrategy(String privacySearchTag) {
        super(privacySearchTag);
    }

    /**
     * Get the privacy type which is set as CUSTOM
     * @return the privacy type's int value
     * @see PrivacyType
     */
    @Override
    public int getPrivacyType() {
        return PrivacyType.CUSTOM.getValue();
    }
}
