package io.proximax.upload;

import io.proximax.utils.CollectionUtils;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

public class FilesAsZipParameterData extends UploadParameterData {

    private final List<File> files;

    FilesAsZipParameterData(List<File> files, String description, String name, Map<String, String> metadata) throws IOException {
        super(zipFiles(files), description, name, "application/zip", metadata);
        this.files = files;
    }

    public List<File> getFiles() {
        return files;
    }

    private static byte[] zipFiles(List<File> files) throws IOException {
        checkParameter(CollectionUtils.isNotEmpty(files), "files cannot be null or empty");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {

            for (File file : files) {
                ZipEntry entry = new ZipEntry(file.getName());

                zos.putNextEntry(entry);
                zos.write(FileUtils.readFileToByteArray(file));
                zos.closeEntry();
            }

            return baos.toByteArray();
        }
    }

    public static FilesAsZipParameterDataBuilder create(List<File> files) {
        return new FilesAsZipParameterDataBuilder(files);
    }

    public static class FilesAsZipParameterDataBuilder extends AbstractParameterDataBuilder<FilesAsZipParameterDataBuilder> {
        private List<File> files;

        FilesAsZipParameterDataBuilder(List<File> files) {
            this.files = files;
        }

        public FilesAsZipParameterData build() throws IOException {
            return new FilesAsZipParameterData(files, description, name, metadata);
        }
    }
}
