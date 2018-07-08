package io.proximax.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import io.proximax.utils.JsonUtils;

public class ProximaxMessage {

	
	private String digest;
	
	@SerializedName("root_data_hash")
	private String rootDataHash;
	private int type;
	private String data;
	@SerializedName("child_data_hash")
	private List<ProximaxChildMessage> messageFiles = new ArrayList<ProximaxChildMessage>();

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

	public List<ProximaxChildMessage> getMessageFiles() {
		return messageFiles;
	}

	public void setMessageFiles(List<ProximaxChildMessage> messageFiles) {
		this.messageFiles = messageFiles;
	}

	public void addChild(ProximaxChildMessage childMessage) {
		this.getMessageFiles().add(childMessage);
	}
	
	public String toPayload() {
		ProximaxMessagePayload payload = new ProximaxMessagePayload();
		payload.setData(this.data);
		payload.setDigest(this.digest);
		payload.setRootDataHash(this.rootDataHash);
		payload.setType(this.type);
		
		return JsonUtils.toJson(payload);
	}
}
