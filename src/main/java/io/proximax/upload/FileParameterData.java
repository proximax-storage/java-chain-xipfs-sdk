package io.proximax.upload;

import io.proximax.exceptions.GetByteStreamFailureException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static io.proximax.model.Constants.RESERVED_CONTENT_TYPES;
import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a file upload
 */
public class FileParameterData extends AbstractByteStreamParameterData {

    private final File file;

    private FileParameterData(File file, String description, String name, String contentType, Map<String, String> metadata) throws IOException {
        super(description, getDefaultName(file, name), contentType, metadata);

        checkParameter(file != null, "file is required");
        checkParameter(file.isFile(), "file is not file");

        this.file = file;
    }

    /**
     * Get the byte stream
     * @return the byte stream
     */
    @Override
    public InputStream getByteStream() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new GetByteStreamFailureException("Failed to open byte stream", e);
        }
    }

    /**
     * Get the file to upload
     * @return the file
     */
    public File getFile() {
        return file;
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

    private static String getDefaultName(File file, String name) {
        return name == null && file != null ? file.getName() : name;
    }

}
