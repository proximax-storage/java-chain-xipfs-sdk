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
