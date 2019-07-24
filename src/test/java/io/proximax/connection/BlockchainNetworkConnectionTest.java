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
        new BlockchainNetworkConnection(BlockchainNetworkType.TEST_NET, null, 3000, HttpProtocol.HTTP);
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenNullNetworkType() {
        new BlockchainNetworkConnection(null, "127.0.0.1", 3000, HttpProtocol.HTTP);
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenPortIsNegativeInt() {
        new BlockchainNetworkConnection(BlockchainNetworkType.TEST_NET, "127.0.0.1", -3000, HttpProtocol.HTTP);
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenNullHttpProtocol() {
        new BlockchainNetworkConnection(BlockchainNetworkType.TEST_NET, "127.0.0.1", 3000, null);
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenRestApiUrlCannotBeBuilt() {
        new BlockchainNetworkConnection(BlockchainNetworkType.TEST_NET, "ASdasdas\\", 3000, HttpProtocol.HTTP);
    }

    @Test
    public void shouldBuildBlockchainNetworkConnection() {
        final BlockchainNetworkConnection result =
                new BlockchainNetworkConnection(BlockchainNetworkType.TEST_NET, "127.0.0.1", 3000, HttpProtocol.HTTP);

        assertThat(result, is(notNullValue()));
        assertThat(result.getNetworkType(), is(BlockchainNetworkType.TEST_NET.networkType));
        assertThat(result.getApiHost(), is("127.0.0.1"));
        assertThat(result.getApiPort(), is(3000));
        assertThat(result.getApiProtocol(), is(HttpProtocol.HTTP));
        assertThat(result.getApiUrl(), is("http://127.0.0.1:3000"));
    }

}
