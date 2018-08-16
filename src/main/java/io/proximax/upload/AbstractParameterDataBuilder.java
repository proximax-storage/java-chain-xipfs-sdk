package io.proximax.upload;

import java.util.Map;

/**
 * The abstract builder class that defines the common fields when building an upload data
 */
public abstract class AbstractParameterDataBuilder<T> {

	protected String description;
	protected Map<String, String> metadata;
	protected String name;
	protected String contentType;

	protected AbstractParameterDataBuilder() {
	}

	/**
	 * Set the description for the data
	 * @param description the description
	 * @return same instance of the builder class
	 */
	public T description(String description) {
		this.description = description;
		return (T) this;
	}

	/**
	 * Set the additional metadata for the data
	 * @param metadata the metadata
	 * @return same instance of the builder class
	 */
	public T metadata(Map<String, String> metadata) {
		this.metadata = metadata;
		return (T) this;
	}

	/**
	 * Set the name for the data
	 * @param name the name
	 * @return same instance of the builder class
	 */
	public T name(String name) {
		this.name = name;
		return (T) this;
	}

	/**
	 * Set the content type for the data
	 * @param contentType the content type
	 * @return same instance of the builder class
	 */
	public T contentType(String contentType) {
		this.contentType = contentType;
		return (T) this;
	}
}
