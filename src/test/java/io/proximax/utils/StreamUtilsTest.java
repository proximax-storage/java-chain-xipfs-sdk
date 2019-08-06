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

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;

public class StreamUtilsTest {

    @Test
    public void shouldConvertToString() {
        final String result = StreamUtils.toString(new ByteArrayInputStream("hello there, old friend".getBytes()), null);

        assertThat(result, is("hello there, old friend"));
    }

    @Test
    public void shouldConvertByteArray() {
        final byte[] result = StreamUtils.toByteArray(new ByteArrayInputStream("hello there, old friend".getBytes()));

        assertThat(ArrayUtils.toObject(result), is(arrayContaining(ArrayUtils.toObject("hello there, old friend".getBytes()))));
    }

    @Test
    public void shouldSaveToFile() throws IOException {
        final File tempFile = File.createTempFile("tmp" + System.currentTimeMillis(), "tmp");

        StreamUtils.saveToFile(new ByteArrayInputStream("hello there, old friend".getBytes()), tempFile);
    }
}
