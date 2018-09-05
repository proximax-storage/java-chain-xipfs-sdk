package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.download.Downloader;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.testsupport.TestHelper;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.STRING_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_download_digestIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldVerifyDownloadWithEnabledValidateDigest() {
		final String transactionHash = TestHelper.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.validateDigest(true)
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(result.getData().getData()), is(STRING_TEST));
	}

	@Test
	public void shouldNotVerifyDownloadWithDisabledValidateDigest() {
		final String transactionHash = TestHelper.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithDisabledComputeDigest", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.validateDigest(false)
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(result.getData().getData()), is(STRING_TEST));
	}
}
