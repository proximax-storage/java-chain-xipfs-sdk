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
package io.proximax.service;

import io.proximax.connection.ConnectionConfig;
import io.proximax.service.factory.FileRepositoryFactory;
import io.proximax.service.repository.FileRepository;
import io.reactivex.Observable;

import java.io.File;
import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for uploading data/file
 */
public class FileUploadService {

    private final FileRepository fileRepository;

    /**
     * Construct this class
     *
     * @param connectionConfig the connection config
     */
    public FileUploadService(final ConnectionConfig connectionConfig) {
        this.fileRepository = FileRepositoryFactory.createFromConnectionConfig(connectionConfig);
    }

    FileUploadService(final FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Upload byte stream
     *
     * @param byteStream the byte stream
     * @return the IPFS upload response
     */
    public Observable<FileUploadResponse> uploadByteStream(final InputStream byteStream) {
        checkParameter(byteStream != null, "byteStream is required");

        return fileRepository.addByteStream(byteStream)
                .map(dataHash -> new FileUploadResponse(dataHash, System.currentTimeMillis()));
    }

    /**
     * Upload a path
     *
     * @param path the data
     * @return the IPFS upload response
     */
    public Observable<FileUploadResponse> uploadPath(final File path) {
        checkParameter(path != null, "path is required");

        return fileRepository.addPath(path)
                .map(dataHash -> new FileUploadResponse(dataHash, System.currentTimeMillis()));
    }
}
