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

import io.proximax.service.client.StorageNodeClient;
import io.proximax.service.client.StorageNodeClient.NodeInfoResponseBlockchainNetwork;

/**
 * The config class that sdk used to connect to work on upload
 * <br>
 * <br>
 * This config details the blockchain network connection and ipfs connection
 */
public class ConnectionConfig {

    private final BlockchainNetworkConnection blockchainNetworkConnection;
    private final IpfsConnection ipfsConnection;
    private final StorageConnection storageConnection;

    private ConnectionConfig(BlockchainNetworkConnection blockchainNetworkConnection,
                             IpfsConnection ipfsConnection) {
        this.blockchainNetworkConnection = blockchainNetworkConnection;
        this.ipfsConnection = ipfsConnection;
        this.storageConnection = null;
    }

    private ConnectionConfig(BlockchainNetworkConnection blockchainNetworkConnection, StorageConnection storageConnection) {
        this.blockchainNetworkConnection = blockchainNetworkConnection;
        this.storageConnection = storageConnection;
        this.ipfsConnection = null;
    }

    /**
     * Get the connection to blockchain network
     *
     * @return the blockchain network connection
     */
    public BlockchainNetworkConnection getBlockchainNetworkConnection() {
        return blockchainNetworkConnection;
    }

    /**
     * Get the connection to IPFS
     *
     * @return the IPFS connection
     */
    public IpfsConnection getIpfsConnection() {
        return ipfsConnection;
    }

    /**
     * Get the connection to storage node
     *
     * @return the connection to storage node
     */
    public StorageConnection getStorageConnection() {
        return storageConnection;
    }

    /**
     * Create connection config with local IPFS connection
     *
     * @param blockchainNetworkConnection the blockchain network connection
     * @param ipfsConnection              the IPFS connection
     * @return instance of this connection config
     */
    public static ConnectionConfig createWithLocalIpfsConnection(BlockchainNetworkConnection blockchainNetworkConnection, IpfsConnection ipfsConnection) {
        return new ConnectionConfig(blockchainNetworkConnection, ipfsConnection);
    }

    /**
     * Create connection config with storage connection
     *
     * @param storageConnection the storage node connection
     * @return instance of this connection config
     */
    public static ConnectionConfig createWithStorageConnection(StorageConnection storageConnection) {
        final StorageNodeClient storageNodeClient = new StorageNodeClient(storageConnection);
        final NodeInfoResponseBlockchainNetwork blockchainNetwork =
                storageNodeClient.getNodeInfo().blockingFirst().getBlockchainNetwork();
        final BlockchainNetworkConnection blockchainNetworkConnection = new BlockchainNetworkConnection(
                blockchainNetwork.getNetworkType(), blockchainNetwork.getHost(),
                blockchainNetwork.getPort(), blockchainNetwork.getProtocol());
        return new ConnectionConfig(blockchainNetworkConnection, storageConnection);
    }

    /**
     * Create connection config with storage connection
     *
     * @param blockchainNetworkConnection the blockchain network connection
     * @param storageConnection           the storage node connection
     * @return instance of this connection config
     */
    public static ConnectionConfig createWithStorageConnection(BlockchainNetworkConnection blockchainNetworkConnection, StorageConnection storageConnection) {
        return new ConnectionConfig(blockchainNetworkConnection, storageConnection);
    }
}
