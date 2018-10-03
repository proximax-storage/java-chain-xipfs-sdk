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
import io.proximax.model.BlockchainNetworkType;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class BlockchainNetworkConnectionTest {

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenNullApiHost() {
        new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, null, 3000, HttpProtocol.HTTP);
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenNullNetworkType() {
        new BlockchainNetworkConnection(null, "127.0.0.1", 3000, HttpProtocol.HTTP);
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenPortIsNegativeInt() {
        new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, "127.0.0.1", -3000, HttpProtocol.HTTP);
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenNullHttpProtocol() {
        new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, "127.0.0.1", 3000, null);
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenRestApiUrlCannotBeBuilt() {
        new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, "ASdasdas\\", 3000, HttpProtocol.HTTP);
    }

    @Test
    public void shouldBuildBlockchainNetworkConnection() {
        final BlockchainNetworkConnection result =
                new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, "127.0.0.1", 3000, HttpProtocol.HTTP);

        assertThat(result, is(notNullValue()));
        assertThat(result.getNetworkType(), is(BlockchainNetworkType.MIJIN_TEST.networkType));
        assertThat(result.getApiHost(), is("127.0.0.1"));
        assertThat(result.getApiPort(), is(3000));
        assertThat(result.getApiProtocol(), is(HttpProtocol.HTTP));
        assertThat(result.getApiUrl(), is("http://127.0.0.1:3000"));
    }

}
