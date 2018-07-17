package io.proximax.download;

public class DownloadDataResult {

    private final byte[] data;

    public DownloadDataResult(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
}
