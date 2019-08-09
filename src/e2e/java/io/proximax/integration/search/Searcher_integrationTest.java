package io.proximax.integration.search;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.search.SearchParameter;
import io.proximax.search.SearchResult;
import io.proximax.search.Searcher;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class Searcher_integrationTest {

	private Searcher unitUnderTest;

	@Before
	public void setUp() {
		unitUnderTest = new Searcher(ConnectionConfig.createWithLocalIpfsConnection(
				new BlockchainNetworkConnection(
						IntegrationTestConfig.getBlockchainNetworkType(),
						IntegrationTestConfig.getBlockchainApiHost(),
						IntegrationTestConfig.getBlockchainApiPort(),
						IntegrationTestConfig.getBlockchainApiProtocol()),
				null));
	}

	@Test
	public void shouldSearchWithAccountAddress() {
		final SearchParameter param = SearchParameter.createForAddress(IntegrationTestConfig.getAddress1()).build();

		final SearchResult search = unitUnderTest.search(param);

		assertThat(search, is(notNullValue()));
		assertThat(search.getResults(), is(notNullValue()));
		assertThat(search.getResults().size(), is(10));
		assertThat(search.getToTransactionId(), is(notNullValue()));
		assertThat(search.getFromTransactionId(), is(nullValue()));
	}

	@Test
	public void shouldSearchWithAccountPublicKey() {
		final SearchParameter param = SearchParameter.createForPublicKey(IntegrationTestConfig.getPublicKey1()).build();

		final SearchResult search = unitUnderTest.search(param);

		assertThat(search, is(notNullValue()));
		assertThat(search.getResults(), is(notNullValue()));
		assertThat(search.getResults().size(), is(10));
		assertThat(search.getToTransactionId(), is(notNullValue()));
		assertThat(search.getFromTransactionId(), is(nullValue()));
	}

	@Test
	public void shouldSearchWithAccountPrivateKey() {
		final SearchParameter param = SearchParameter.createForPrivateKey(IntegrationTestConfig.getPrivateKey1()).build();

		final SearchResult search = unitUnderTest.search(param);

		assertThat(search, is(notNullValue()));
		assertThat(search.getResults(), is(notNullValue()));
		assertThat(search.getResults().size(), is(10));
		assertThat(search.getToTransactionId(), is(notNullValue()));
		assertThat(search.getFromTransactionId(), is(nullValue()));
	}

	@Test
	public void shouldSearchAccountWithNoUploadTxns() {
		final SearchParameter param = SearchParameter.createForPrivateKey("D962E38EA1CD8E1B2583E3DB8F1113F060351300575806D5D56FE10CA234DB2A").build();

		final SearchResult search = unitUnderTest.search(param);

		assertThat(search, is(notNullValue()));
		assertThat(search.getResults(), is(notNullValue()));
		assertThat(search.getResults().size(), is(0));
		assertThat(search.getToTransactionId(), is(nullValue()));
		assertThat(search.getFromTransactionId(), is(nullValue()));
	}

}
