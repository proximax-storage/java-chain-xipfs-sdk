package io.proximax.service;

import io.proximax.connection.FileStorageConnection;
import io.proximax.exceptions.DownloadForDataTypeNotSupportedException;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.reactivex.Observable;

import java.io.InputStream;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * The service class responsible for retrieving data
 */
public class RetrieveProximaxDataService {

    private final FileDownloadService fileDownloadService;

    /**
     * Construct this class
     *
     * @param fileStorageConnection the connection to file storage
     */
    public RetrieveProximaxDataService(FileStorageConnection fileStorageConnection) {
        this.fileDownloadService = new FileDownloadService(fileStorageConnection);
    }

    RetrieveProximaxDataService(FileDownloadService fileDownloadService) {
        this.fileDownloadService = fileDownloadService;
    }

    /**
     * Retrieve data's byte stream
     *
     * @param dataHash        the data hash of the target download
     * @param privacyStrategy the privacy strategy to decrypt the data
     * @param validateDigest  the flag whether to validate digest
     * @param digest          the digest of the target download
     * @param contentType     the content type of the target download
     * @return the data's byte stream
     */
    public Observable<InputStream> getDataByteStream(String dataHash, PrivacyStrategy privacyStrategy, boolean validateDigest,
                                                     String digest, String contentType) {
        checkParameter(dataHash != null, "dataHash is required");
        checkParameter(privacyStrategy != null, "privacyStrategy is required");

        if (contentType != null && contentType.equals(PATH_UPLOAD_CONTENT_TYPE)) { // path
            throw new DownloadForDataTypeNotSupportedException("download of path is not yet supported");
        } else { // byte array
            final String digestToUse = validateDigest ? digest : null;
            return fileDownloadService.getByteStream(dataHash, privacyStrategy, digestToUse);
        }
    }
}
