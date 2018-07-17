package io.proximax.upload;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class FileParameterData extends UploadParameterData {

    private final File file;

    FileParameterData(File file, String description, String name, String contentType, Map<String, String> metadata) throws IOException {
        super(readFileToByteArray(file), description, name == null ? file.getName() : name, contentType, metadata);
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    private static byte[] readFileToByteArray(File file) throws IOException {
        checkParameter(file != null, "file is required");

        return FileUtils.readFileToByteArray(file);
    }

    public static FileParameterDataBuilder create(File file) {
        return new FileParameterDataBuilder(file);
    }

    public static class FileParameterDataBuilder extends AbstractParameterDataBuilder<FileParameterDataBuilder> {
        private File file;

        FileParameterDataBuilder(File file) {
            this.file = file;
        }

        public FileParameterData build() throws IOException {
            return new FileParameterData(file, description, name, contentType, metadata);
        }
    }
}
