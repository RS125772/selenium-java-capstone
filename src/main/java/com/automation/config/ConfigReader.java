package com.automation.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private Properties prop;

    public ConfigReader() {
        try {
            prop = new Properties();

            // Load from classpath (recommended)
            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream("config/config.properties");

            if (is == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }

            prop.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public String getProperty(String key) {
        String value = prop.getProperty(key);

        if (value == null) {
            throw new RuntimeException("Property not found: " + key);
        }

        return value.trim();
    }

    // Optional helper (very useful)
    public int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
}