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

    public static final String PRIVATE_KEY_1 = "8374B5915AEAB6308C34368B15ABF33C79FD7FEFC0DEAF9CC51BA57F120F1190";
    public static final String PUBLIC_KEY_1 = "9E7930144DA0845361F650BF78A36791ABF2577E251706ECA45480998FE61D18";
    public static final String ADDRESS_1 = "SBI5EBRHONTC6FSO4DNKKBDI7SBUNU4L2W76WUUD";

    public static final String PRIVATE_KEY_2 = "369CB3195F88A16F8326DABBD37DA5F8458B55AA5DA6F7E2F756A12BE6CAA546";
    public static final String PUBLIC_KEY_2 = "8E1A94D534EA6A3B02B0B967701549C21724C7644B2E4C20BF15D01D50097ACB";
    public static final String ADDRESS_2 = "SCW3N4V7LQ4UQBJK3PZGV3YGKMC67LLXYADF765M";

    public static final String PRIVATE_KEY_3 = "49DCE5457D6D83983FB4C28F0E58668DA656F8BB46AAFEEC800EBD420E1FDED5";
    public static final String PUBLIC_KEY_3 = "353BCDAF409724F9E3F7D1246E91BFD3EC1782D9C04D72C0C162F4B346126E45";
    public static final String ADDRESS_3 = "SB262OC4KLUDBORVEOHMZDPOHRX3PR3JFAPOAEU7";

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
    public static final File PATH_FILE = new File("src//test//resources//test_path");

    public static final String PASSWORD	= "hkcymenwcxpzkoyowuagcuhvrhavtdcrxbfqganecoxuirxekq";

    public static final byte[] SHAMIR_SECRET = ("dhsakdhsauihcxznmneywquidkjsanjcbnxztyduisyaheqkwjncbxzcgyuiagsdiujasodjk" +
            "wqehqtsyiudhsandmbsamnbcxzygcahgisudhasnmbdmnasbcysagciugxziuhkjsbdamndnmsabfgaduishadshakdnsakdbsajbeh" +
            "wqyuieyqwiueyqwohdsanlnalsfjkhgkzgmnbcmnxzbhjgdsajgduisayiuyewquehwqkjbeqwnbdmnabsdabjshgdasudhgsuakghk" +
            "cbxzcbajsbdkasjgkjhwgquegqwbzmcbmzxn").getBytes();

    public static final int SHAMIR_SECRET_TOTAL_PART_COUNT = 10;

    public static final int SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD = 3;

    public static final Scheme SHAMIR_SCHEME = Scheme.of(SHAMIR_SECRET_TOTAL_PART_COUNT, SHAMIR_SECRET_MINIMUM_PART_COUNT_TO_BUILD);

    public static final Map<Integer, byte[]> SHAMIR_SECRET_PARTS = SHAMIR_SCHEME.split(SHAMIR_SECRET);
}
