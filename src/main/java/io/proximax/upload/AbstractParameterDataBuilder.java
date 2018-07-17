package io.proximax.upload;

import java.util.Map;

public abstract class AbstractParameterDataBuilder<T> {

	protected String description;
	protected Map<String, String> metadata;
	protected String name;
	protected String contentType;

	protected AbstractParameterDataBuilder() {
	}

	public T description(String description) {
		this.description = description;
		return (T) this;
	}

	public T metadata(Map<String, String> metadata) {
		this.metadata = metadata;
		return (T) this;
	}

	public T name(String name) {
		this.name = name;
		return (T) this;
	}

	public T contentType(String contentType) {
		this.contentType = contentType;
		return (T) this;
	}
}
