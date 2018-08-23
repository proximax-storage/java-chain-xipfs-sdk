package io.proximax.upload;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static io.proximax.model.Constants.RESERVED_CONTENT_TYPES;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a file upload
 */
public class FileParameterData extends ByteArrayParameterData {

    private final File file;

    FileParameterData(File file, String description, String name, String contentType, Map<String, String> metadata) throws IOException {
        super(readFileToByteArray(file), description, name == null ? file.getName() : name, contentType, metadata);
        this.file = file;
    }

    /**
     * Get the file to upload
     * @return the file
     * @see FileParameterDataBuilder
     */
    public File getFile() {
        return file;
    }

    private static byte[] readFileToByteArray(File file) throws IOException {
        checkParameter(file != null, "file is required");
        checkParameter(file.isFile(), "file is not file");

        return FileUtils.readFileToByteArray(file);
    }

    /**
     * Start creating an instance of FileParameterData using the FileParameterDataBuilder
     * @param file the file to upload
     * @return the file parameter data builder
     */
    public static FileParameterDataBuilder create(File file) {
        return new FileParameterDataBuilder(file);
    }

    /**
     * This builder class creates the FileParameterData
     */
    public static class FileParameterDataBuilder extends AbstractParameterDataBuilder<FileParameterDataBuilder> {
        private File file;
        private String contentType;

        FileParameterDataBuilder(File file) {
            this.file = file;
        }

        /**
         * Set the content type for the data
         * @param contentType the content type
         * @return same instance of the builder class
         */
        public FileParameterDataBuilder contentType(String contentType) {
            checkParameter(!RESERVED_CONTENT_TYPES.contains(contentType), String.format("%s cannot be used as it is reserved", contentType));
            this.contentType = contentType;
            return this;
        }

        /**
         * Builds the FileParameterData
         * @return the file parameter data
         * @throws IOException when reading file fails
         */
        public FileParameterData build() throws IOException {
            return new FileParameterData(file, description, name, contentType, metadata);
        }
    }
}
