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

import java.io.InputStream;

/**
 * The plain privacy strategy.
 * <br>
 * <br>
 * This strategy does not encrypt nor decrypt the data.
 */
public final class PlainPrivacyStrategy extends PrivacyStrategy {

    private PlainPrivacyStrategy() {

    }

    /**
     * Get the privacy type which is set as PLAIN
     *
     * @return the privacy type's int value
     * @see PrivacyType
     */
    @Override
    public int getPrivacyType() {
        return PrivacyType.PLAIN.getValue();
    }

    /**
     * Return same byte stream
     *
     * @param byteStream the byte stream to encrypt
     * @return same byte stream
     */
    @Override
    public final InputStream encryptStream(final InputStream byteStream) {
        return byteStream;
    }

    /**
     * Return same byte stream
     *
     * @param byteStream the byte stream to decrypt
     * @return same byte stream
     */
    @Override
    public final InputStream decryptStream(final InputStream byteStream) {
        return byteStream;
    }

    /**
     * Create instance of this strategy
     *
     * @return the instance of this strategy
     */
    public static PlainPrivacyStrategy create() {
        return new PlainPrivacyStrategy();
    }
}
