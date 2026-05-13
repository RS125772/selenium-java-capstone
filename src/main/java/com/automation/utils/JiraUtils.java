package com.automation.utils;

import com.automation.model.UserStory;
import com.automation.config.TestDataReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.json.JSONObject;

import static io.restassured.RestAssured.*;

public class JiraUtils {

    static String jiraEmail =
            "rachit.saurabh123@gmail.com";

    static String jiraToken = TestDataReader.getProperty("jira_token");
            
    public static UserStory getStory(String storyId) {

        RestAssured.baseURI =
                "https://rachitsaurabh123.atlassian.net";

        Response response = given()

                .auth()
                .preemptive()
                .basic(jiraEmail, jiraToken)

                .header("Accept", "application/json")

                .when()

                .get("/rest/api/3/issue/" + storyId)

                .then()
                .extract()
                .response();

        System.out.println(
                "Status Code: "
                        + response.statusCode());

        JSONObject json =
                new JSONObject(response.asString());

        if (response.statusCode() != 200) {
            String message = "Failed to fetch JIRA story " + storyId + " (status=" + response.statusCode() + ")";
            if (json.has("errorMessages")) {
                message += ": " + json.getJSONArray("errorMessages").toString();
            } else if (json.has("errors")) {
                message += ": " + json.getJSONObject("errors").toString();
            }
            throw new RuntimeException(message);
        }

        JSONObject fields = json.optJSONObject("fields");
        if (fields == null) {
            throw new RuntimeException(
                    "JIRA issue response did not contain 'fields'. Response: "
                            + response.asString());
        }

        UserStory story =
                new UserStory();

        story.setStoryId(
                json.getString("key"));

        story.setSummary(
                fields.getString("summary"));

        String parsedDescription =
        ADFParser.parseDescription(
                fields.getJSONObject("description"));

story.setDescription(parsedDescription);

        // Optional
        if(fields.has("priority")) {

            story.setPriority(
                    fields.getJSONObject("priority")
                            .getString("name"));
        }

        // Temporary hardcoded AC
        story.setAcceptanceCriteria(
                "Valid login should work");

        return story;
    }
}