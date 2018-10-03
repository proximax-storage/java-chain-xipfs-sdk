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
package io.proximax.testsupport;

import com.codahale.shamir.Scheme;

import java.io.File;
import java.util.Map;

public class Constants {

    public static final String TEST_PRIVATE_KEY_1 = "8374B5915AEAB6308C34368B15ABF33C79FD7FEFC0DEAF9CC51BA57F120F1190";
    public static final String TEST_PUBLIC_KEY_1 = "9E7930144DA0845361F650BF78A36791ABF2577E251706ECA45480998FE61D18";
    public static final String TEST_ADDRESS_1 = "SBI5EBRHONTC6FSO4DNKKBDI7SBUNU4L2W76WUUD";

    public static final String TEST_PRIVATE_KEY_2 = "369CB3195F88A16F8326DABBD37DA5F8458B55AA5DA6F7E2F756A12BE6CAA546";
    public static final String TEST_PUBLIC_KEY_2 = "8E1A94D534EA6A3B02B0B967701549C21724C7644B2E4C20BF15D01D50097ACB";
    public static final String TEST_ADDRESS_2 = "SCW3N4V7LQ4UQBJK3PZGV3YGKMC67LLXYADF765M";

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
