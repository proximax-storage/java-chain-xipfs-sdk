package io.proximax.utils;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ContentTypeUtilsTest {

    private ContentTypeUtils unitUnderTest;

    @Before
    public void setUp() {
        unitUnderTest = new ContentTypeUtils();
    }

    @Test
    public void shouldUseProvidedContentTypeOnDetectContentType() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_small_file.txt"));
        final String result = unitUnderTest.detectContentType(bytes, "text/html").blockingFirst();

        assertThat(result, is("text/html"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataOnDetectContentTypeWhenText() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_small_file.txt"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("text/plain"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataOnDetectContentTypeWhenHtml() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_html.html"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("text/html"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataOnDetectContentTypeWhenMov() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_file.mov"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("video/quicktime"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataOnDetectContentTypeWhenImage() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_image.png"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("image/png"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataOnDetectContentTypeWhenPdf() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_pdf_file_v2.pdf"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("application/pdf"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataOnDetectContentTypeWhenZip() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_large_file.zip"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("application/zip"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataOnDetectContentTypeWhenMp3() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_large_audio.mp3"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("audio/mpeg"));
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataOnDetectContentTypeWhenMp4() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_large_video.mp4"));
        final String result = unitUnderTest.detectContentType(bytes, null).blockingFirst();

        assertThat(result, is("video/mp4"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDetectContentTypeForListWhenNullDataList() throws IOException {
        unitUnderTest.detectContentTypeForList(null, asList("text/plain"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failOnDetectContentTypeForListWhenNullContentTypeList() throws IOException {
        final byte[] bytes = FileUtils.readFileToByteArray(new File("src//test//resources//test_small_file.txt"));

        unitUnderTest.detectContentTypeForList(asList(bytes), null);
    }

    @Test
    public void shouldIdentifyContentTypeBasedOnDataOnDetectContentTypeForList() throws IOException {
        final byte[] bytes1 = FileUtils.readFileToByteArray(new File("src//test//resources//test_small_file.txt"));
        final byte[] bytes2 = FileUtils.readFileToByteArray(new File("src//test//resources//test_large_video.mp4"));
        final byte[] bytes3 = FileUtils.readFileToByteArray(new File("src//test//resources//test_large_audio.mp3"));
        final List<byte[]> dataList = asList(bytes1, bytes2, bytes3);
        final List<String> contentTypeList = asList(null, null, null);

        final List<String> results = unitUnderTest.detectContentTypeForList(dataList, contentTypeList).blockingFirst();

        assertThat(results, is(notNullValue()));
        assertThat(results, hasSize(3));
        assertThat(results, contains("text/plain", "video/mp4", "audio/mpeg"));
    }

    @Test
    public void shouldIdentifySomeContentTypeBasedOnDataOnDetectContentTypeForList() throws IOException {
        final byte[] bytes1 = FileUtils.readFileToByteArray(new File("src//test//resources//test_small_file.txt"));
        final byte[] bytes2 = FileUtils.readFileToByteArray(new File("src//test//resources//test_large_video.mp4"));
        final byte[] bytes3 = FileUtils.readFileToByteArray(new File("src//test//resources//test_large_audio.mp3"));
        final List<byte[]> dataList = asList(bytes1, bytes2, bytes3);
        final List<String> contentTypeList = asList(null, "video/mpeg", null);

        final List<String> results = unitUnderTest.detectContentTypeForList(dataList, contentTypeList).blockingFirst();

        assertThat(results, is(notNullValue()));
        assertThat(results, hasSize(3));
        assertThat(results, contains("text/plain", "video/mpeg", "audio/mpeg"));
    }
}
