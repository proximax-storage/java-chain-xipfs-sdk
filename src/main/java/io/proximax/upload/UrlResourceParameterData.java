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

import io.proximax.exceptions.GetByteStreamFailureException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a URL resource upload
 */
public class UrlResourceParameterData extends AbstractByteStreamParameterData {

    private final URL url;

    private UrlResourceParameterData(URL url, String description, String name, String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata);

        checkParameter(url != null, "url is required");

        this.url = url;
    }

    /**
     * Get the byte stream
     *
     * @return the byte stream
     */
    @Override
    public InputStream getByteStream() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new GetByteStreamFailureException("Failed to open byte stream", e);
        }
    }

    /**
     * Get the URL resource to upload
     *
     * @return the URL resource
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Create instance by providing the url
     *
     * @param url the URL resource to upload
     * @return the instance of this class
     */
    public static UrlResourceParameterData create(URL url) {
        return create(url, null, null, null, null);
    }

    /**
     * Create instance by providing the file
     *
     * @param url         the URL resource to upload
     * @param description a searchable description attach on the upload
     * @param name        a searchable name attach on the upload
     * @param contentType the content type attach on the upload
     * @param metadata    a searchable key-pair metadata attach on the upload
     * @return the instance of this class
     */
    public static UrlResourceParameterData create(URL url, String description, String name, String contentType, Map<String, String> metadata) {
        return new UrlResourceParameterData(url, description, name, contentType, metadata);
    }
}
