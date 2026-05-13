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
        String storyId = TestDataReader.getProperty("story.ids");
        UserStory story = JiraUtils.getStory(storyId);

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
    }
}