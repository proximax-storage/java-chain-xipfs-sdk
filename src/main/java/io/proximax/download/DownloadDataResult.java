package io.proximax.download;

/**
 * The model class that defines the result of a download data. This class wraps the data represented as byte array.
 * @see Downloader#downloadData(DownloadDataParameter)
 */
public class DownloadDataResult {

    private final byte[] data;

    DownloadDataResult(byte[] data) {
        this.data = data;
    }

    /**
     * Get the data (byte array)
     * @return the data
     */
    public byte[] getData() {
        return data;
    }
}
