package com.automation.utils;

import com.automation.model.UserStory;
import com.automation.model.TestCase;
import com.automation.config.TestDataReader;
import java.util.List;
import java.util.ArrayList;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.*;

public class JiraUtils {

    static String jiraEmail = TestDataReader.getProperty("jira_email_id"); // Read Jira email from properties file

    static String jiraToken = TestDataReader.getProperty("jira_token"); // Read Jira API token from properties file

    public static UserStory getStory(String storyId) { // Method to fetch Jira User Story details

        RestAssured.baseURI = TestDataReader.getProperty("jira_base_url"); // Set Jira base URL

        Response response = given() // Start API request
                .auth() // Enable authentication
                .preemptive() // Use preemptive authentication
                .basic(jiraEmail, jiraToken) // Pass Jira email and token
                .header("Accept", "application/json") // Accept JSON response
                .when() // Start request execution
                .get("/rest/api/3/issue/" + storyId) // Jira API endpoint to fetch story details
                .then()
                .extract()
                .response(); // Extract complete API response

        System.out.println("Status Code: " + response.statusCode()); // Print response status code

        JSONObject json = new JSONObject(response.asString()); // Convert response into JSONObject

        if (response.statusCode() != 200) { // Validate successful response
            String message = "Failed to fetch JIRA story " + storyId + " (status=" + response.statusCode() + ")"; // Prepare error message
            if (json.has("errorMessages")) { // Check if Jira returned error messages
                message += ": " + json.getJSONArray("errorMessages").toString(); // Append error messages
            } else if (json.has("errors")) { // Check if Jira returned errors object
                message += ": " + json.getJSONObject("errors").toString(); // Append errors object
            }
            throw new RuntimeException(message); // Throw runtime exception if API call fails
        }

        JSONObject fields = json.optJSONObject("fields"); // Extract fields object from response
        if (fields == null) { // Validate fields object exists
            throw new RuntimeException("JIRA issue response did not contain 'fields'. Response: " + response.asString()); // Throw exception if fields are missing
        }
        
        UserStory story = new UserStory(); // Create UserStory object
        story.setStoryId(json.getString("key")); // Set story ID
        story.setSummary(fields.getString("summary")); // Set story summary
        String parsedDescription = ADFParser.parseDescription(fields.getJSONObject("description")); // Parse Jira ADF description into readable text
        story.setDescription(parsedDescription); // Set parsed description
        if(fields.has("priority")) { // Check if priority field exists
            story.setPriority(fields.getJSONObject("priority").getString("name")); // Set story priority
        }
        story.setAcceptanceCriteria("Valid login should work"); // Temporary hardcoded acceptance criteria
        return story; // Return populated UserStory object
    }

