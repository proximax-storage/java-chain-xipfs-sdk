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
package io.proximax.connection;

import io.proximax.exceptions.ConnectionConfigNotValidException;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class StorageConnectionTest {

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenNullApiHost() {
        new StorageConnection(null, 3000, HttpProtocol.HTTP, "11111", "ABC");
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenPortIsNegativeInt() {
        new StorageConnection("127.0.0.1", -3000, HttpProtocol.HTTP, "11111", "ABC");
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenNullHttpProtocol() {
        new StorageConnection("127.0.0.1", 3000, null, "11111", "ABC");
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenRestApiUrlCannotBeBuilt() {
        new StorageConnection("ASdasdas\\", 3000, HttpProtocol.HTTP, "11111", "ABC");
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenNullBearerToken() {
        new StorageConnection("127.0.0.1", 3000, HttpProtocol.HTTP, null, "ABC");
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenNullNemAddress() {
        new StorageConnection("127.0.0.1", 3000, HttpProtocol.HTTP, "11111", null);
    }

    @Test
    public void shouldBuildStorageConnection() {
        final StorageConnection result =
                new StorageConnection("127.0.0.1", 3000, HttpProtocol.HTTP, "11111", "ABC");

        assertThat(result, is(notNullValue()));
        assertThat(result.getApiHost(), is("127.0.0.1"));
        assertThat(result.getApiPort(), is(3000));
        assertThat(result.getApiProtocol(), is(HttpProtocol.HTTP));
        assertThat(result.getApiUrl(), is("http://127.0.0.1:3000"));
        assertThat(result.getBearerToken(), is("11111"));
        assertThat(result.getNemAddress(), is("ABC"));
    }

}
