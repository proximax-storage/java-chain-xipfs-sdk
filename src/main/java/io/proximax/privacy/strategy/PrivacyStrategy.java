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

import java.io.InputStream;

/**
 * The abstract class privacy strategy
 * <br>
 * <br>
 * Privacy strategy handles the encrypting and decrypting of data
 * <br>
 * <br>
 * When creating a custom Privacy Strategy, implement CustomPrivacyStrategy
 *
 * @see CustomPrivacyStrategy
 */
public abstract class PrivacyStrategy {

    /**
     * Get the privacy type's int value
     *
     * @return the privacy type's int value
     */
    public abstract int getPrivacyType();

    /**
     * Encrypt byte stream
     *
     * @param byteStream the byte stream to encrypt
     * @return encrypted byte stream
     */
    public abstract InputStream encryptStream(final InputStream byteStream);

    /**
     * Encrypt byte stream
     *
     * @param byteStream the byte stream to decrypt
     * @return the decrypted data
     */
    public abstract InputStream decryptStream(final InputStream byteStream);
}