    public static List<String> createTestSubTasks(UserStory story, List<TestCase> testCases) { // Method to create Jira subtasks from AI generated test cases
        RestAssured.baseURI = TestDataReader.getProperty("jira_base_url"); // Set Jira base URL
        List<String> created = new ArrayList<>(); // Store created Jira subtask keys
        try {
            String projectKey = ""; // Initialize project key
            if (story.getStoryId() != null && story.getStoryId().contains("-")) { // Extract project key from story ID
                projectKey = story.getStoryId().split("-")[0]; // Example: SCRUM-3 → SCRUM
            }

            String subTaskIssueTypeId = null; // Store Jira subtask issue type ID
            io.restassured.response.Response typesResp = given() // Call Jira API to fetch issue types
                    .auth().preemptive().basic(jiraEmail, jiraToken) // Authenticate request
                    .header("Accept", "application/json") // Accept JSON response
                    .when()
                    .get("/rest/api/3/issuetype") // Jira API endpoint to fetch issue types
                    .then().extract().response(); // Extract response
            if (typesResp.statusCode() == 200) { // Validate successful response
                org.json.JSONArray typesArr = new org.json.JSONArray(typesResp.asString()); // Convert response into JSON array
                for (int i = 0; i < typesArr.length(); i++) { // Loop through all issue types
                    org.json.JSONObject t = typesArr.getJSONObject(i); // Get current issue type object
                    if (t.has("subtask") && t.getBoolean("subtask")) { // Check if issue type is subtask
                        subTaskIssueTypeId = t.getString("id"); // Store subtask issue type ID
                        break; // Exit loop after finding subtask type
                    }
                }
            }

            if (subTaskIssueTypeId == null) { // Validate subtask issue type exists

                throw new RuntimeException("No sub-task issue type found in JIRA. Ensure sub-tasks are enabled for this JIRA instance/project."); // Throw exception if subtask type missing
            }

            for (TestCase tc : testCases) { // Loop through all AI generated test cases

                org.json.JSONObject payload = new org.json.JSONObject(); // Main request payload object

                org.json.JSONObject fields = new org.json.JSONObject(); // Jira fields object

                org.json.JSONObject project = new org.json.JSONObject(); // Jira project object

                project.put("key", projectKey); // Add project key

                fields.put("project", project); // Add project object into fields

                fields.put("summary", tc.getTestCaseId() + " - " + tc.getScenario()); // Add subtask summary

                org.json.JSONObject issuetype = new org.json.JSONObject(); // Create issue type object

                issuetype.put("id", subTaskIssueTypeId); // Set subtask issue type ID

                fields.put("issuetype", issuetype); // Add issue type into fields

                org.json.JSONObject parent = new org.json.JSONObject(); // Create parent object

                parent.put("key", story.getStoryId()); // Set parent story key

                fields.put("parent", parent); // Link subtask to parent story

                StringBuilder plain = new StringBuilder(); // Create plain text description builder

                plain.append("Test case generated for ")
                        .append(story.getStoryId())
                        .append(" - ")
                        .append(story.getSummary())
                        .append("\n\n"); // Add story information

                plain.append("Preconditions: ")
                        .append(tc.getPreconditions())
                        .append("\n\n"); // Add preconditions

                plain.append("Steps:\n")
                        .append(tc.getTestSteps())
                        .append("\n\n"); // Add test steps

                plain.append("Expected: ")
                        .append(tc.getExpectedResult())
                        .append("\n\n"); // Add expected result

                plain.append("Priority: ")
                        .append(tc.getPriority())
                        .append(" | Severity: ")
                        .append(tc.getSeverity())
                        .append(" | Type: ")
                        .append(tc.getTestType())
                        .append("\n"); // Add priority, severity and test type

                org.json.JSONObject adf = new org.json.JSONObject(); // Create Atlassian Document Format object

                adf.put("type", "doc"); // Set ADF document type

                adf.put("version", 1); // Set ADF version

                org.json.JSONArray contentArr = new org.json.JSONArray(); // Store description paragraphs

                String[] lines = plain.toString().split("\\r?\\n"); // Split plain text into lines

                for (String line : lines) { // Loop through each line

                    org.json.JSONObject paragraph = new org.json.JSONObject(); // Create paragraph object

                    paragraph.put("type", "paragraph"); // Set paragraph type

                    org.json.JSONArray pContent = new org.json.JSONArray(); // Store paragraph content

                    org.json.JSONObject textObj = new org.json.JSONObject(); // Create text object

                    textObj.put("type", "text"); // Set text type

                    textObj.put("text", line); // Add line text

                    pContent.put(textObj); // Add text object into paragraph content

                    paragraph.put("content", pContent); // Add paragraph content

                    contentArr.put(paragraph); // Add paragraph into content array
                }

                adf.put("content", contentArr); // Add content array into ADF document

                fields.put("description", adf); // Add description into Jira fields

                payload.put("fields", fields); // Add fields into payload

                io.restassured.response.Response response = given() // Send Jira create issue request

                        .auth()
                        .preemptive()
                        .basic(jiraEmail, jiraToken) // Authenticate request
                        .header("Accept", "application/json") // Accept JSON response
                        .header("Content-Type", "application/json") // Set request content type
                        .body(payload.toString()) // Attach payload
                        .when()
                        .post("/rest/api/3/issue") // Jira create issue API endpoint
                        .then()
                        .extract()
                        .response(); // Extract API response

                System.out.println("Create sub-task response code: " + response.statusCode()); // Print response status code

                System.out.println(response.asString()); // Print complete API response

                if (response.statusCode() != 201) { // Validate successful subtask creation

                    System.out.println("Failed to create sub-task for " + tc.getTestCaseId() + ": " + response.asString()); // Print failure message

                    continue; // Skip current test case and continue loop
                }

                org.json.JSONObject createdObj = new org.json.JSONObject(response.asString()); // Convert response into JSON object

                String newKey = createdObj.getString("key"); // Extract created Jira issue key

                created.add(newKey); // Store created Jira key

                System.out.println("Created sub-task " + newKey + " under " + story.getStoryId()); // Print success message
            }

            return created; // Return list of created Jira subtasks

        } catch (Exception e) { // Catch any exception during execution

            throw new RuntimeException("Error creating JIRA sub-tasks", e); // Throw runtime exception
        }
    }
}