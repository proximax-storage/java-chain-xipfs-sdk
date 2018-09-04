package io.proximax.utils;

import io.nem.sdk.model.account.Account;
import io.nem.sdk.model.account.Address;
import io.nem.sdk.model.blockchain.NetworkType;
import io.nem.sdk.model.transaction.SignedTransaction;
import io.nem.sdk.model.transaction.Transaction;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The utility class for handling blockchain-related actions
 */
public class NemUtils {

    private final NetworkType networkType;

    /**
     * Construct an instance of this utility class
     * @param networkType the network type of the blockchain
     */
    public NemUtils(NetworkType networkType) {
        this.networkType = networkType;
    }

    /**
     * Convert to nem address
     * @param address the address
     * @return the address
     */
    public Address getAddress(String address) {
        checkParameter(address != null, "address is required");

        return Address.createFromRawAddress(address);
    }

    /**
     * Converts a public key to an address
     * @param publicKey the public key
     * @return the address
     */
    public Address getAddressFromPublicKey(String publicKey) {
        checkParameter(publicKey != null, "publicKey is required");

        return Address.createFromPublicKey(publicKey, networkType);
    }

    /**
     * Converts a private key to an address
     * @param privateKey the public key
     * @return the address
     */
    public Address getAddressFromPrivateKey(String privateKey) {
        checkParameter(privateKey != null, "privateKey is required");

        return getAccount(privateKey).getAddress();
    }

    /**
     * Converts a private key to an account
     * @param privateKey the public key
     * @return the account
     */
    public Account getAccount(String privateKey) {
        checkParameter(privateKey != null, "privateKey is required");

        return Account.createFromPrivateKey(privateKey, networkType);
    }

    /**
     * Sign a transaction
     * @param signerPrivateKey the signer's private key
     * @param transaction the transaction to sign
     * @return the signed transaction
     */
    public SignedTransaction signTransaction(String signerPrivateKey, Transaction transaction) {
        checkParameter(signerPrivateKey != null, "signerPrivateKey is required");
        checkParameter(transaction != null, "transaction is required");

        return getAccount(signerPrivateKey).sign(transaction);
    }
}
