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

import java.util.stream.Stream;

/**
 * The enum to indicate http scheme/ protocol
 */
public enum HttpProtocol {
    HTTP("http"),
    HTTPS("https");

    private String protocol;

    HttpProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Get the protocol
     *
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Get the enum value from string
     *
     * @param protocol the protocol
     * @return the enum value
     */
    public static HttpProtocol fromString(String protocol) {
        return Stream.of(values()).filter(val -> val.protocol.equals(protocol)).findFirst().orElse(null);
    }
}
