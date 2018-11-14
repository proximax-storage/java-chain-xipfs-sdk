package io.proximax.search;

import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransactionInfo;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.async.AsyncCallbacks;
import io.proximax.async.AsyncTask;
import io.proximax.connection.ConnectionConfig;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.service.RetrieveProximaxMessagePayloadService;
import io.proximax.service.client.catapult.AccountClient;
import io.proximax.utils.AsyncUtils;
import io.reactivex.Observable;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class Searcher {

    private static final int BATCH_TRANSACTION_SIZE = 100;

    private final AccountClient accountClient;
    private final RetrieveProximaxMessagePayloadService retrieveProximaxMessagePayloadService;

    public Searcher(ConnectionConfig connectionConfig) {
        try {
            this.accountClient = new AccountClient(connectionConfig.getBlockchainNetworkConnection());
            this.retrieveProximaxMessagePayloadService = new RetrieveProximaxMessagePayloadService(connectionConfig.getBlockchainNetworkConnection());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to initialize", e);
        }
    }

    Searcher(AccountClient accountClient, RetrieveProximaxMessagePayloadService retrieveProximaxMessagePayloadService) {
        this.accountClient = accountClient;
        this.retrieveProximaxMessagePayloadService = retrieveProximaxMessagePayloadService;
    }

    public SearchResult search(SearchParameter param) {
        checkParameter(param != null, "param is required");

        return doSearch(param).blockingFirst();
    }

    public AsyncTask uploadAsync(SearchParameter param, AsyncCallbacks<SearchResult> asyncCallbacks) {
        checkParameter(param != null, "param is required");

        final AsyncTask asyncTask = new AsyncTask();

        AsyncUtils.processFirstItem(this.doSearch(param), asyncCallbacks, asyncTask);

        return asyncTask;
    }

    private Observable<SearchResult> doSearch(SearchParameter param) {
        String fromTransactionId = param.getFromTransactionId();
        String toTransactionId = null;
        final List<SearchResultItem> results = new ArrayList<>();

        while (results.size() < param.getResultSize()) {
            final List<Transaction> transactions = accountClient.getTransactions(param.getTransactionFilter(), BATCH_TRANSACTION_SIZE, param.getAccountPrivateKey(),
                    param.getAccountPublicKey(), param.getAccountAddress(), fromTransactionId).blockingFirst();

            if (transactions != null && transactions.size() > 0) {
                for (int index = 0; index < transactions.size() && results.size() < param.getResultSize(); index++) {
                    final Transaction transaction = transactions.get(index);
                    final Optional<SearchResultItem> resultItem = convertToResultItemIfMatchingCriteria(transaction, param);

                    resultItem.ifPresent(results::add);

                    toTransactionId = transaction.getTransactionInfo().flatMap(TransactionInfo::getId).orElse(null);
                }
            }

            if (transactions != null && transactions.size() == Searcher.BATCH_TRANSACTION_SIZE) {
                fromTransactionId = transactions.get(transactions.size() - 1).getTransactionInfo().flatMap(TransactionInfo::getId).orElse(null);
            } else {
                break;
            }
        }

        return Observable.just(new SearchResult(results, param.getFromTransactionId(), toTransactionId));
    }

    private Optional<SearchResultItem> convertToResultItemIfMatchingCriteria(Transaction transaction, SearchParameter param) {
        if (transaction instanceof TransferTransaction) {
            try {
                final ProximaxMessagePayloadModel messagePayload = retrieveProximaxMessagePayloadService.getMessagePayload(
                        (TransferTransaction) transaction, param.getAccountPrivateKey());

                // verify message payload is upload transaction by having the right json fields
                if (messagePayload != null &&
                        messagePayload.getVersion() != null &&
                        messagePayload.getPrivacyType() != 0 &&
                        messagePayload.getData() != null &&
                        messagePayload.getData().getTimestamp() != null &&
                        messagePayload.getData().getDataHash() != null) {
                    if (matchesSearchCriteria(messagePayload,
                            param.getNameFilter(),
                            param.getDescriptionFilter(),
                            param.getMetadataKeyFilter(),
                            param.getMetadataValueFilter())) {
                        return Optional.of(new SearchResultItem(
                                transaction.getTransactionInfo().flatMap(TransactionInfo::getHash).orElse(null),
                                messagePayload));
                    }
                }
            } catch (Exception e) {
                // skip transaction
            }
        }
        return Optional.empty();
    }

    private boolean matchesSearchCriteria(ProximaxMessagePayloadModel messagePayload, String nameFilter,
                                          String descriptionFilter, String metadataKeyFilter, String metadataValueFilter) {
        if (nameFilter != null) {
            if (!(messagePayload.getData().getName() != null &&
                    messagePayload.getData().getName().contains(nameFilter))) {
                return false;
            }
        }
        if (descriptionFilter != null) {
            if (!(messagePayload.getData().getDescription() != null &&
                    messagePayload.getData().getDescription().contains(descriptionFilter))) {
                return false;
            }
        }
        if (metadataKeyFilter != null) {
            if (metadataValueFilter != null) {
                if (!(messagePayload.getData().getMetadata() != null &&
                        metadataValueFilter.equals(messagePayload.getData().getMetadata().get(metadataKeyFilter)))) {
                    return false;
                }
            } else {
                if (!(messagePayload.getData().getMetadata() != null &&
                        messagePayload.getData().getMetadata().containsKey(metadataKeyFilter))) {
                    return false;
                }
            }
        }

        return true;
    }


}
