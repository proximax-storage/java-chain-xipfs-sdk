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
package io.proximax.integration.storageconnection;

import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.StorageConnection;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.utils.JsonUtils;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ConnectionConfigIntegrationTest {

    @Test
    public void shouldInitializeBlockchainNetworkConnectionWithJustStorageConnection() {
        final ConnectionConfig connectionConfig = ConnectionConfig.createWithStorageConnection(
                new StorageConnection(
                        IntegrationTestConfig.getStorageNodeApiHost(),
                        IntegrationTestConfig.getStorageNodeApiPort(),
                        IntegrationTestConfig.getStorageNodeApiProtocol(),
                        IntegrationTestConfig.getStorageNodeApiBearerToken(),
                        IntegrationTestConfig.getStorageNodeApiNemAddress()));

        assertThat(connectionConfig, is(notNullValue()));
        assertThat(connectionConfig.getBlockchainNetworkConnection(), is(notNullValue()));
        assertThat(connectionConfig.getBlockchainNetworkConnection().getApiProtocol(), is(notNullValue()));
        assertThat(connectionConfig.getBlockchainNetworkConnection().getApiPort(), is(notNullValue()));
        assertThat(connectionConfig.getBlockchainNetworkConnection().getApiHost(), is(notNullValue()));
        assertThat(connectionConfig.getBlockchainNetworkConnection().getNetworkType(), is(notNullValue()));
        assertThat(connectionConfig.getBlockchainNetworkConnection().getApiUrl(), is(notNullValue()));

        System.out.println(JsonUtils.toJson(connectionConfig.getBlockchainNetworkConnection()));
    }

}
