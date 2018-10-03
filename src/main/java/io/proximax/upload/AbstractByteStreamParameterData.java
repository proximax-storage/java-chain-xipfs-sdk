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

import java.io.InputStream;
import java.util.Map;

import static io.proximax.model.Constants.RESERVED_CONTENT_TYPES;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a byte stream upload
 */
public abstract class AbstractByteStreamParameterData extends UploadParameterData {

    protected AbstractByteStreamParameterData(String description, String name, String contentType, Map<String, String> metadata) {
        super(description, name, contentType, metadata);

        checkParameter(contentType == null || !RESERVED_CONTENT_TYPES.contains(contentType),
                String.format("%s cannot be used as it is reserved", contentType));
    }

    /**
     * Get the byte stream
     *
     * @return the byte stream
     */
    public abstract InputStream getByteStream();
}
