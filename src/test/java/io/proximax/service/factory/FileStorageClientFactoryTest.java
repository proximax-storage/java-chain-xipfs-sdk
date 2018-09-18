package io.proximax.service.factory;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.connection.StorageConnection;
import io.proximax.service.api.FileStorageClientApi;
import io.proximax.service.client.IpfsClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class FileStorageClientFactoryTest {

    @Mock
    private IpfsConnection mockIpfsConnection;

    @Mock
    private BlockchainNetworkConnection mockBlockchainNetworkConnection;

    @Mock
    private StorageConnection mockStorageConnection;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnIpfsClientOnIpfsConnection() {
        final FileStorageClientApi result = FileStorageClientFactory.createFromConnectionConfig(
                ConnectionConfig.createWithLocalIpfsConnection(
                        mockBlockchainNetworkConnection, mockIpfsConnection
                )
        );

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(IpfsClient.class)));

    }

    @Test
    public void shouldReturnStorageNodeClientOnStorageConnection() {
        final FileStorageClientApi result = FileStorageClientFactory.createFromConnectionConfig(
                ConnectionConfig.createWithStorageConnection(
                        mockBlockchainNetworkConnection, mockStorageConnection
                )
        );

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(FileStorageClientApi.class)));

    }
}
