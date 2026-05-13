package com.automation.utils;

import com.automation.model.UserStory;

public class PromptBuilder {

    public static String buildTestCasePrompt(
            UserStory story) {

        return """
                You are a Senior QA Automation Engineer.

                Analyze the following JIRA User Story carefully.

                Generate:
                - Functional Test Cases
                - Negative Test Cases
                - Edge Cases
                - API Test Scenarios
                - UI Automation Scenarios
                - Security Test Cases

                Rules:
                - Avoid duplicate scenarios
                - Include realistic validations
                - Include boundary conditions
                - Include invalid inputs
                - Include usability checks
                - Include browser/session checks
                - Include security scenarios
                - Use concise and business-friendly language.

                IMPORTANT:
                Return response ONLY in markdown table format.

                Create separate tables for:
                1. Functional Test Cases
                2. Negative Test Cases
                3. Edge Cases
                4. API Test Scenarios
                5. Automation Scenarios

                Each table must contain:

                | Test Case ID |
                | Scenario |
                | Preconditions |
                | Test Steps |
                | Expected Result |
                | Priority |
                | Severity |
                | Test Type |

                User Story ID:
                %s

                Summary:
                %s

                Description:
                %s

                Acceptance Criteria:
                %s
                """.formatted(

                story.getStoryId(),
                story.getSummary(),
                story.getDescription(),
                story.getAcceptanceCriteria()
        );
    }
}