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

    PathParameterData(File path, String description, String name, Map<String, String> metadata) {
        super(description, name, PATH_UPLOAD_CONTENT_TYPE, metadata);

        checkParameter(path != null, "path is required");
        checkParameter(path.isDirectory(), "path is not a directory ");

        this.path = path;
    }

    /**
     * Get file the path
     * @return the file path
     */
    public File getPath() {
        return path;
    }

    /**
     * Start creating an instance of PathParameterData using the PathParameterDataBuilder
     * @param path the path to upload
     * @return the path parameter data builder
     */
    public static PathParameterDataBuilder create(File path) {
        return new PathParameterDataBuilder(path);
    }

    /**
     * This builder class creates the PathParameterDataBuilder
     */
    public static class PathParameterDataBuilder extends AbstractParameterDataBuilder<PathParameterDataBuilder> {
        private File path;

        PathParameterDataBuilder(File path) {
            this.path = path;
        }

        /**
         * Builds the PathParameterData
         * @return the path parameter
         */
        public PathParameterData build() {
            return new PathParameterData(path, description, name, metadata);
        }
    }
}
