package io.proximax.testsupport;

import com.codahale.shamir.Scheme;

import java.io.File;
import java.util.Map;

public class Constants {

    public static final String TEST_PRIVATE_KEY_1 = "322EB09F00E1F6AE6ABA96977E7676575E315CBDF79A83164FFA03B7CAE88927";
    public static final String TEST_PUBLIC_KEY_1 = "2EEDB614740F60B11F3E4EC387388D8826CDFF33C0B0DACB9BA0BB8793DEBF6E";
    public static final String TEST_ADDRESS_1 = "VAFHIY4GYEAQUZK4ECTAZD5R3JU2KFWEPW54ZBHA";

    public static final String TEST_PRIVATE_KEY_2 = "E678B975F1AC5C8D88A9673287EE60840161B0117CF320CF2EBF384C17C71F9C";
    public static final String TEST_PUBLIC_KEY_2 = "44AFFBEFF7EE8AFDE907EAD933C88374DE22F359CCBB6575BB14A8CB96B11C90";
    public static final String TEST_ADDRESS_2 = "VC6LV4EP2MT3I5AV76IKVTPVAZRJUQDMKSABLE2M";

    public static final String TEST_STRING = "Nam a porta augue. Fusce ut tempus elit, vel vehicula nulla. " +
            "Etiam sed est purus. Sed consectetur risus vel velit ultricies gravida. " +
            "Ut semper augue massa, vitae dignissim elit luctus vel. " +
            "Pellentesque in placerat nisl, interdum rutrum dui. " +
            "In ex tortor, condimentum et odio ut, feugiat pretium nibh. " +
            "Fusce ac iaculis metus. Duis accumsan ac nunc in maximus. " +
            "Quisque varius lobortis tortor, a facilisis nisi convallis ut. " +
            "Curabitur vitae consectetur risus, in fermentum magna. " +
            "In non lectus interdum massa tempor dapibus. " +
            "Etiam eu malesuada turpis, tempor gravida mi. Ut vitae dapibus tellus, at viverra nibh. " +
            "Nam bibendum, urna condimentum ultrices dapibus, nisl quam accumsan urna, id feugiat diam ligula rhoncus elit.";

    public static final String TEST_FILES_FOLDER = "src//test//resources//test_files//";
    public static final File TEST_PDF_FILE_1 = new File(TEST_FILES_FOLDER + "test_pdf_file_1.pdf");
    public static final File TEST_PDF_FILE_2 = new File(TEST_FILES_FOLDER + "test_pdf_file_2.pdf");
    public static final File TEST_ZIP_FILE = new File(TEST_FILES_FOLDER + "test_zip_file.zip");
    public static final File TEST_VIDEO_MP4_FILE = new File(TEST_FILES_FOLDER + "test_large_video.mp4");
    public static final File TEST_VIDEO_MOV_FILE = new File(TEST_FILES_FOLDER + "test_video_file.mov");
    public static final File TEST_AUDIO_MP3_FILE = new File(TEST_FILES_FOLDER + "test_audio_file.mp3");
    public static final File TEST_TEXT_FILE = new File(TEST_FILES_FOLDER + "test_text_file.txt");
    public static final File TEST_HTML_FILE = new File(TEST_FILES_FOLDER + "test_html_file.html");
    public static final File TEST_IMAGE_PNG_FILE = new File(TEST_FILES_FOLDER + "test_image_file.png");
    public static final File TEST_PATH_FILE = new File("src//test//resources//test_path");

    public static final String TEST_PASSWORD = "hkcymenwcxpzkoyowuagcuhvrhavtdcrxbfqganecoxuirxekq";

    public static final byte[] TEST_SHAMIR_SECRET = ("dhsakdhsauihcxznmneywquidkjsanjcbnxztyduisyaheqkwjncbxzcgyuiagsdiujasodjk" +
            "wqehqtsyiudhsandmbsamnbcxzygcahgisudhasnmbdmnasbcysagciugxziuhkjsbdamndnmsabfgaduishadshakdnsakdbsajbeh" +
            "wqyuieyqwiueyqwohdsanlnalsfjkhgkzgmnbcmnxzbhjgdsajgduisayiuyewquehwqkjbeqwnbdmnabsdabjshgdasudhgsuakghk" +
            "cbxzcbajsbdkasjgkjhwgquegqwbzmcbmzxn").getBytes();
    public static final int TEST_SHAMIR_SECRET_TOTAL_SHARES = 10;
    public static final int TEST_SHAMIR_SECRET_THRESHOLD = 3;
    public static final Scheme TEST_SHAMIR_SCHEME = Scheme.of(TEST_SHAMIR_SECRET_TOTAL_SHARES, TEST_SHAMIR_SECRET_THRESHOLD);
    public static final Map<Integer, byte[]> TEST_SHAMIR_SECRET_SHARES = TEST_SHAMIR_SCHEME.split(TEST_SHAMIR_SECRET);
}
