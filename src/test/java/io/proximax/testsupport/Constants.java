package io.proximax.testsupport;

import com.codahale.shamir.Scheme;

import java.io.File;
import java.util.Map;

public class Constants {
    public static final String IPFS_MULTI_ADDRESS = "/ip4/127.0.0.1/tcp/5001";
//    public static final String BLOCKCHAIN_ENDPOINT_URL = "http://catapult.internal.proximax.io:3000";
    public static final String BLOCKCHAIN_ENDPOINT_URL = "http://127.0.0.1:3000";

    // OLD nemesis accounts on catapult.internal.proximax.io:3000
//    public static final String PRIVATE_KEY_1 = "CDB825EBFED7ABA031E19AB6A91B637E5A6B13DACF50F0EA579885F68BED778C";
//    public static final String PUBLIC_KEY_1 = "0BB0FC937EF6C10AD16ABCC3FF4A2848F16BF360A98D64140E7674F31702903B";
//    public static final String ADDRESS_1 = "SDROED2EKLFO3WNGK2VADE7QVENDZBK5JUKNAGME";
//
//    public static final String PRIVATE_KEY_2 = "1A5B81AE8830B8A79232CD366552AF6496FE548B4A23D4173FEEBA41B8ABA81F";
//    public static final String PUBLIC_KEY_2 = "E9F6576AF9F05E6738CD4E55B875A823CC75B4E8AE8984747DF7B235685C1577";
//    public static final String ADDRESS_2 = "SBRHESWCLX3VGQ6CHCZNKDN6DT7GLS6CZKJXCT5F";
//
//    public static final String PRIVATE_KEY_3 = "08871D3EB4CF3D6695A61E8E1B60DC64DCC9EED40F33D4848BF9079168CCD4A4";
//    public static final String PUBLIC_KEY_3 = "ACD7CD0CA3FC46CE1A8AA8CFF34D4096E349B01828D5903E9A67A5E5F27785A4";
//    public static final String ADDRESS_3 = "SCASIIAPS6BSFEC66V6MU5ZGEVWM53BES5GYBGLE";

    public static final String PRIVATE_KEY_1 = "46210658B3FBA043F531AC4BC32F0AC979948C88A37AE3F3D21CE27FC276FDF3";
    public static final String PUBLIC_KEY_1 = "335B5A1AAD2BB89BBC8A4C901BFA52DA766613D2E59F98A095C029528056DDD8";
    public static final String ADDRESS_1 = "SCPPVHCTAGRNWK2X62IHMCR52M3ZEIOH6HAPCEJJ";

    public static final String PRIVATE_KEY_2 = "673847298DC0BC051B1BC14D19A95F8012FB61D2471FC69EC0B3131F08892A7A";
    public static final String PUBLIC_KEY_2 = "30A9BFBC640BB37B390395896E9371E4788193EA07495EA0874D9CD995830BD5";
    public static final String ADDRESS_2 = "SCCVDX3QX5646MSDKAQI4MSKFIT3BSCBF2BX7V4L";

    public static final String PRIVATE_KEY_3 = "B5E4B4C8A0661BDC879ECF401E9C7BCD7F9AB37730F9A9DB0D7B23EBCCD6C7E0";
    public static final String PUBLIC_KEY_3 = "1AFCA4C1EA40B1E12E5EE3881042E2648C96CFF494CD517152FA73CC86BB3DA4";
    public static final String ADDRESS_3 = "SDFVXPCPH2G5LN6LIOEUXVCLJOESSKMZGVZUZHDH";

    public static final String STRING_TEST = "The quick brown fox jumps over the lazy dog.";

    public static final File PDF_FILE1 = new File("src//test//resources//test_pdf_file_v1.pdf");
    public static final File PDF_FILE2 = new File("src//test//resources//test_pdf_file_v2.pdf");
    public static final File LARGE_FILE = new File("src//test//resources//test_large_file.zip");
    public static final File ZIP_FILE = new File("src//test//resources//zip_file.zip");
    public static final File LARGE_VIDEO_MP4_FILE = new File("src//test//resources//test_large_video.mp4");
    public static final File SMALL_VIDEO_MOV_FILE = new File("src//test//resources//test_file.mov");
    public static final File SMALL_FILE = new File("src//test//resources//test_small_file.txt");
    public static final File HTML_FILE = new File("src//test//resources//test_html.html");
    public static final File IMAGE_FILE = new File("src//test//resources//test_image.png");

    public static final String PASSWORD	= "hkcymenwcxpzkoyowuagcuhvrhavtdcrxbfqganecoxuirxekq";

    public static final byte[] SHAMIR_SECRET = ("dhsakdhsauihcxznmneywquidkjsanjcbnxztyduisyaheqkwjncbxzcgyuiagsdiujasodjk" +
            "wqehqtsyiudhsandmbsamnbcxzygcahgisudhasnmbdmnasbcysagciugxziuhkjsbdamndnmsabfgaduishadshakdnsakdbsajbeh" +
            "wqyuieyqwiueyqwohdsanlnalsfjkhgkzgmnbcmnxzbhjgdsajgduisayiuyewquehwqkjbeqwnbdmnabsdabjshgdasudhgsuakghk" +
            "cbxzcbajsbdkasjgkjhwgquegqwbzmcbmzxn").getBytes();

    public static final int SHAMIR_SECRET_TOTAL_PART_COUNT = 10;

    public static final int SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD = 3;

    public static final Scheme SCHEME = Scheme.of(SHAMIR_SECRET_TOTAL_PART_COUNT, SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD);

    public static final Map<Integer, byte[]> SHAMIR_SECRET_PARTS = SCHEME.split(SHAMIR_SECRET);
}
