package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.DirectDownloadParameter;
import io.proximax.download.Downloader;
import io.proximax.model.BlockchainNetworkType;
import io.proximax.testsupport.TestHelper;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.STRING_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Downloader_directDownload_digestIntegrationTest {

	private Downloader unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Downloader(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetworkType.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldVerifyDirectDownloadUsingTransactionHashWithEnabledValidateDigest() throws IOException {
		final String transactionHash = TestHelper.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash, true).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result)), is(STRING_TEST));
	}

	@Test
	public void shouldNotVerifyDirectDownloadUsingTransactionHashWithDisabledValidateDigest() throws IOException {
		final String transactionHash = TestHelper.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "transactionHash");
		final DirectDownloadParameter param =
				DirectDownloadParameter.createFromTransactionHash(transactionHash, false).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result)), is(STRING_TEST));
	}

	@Test
	public void shouldVerifyDirectDownloadUsingDataHashAndDigest() throws IOException {
		final String dataHash = TestHelper.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "dataHash");
		final String digest = TestHelper.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "digest");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash, digest).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result)), is(STRING_TEST));
	}

	@Test
	public void shouldNotVerifyDirectDownloadUsingDataHashOnly() throws IOException {
		final String dataHash = TestHelper.getData("Uploader_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "dataHash");
		final DirectDownloadParameter param = DirectDownloadParameter.createFromDataHash(dataHash).build();

		final InputStream result = unitUnderTest.directDownload(param);

		assertThat(result, is(notNullValue()));
		assertThat(new String(IOUtils.toByteArray(result)), is(STRING_TEST));
	}
}
