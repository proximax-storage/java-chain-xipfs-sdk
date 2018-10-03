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
package io.proximax.service.factory;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.connection.StorageConnection;
import io.proximax.service.client.IpfsClient;
import io.proximax.service.repository.FileRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class FileRepositoryFactoryTest {

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
        final FileRepository result = FileRepositoryFactory.createFromConnectionConfig(
                ConnectionConfig.createWithLocalIpfsConnection(
                        mockBlockchainNetworkConnection, mockIpfsConnection
                )
        );

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(IpfsClient.class)));

    }

    @Test
    public void shouldReturnStorageNodeClientOnStorageConnection() {
        final FileRepository result = FileRepositoryFactory.createFromConnectionConfig(
                ConnectionConfig.createWithStorageConnection(
                        mockBlockchainNetworkConnection, mockStorageConnection
                )
        );

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(FileRepository.class)));

    }
}
