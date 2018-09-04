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

    private FileParameterData(File file, String description, String name, String contentType, Map<String, String> metadata) throws IOException {
        super(readFileToByteArray(file), description, name == null ? file.getName() : name, contentType, metadata);

        checkParameter(contentType == null || !RESERVED_CONTENT_TYPES.contains(contentType),
                String.format("%s cannot be used as it is reserved", contentType));

        this.file = file;
    }


    /**
     * Get the file to upload
     * @return the file
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
     * Create instance by providing the file
     * @param file the file to upload
     * @return the instance of this class
     * @throws IOException file read failures
     */
    public static FileParameterData create(File file) throws IOException {
        return create(file, null, null, null, null);
    }

    /**
     * Create instance by providing the file
     * @param file the file to upload
     * @param description a searchable description attach on the upload
     * @param name a searchable name attach on the upload
     * @param contentType the content type attach on the upload
     * @param metadata a searchable key-pair metadata attach on the upload
     * @throws IOException file read failures
     * @return the instance of this class
     */
    public static FileParameterData create(File file, String description, String name, String contentType, Map<String, String> metadata) throws IOException {
        return new FileParameterData(file, description, name, contentType, metadata);
    }

}
