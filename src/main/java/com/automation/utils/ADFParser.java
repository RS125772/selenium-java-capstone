package com.automation.utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class ADFParser {

    public static String parseDescription(JSONObject descriptionObj) {

        StringBuilder parsedText =
                new StringBuilder();

        JSONArray contentArray =
                descriptionObj.getJSONArray("content");

        extractText(contentArray, parsedText);

        return parsedText.toString();
    }

    private static void extractText(
            JSONArray array,
            StringBuilder builder) {

        for (int i = 0; i < array.length(); i++) {

            JSONObject item =
                    array.getJSONObject(i);

            // Extract direct text
            if(item.has("text")) {

                builder.append(
                        item.getString("text"))
                        .append(" ");
            }

            // Handle hardBreak
            if(item.has("type") &&
                    item.getString("type")
                            .equals("hardBreak")) {

                builder.append("\n");
            }

            // Recursive parsing
            if(item.has("content")) {

                extractText(
                        item.getJSONArray("content"),
                        builder);

                builder.append("\n");
            }
        }
    }
}