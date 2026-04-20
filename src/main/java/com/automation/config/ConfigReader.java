package com.automation.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private Properties prop;

    public ConfigReader() {
        try {
            prop = new Properties(); // Initialize Properties object

            // Load config.properties file from classpath (inside resources folder)
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("config/config.properties");

            // If file is not found, throw error immediately
            if (is == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }

            prop.load(is); // Load all key-value pairs from file into prop object

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    // Method to fetch value using key (e.g., browser, url)
    public String getProperty(String key) {
        String value = prop.getProperty(key); // Get value from properties file

        // If key is not present, throw error to avoid silent failures
        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }

        return value.trim();
    }

    // Method to fetch value using key with default fallback
    public String getProperty(String key, String defaultValue) {
        String value = prop.getProperty(key);
        return value == null ? defaultValue : value.trim();
    }

    // Helper method to directly get integer values (useful for timeout, etc.)
    public int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key)); // Convert string value to integer
    }
}