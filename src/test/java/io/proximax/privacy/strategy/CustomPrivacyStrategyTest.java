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
package io.proximax.privacy.strategy;

import io.proximax.model.PrivacyType;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CustomPrivacyStrategyTest {

    @Test
    public void shouldHaveCustomPrivacyType() {
        final CustomPrivacyStrategy result = new CustomPrivacyStrategy() {

            @Override
            public InputStream encryptStream(InputStream byteStream) {
                return null;
            }

            @Override
            public InputStream decryptStream(InputStream byteStream) {
                return null;
            }
        };

        assertThat(result.getPrivacyType(), is(PrivacyType.CUSTOM.getValue()));
    }
}
