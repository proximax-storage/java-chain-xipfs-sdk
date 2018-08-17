package io.proximax.privacy.strategy;

import io.nem.sdk.model.transaction.Message;
import org.junit.Test;

public class PrivacyStrategyTest {

    @Test
    public void shouldAllowNullSearchTag() {
        new PrivacyStrategy(null) {
            @Override
            public int getPrivacyType() {
                return 0;
            }

            @Override
            public byte[] encryptData(byte[] data) {
                return new byte[0];
            }

            @Override
            public byte[] decryptData(byte[] data) {
                return new byte[0];
            }

            @Override
            public Message encodePayloadAsMessage(String payload) {
                return null;
            }

            @Override
            public String decodePayloadFromMessage(Message message) {
                return null;
            }
        };
    }

    @Test
    public void shouldAllowSearchTagWithinLimit() {
        new PrivacyStrategy("this is a very long search tag") {
            @Override
            public int getPrivacyType() {
                return 0;
            }

            @Override
            public byte[] encryptData(byte[] data) {
                return new byte[0];
            }

            @Override
            public byte[] decryptData(byte[] data) {
                return new byte[0];
            }

            @Override
            public Message encodePayloadAsMessage(String payload) {
                return null;
            }

            @Override
            public String decodePayloadFromMessage(Message message) {
                return null;
            }
        };
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNoAllowSearchTagExceedingLimit() {
        new PrivacyStrategy("this is a very long search tag that exceeds limit by several characters") {
            @Override
            public int getPrivacyType() {
                return 0;
            }

            @Override
            public byte[] encryptData(byte[] data) {
                return new byte[0];
            }

            @Override
            public byte[] decryptData(byte[] data) {
                return new byte[0];
            }

            @Override
            public Message encodePayloadAsMessage(String payload) {
                return null;
            }

            @Override
            public String decodePayloadFromMessage(Message message) {
                return null;
            }
        };
    }
}
