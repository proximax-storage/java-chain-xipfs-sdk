package io.proximax.service;

import io.proximax.connection.IpfsConnection;
import io.proximax.exceptions.DownloadForDataTypeNotSupportedException;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.service.client.IpfsClient;
import io.proximax.utils.DigestUtils;
import io.reactivex.Observable;

import java.io.InputStream;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for retrieving data
 */
public class RetrieveProximaxDataService {

    private final IpfsClient ipfsClient;
    private final DigestUtils digestUtils;

    /**
     * Construct this class
     * @param ipfsConnection the config class to connect to IPFS
     */
    public RetrieveProximaxDataService(IpfsConnection ipfsConnection) {
        this.ipfsClient = new IpfsClient(ipfsConnection);
        this.digestUtils = new DigestUtils();
    }

    RetrieveProximaxDataService(IpfsClient ipfsClient, DigestUtils digestUtils) {
        this.ipfsClient = ipfsClient;
        this.digestUtils = digestUtils;
    }

    /**
     * Retrieve data's byte stream
     * @param dataHash the data hash of the target download
     * @param privacyStrategy the privacy strategy to decrypt the data
     * @param validateDigest the flag whether to validate digest
     * @param digest the digest of the target download
     * @param contentType the content type of the target download
     * @return the data's byte stream
     */
    public Observable<InputStream> getDataByteStream(String dataHash, PrivacyStrategy privacyStrategy, boolean validateDigest,
                                                     String digest, String contentType) {
        checkParameter(dataHash != null, "dataHash is required");
        checkParameter(privacyStrategy != null, "privacyStrategy is required");

        if (contentType != null && contentType.equals(PATH_UPLOAD_CONTENT_TYPE)) { // path
            throw new DownloadForDataTypeNotSupportedException("download of path is not yet supported");
        } else { // byte array
            return ipfsClient.getByteStream(dataHash)
                    .flatMap(undecryptedStream -> validateDigest(validateDigest, digest, dataHash)
                            .map(result -> undecryptedStream))
                    .map(privacyStrategy::decryptStream);
        }
    }

    private Observable<Boolean> validateDigest(boolean validateDigest, String digest, String dataHash) {
        return validateDigest ? ipfsClient.getByteStream(dataHash)
                .flatMap(undecryptedStream -> digestUtils.validateDigest(undecryptedStream, digest)) : Observable.just(true);
    }
}
