package io.proximax.download;

import io.proximax.model.RawDataModel;

import java.util.Map;

public class DownloadResultData extends RawDataModel {

    DownloadResultData(byte[] data, String description, String name, String contentType, Map<String, String> metadata) {
        super(data, description, name, contentType, metadata);
    }
}
