package io.proximax.download;

import io.proximax.model.ProximaxRootDataModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * The model class that defines the result of a download
 * <br>
 * <br>
 * The following are its fields:
 * <ul>
 *     <li><b>privacyType</b> - the privacy type from privacy strategy used to encrypt data</li>
 *     <li><b>description</b> - the short description of the upload</li>
 *     <li><b>version</b> - the version of upload</li>
 *     <li><b>dataList</b> - the list of downloaded data</li>
 * </ul>
 * @see Downloader#download(DownloadParameter)
 */
public class DownloadResult {

    private final int privacyType;
    private final String description;
    private final String version;
    private final List<DownloadResultData> dataList;

    private DownloadResult(int privacyType, String description, String version, List<DownloadResultData> dataList) {
        this.privacyType = privacyType;
        this.description = description;
        this.version = version;
        this.dataList = dataList == null ? Collections.emptyList() : Collections.unmodifiableList(dataList);
    }

    /**
     * Get the privacy type from privacy strategy used to encrypt data
     * @return the privacy type
     */
    public int getPrivacyType() {
        return privacyType;
    }

    /**
     * Get the short description of the upload
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the version of upload
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Get the list of downloaded data
     * @return get the list of downloaded data
     */
    public List<DownloadResultData> getDataList() {
        return dataList;
    }

    static DownloadResult create(ProximaxRootDataModel rootData, List<byte[]> decryptedDataList) {
        final List<DownloadResultData> downloadDataList = IntStream.range(0, decryptedDataList.size())
                .mapToObj(index -> new DownloadResultData(decryptedDataList.get(index),
                        rootData.getDataList().get(index).getDescription(),
                        rootData.getDataList().get(index).getName(),
                        rootData.getDataList().get(index).getContentType(),
                        rootData.getDataList().get(index).getMetadata())).collect(toList());

        return new DownloadResult(rootData.getPrivacyType(), rootData.getDescription(), rootData.getVersion(), downloadDataList);
    }
}
