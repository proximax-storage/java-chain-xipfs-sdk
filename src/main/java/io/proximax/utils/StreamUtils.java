/*
 *
 *  * Copyright 2018 ProximaX Limited
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package io.proximax.utils;

import org.apache.tika.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class StreamUtils {

    private StreamUtils() {}

    public static String toString(InputStream stream, String encoding) {
        try {
            return IOUtils.toString(stream, encoding);
        } catch(Exception e) {
            throw new RuntimeException("Failed to convert to string", e);
        }
    }

    public static byte[] toByteArray(InputStream stream) {
        try {
            return IOUtils.toByteArray(stream);
        } catch(Exception e) {
            throw new RuntimeException("Failed to convert to byte array", e);
        }
    }

    public static void saveToFile(InputStream stream, File file) {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(stream, outputStream);
        } catch(Exception e) {
            throw new RuntimeException("Failed to save to file", e);
        }
    }
}
