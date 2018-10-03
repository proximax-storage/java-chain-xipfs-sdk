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
    @Ignore("this test connects to actual ipfs daemon - should be covered already by integreation tests")
    public void shouldBuildIpfsConnection() {
        final IpfsConnection result = new IpfsConnection("127.0.0.1", 5001);

        assertThat(result, is(notNullValue()));
        assertThat(result.getIpfs(), is(notNullValue()));
        assertThat(result.getApiHost(), is("127.0.0.1"));
        assertThat(result.getApiPort(), is(5001));
    }

}
