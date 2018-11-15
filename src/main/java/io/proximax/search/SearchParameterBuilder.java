package io.proximax.search;

import io.proximax.model.TransactionFilter;
import io.proximax.upload.UploadParameter;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This builder class creates the UploadParameter
 * @see UploadParameter
 */
public class SearchParameterBuilder {

    private String accountAddress;
    private String accountPublicKey;
    private String accountPrivateKey;
    private TransactionFilter transactionFilter;
    private Integer resultSize;
    private String nameFilter;
    private String descriptionFilter;
    private String metadataKeyFilter;
    private String metadataValueFilter;
    private String fromTransactionId;

    SearchParameterBuilder(String accountAddress, String accountPublicKey, String accountPrivateKey) {
        this.accountAddress = accountAddress;
        this.accountPublicKey = accountPublicKey;
        this.accountPrivateKey = accountPrivateKey;
    }

    public static SearchParameterBuilder createForAddress(String accountAddress) {
        return new SearchParameterBuilder(accountAddress, null, null);
    }

    public static SearchParameterBuilder createForPublicKey(String accountPublicKey) {
        return new SearchParameterBuilder(null, accountPublicKey, null);
    }

    public static SearchParameterBuilder createForPrivateKey(String accountPrivateKey) {
        return new SearchParameterBuilder(null, null, accountPrivateKey);
    }

    public SearchParameterBuilder withTransactionFilter(TransactionFilter transactionFilter) {
        this.transactionFilter = transactionFilter;
        return this;
    }

    public SearchParameterBuilder withResultSize(Integer resultSize) {
        checkParameter(resultSize == null || (resultSize >= 1 && resultSize <= 20),
                "result size should be between 1 and 20");
        this.resultSize = resultSize;
        return this;
    }

    public SearchParameterBuilder withNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
        return this;
    }

    public SearchParameterBuilder withDescriptionFilter(String descriptionFilter) {
        this.descriptionFilter = descriptionFilter;
        return this;
    }

    public SearchParameterBuilder withMetadataKeyFilter(String metadataKeyFilter) {
        this.metadataKeyFilter = metadataKeyFilter;
        return this;
    }

    public SearchParameterBuilder withMetadataValueFilter(String metadataValueFilter) {
        this.metadataValueFilter = metadataValueFilter;
        return this;
    }

    public SearchParameterBuilder withFromTransactionId(String fromTransactionId) {
        this.fromTransactionId = fromTransactionId;
        return this;
    }

    public SearchParameter build() {
        if (this.transactionFilter == null)
            this.transactionFilter = TransactionFilter.OUTGOING;
        if (this.resultSize == null)
            this.resultSize = 10;

        return new SearchParameter(
                transactionFilter,
                resultSize,
                accountAddress,
                accountPublicKey,
                accountPrivateKey,
                nameFilter,
                descriptionFilter,
                metadataKeyFilter,
                metadataValueFilter,
                fromTransactionId
        );
    }

}
