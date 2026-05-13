package com.automation.utils;

import com.automation.model.TestCase;

import java.util.ArrayList;
import java.util.List;

public class AIResponseParser {

    public static List<TestCase> parseAIResponse(
            String aiResponse) {

        List<TestCase> testCases =
                new ArrayList<>();

        String[] lines =
                aiResponse.split("\\n");

        for(String line : lines) {

            // Ignore headers/separators
            if(line.startsWith("|---")
                    || line.contains("Test Case ID")
                    || !line.startsWith("|")) {

                continue;
            }

            String[] columns =
                    line.split("\\|");

            // Validate proper row
            if(columns.length < 9) {
                continue;
            }

            TestCase testCase =
                    new TestCase();

            testCase.setTestCaseId(
                    columns[1].trim());

            testCase.setScenario(
                    columns[2].trim());

            testCase.setPreconditions(
                    columns[3].trim());

            testCase.setTestSteps(
                    columns[4].trim());

            testCase.setExpectedResult(
                    columns[5].trim());

            testCase.setPriority(
                    columns[6].trim());

            testCase.setSeverity(
                    columns[7].trim());

            testCase.setTestType(
                    columns[8].trim());

            testCases.add(testCase);
        }

        return testCases;
    }
}