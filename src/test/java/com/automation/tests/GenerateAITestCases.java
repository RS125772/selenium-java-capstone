package com.automation.tests;

import com.automation.config.TestDataReader;
import com.automation.model.TestCase;
import com.automation.model.UserStory;

import com.automation.utils.AIResponseParser;
import com.automation.utils.AIUtils;
import com.automation.utils.ExcelUtils;
import com.automation.utils.JiraUtils;
import com.automation.utils.PromptBuilder;

import java.util.List;

import org.testng.annotations.Test;

public class GenerateAITestCases {

    @Test
    public static void generateAITestCases() {

        // Fetch JIRA Story
        String storyIds = TestDataReader.getProperty("story.ids");
        String storyId = storyIds.split(",")[0].trim();
        if (storyIds.contains(",")) {
            System.out.println("Using first configured JIRA story ID: " + storyId);
        }

        UserStory story;
        try {
            story = JiraUtils.getStory(storyId);
        } catch (RuntimeException e) {
            System.out.println("Skipping AI test generation due to JIRA fetch failure: " + e.getMessage());
            return;
        }

        // Build AI Prompt
        String prompt =
                PromptBuilder
                        .buildTestCasePrompt(story);

        // Send Prompt to AI
        String aiResponse =
                AIUtils.askAI(prompt);

        System.out.println(aiResponse);

        // Parse AI Response
        List<TestCase> testCases =
                AIResponseParser
                        .parseAIResponse(aiResponse);

        // Print Parsed Objects
        for(TestCase tc : testCases) {

            System.out.println(
                    tc.getTestCaseId()
                            + " | "
                            + tc.getScenario());
        }

        // Save Excel
        ExcelUtils.saveToExcel(testCases, story.getStoryId());

                // Create JIRA Sub-tasks under the story for each generated test case
                try {
                        java.util.List<String> createdKeys = JiraUtils.createTestSubTasks(story, testCases);
                        System.out.println("Created JIRA sub-tasks: " + createdKeys);
                } catch(Exception e) {
                        System.out.println("Failed to create JIRA sub-tasks: " + e.getMessage());
                }
    }
}