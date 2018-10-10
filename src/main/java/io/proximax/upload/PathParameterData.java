package io.proximax.upload;

import java.io.File;
import java.util.Map;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a path upload
 */
public class PathParameterData extends UploadParameterData {

    private final File path;

    private PathParameterData(File path, String description, String name, Map<String, String> metadata) {
        super(description, name, PATH_UPLOAD_CONTENT_TYPE, metadata);

        checkParameter(path != null, "path is required");
        checkParameter(path.isDirectory(), "path is not a directory ");

        this.path = path;
    }

    /**
     * Get the file path
     * @return the file path
     */
    public File getPath() {
        return path;
    }

    /**
     * Create instance by providing the file path
     * @param path the path to upload
     * @return the instance of this class
     */
    public static PathParameterData create(File path) {
        return create(path, null, null, null);
    }

    /**
     * Create instance by providing the file path
     * @param path the path to upload
     * @param description a searchable description attach on the upload
     * @param name a searchable name attach on the upload
     * @param metadata a searchable key-pair metadata attach on the upload
     * @return the instance of this class
     */
    public static PathParameterData create(File path, String description, String name, Map<String, String> metadata) {
        return new PathParameterData(path, description, name, metadata);
    }
}
