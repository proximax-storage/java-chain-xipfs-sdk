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
package io.proximax.service.factory;

import io.proximax.connection.ConnectionConfig;
import io.proximax.service.repository.FileRepository;
import io.proximax.service.client.IpfsClient;
import io.proximax.service.client.StorageNodeClient;

/**
 * The factory class to create the file storage client based on connection config
 */
public class FileRepositoryFactory {

    private FileRepositoryFactory() {

    }

    /**
     * Create the file storage client based on connection config
     *
     * @param connectionConfig the connection config
     * @return the file storage client created
     */
    public static FileRepository createFromConnectionConfig(ConnectionConfig connectionConfig) {
        if (connectionConfig.getIpfsConnection() != null) {
            return new IpfsClient(connectionConfig.getIpfsConnection());
        } else {
            return new StorageNodeClient(connectionConfig.getStorageConnection());
        }
    }
}
