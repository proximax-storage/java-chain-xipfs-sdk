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
package io.proximax.integration.bigfile;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.upload.UploadParameter;
import io.proximax.upload.UploadResult;
import io.proximax.upload.Uploader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static io.proximax.integration.TestDataRepository.logAndSaveResult;
import static io.proximax.integration.bigfile.BigFileConstants.TEST_BIG_FILE;
import static io.proximax.integration.bigfile.BigFileConstants.TEST_BIG_FILE_SIZE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Uploader_bigFileIntegrationTest {

    private Uploader unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new Uploader(ConnectionConfig.createWithLocalIpfsConnection(
                new BlockchainNetworkConnection(
                        IntegrationTestConfig.getBlockchainNetworkType(),
                        IntegrationTestConfig.getBlockchainApiHost(),
                        IntegrationTestConfig.getBlockchainApiPort(),
                        IntegrationTestConfig.getBlockchainApiProtocol()),
                new IpfsConnection(
                        IntegrationTestConfig.getIpfsApiHost(),
                        IntegrationTestConfig.getIpfsApiPort())));
    }

    // Switch to storage node
//	@Before
//	public void setUp() {
//		unitUnderTest = new Uploader(ConnectionConfig.createWithStorageConnection(
//				new BlockchainNetworkConnection(
//						IntegrationTestConfig.getBlockchainNetworkType(),
//						IntegrationTestConfig.getBlockchainApiHost(),
//						IntegrationTestConfig.getBlockchainApiPort(),
//						IntegrationTestConfig.getBlockchainApiProtocol()),
//				new StorageConnection(
//						IntegrationTestConfig.getStorageNodeApiHost(),
//						IntegrationTestConfig.getStorageNodeApiPort(),
//						IntegrationTestConfig.getStorageNodeApiProtocol(),
//						IntegrationTestConfig.getStorageNodeApiBearerToken(),
//						IntegrationTestConfig.getStorageNodeApiNemAddress())));
//	}

    @Test
    public void shouldUploadBigFile() throws Exception {
        generateBigFile();

        final UploadParameter param = UploadParameter.createForFileUpload(new File(TEST_BIG_FILE),
                IntegrationTestConfig.getPrivateKey1())
                .build();

        final UploadResult result = unitUnderTest.upload(param);

        assertThat(result, is(notNullValue()));
        assertThat(result.getTransactionHash(), is(notNullValue()));
        assertThat(result.getData().getDataHash(), is(notNullValue()));

        logAndSaveResult(result, getClass().getSimpleName() + ".shouldUploadBigFile");
    }

    private void generateBigFile() throws IOException {
        final RandomAccessFile f = new RandomAccessFile(TEST_BIG_FILE, "rw");
        f.setLength(TEST_BIG_FILE_SIZE);
    }
}
