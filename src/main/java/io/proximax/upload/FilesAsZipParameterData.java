/*
 * Copyright 2018 ProximaX Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.proximax.upload;

import io.proximax.exceptions.ParamDataCreationException;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static io.proximax.utils.ParameterValidationUtils.checkParameter;

/**
 * This model class is one type of the upload parameter data that defines a zip upload
 */
public class FilesAsZipParameterData extends AbstractByteStreamParameterData {

    private final List<File> files;
    private final byte[] zipData;

    private FilesAsZipParameterData(List<File> files, String description, String name, Map<String, String> metadata) {
        super(description, name, "application/zip", metadata);

        checkParameter(files != null && !files.isEmpty(), "files cannot be null or empty");
        checkParameter(files.stream().allMatch(File::isFile), "not all files are file");

        this.files = files;
        zipData = zipFiles(files);
    }

    /**
     * Get the byte stream
     *
     * @return the byte stream
     */
    @Override
    public InputStream getByteStream() {
        return new ByteArrayInputStream(zipData);
    }

    /**
     * Get the list of files to upload as zip
     *
     * @return the list of files to zip
     */
    public List<File> getFiles() {
        return files;
    }

    private static byte[] zipFiles(List<File> files) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {

            for (File file : files) {
                ZipEntry entry = new ZipEntry(file.getName());

                zos.putNextEntry(entry);
                zos.write(FileUtils.readFileToByteArray(file));
                zos.closeEntry();
            }

            return baos.toByteArray();
        } catch (Exception e) {
            throw new ParamDataCreationException("Failed to create zip file", e);
        }
    }

    /**
     * Create instance by providing the list of files
     *
     * @param files the list of files to upload as zip
     * @return the instance of this class
     */
    public static FilesAsZipParameterData create(List<File> files) {
        return create(files, null, null, null);
    }

    /**
     * Create instance by providing the list of files
     *
     * @param files       the list of files to upload as zip
     * @param description a searchable description attach on the upload
     * @param name        a searchable name attach on the upload
     * @param metadata    a searchable key-pair metadata attach on the upload
     * @return the instance of this class
     */
    public static FilesAsZipParameterData create(List<File> files, String description, String name, Map<String, String> metadata) {
        return new FilesAsZipParameterData(files, description, name, metadata);
    }
}
