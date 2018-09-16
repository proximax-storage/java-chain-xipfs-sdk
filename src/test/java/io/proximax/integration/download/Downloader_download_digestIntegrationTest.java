package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.download.Downloader;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.integration.TestDataRepository;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.proximax.testsupport.Constants.TEST_STRING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_download_digestIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST,
						IntegrationTestConfig.getBlockchainRestUrl()),
				new IpfsConnection(IntegrationTestConfig.getIpfsMultiAddress())));
	}

	@Test
	public void shouldVerifyDownloadWithEnabledValidateDigest() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.withValidateDigest(true)
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result.getData().getByteStream())), is(TEST_STRING));
	}

	@Test
	public void shouldNotVerifyDownloadWithDisabledValidateDigest() throws IOException {
		final String transactionHash = TestDataRepository
				.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithDisabledComputeDigest", "transactionHash");
		final DownloadParameter param = DownloadParameter.create(transactionHash)
				.withValidateDigest(false)
				.build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result.getData().getByteStream())), is(TEST_STRING));
	}
}
