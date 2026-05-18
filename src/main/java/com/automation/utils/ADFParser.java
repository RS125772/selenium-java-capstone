package com.automation.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class ADFParser {

    // Method to parse Jira Atlassian Document Format (ADF)
    // and convert it into readable plain text
    public static String parseDescription(JSONObject descriptionObj) {

        // Create StringBuilder to store extracted text
        StringBuilder parsedText = new StringBuilder();

        // Get content array from Jira ADF JSON object
        JSONArray contentArray = descriptionObj.getJSONArray("content");

        // Start recursive extraction of text
        extractText(contentArray, parsedText);

        // Return final parsed text
        return parsedText.toString();
    }

    // Recursive method to extract text from nested ADF structure
    private static void extractText(JSONArray array, StringBuilder builder) {

        // Loop through all JSON objects inside array
        for (int i = 0; i < array.length(); i++) {

            // Get current JSON object
            JSONObject item = array.getJSONObject(i);

            // Check if current object contains direct text
            if(item.has("text")) {
                // Append extracted text into StringBuilder
                builder.append(item.getString("text")).append(" ");
            }

            // Check if current object type is hardBreak
            // hardBreak represents new line in Jira description
            if(item.has("type") && item.getString("type").equals("hardBreak")) {

                // Add new line in parsed text
                builder.append("\n");
            }

            // Check if current object contains nested content
            if(item.has("content")) {
                // Recursively parse nested content array
                extractText(item.getJSONArray("content"), builder);
                // Add new line after nested content parsing
                builder.append("\n");
            }
        }
    }
}