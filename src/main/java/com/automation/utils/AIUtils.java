package com.automation.utils;

import com.automation.config.TestDataReader;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

public class AIUtils {

    private static final String API_KEY = TestDataReader.getProperty("open_api_key");

    public static String askAI(String prompt) {

        OpenAIClient client =
                OpenAIOkHttpClient.builder()
                        .apiKey(API_KEY)
                        .build();

        ChatCompletionCreateParams params =
                ChatCompletionCreateParams.builder()
                        .model(ChatModel.GPT_3_5_TURBO)
                        .addUserMessage(prompt)
                        .build();

        return client.chat()
                .completions()
                .create(params)
                .choices()
                .get(0)
                .message()
                .content()
                .orElse("No response received");
    }
}