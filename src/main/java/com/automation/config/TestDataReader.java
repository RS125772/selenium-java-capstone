package com.automation.config;

import java.io.InputStream;
import java.util.Properties;

public class TestDataReader {

    private Properties prop; // Properties object to hold all test data values

    // Constructor - loads test data file when object is created
    public TestDataReader() {
        try {
            prop = new Properties(); // Initialize Properties object

            // Load testdata.properties file from resources folder
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("testdata/testdata.properties");

            // If file is not found, throw error immediately
            if (is == null) {
                throw new RuntimeException("testdata.properties not found");
            }

            // Load all key-value pairs into prop object
            prop.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load testdata.properties", e);
        }
    }

    // Method to fetch test data value using key
    public String getProperty(String key) {
        String value = prop.getProperty(key); // Get value from properties file

        // If key is missing, throw error to avoid test failures later
        if (value == null) {
            throw new RuntimeException("Test data not found: " + key);
        }

        return value.trim();
    }
}