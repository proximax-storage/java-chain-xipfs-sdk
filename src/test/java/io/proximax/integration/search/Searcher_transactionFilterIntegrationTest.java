/*
 *
 *  * Copyright 2018 ProximaX Limited
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package io.proximax.integration.search;

import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.connection.ConnectionConfig;
import io.proximax.integration.IntegrationTestConfig;
import io.proximax.model.TransactionFilter;
import io.proximax.search.SearchParameter;
import io.proximax.search.SearchResult;
import io.proximax.search.Searcher;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class Searcher_transactionFilterIntegrationTest {

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
	public void shouldSearchOutgoingTransactions() {
		final SearchParameter param = SearchParameter.createForAddress(IntegrationTestConfig.getAddress1())
				.withTransactionFilter(TransactionFilter.OUTGOING)
				.build();

		final SearchResult search = unitUnderTest.search(param);

		assertThat(search, is(notNullValue()));
		assertThat(search.getResults(), is(notNullValue()));
		assertThat(search.getResults().size(), is(10));
	}

	@Test
	public void shouldSearchIncomingTransactions() {
		final SearchParameter param = SearchParameter.createForAddress(IntegrationTestConfig.getAddress1())
				.withTransactionFilter(TransactionFilter.INCOMING)
				.build();

		final SearchResult search = unitUnderTest.search(param);

		assertThat(search, is(notNullValue()));
		assertThat(search.getResults(), is(notNullValue()));
		assertThat(search.getResults().size(), is(10));
	}

	@Test
	public void shouldSearchAllTransactions() {
		final SearchParameter param = SearchParameter.createForAddress(IntegrationTestConfig.getAddress1())
				.withTransactionFilter(TransactionFilter.ALL)
				.build();

		final SearchResult search = unitUnderTest.search(param);

		assertThat(search, is(notNullValue()));
		assertThat(search.getResults(), is(notNullValue()));
		assertThat(search.getResults().size(), is(10));
	}
}
