/*
 *
 *  * Copyright 2018 ProximaX Limited
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package io.proximax.service;

import io.proximax.connection.FileStorageConnection;
import io.proximax.privacy.strategy.PlainPrivacyStrategy;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.service.factory.FileRepositoryFactory;
import io.proximax.service.repository.FileRepository;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for retrieving data
 */
public class FileDownloadService {

    private final FileRepository fileRepository;

    /**
     * Construct this class
     *
     * @param fileStorageConnection the connection to file storage
     */
    public FileDownloadService(FileStorageConnection fileStorageConnection) {
        this.fileRepository = FileRepositoryFactory.create(fileStorageConnection);
    }

    FileDownloadService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Retrieve byte stream
     *
     * @param dataHash        the data hash of the target download
     * @param privacyStrategy the privacy strategy to decrypt the data
     * @param digest          the digest of the target download
     * @return the byte stream
     */
    public Observable<InputStream> getByteStream(String dataHash, PrivacyStrategy privacyStrategy, String digest) {
        checkParameter(dataHash != null, "dataHash is required");

        final PrivacyStrategy privacyStrategyToUse = privacyStrategy == null ? PlainPrivacyStrategy.create() : privacyStrategy;

        validateDigest(digest, dataHash);
        return fileRepository.getByteStream(dataHash).map(privacyStrategyToUse::decryptStream);
    }

    private void validateDigest(String digest, String dataHash) {
        if (StringUtils.isNotEmpty(digest)) {
            fileRepository.getByteStream(dataHash)
                    .map(undecryptedStream -> DigestUtils.validateDigest(undecryptedStream, digest))
                    .blockingFirst();
        }
    }
}
