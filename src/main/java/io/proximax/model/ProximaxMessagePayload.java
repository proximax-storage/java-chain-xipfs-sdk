package io.proximax.model;

import com.google.gson.annotations.SerializedName;

public class ProximaxMessagePayload {
	private String digest;

	@SerializedName("root_data_hash")
	private String rootDataHash;
	private int type;
	private String data;

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getRootDataHash() {
		return rootDataHash;
	}

	public void setRootDataHash(String rootDataHash) {
		this.rootDataHash = rootDataHash;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
