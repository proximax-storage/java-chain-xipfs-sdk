package io.proximax.service.client.catapult;

import io.nem.core.crypto.PublicKey;
import io.nem.sdk.infrastructure.AccountHttp;
import io.nem.sdk.infrastructure.QueryParams;
import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.account.AccountInfo;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.account.PublicAccount;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.transaction.Transaction;
import io.proximax.connection.BlockchainNetworkConnection;
import io.proximax.exceptions.AccountNotFoundException;
import io.proximax.exceptions.PublicKeyNotFoundException;
import io.proximax.model.TransactionFilter;
import io.reactivex.Observable;

import java.net.MalformedURLException;
import java.util.List;

import static io.nem.core.crypto.PublicKey.fromHexString;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;
import static java.lang.String.format;

/**
 * The client class that directly interface with the blockchain's transaction APIs
 */
public class AccountClient {

    /**
     * The public key constant when it is not yet used to send transaction on catapult.
     */
    public static final String PUBLIC_KEY_NOT_FOUND = "0000000000000000000000000000000000000000000000000000000000000000";

    private final AccountHttp accountHttp;
    private final NetworkType networkType;

    /**
     * Create instance of AccountClient
     * @param blockchainNetworkConnection the blockchain connection
     * @throws MalformedURLException exception when invalid blockchain URl
     */
    public AccountClient(BlockchainNetworkConnection blockchainNetworkConnection) throws MalformedURLException {
        checkParameter(blockchainNetworkConnection != null, "blockchainNetworkConnection is required");

        this.accountHttp = new AccountHttp(blockchainNetworkConnection.getApiUrl());
        this.networkType = blockchainNetworkConnection.getNetworkType();
    }

    AccountClient(AccountHttp accountHttp, NetworkType networkType) {
        this.accountHttp = accountHttp;
        this.networkType = networkType;
    }

    /**
     * Retrieves public key of address
     * @param address the address
     * @return the public key of address
     */
    public PublicKey getPublicKey(String address) {
        checkParameter(address != null, "address is required.");

        try {
            final AccountInfo accountInfo = accountHttp.getAccountInfo(Address.createFromRawAddress(address)).blockingFirst();
            if (accountInfo.getPublicKey().equals(PUBLIC_KEY_NOT_FOUND))
                throw new PublicKeyNotFoundException(format("Address %s has no public key yet on blockchain", address));

            return fromHexString(accountInfo.getPublicKey());
        } catch (PublicKeyNotFoundException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new AccountNotFoundException(
                    format("Failed to retrieve account for %s. Probably address is not yet revealed on blockchain.", address), e);
        }
    }

    public Observable<List<Transaction>> getTransactions(TransactionFilter transactionFilter, int resultSize, String accountPrivateKey,
                                                         String accountPublicKey, String accountAddress, String fromTransactionId) {
        checkParameter(transactionFilter != null, "transactionFilter is required");

        final PublicAccount publicAccount = getPublicAccount(accountPrivateKey, accountPublicKey, accountAddress);

        final QueryParams queryParams = new QueryParams(resultSize, fromTransactionId);

        if (transactionFilter == TransactionFilter.ALL) {
            return accountHttp.transactions(publicAccount, queryParams);
        } else if (transactionFilter == TransactionFilter.OUTGOING) {
            return accountHttp.outgoingTransactions(publicAccount, queryParams);
        } else if (transactionFilter == TransactionFilter.INCOMING) {
            return accountHttp.incomingTransactions(publicAccount, queryParams);
        } else {
            throw new IllegalArgumentException(format("Unknown transactionFilter %s", transactionFilter.name()));
        }
    }

    private PublicAccount getPublicAccount(String accountPrivateKey, String accountPublicKey, String accountAddress) {
        if (accountPrivateKey != null) {
            return Account.createFromPrivateKey(accountPrivateKey, networkType).getPublicAccount();
        } else if (accountPublicKey != null) {
            return PublicAccount.createFromPublicKey(accountPublicKey, networkType);
        } else if (accountAddress != null) {
            return PublicAccount.createFromPublicKey(getPublicKey(accountAddress).toString(), networkType);
        } else {
            throw new IllegalArgumentException("accountPrivateKey, accountPublicKey or accountAddress must be provided");
        }
    }

}
