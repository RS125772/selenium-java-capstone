package com.automation.config;

import java.io.InputStream;
import java.util.Properties;

public class TestDataReader {

    private static Properties prop;

    // Static block executes once automatically
    static {

        try {

            prop = new Properties();

            InputStream is =
                    TestDataReader.class
                            .getClassLoader()
                            .getResourceAsStream(
                                    "testdata/testdata.properties");

            if(is == null) {

                throw new RuntimeException(
                        "testdata.properties not found");
            }

            prop.load(is);

        } catch(Exception e) {

            throw new RuntimeException(
                    "Failed to load testdata.properties",
                    e);
        }
    }

    public static String getProperty(String key) {

        String value =
                prop.getProperty(key);

        if(value == null) {

            throw new RuntimeException(
                    "Test data not found: " + key);
        }

        return value.trim();
    }
}