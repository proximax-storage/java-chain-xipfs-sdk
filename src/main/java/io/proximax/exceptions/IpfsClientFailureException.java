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
package io.proximax.exceptions;

/**
 * The exception when an IPFS action failed
 */
public class IpfsClientFailureException extends RuntimeException {

    /**
     * Create instance of this exception
     *
     * @param message the exception message
     */
    public IpfsClientFailureException(String message) {
        super(message);
    }

    /**
     * Create instance of this exception
     *
     * @param message the exception message
     * @param cause   the cause of this exception
     */
    public IpfsClientFailureException(String message, Throwable cause) {
        super(message, cause);
    }

}
