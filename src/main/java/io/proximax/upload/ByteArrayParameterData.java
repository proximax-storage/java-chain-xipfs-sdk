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
package io.proximax.upload;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a byte array upload
 */
public class ByteArrayParameterData extends AbstractByteStreamParameterData {

    private final byte[] data;

    protected ByteArrayParameterData(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata);

        checkParameter(data != null, "data is required");

        this.data = data;
    }

    /**
     * Get the byte stream
     *
     * @return the byte stream
     */
    @Override
    public InputStream getByteStream() {
        return new ByteArrayInputStream(data);
    }

    /**
     * Get the raw/unencrypted data
     *
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Create instance by providing the byte array
     *
     * @param data the byte array to upload
     * @return the instance of this class
     */
    public static ByteArrayParameterData create(byte[] data) {
        return create(data, null, null, null, null);
    }

    /**
     * Create instance by providing the byte array
     *
     * @param data        the byte array to upload
     * @param description a searchable description attach on the upload
     * @param name        a searchable name attach on the upload
     * @param contentType the content type attach on the upload
     * @param metadata    a searchable key-pair metadata attach on the upload
     * @return the instance of this class
     */
    public static ByteArrayParameterData create(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
        return new ByteArrayParameterData(data, description, name, contentType, metadata);
    }
}
