package io.proximax.model;

import java.util.Collections;
import java.util.List;

public final class ProximaxRootDataModel {

    private final int privacyType;
    private final String privacySearchTag;
    private final String description;
    private final String version;
	private final List<ProximaxDataModel> dataList;

    public ProximaxRootDataModel(int privacyType, String privacySearchTag, String description,
                                 String version, List<ProximaxDataModel> dataList) {
        this.privacyType = privacyType;
        this.privacySearchTag = privacySearchTag;
        this.description = description;
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

    public String getVersion() {
        return version;
    }

    public List<ProximaxDataModel> getDataList() {
		return dataList;
	}
}
