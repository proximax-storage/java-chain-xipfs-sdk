package io.proximax.connection;

import java.util.stream.Stream;

/**
 * The enum to indicate http scheme/ protocol
 */
public enum HttpProtocol {
    HTTP ("http"),
    HTTPS ("https");

    private String protocol;

    HttpProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Get the protocol
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Get the enum value from string
     * @param protocol the protocol
     * @return the enum value
     */
    public static HttpProtocol fromString(String protocol) {
        return Stream.of(values()).filter(val -> val.protocol.equals(protocol)).findFirst().orElse(null);
    }
}
