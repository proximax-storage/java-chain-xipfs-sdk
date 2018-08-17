package io.proximax.model;

import io.proximax.service.IpfsUploadResponse;
import io.proximax.upload.UploadParameterData;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * This model class represents the details of an uploaded data
 * <br>
 * <br>
 * The following are its fields:
 * <ul>
 *     <li><b>digest</b> - the computed digest for the data (already encrypted data by privacy strategy)</li>
 *     <li><b>dataHash</b> - the data hash to access the data (already encrypted data by privacy strategy)</li>
 *     <li><b>description</b> - a description of the data</li>
 *     <li><b>metadata</b> - an additional metadata for the data</li>
 *     <li><b>timestamp</b> - the timestamp when the upload occurred</li>
 *     <li><b>name</b> - the name for the data (file name by default if a file upload)</li>
 *     <li><b>contentType</b> - the content type of the data (detected if none specified)</li>
 * </ul>
 * @see ProximaxRootDataModel
 */
public final class ProximaxDataModel {

	private final String digest;
	private final String dataHash;
	private final String description;
	private final Map<String, String> metadata;
	private final long timestamp;
	private final String name;
	private final String contentType;

	/**
	 * Construct an instance
	 * @param digest the computed digest for the data (already encrypted data by privacy strategy)
	 * @param dataHash the data hash to access the data (already encrypted data by privacy strategy)
	 * @param description a description of the data
	 * @param metadata an additional metadata for the data
	 * @param timestamp the timestamp when the upload occurred
	 * @param name the name for the data (file name by default if a file upload)
	 * @param contentType the content type of the data (detected if none specified)
	 */
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

	/**
	 * Get the computed digest for the data
	 * @return the digest
	 */
	public String getDigest() {
		return digest;
	}

	/**
	 * Get the data hash to access the data
	 * @return the data hash
	 */
	public String getDataHash() {
		return dataHash;
	}

	/**
	 * Get the description of the data
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the additional metadata for the data
	 * @return the metadata
	 */
	public Map<String, String> getMetadata() {
		return metadata;
	}

	/**
	 * Get the upload timestamp
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * Get the name for the data
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the content type of the data
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Creates a list of this model
	 * @param parameterDataList the list of upload param data containing the description, metadata and name
	 * @param dataUploadResponseList the list of ipfs upload response containing the datahash and the timestamp uploaded
	 * @param digestList the list of computed digests
	 * @param contentTypeList the list of content types
	 * @return a list of instance of this model
	 */
	public static List<ProximaxDataModel> createList(List<UploadParameterData> parameterDataList, List<IpfsUploadResponse> dataUploadResponseList,
													 List<String> digestList, List<String> contentTypeList) {
		return IntStream.range(0, parameterDataList.size())
				.mapToObj(index -> create(parameterDataList.get(index), dataUploadResponseList.get(index).getDataHash(),
						digestList.get(index), contentTypeList.get(index), dataUploadResponseList.get(index).getTimestamp()))
				.collect(toList());
	}

	/**
	 * Creates an instance of this model
	 * @param parameterData an upload param data containing the description, metadata and name
	 * @param dataHash the data hash
	 * @param digest the computed digest
	 * @param contentType the content type
	 * @param timestamp the timestamp uploaded
	 * @return the instance of this model
	 */
	public static ProximaxDataModel create(RawDataModel parameterData, String dataHash, String digest, String contentType, Long timestamp) {
		return new ProximaxDataModel(digest, dataHash, parameterData.getDescription(), parameterData.getMetadata(),
				timestamp, parameterData.getName(), contentType);
	}
}
