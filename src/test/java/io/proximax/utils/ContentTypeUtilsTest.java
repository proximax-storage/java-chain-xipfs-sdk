package io.proximax.utils;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ContentTypeUtilsTest {

    private ContentTypeUtils unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new ContentTypeUtils();
    }

    @Test
    public void shouldUseProvidedContentType() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_small_file.txt"));
        final String result = unitUnderTest.detectContentType(bytes, "text/html").blockingFirst();

        assertThat(result, is("text/html"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenText() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_small_file.txt"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("text/plain"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenHtml() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_html.html"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("text/html"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenMov() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_file.mov"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("video/quicktime"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenImage() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_image.png"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("image/png"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenPdf() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_pdf_file_v2.pdf"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("application/pdf"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenZip() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_large_file.zip"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("application/zip"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenMp3() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_large_audio.mp3"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("audio/mpeg"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataWhenMp4() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_large_video.mp4"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("video/mp4"));
    }
}
