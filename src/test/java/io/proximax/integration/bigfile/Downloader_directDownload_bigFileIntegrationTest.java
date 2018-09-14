package io.proximax.integration.bigfile;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.testsupport.IntegrationTestProperties;
import io.proximax.testsupport.TestDataRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static io.proximax.integration.bigfile.BigFileConstants.TEST_BIG_FILE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_directDownload_bigFileIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST,
						IntegrationTestProperties.getBlockchainRestUrl()),
				new IpfsConnection(IntegrationTestProperties.getIpfsMultiAddress())));
	}

	@Test
	public void shouldDownloadBigFileByTransactionHash() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_bigFileIntegrationTest.shouldUploadBigFile", "transactionHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromTransactionHash(transactionHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(IOUtils.contentEquals(result, new FileInputStream(new File(TEST_BIG_FILE))), is(true));
	}

	@Test
	public void shouldDownloadBigFileByDataHash() throws IOException {
		final String dataHash = TestDataRepository
				.getData("Uploader_bigFileIntegrationTest.shouldUploadBigFile", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(IOUtils.contentEquals(result, new FileInputStream(new File(TEST_BIG_FILE))), is(true));
	}

}
