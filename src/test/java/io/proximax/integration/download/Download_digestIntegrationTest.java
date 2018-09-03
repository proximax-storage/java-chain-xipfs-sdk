package io.proximax.integration.download;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.connection.IpfsConnection;
import io.proximax.download.Download;
import io.proximax.download.DownloadParameter;
import io.proximax.download.DownloadResult;
import io.proximax.exceptions.DownloadFailureException;
import io.proximax.model.BlockchainNetwork;
import io.proximax.testsupport.TestHelper;
import org.junit.Before;
import org.junit.Test;

import static io.proximax.testsupport.Constants.BLOCKCHAIN_ENDPOINT_URL;
import static io.proximax.testsupport.Constants.IPFS_MULTI_ADDRESS;
import static io.proximax.testsupport.Constants.STRING_TEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Download_digestIntegrationTest {

	private Download unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Download(ConnectionConfig.create(
				new BlockchainNetworkConnection(BlockchainNetwork.MIJIN_TEST, BLOCKCHAIN_ENDPOINT_URL),
				new IpfsConnection(IPFS_MULTI_ADDRESS)));
	}

	@Test
	public void shouldDownloadAnUploadWithDigestUsingTransactionHashOnly() {
		final String transactionHash = TestHelper.getData("Upload_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(new String(result.getDataList().get(0).getData()), is(STRING_TEST));
	}

	@Test
	public void shouldDownloadAnUploadWithoutDigestUsingTransactionHashOnly() {
		final String transactionHash = TestHelper.getData("Upload_computeDigestIntegrationTest.shouldUploadWithDisabledComputeDigest", "transactionHash");
		final DownloadParameter param =
				DownloadParameter.createWithTransactionHash(transactionHash).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(new String(result.getDataList().get(0).getData()), is(STRING_TEST));
	}

	@Test
	public void shouldDownloadAnUploadWithDigestUsingRootDataHashOnly() {
		final String rootDataHash = TestHelper.getData("Upload_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "rootDataHash");
		final DownloadParameter param =
				DownloadParameter.createWithRootDataHash(rootDataHash, null).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(new String(result.getDataList().get(0).getData()), is(STRING_TEST));
	}

	@Test
	public void shouldDownloadAnUploadWithDigestUsingRootDataHashAndDigest() {
		final String rootDataHash = TestHelper.getData("Upload_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "rootDataHash");
		final String digest = TestHelper.getData("Upload_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "digest");
		final DownloadParameter param =
				DownloadParameter.createWithRootDataHash(rootDataHash, digest).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(new String(result.getDataList().get(0).getData()), is(STRING_TEST));
	}

	@Test(expected = DownloadFailureException.class)
	public void failDownloadOfAnUploadWithDigestUsingRootDataHashButDigestsDoesNotMatch() {
		final String rootDataHash = TestHelper.getData("Upload_computeDigestIntegrationTest.shouldUploadWithEnabledComputeDigest", "rootDataHash");
		final DownloadParameter param =
				DownloadParameter.createWithRootDataHash(rootDataHash, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();

		unitUnderTest.download(param);
	}

	@Test
	public void shouldDownloadAnUploadWithoutDigestUsingRootDataHashOnly() {
		final String rootDataHash = TestHelper.getData("Upload_computeDigestIntegrationTest.shouldUploadWithDisabledComputeDigest", "rootDataHash");
		final DownloadParameter param =
				DownloadParameter.createWithRootDataHash(rootDataHash, null).build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(new String(result.getDataList().get(0).getData()), is(STRING_TEST));
	}

	@Test(expected = DownloadFailureException.class)
	public void shouldDownloadAnUploadWithoutDigestUsingRootDataHashButDigestDoesNotMatch() {
		final String rootDataHash = TestHelper.getData("Upload_computeDigestIntegrationTest.shouldUploadWithDisabledComputeDigest", "rootDataHash");
		final DownloadParameter param =
				DownloadParameter.createWithRootDataHash(rootDataHash, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();

		final DownloadResult result = unitUnderTest.download(param);

		assertThat(result, is(notNullValue()));
		assertThat(result.getDataList(), hasSize(1));
		assertThat(new String(result.getDataList().get(0).getData()), is(STRING_TEST));
	}


}
