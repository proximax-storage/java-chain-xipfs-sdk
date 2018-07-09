package io.proximax.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import io.ipfs.api.IPFS;
import io.ipfs.api.NamedStreamable;
import io.proximax.model.ProximaxChildMessage;
import io.proximax.model.ProximaxMessage;
import io.proximax.privacy.strategy.PrivacyStrategy;
import io.proximax.utils.JsonUtils;

public class UploadPayloadBuilder {

	private UploadPayloadBuilder() {

	}

	public static IData create(IPFS ipfsConnection) {
		return new UploadPayloadBuilder.Builder(ipfsConnection);
	}
	
	public static IData create(String ipfsConnection) {
		return new UploadPayloadBuilder.Builder(ipfsConnection);
	}
	public interface IData {
		IPrivacyType data(String data);

	}

	public interface IPrivacyType {
		IByteFile privacyType(PrivacyStrategy privacyType);
	}
	public interface IByteFile {

		IByteFile addFileOrResource(byte[] resource);

		IByteFile addFileOrResource(byte[] resource, String name);

		IByteFile addFileOrResource(byte[] resource, String name, String data);
		
		IByteFile addFileOrResource(byte[] resource, String name, String data, String type);

		IByteFile addFileOrResource(File resource, String data) throws IOException;
		
		ProximaxMessage build() throws IOException;

	}


	private static class Builder implements IData, IPrivacyType, IByteFile {

		List<ChildMessageParameter> children = new ArrayList<ChildMessageParameter>();
		private String data;
		private String privacyType;
		private IPFS ipfs;
		
		public Builder(IPFS ipfsConnection) {
			ipfs = ipfsConnection;
		}
		
		public Builder(String ipfsConnection) {
			ipfs = new IPFS(ipfsConnection);
		}
		@Override
		public IPrivacyType data(String data) {
			this.data = data;
			return this;
		}

		@Override
		public IByteFile privacyType(PrivacyStrategy privacyType) {
			this.privacyType = String.valueOf(privacyType.getValue());
			return this;
		}

		@Override
		public IByteFile addFileOrResource(byte[] resource, String name) {
			ChildMessageParameter child = new ChildMessageParameter();
			child.setName(name);
			child.setResource(resource);
			children.add(child);
			return this;
		}

		@Override
		public IByteFile addFileOrResource(byte[] resource, String name, String data) {
			ChildMessageParameter child = new ChildMessageParameter();
			child.setName(name);
			child.setResource(resource);
			children.add(child);
			return this;
		}

		@Override
		public IByteFile addFileOrResource(File resource, String data) throws IOException {
			ChildMessageParameter child = new ChildMessageParameter();
			child.setName(resource.getName());
			child.setResource(FileUtils.readFileToByteArray(resource));
			child.setData(data);
			children.add(child);
			return this;
		}

		@Override
		public IByteFile addFileOrResource(byte[] resource) {
			ChildMessageParameter child = new ChildMessageParameter();
			child.setResource(resource);
			children.add(child);
			return this;
		}

		@Override
		public ProximaxMessage build() throws IOException {

			// For testing only
//			IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
			ProximaxMessage message = new ProximaxMessage();
			List<ProximaxChildMessage> childMessageGroup = new ArrayList<ProximaxChildMessage>();
			for (ChildMessageParameter child : children) {
				NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(child.getResource());
				ProximaxChildMessage childMessage = new ProximaxChildMessage();
				childMessage.setDataHash(ipfs.add(file).get(0).hash.toBase58());
				childMessage.setData(child.getData());
				childMessage.setName(child.getName());
				childMessage.setType(child.getType());
				childMessage.setTimestamp(System.currentTimeMillis());
				childMessageGroup.add(childMessage);
				message.addChild(childMessage);
			}

			message.setData(this.data);
			message.setType(Integer.valueOf(this.privacyType));
			message.setRootDataHash(
					ipfs.add(new NamedStreamable.ByteArrayWrapper(JsonUtils.toJson(childMessageGroup).getBytes()))
							.get(0).hash.toBase58());
			
			
			
			return message;
		}

		@Override
		public IByteFile addFileOrResource(byte[] resource, String name, String data, String type) {
			ChildMessageParameter child = new ChildMessageParameter();
			child.setResource(resource);
			child.setData(data);
			child.setType(type);
			children.add(child);
			return this;
		}
	}
}
