package io.proximax.search;

import java.util.List;

public class SearchResult {

    private final List<SearchResultItem> results;
    private final String fromTransactionId;
    private final String toTransactionId;

    SearchResult(List<SearchResultItem> results, String fromTransactionId, String toTransactionId) {
        this.results = results;
        this.fromTransactionId = fromTransactionId;
        this.toTransactionId = toTransactionId;
    }

    public List<SearchResultItem> getResults() {
        return results;
    }

    public String getFromTransactionId() {
        return fromTransactionId;
    }

    public String getToTransactionId() {
        return toTransactionId;
    }
}
