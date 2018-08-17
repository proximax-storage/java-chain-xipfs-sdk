package io.proximax.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The utility class for handling JSON serialization and deserialization
 */
public class JsonUtils {

	private static Gson gson;

	/**
	 * Construct an instance of this utility class
	 */
	private JsonUtils() {
	}

	/**
	 * Initializes the current Gson object if null and returns it. The Gson
	 * object has custom adapters to manage datatypes according to Facebook
	 * formats.
	 * 
	 * @return the current instance of Gson.
	 */
	private static Gson getGson() {
		if (gson == null) {
			// Creates the Gson object which will manage the information
			// received
			GsonBuilder builder = new GsonBuilder();

			gson = builder.create();
		}
		return gson;
	}

	/**
	 * Read a JSON into an object of given type
	 *
	 * @param <T>
	 *     		the generic type
	 * @param json
	 *            the string from which the object is to be deserialized.
	 * @param targetType
	 *            the target type
	 * @return an object of type T from the string. Returns null if json is
	 *         null.
	 * @see Gson#fromJson(String, Class)
	 */
	public static <T> T fromJson(String json, Class<T> targetType) {
		return getGson().fromJson(json, targetType);
	}

	/**
	 * Create a JSON representation of the object
	 *
	 * @param source
	 *            the object for which Json representation is to be created
	 *            setting for Gson .
	 * @return Json representation of src.
	 * @see Gson#toJson(Object)
	 */
	public static String toJson(Object source) {
		return getGson().toJson(source);
	}

}
