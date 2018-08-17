package io.proximax.model;

import java.util.Collections;
import java.util.List;

/**
 * This model class represents the upload instance containing the list of uploaded data and details
 * <br>
 * <br>
 * The following are its fields:
 * <ul>
 *     <li><b>privacyType</b> - the privacy type from privacy strategy used to encrypt data</li>
 *     <li><b>privacySearchTag</b> - the privacy search tag applied</li>
 *     <li><b>description</b> - the short description for the upload</li>
 *     <li><b>version</b> - the version of upload</li>
 *     <li><b>dataList</b> - the list of uploaded data</li>
 * </ul>
 * @see ProximaxRootDataModel
 */
public final class ProximaxRootDataModel {

    private final int privacyType;
    private final String privacySearchTag;
    private final String description;
    private final String version;
	private final List<ProximaxDataModel> dataList;

    /**
     * Construct instance of this model
     * @param privacyType the privacy type from privacy strategy used to encrypt data
     * @param privacySearchTag the privacy search tag applied
     * @param description the short description for the upload
     * @param version the version of upload
     * @param dataList the list of uploaded data
     */
    public ProximaxRootDataModel(int privacyType, String privacySearchTag, String description,
                                 String version, List<ProximaxDataModel> dataList) {
        this.privacyType = privacyType;
        this.privacySearchTag = privacySearchTag;
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
     * Get the privacy search tag applied
     * @return the privacy search
     */
    public String getPrivacySearchTag() {
        return privacySearchTag;
    }

    /**
     * Get the short description for the upload
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
     * Get the list of uploaded data
     * @return the list of data
     */
    public List<ProximaxDataModel> getDataList() {
		return dataList;
	}
}
