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
package io.proximax.model;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * This class specifies the constants used by this SDK
 */
public class Constants {

    /**
     * The upload schema version
     */
    public static final String SCHEMA_VERSION = "1.0";

    /**
     * The content type to indicate it is a path upload
     */
    public static final String PATH_UPLOAD_CONTENT_TYPE = "ipfs/directory";

    /**
     * The reserved content types that cannot be specified as content type when uploading
     */
    public static final List<String> RESERVED_CONTENT_TYPES = asList(PATH_UPLOAD_CONTENT_TYPE);

    private Constants() {
    }
}
