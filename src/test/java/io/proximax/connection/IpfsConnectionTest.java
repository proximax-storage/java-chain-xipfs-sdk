package io.proximax.connection;

import io.proximax.exceptions.ConnectionConfigNotValidException;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class IpfsConnectionTest {

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenNullApiHost() {
        new IpfsConnection(null, 3000);
    }

    @Test(expected = ConnectionConfigNotValidException.class)
    public void failWhenPortIsNegativeInt() {
        new IpfsConnection("127.0.0.1", -3000);
    }

    @Test
    @Ignore("this test connects to actual ipfs daemon - should be covered by integreation tests")
    public void shouldBuildIpfsConnection() {
        final IpfsConnection result = new IpfsConnection("127.0.0.1", 5001);

        assertThat(result, is(notNullValue()));
        assertThat(result.getIpfs(), is(notNullValue()));
        assertThat(result.getApiHost(), is("127.0.0.1"));
        assertThat(result.getApiPort(), is(5001));
    }

}
