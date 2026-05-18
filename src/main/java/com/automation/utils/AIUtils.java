package com.automation.utils;

import com.automation.config.TestDataReader;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

public class AIUtils {

    // Read OpenAI API key from testData.properties file. This helps avoid hardcoding sensitive API keys directly in code
    private static final String API_KEY = TestDataReader.getProperty("open_api_key");

    // Common reusable method to send prompt to AI model and get generated AI response
    public static String askAI(String prompt) {

        // Create OpenAI client object using API key. This establishes connection with OpenAI services
        OpenAIClient client = OpenAIOkHttpClient.builder()
                        // Pass API key for authentication
                        .apiKey(API_KEY)
                        // Build OpenAI client object
                        .build();

        // Create request parameters object. This contains model name and user prompt
        ChatCompletionCreateParams params =
                // Start building chat completion request
                ChatCompletionCreateParams.builder()
                        // Select OpenAI model to use
                        .model(ChatModel.GPT_3_5_TURBO)
                        // Send user input prompt to AI
                        .addUserMessage(prompt)
                        // Build final request object
                        .build();

        // Send request to OpenAI API
        // Receive AI generated response
        return client.chat()
                // Access chat APIs
                .completions()
                // Create AI completion response
                .create(params)
                // Get all response choices
                .choices()
                // Fetch first AI response
                .get(0)
                // Get message object
                .message()
                // Get actual response content
                .content()
                // Return AI response text
                // If response is empty then return default message
                .orElse("No response received");
    }
}