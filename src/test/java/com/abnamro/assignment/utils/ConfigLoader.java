package com.abnamro.assignment.utils;

import io.restassured.internal.support.FileReader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * @author Soumya
 */
public class ConfigLoader {
    static final public String file_config = "./src/test/resources/config.properties";
    static final public String file_api_test_data = "./src/test/resources/apiTestData.json";

    public static JSONObject get_api_test_data() throws JSONException {
        return new JSONObject(FileReader.readToString(new File(file_api_test_data), "UTF-8"));
    }
}
