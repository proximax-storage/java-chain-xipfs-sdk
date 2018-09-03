package io.proximax.privacy.strategy;

import io.proximax.model.PrivacyType;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CustomPrivacyStrategyTest {

    @Test
    public void shouldHaveCustomPrivacyType() {
        final CustomPrivacyStrategy result = new CustomPrivacyStrategy() {
            @Override
            public byte[] encryptData(byte[] data) {
                return new byte[0];
            }

            @Override
            public byte[] decryptData(byte[] data) {
                return new byte[0];
            }
        };

        assertThat(result.getPrivacyType(), is(PrivacyType.CUSTOM.getValue()));
    }
}
