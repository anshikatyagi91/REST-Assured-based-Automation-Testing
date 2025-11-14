package com.railse.workforcemgmt.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerRestAssuredTest {
    private static Long taskId;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080; // agar random port nahi hai to 8080 rakho
    }

    // Create Task
    @Test
    @Order(1)
    void createTask_shouldReturnCreatedTask() {
        String body = """
            {
                "title": "Automation Task",
                "status": "ACTIVE",
                "startDate": "2025-11-12",
                "dueDate": "2025-11-15",
                "assignee": {
                    "id": 1,
                    "name": "Anshika"
                },
                "priority": "MEDIUM"
            }
        """;

        Integer idInt =
                given()
                        .contentType(ContentType.JSON)
                        .body(body)
                        .when()
                        .post("/tasks")
                        .then()
                        .statusCode(200)
                        .body("title", equalTo("Automation Task"))
                        .body("priority", equalTo("MEDIUM"))
                        .extract()
                        .path("id");

        taskId = idInt.longValue();
        System.out.println("Created Task ID: " + taskId);
    }

    // Get All Tasks
    @Test
    @Order(2)
    void updatePriority_shouldChangePrioritySuccessfully() {
        given()
                .contentType(ContentType.JSON)
                .param("taskId", taskId)
                .param("level", "HIGH")
                .when()
                .put("/tasks/priority")
                .then()
                .statusCode(200);

        System.out.println(" Priority updated to HIGH successfully");
    }


    // Update Task Priority
    @Test
    @Order(3)
    void addComment_shouldWork() {
        given()
                .contentType(ContentType.JSON)
                .param("comment", "This is a test comment")
                .when()
                .post("/tasks/" + taskId + "/comment")
                .then()
                .statusCode(200);

        System.out.println(" Comment added successfully");
    }

    // Verify Priority Change
    @Test
    @Order(4)
    void verifyPriorityChange() {
        when()
                .get("/tasks/" + taskId)
                .then()
                .statusCode(200)
                .body("priority", equalTo("HIGH"));
    }



}
