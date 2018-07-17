package io.proximax.testsupport;

import io.proximax.upload.UploadResult;
import io.proximax.utils.JsonUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.IntStream;

public class TestHelper {

    private static Map<String, String> TEST_DATA_MAP = loadTestDataMap();

    public static void logAndSaveResult(UploadResult result, String testMethodName) {
        System.out.println("transaction hash: " + result.getTransactionHash());
        System.out.println("digest: " + result.getDigest());
        System.out.println("root data hash: " + result.getRootDataHash());
        result.getRootData().getDataList().stream().forEach(data ->  {
            System.out.println("data hash: " + data.getDataHash());
            System.out.println("data digest: " + data.getDigest());
        });

        final String testDataPrefix = testMethodName;
        TEST_DATA_MAP.putIfAbsent(testDataPrefix + ".rootDataHash", result.getRootDataHash());
        TEST_DATA_MAP.putIfAbsent(testDataPrefix + ".transactionHash", result.getTransactionHash());
        TEST_DATA_MAP.putIfAbsent(testDataPrefix + ".rootDataDigest", result.getDigest());
        IntStream.range(0, result.getRootData().getDataList().size()).forEach(index -> {
            TEST_DATA_MAP.putIfAbsent(testDataPrefix + ".dataList[" + index + "].dataHash",
                    result.getRootData().getDataList().get(index).getDataHash());
            TEST_DATA_MAP.putIfAbsent(testDataPrefix + ".dataList[" + index + "].digest",
                    result.getRootData().getDataList().get(index).getDigest());
        });

        saveTestDataMap();
    }

    public static String getData(String testMethodName, String dataName) {
        return TEST_DATA_MAP.get(testMethodName + "." + dataName);
    }

    public static void saveTestDataMap() {
        try {
            FileUtils.writeStringToFile(new File("src//test/resources//test_data//test_data.json"), JsonUtils.toJson(TEST_DATA_MAP));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> loadTestDataMap() {
        try {
            final String testDataJson = FileUtils.readFileToString(new File("src//test/resources//test_data//test_data.json"));
            return JsonUtils.fromJson(testDataJson, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
