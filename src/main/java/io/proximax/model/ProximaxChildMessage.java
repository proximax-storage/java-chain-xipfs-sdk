package io.proximax.model;

import com.google.gson.annotations.SerializedName;

public class ProximaxChildMessage {

	@SerializedName("data_hash")
	private String dataHash;
	private String data;
	private Long timestamp;
	private String name;
	private String type;

	public String getDataHash() {
		return dataHash;
	}

	public void setDataHash(String dataHash) {
		this.dataHash = dataHash;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
