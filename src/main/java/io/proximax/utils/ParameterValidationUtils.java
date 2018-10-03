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
package io.proximax.utils;

import java.util.function.Supplier;

/**
 * The utility class to verify method parameters
 */
public class ParameterValidationUtils {

    private ParameterValidationUtils() {
    }

    /**
     * Validates a given parameter by accepting a resolved condition.
     * <br>
     * <br>
     * This throws an IllegalArgumentException with the provided message if condition not valid.
     *
     * @param isValid        a resolved validation result for a given parameter
     * @param invalidMessage the exception message to use when throwing exception
     */
    public static void checkParameter(boolean isValid, String invalidMessage) {
        if (!isValid) throw new IllegalArgumentException(invalidMessage);
    }

    /**
     * Validates a given parameter by accepting a non-resolved condition.
     * <br>
     * <br>
     * This throws an IllegalArgumentException with the provided message if condition is not valid and has exception
     *
     * @param isValidSupplier a resolved validation result for a given parameter
     * @param invalidMessage  the exception message to use when throwing exception
     */
    public static void checkParameter(Supplier<Boolean> isValidSupplier, String invalidMessage) {
        try {
            if (!isValidSupplier.get()) {
                throw new IllegalArgumentException(invalidMessage);
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException(invalidMessage, ex);
        }
    }
}
