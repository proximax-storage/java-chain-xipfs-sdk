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
package io.proximax.service.repository;

import io.reactivex.Observable;

import java.io.File;
import java.io.InputStream;

/**
 * This interface defines the methods that a file repository must implement
 */
public interface FileRepository {

    /**
     * Add/Upload a file (represented as byte stream) to storage node
     *
     * @param byteStream the byte stream to upload
     * @return the hash (base58) for the data uploaded
     */
    Observable<String> addByteStream(InputStream byteStream);

    /**
     * Add/Upload a path
     *
     * @param path the path being added
     * @return the hash (base58) for the data uploaded
     */
    Observable<String> addPath(File path);

    /**
     * Retrieves the file stream given a hash
     *
     * @param dataHash the hash (base58) of an IPFS file
     * @return the file (represented as byte stream)
     */
    Observable<InputStream> getByteStream(String dataHash);
}
