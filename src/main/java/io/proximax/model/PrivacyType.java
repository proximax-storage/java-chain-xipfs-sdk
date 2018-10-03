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
package io.proximax.model;

import io.proximax.privacy.strategy.CustomPrivacyStrategy;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithNemKeysPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithPasswordPrivacyStrategy;
import io.proximax.privacy.strategy.SecuredWithShamirSecretSharingPrivacyStrategy;

/**
 * Enumerates the different privacy types
 *
 * @see io.proximax.privacy.strategy.PrivacyStrategy
 */
public enum PrivacyType {

    /**
     * The type for plain privacy strategy
     *
     * @see PlainPrivacyStrategy
     */
    PLAIN(1001),
    /**
     * The type for secured with nem keys privacy strategy
     *
     * @see SecuredWithNemKeysPrivacyStrategy
     */
    NEMKEYS(1002),
    /**
     * The type for secured with shamir secret sharing privacy strategy
     *
     * @see SecuredWithShamirSecretSharingPrivacyStrategy
     */
    SHAMIR(1003),
    /**
     * The type for secured with password privacy strategy
     *
     * @see SecuredWithPasswordPrivacyStrategy
     */
    PASSWORD(1004),
    /**
     * The type for custom privacy strategy
     *
     * @see CustomPrivacyStrategy
     */
    CUSTOM(2001);

    private final int value;

    private PrivacyType(int value) {
        this.value = value;
    }

    /**
     * Get the int value of the privacy type
     *
     * @return the privacy type's int value
     */
    public int getValue() {
        return value;
    }
}
