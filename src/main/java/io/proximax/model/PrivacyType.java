package io.proximax.model;

import io.proximax.privacy.strategy.CustomPrivacyStrategy;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy;

/**
 * Enumerates the different privacy types
 * <br>
 * <br>
 * This type is attached on the upload to optimize searching and decrypting transactions.
 * @see io.proximax.privacy.strategy.PrivacyStrategy
 */
public enum PrivacyType {

    /**
     * The type for plain privacy strategy
     * @see PlainPrivacyStrategy
     */
    PLAIN(1001),
    /**
     * The type for secured with nem keys privacy strategy
     * @see SecuredWithNemKeysPrivacyStrategy
     */
    NEMKEYS(1002),
    /**
     * The type for secured with shamir secret sharing privacy strategy
     * @see SecuredWithShamirSecretSharingPrivacyStrategy
     */
    SHAMIR(1003),
    /**
     * The type for secured with password privacy strategy
     * @see SecuredWithPasswordPrivacyStrategy
     */
    PASSWORD(1004),
    /**
     * The type for custom privacy strategy
     * @see CustomPrivacyStrategy
     */
    CUSTOM(2001);

    private final int value;

    private PrivacyType(int value) {
        this.value = value;
    }

    /**
     * Get the int value of the privacy type
     * @return the privacy type's int value
     */
    public int getValue() {
        return value;
    }
}
