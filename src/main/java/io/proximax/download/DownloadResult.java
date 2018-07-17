package io.proximax.download;

import io.proximax.model.ProximaxRootDataModel;
import io.proximax.model.StoreType;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class DownloadResult {

    private final int privacyType;
    private final String privacySearchTag;
    private final String description;
    private final StoreType storeType;
    private final String version;
    private final List<DownloadResultData> dataList;

    private DownloadResult(int privacyType, String privacySearchTag, String description, StoreType storeType,
                          String version, List<DownloadResultData> dataList) {
        this.privacyType = privacyType;
        this.privacySearchTag = privacySearchTag;
        this.description = description;
        this.storeType = storeType;
        this.version = version;
        this.dataList = dataList == null ? Collections.emptyList() : Collections.unmodifiableList(dataList);
    }

    public int getPrivacyType() {
        return privacyType;
    }

    public String getPrivacySearchTag() {
        return privacySearchTag;
    }

    public String getDescription() {
        return description;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public String getVersion() {
        return version;
    }

    public List<DownloadResultData> getDataList() {
        return dataList;
    }

    public static DownloadResult create(ProximaxRootDataModel rootData, List<byte[]> decryptedDataList) {
        final List<DownloadResultData> downloadDataList = IntStream.range(0, decryptedDataList.size())
                .mapToObj(index -> new DownloadResultData(decryptedDataList.get(index),
                        rootData.getDataList().get(index).getDescription(),
                        rootData.getDataList().get(index).getName(),
                        rootData.getDataList().get(index).getContentType(),
                        rootData.getDataList().get(index).getMetadata())).collect(toList());

        return new DownloadResult(rootData.getPrivacyType(),
                rootData.getPrivacySearchTag(), rootData.getDescription(), rootData.getStoreType(),
                rootData.getVersion(), downloadDataList);
    }
}
