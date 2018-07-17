package io.proximax.model;

import io.proximax.service.IpfsUploadResponse;
import io.proximax.upload.UploadParameterData;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public final class ProximaxDataModel {

	private final String digest;
	private final String dataHash;
	private final String description;
	private final Map<String, String> metadata;
	private final long timestamp;
	private final String name;
	private final String contentType;

	public ProximaxDataModel(final String digest,
							 final String dataHash,
							 final String description,
							 final Map<String, String> metadata,
							 final Long timestamp,
							 final String name,
							 final String contentType) {
		this.digest = digest;
		this.dataHash = dataHash;
		this.description = description;
		this.metadata = metadata == null ? Collections.emptyMap() : Collections.unmodifiableMap(metadata);
		this.timestamp = timestamp;
		this.name = name;
		this.contentType = contentType;
	}

	public String getDigest() {
		return digest;
	}

	public String getDataHash() {
		return dataHash;
	}

	public String getDescription() {
		return description;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public String getName() {
		return name;
	}

	public String getContentType() {
		return contentType;
	}

	public static List<ProximaxDataModel> createList(List<UploadParameterData> parameterDataList, List<IpfsUploadResponse> dataUploadResponseList,
													 List<String> digestList, List<String> contentTypeList) {
		return IntStream.range(0, parameterDataList.size())
				.mapToObj(index -> create(parameterDataList.get(index), dataUploadResponseList.get(index).getDataHash(),
						digestList.get(index), contentTypeList.get(index), dataUploadResponseList.get(index).getTimestamp()))
				.collect(toList());
	}

	public static ProximaxDataModel create(RawDataModel parameterData, String dataHash, String digest, String contentType, Long timestamp) {
		return new ProximaxDataModel(digest, dataHash, parameterData.getDescription(), parameterData.getMetadata(),
				timestamp, parameterData.getName(), contentType);
	}
}
