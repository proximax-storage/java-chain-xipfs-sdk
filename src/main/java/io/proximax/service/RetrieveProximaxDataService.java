package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.DirectDownloadFailureException;
import io.proximax.exceptions.DownloadForTypeNotSupportedException;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for retrieving data
 */
public class RetrieveProximaxDataService {

    private final IpfsDownloadService ipfsDownloadService;
    private final DigestUtils digestUtils;

    /**
     * Construct this class
     * @param ipfsConnection the config class to connect to IPFS
     */
    public RetrieveProximaxDataService(IpfsConnection ipfsConnection) {
        this.ipfsDownloadService = new IpfsDownloadService(ipfsConnection);
        this.digestUtils = new DigestUtils();
    }

    RetrieveProximaxDataService(IpfsDownloadService ipfsDownloadService, DigestUtils digestUtils) {
        this.ipfsDownloadService = ipfsDownloadService;
        this.digestUtils = digestUtils;
    }

    /**
     * Retrieve data itself
     * @param dataHash the data hash of the target download
     * @param privacyStrategy the privacy strategy to decrypt the data
     * @param validateDigest the flag whether to validate digest
     * @param digest the digest of the target download
     * @param contentType the content type of the target download
     * @return the data
     */
    public Observable<byte[]> getData(String dataHash, PrivacyStrategy privacyStrategy, boolean validateDigest,
                                      String digest, String contentType) {
        checkParameter(dataHash != null, "dataHash is required");
        checkParameter(privacyStrategy != null, "privacyStrategy is required");

        if (contentType != null && contentType.equals(PATH_UPLOAD_CONTENT_TYPE)) { // path
            throw new DownloadForTypeNotSupportedException("download of path is not yet supported");
        } else { // byte array
            return ipfsDownloadService.download(dataHash)
                    .flatMap(undecryptedData -> validateDigest(undecryptedData, validateDigest, digest).map(result -> undecryptedData))
                    .map(privacyStrategy::decryptData);
        }
    }

    private Observable<Boolean> validateDigest(byte[] undecryptedData, boolean validateDigest, String digest) {
        return validateDigest ? digestUtils.validateDigest(undecryptedData, digest) : Observable.just(true);
    }
}
