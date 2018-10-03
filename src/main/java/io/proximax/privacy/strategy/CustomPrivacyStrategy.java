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
package io.proximax.privacy.strategy;

import io.proximax.model.PrivacyType;

/**
 * The abstract class to be used when creating custom privacy strategy
 * <br>
 * <br>
 * This fixes the privacy type as CUSTOM
 *
 * @see PrivacyType
 */
public abstract class CustomPrivacyStrategy extends PrivacyStrategy {

    /**
     * Get the privacy type which is set as CUSTOM
     *
     * @return the privacy type's int value
     * @see PrivacyType
     */
    @Override
    public int getPrivacyType() {
        return PrivacyType.CUSTOM.getValue();
    }
}
