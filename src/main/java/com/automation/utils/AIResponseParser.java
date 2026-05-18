package com.automation.utils;

import com.automation.model.TestCase;

import java.util.ArrayList;
import java.util.List;

public class AIResponseParser {

    // Method to parse AI generated response
    // and convert it into List<TestCase> objects
    public static List<TestCase> parseAIResponse(
            String aiResponse) {

        // Create empty list to store parsed test cases
        List<TestCase> testCases = new ArrayList<>();

        // Split AI response line by line
        String[] lines = aiResponse.split("\\n");

        // Loop through each line from AI response
        for(String line : lines) {

            // Ignore table headers, separators and invalid rows
            if(line.startsWith("|---")
                    || line.contains("Test Case ID")
                    || !line.startsWith("|")) {

                // Skip current line and move to next line
                continue;
            }

            // Split row data using pipe separator
            String[] columns =
                    line.split("\\|");

            // Validate proper table row structure
            // Skip row if expected columns are missing
            if(columns.length < 9) {
                continue;
            }

            // Create new TestCase object
            TestCase testCase =
                    new TestCase();

            // Set Test Case ID
            testCase.setTestCaseId(
                    columns[1].trim());

            // Set Scenario
            testCase.setScenario(
                    columns[2].trim());

            // Set Preconditions
            testCase.setPreconditions(
                    columns[3].trim());

            // Format test steps properly
            // Adds new line before numbered steps like 1. 2. 3.
            String formattedSteps =
        columns[4]
                .trim()
                .replaceAll("(\\d+\\.)", "\n$1")
                .trim();

            // Set formatted test steps
            testCase.setTestSteps(formattedSteps);

            // Set Expected Result
            testCase.setExpectedResult(
                    columns[5].trim());

            // Set Priority
            testCase.setPriority(
                    columns[6].trim());

            // Set Severity
            testCase.setSeverity(
                    columns[7].trim());

            // Set Test Type
            testCase.setTestType(
                    columns[8].trim());

            // Add populated TestCase object into list
            testCases.add(testCase);
        }

        // Return complete list of parsed test cases
        return testCases;
    }
}