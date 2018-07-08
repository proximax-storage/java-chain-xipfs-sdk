package io.proximax.builder;

import io.proximax.model.ProximaxChildMessage;

public class ChildMessageParameter extends ProximaxChildMessage {
	
	private byte[] resource;

	public byte[] getResource() {
		return resource;
	}

	public void setResource(byte[] resource) {
		this.resource = resource;
	}
	
}
