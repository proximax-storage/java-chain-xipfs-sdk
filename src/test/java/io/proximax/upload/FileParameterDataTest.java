package io.proximax.upload;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static io.proximax.model.Constants.PATH_UPLOAD_CONTENT_TYPE;
import static io.proximax.testsupport.Constants.IMAGE_FILE;
import static io.proximax.testsupport.Constants.PATH_FILE;
import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContainingInOrder.arrayContaining;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class FileParameterDataTest {

    private static final File NON_EXISTENT_FILE = new File("src//test//resources//pdf_non_existent.pdf");

    @Test(expected = IllegalArgumentException.class)
    public void failWhenNullFile() throws IOException {
        FileParameterData.create((File) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenFileIsADirectory() throws IOException {
        FileParameterData.create(PATH_FILE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenFileDoesNotExist() throws IOException {
        FileParameterData.create(NON_EXISTENT_FILE);
    }

    @Test
    public void createWithFileOnly() throws IOException {
        final FileParameterData param = FileParameterData.create(IMAGE_FILE);

        assertThat(param, is(notNullValue()));
        assertThat(param.getFile(), is(IMAGE_FILE));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(param.getByteStream())),
                is(arrayContaining(ArrayUtils.toObject(FileUtils.readFileToByteArray(IMAGE_FILE)))));
        assertThat(param.getContentType(), is(nullValue()));
        assertThat(param.getDescription(), is(nullValue()));
        assertThat(param.getMetadata(), is(nullValue()));
        assertThat(param.getName(), is("test_image.png"));
    }

    @Test
    public void createWithCompleteDetails() throws IOException {
        final FileParameterData param = FileParameterData.create(IMAGE_FILE, "describe me",
                "name here", "text/plain", singletonMap("mykey", "myvalue"));

        assertThat(param, is(notNullValue()));
        assertThat(param.getFile(), is(IMAGE_FILE));
        assertThat(ArrayUtils.toObject(IOUtils.toByteArray(param.getByteStream())),
                is(arrayContaining(ArrayUtils.toObject(FileUtils.readFileToByteArray(IMAGE_FILE)))));
        assertThat(param.getContentType(), is("text/plain"));
        assertThat(param.getDescription(), is("describe me"));
        assertThat(param.getMetadata(), is(singletonMap("mykey", "myvalue")));
        assertThat(param.getName(), is("name here"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWhenContentTypeIsReservedExist() throws IOException {
        FileParameterData.create(IMAGE_FILE, null, null, PATH_UPLOAD_CONTENT_TYPE, null);
    }

}
