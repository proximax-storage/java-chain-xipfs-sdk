package io.proximax.service.factory;

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
    private StorageConnection mockStorageConnection;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnNullFileStorageConnection() {
        FileRepositoryFactory.create(null);
    }

    @Test
    public void shouldReturnIpfsClientOnIpfsConnection() {
        final FileRepository result = FileRepositoryFactory.create(mockIpfsConnection);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(IpfsClient.class)));
    }

    @Test
    public void shouldReturnStorageNodeClientOnStorageConnection() {
        final FileRepository result = FileRepositoryFactory.create(mockStorageConnection);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(instanceOf(FileRepository.class)));
    }
}
