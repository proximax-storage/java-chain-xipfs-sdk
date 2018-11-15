package io.proximax.integration.search;

import io.proximax.async.AsyncCallbacks;
import io.proximax.async.AsyncTask;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.exceptions.SearchFailureException;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.search.SearchParameter;
import io.proximax.search.SearchResult;
import io.proximax.search.Searcher;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Searcher_asyncIntegrationTest {

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
	public void shouldSearchAsynchronouslyWithoutCallback() throws Exception {
		final SearchParameter param = SearchParameter.createForAddress(IntegrationTestConfig.getAddress1()).build();

		final AsyncTask asyncTask = unitUnderTest.searchAsync(param, null);
		while (!asyncTask.isDone()) {
			Thread.sleep(50);
		}

		assertThat(asyncTask.isDone(), is(true));
	}

	@Test
	public void shouldSearchAsynchronouslyWithSuccessCallback() throws Exception {
		final SearchParameter param = SearchParameter.createForAddress(IntegrationTestConfig.getAddress1()).build();
		final CompletableFuture<SearchResult> toPopulateOnSuccess = new CompletableFuture<>();

		unitUnderTest.searchAsync(param, AsyncCallbacks.create(toPopulateOnSuccess::complete, null));
		final SearchResult result = toPopulateOnSuccess.get(5, TimeUnit.SECONDS);

		assertThat(result, is(notNullValue()));
		assertThat(result.getResults(), is(notNullValue()));
		assertThat(result.getResults().size(), is(10));
	}

	@Test
	public void shouldSearchAsynchronouslyWithFailureCallback() throws Exception {
		final SearchParameter param = SearchParameter.createForAddress("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").build();
		final CompletableFuture<Throwable> toPopulateOnFailure = new CompletableFuture<>();

		unitUnderTest.searchAsync(param, AsyncCallbacks.create(null, toPopulateOnFailure::complete));
		final Throwable throwable = toPopulateOnFailure.get(5, TimeUnit.SECONDS);

		assertThat(throwable, instanceOf(SearchFailureException.class));
	}
}
