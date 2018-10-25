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
            public InputStream encryptStream(InputStream stream) {
                return null;
            }

            @Override
            public InputStream decryptStream(InputStream encryptedStream) {
                return null;
            }
        };

        assertThat(result.getPrivacyType(), is(PrivacyType.CUSTOM.getValue()));
    }
}
