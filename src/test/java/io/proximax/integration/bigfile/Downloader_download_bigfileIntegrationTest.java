package io.proximax.integration.bigfile;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.download.Downloader;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.testsupport.TestHelper;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static io.proximax.integration.bigfile.BigFileConstants.BIG_FILE;
import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_download_bigfileIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldDownloadBigFileByTransactionHash() throws IOException {
		final String transactionHash = TestHelper.getData("Uploader_bigFileIntegrationTest.shouldUploadBigFile", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getTransactionHash(), is(transactionHash));
		assertThat(result.getData().getByteStream(), is(notNullValue()));
		assertThat(IOUtils.contentEquals(result.getData().getByteStream(), new FileInputStream(new File(BIG_FILE))), is(true));
	}
}
