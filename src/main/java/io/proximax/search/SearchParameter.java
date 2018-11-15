package io.proximax.search;

import io.proximax.model.TransactionFilter;

public class SearchParameter {

    private final TransactionFilter transactionFilter;
    private final int resultSize;
    private final String accountAddress;
    private final String accountPublicKey;
    private final String accountPrivateKey;
    private final String nameFilter;
    private final String descriptionFilter;
    private final String metadataKeyFilter;
    private final String metadataValueFilter;
    private final String fromTransactionId;

    SearchParameter(TransactionFilter transactionFilter, int resultSize, String accountAddress, String accountPublicKey, String accountPrivateKey, String nameFilter, String descriptionFilter, String metadataKeyFilter, String metadataValueFilter, String fromTransactionId) {
        this.transactionFilter = transactionFilter;
        this.resultSize = resultSize;
        this.accountAddress = accountAddress;
        this.accountPublicKey = accountPublicKey;
        this.accountPrivateKey = accountPrivateKey;
        this.nameFilter = nameFilter;
        this.descriptionFilter = descriptionFilter;
        this.metadataKeyFilter = metadataKeyFilter;
        this.metadataValueFilter = metadataValueFilter;
        this.fromTransactionId = fromTransactionId;
    }

    public TransactionFilter getTransactionFilter() {
        return transactionFilter;
    }

    public int getResultSize() {
        return resultSize;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public String getAccountPublicKey() {
        return accountPublicKey;
    }

    public String getAccountPrivateKey() {
        return accountPrivateKey;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public String getDescriptionFilter() {
        return descriptionFilter;
    }

    public String getMetadataKeyFilter() {
        return metadataKeyFilter;
    }

    public String getMetadataValueFilter() {
        return metadataValueFilter;
    }

    public String getFromTransactionId() {
        return fromTransactionId;
    }

    public static SearchParameterBuilder createForAddress(String accountAddress) {
        return SearchParameterBuilder.createForAddress(accountAddress);
    }

    public static SearchParameterBuilder createForPublicKey(String accountPublicKey) {
        return SearchParameterBuilder.createForPublicKey(accountPublicKey);
    }

    public static SearchParameterBuilder createForPrivateKey(String accountPrivateKey) {
        return SearchParameterBuilder.createForPrivateKey(accountPrivateKey);
    }

}
