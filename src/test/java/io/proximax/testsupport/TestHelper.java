package io.proximax.testsupport;

import io.proximax.upload.UploadResult;
import io.proximax.utils.JsonUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestHelper {

    public static final File TEST_DATA_JSON_FILE = new File("src//test/resources//test_data//test_data.json");
    private static Map<String, String> TEST_DATA_MAP = loadTestDataMap();

    public static void logAndSaveResult(UploadResult result, String testMethodName) {
        System.out.println("transaction hash: " + result.getTransactionHash());
        System.out.println("data hash: " + result.getData().getDataHash());
        System.out.println("data digest: " + result.getData().getDigest());

        TEST_DATA_MAP.put(testMethodName + ".transactionHash", result.getTransactionHash());
        TEST_DATA_MAP.put(testMethodName + ".dataHash", result.getData().getDataHash());
        TEST_DATA_MAP.put(testMethodName + ".digest", result.getData().getDigest());

        saveTestDataMap();
    }

    public static String getData(String testMethodName, String dataName) {
        return TEST_DATA_MAP.get(testMethodName + "." + dataName);
    }

    private static void saveTestDataMap() {
        try {
            FileUtils.writeStringToFile(TEST_DATA_JSON_FILE, JsonUtils.toJson(TEST_DATA_MAP));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> loadTestDataMap() {
        try {
            if (TEST_DATA_JSON_FILE.exists()) {
                final String testDataJson = FileUtils.readFileToString(new File("src//test/resources//test_data//test_data.json"));
                return JsonUtils.fromJson(testDataJson, Map.class);
            } else {
                return new HashMap<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
