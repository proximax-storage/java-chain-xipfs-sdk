package io.proximax.utils;

import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.TransferTransaction;

import static com.google.common.base.Preconditions.checkArgument;

public class NemUtils {

    private final NetworkType networkType;

    public NemUtils(NetworkType networkType) {
        this.networkType = networkType;
    }

    public Address toAddress(String recipientPublicKey) {
        checkArgument(recipientPublicKey != null, "recipientPublicKey is required");

        return Address.createFromPublicKey(recipientPublicKey, networkType);
    }

    public Account toAccount(String signerPrivateKey) {
        checkArgument(signerPrivateKey != null, "signerPrivateKey is required");

        return Account.createFromPrivateKey(signerPrivateKey, networkType);
    }

    public SignedTransaction signTransaction(String signerPrivateKey, TransferTransaction transferTransaction) {
        checkArgument(signerPrivateKey != null, "signerPrivateKey is required");
        checkArgument(transferTransaction != null, "transferTransaction is required");

        return toAccount(signerPrivateKey).sign(transferTransaction);
    }
}
