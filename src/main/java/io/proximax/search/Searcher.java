package io.proximax.search;

import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.account.PublicAccount;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.transaction.Transaction;
import io.nem.sdk.model.transaction.TransactionInfo;
import io.nem.sdk.model.transaction.TransferTransaction;
import io.proximax.async.AsyncCallbacks;
import io.proximax.async.AsyncTask;
import io.proximax.connection.ConnectionConfig;
import io.proximax.exceptions.SearchFailureException;
import io.proximax.model.ProximaxMessagePayloadModel;
import io.proximax.service.RetrieveProximaxMessagePayloadService;
import io.proximax.service.client.catapult.AccountClient;
import io.proximax.utils.AsyncUtils;
import io.reactivex.Observable;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class Searcher {

    private static final int BATCH_TRANSACTION_SIZE = 100;

    private final NetworkType networkType;
    private final AccountClient accountClient;
    private final RetrieveProximaxMessagePayloadService retrieveProximaxMessagePayloadService;

    public Searcher(ConnectionConfig connectionConfig) {
        this.networkType = connectionConfig.getBlockchainNetworkConnection().getNetworkType();
        try {
            this.accountClient = new AccountClient(connectionConfig.getBlockchainNetworkConnection());
            this.retrieveProximaxMessagePayloadService = new RetrieveProximaxMessagePayloadService(connectionConfig.getBlockchainNetworkConnection());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to initialize", e);
        }
    }

    Searcher(AccountClient accountClient, RetrieveProximaxMessagePayloadService retrieveProximaxMessagePayloadService,
             NetworkType networkType) {
        this.networkType = networkType;
        this.accountClient = accountClient;
        this.retrieveProximaxMessagePayloadService = retrieveProximaxMessagePayloadService;
    }

    public SearchResult search(SearchParameter param) {
        checkParameter(param != null, "param is required");

        return doSearch(param).blockingFirst();
    }

    public AsyncTask searchAsync(SearchParameter param, AsyncCallbacks<SearchResult> asyncCallbacks) {
        checkParameter(param != null, "param is required");

        final AsyncTask asyncTask = new AsyncTask();

        AsyncUtils.processFirstItem(this.doSearch(param), asyncCallbacks, asyncTask);

        return asyncTask;
    }

    private Observable<SearchResult> doSearch(SearchParameter param) {
        return Observable.fromCallable(
                () -> {
                    try {
                        String fromTransactionId = param.getFromTransactionId();
                        final List<SearchResultItem> results = new ArrayList<>();
                        final PublicAccount publicAccount = getPublicAccount(param.getAccountPrivateKey(), param.getAccountPublicKey(),
                                param.getAccountAddress());

                        while (results.size() < param.getResultSize()) {

                            final List<Transaction> transactions = accountClient.getTransactions(param.getTransactionFilter(),
                                    BATCH_TRANSACTION_SIZE, publicAccount, fromTransactionId).blockingFirst();

                            // Search txns
                            final List<SearchResultItem> resultSet = transactions.parallelStream()
                                    .map(txn -> convertToResultItemIfMatchingCriteria(txn, param))
                                    .filter(Optional::isPresent)
                                    .map(Optional::get)
                                    .limit(param.getResultSize() - results.size())
                                    .collect(Collectors.toList());

                            results.addAll(resultSet);

                            // if last fetch is full, there might be more transactions in account
                            // otherwise, search is done
                            if (transactions.size() == Searcher.BATCH_TRANSACTION_SIZE) {
                                fromTransactionId = transactions.get(transactions.size() - 1).getTransactionInfo().flatMap(TransactionInfo::getId).orElse(null);
                            } else {
                                break;
                            }
                        }

                        final String toTransactionId = results.isEmpty() ? null : results.get(results.size() - 1).getTransactionId();
                        return new SearchResult(results, param.getFromTransactionId(), toTransactionId);
                    } catch (RuntimeException ex) {
                        throw new SearchFailureException("Search failed.", ex);
                    }
                }
        );
    }

    private PublicAccount getPublicAccount(String accountPrivateKey, String accountPublicKey, String accountAddress) {
        if (accountPrivateKey != null) {
            return Account.createFromPrivateKey(accountPrivateKey, networkType).getPublicAccount();
        } else if (accountPublicKey != null) {
            return PublicAccount.createFromPublicKey(accountPublicKey, networkType);
        } else if (accountAddress != null) {
            return PublicAccount.createFromPublicKey(accountClient.getPublicKey(accountAddress).toString(), networkType);
        } else {
            throw new IllegalArgumentException("accountPrivateKey, accountPublicKey or accountAddress must be provided");
        }
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
                                transaction.getTransactionInfo().flatMap(TransactionInfo::getId).orElse(null),
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
