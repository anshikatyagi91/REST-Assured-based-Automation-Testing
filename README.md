# REST-Assured based Automation-Testing
This project implements REST Assured–based Automation Testing for the Workforce Management API, specifically focusing on the Task Management Module.
The automated test suite validates the behavior of backend task operations by executing real HTTP requests against a running Spring Boot server and verifying responses using JUnit 5 and REST Assured assertions.
The goal is to ensure that the Task API functions correctly across all key operations—creation, update, comment addition, and data retrieval.

Project Requirements & Functionalities
Functional Requirements Covered Through Automation
1.Create Task:-
a.API must accept a new task request.
b.Should return status 200 with correct task details.
c.Must generate and return a unique task ID.

2.Update Task Priority:-
a.API should allow updating an existing task’s priority.
b.Must return success when priority changes (e.g., MEDIUM → HIGH).

3.Add Comment to Task:-
a.API must allow adding user comments to a specific task.
b.Should return 200 status.

4.Fetch Updated Task:-
a.API should return task details based on ID.
b.Returned data must include updated priority and previously added comment(s).


Non-Functional Expectations
1.API must be running while tests execute.
2.Tests should run in a controlled sequence using @Order.
3.Response fields must be validated using REST Assured matchers.
4.Automation should be readable, structured, and reusable.

Technologies & Concepts Used
Languages & Tools:-
Java 17
Spring Boot 3
JUnit 5
REST Assured
Gradle

Testing Concepts
API Automation
Request/Response Validation
JSON Field Assertions
Chained API Testing (ID extraction → reuse)
Ordered Test Execution
Content-Type Handling (JSON)
Logging & Debug Printing

Backend Concepts (Under Test)
RESTful API Design
CRUD Operations
Task Management (Domain Layer)
Priority Management
Comments & Activity logs


Project Structure & Explanation
src
└── test
└── java
└── com.railse.workforcemgmt.restassured
└── TaskControllerRestAssuredTest.java
TaskControllerRestAssuredTest.java
This class contains four ordered test cases:-


1️. createTask_shouldReturnCreatedTask()
a.Sends POST /tasks
b.Validates:
HTTP 200
title = “Automation Task”
priority = “MEDIUM”
c.Extracts taskId
d.Stores it for next tests

2️. updatePriority_shouldChangePrioritySuccessfully()
a.Sends PUT /tasks/priority with params:-
taskId
level = HIGH
b.Validates:-
HTTP 200 (priority updated)

3️. addComment_shouldWork()
a.Sends POST /tasks/{taskId}/comment
b.Attaches comment payload
c.Validates success response

4️. verifyPriorityChange()
a.Performs GET /tasks/{taskId}
b.Validates:-
priority = “HIGH”
task still exists
c.Confirms successful update

Summary:-
This project demonstrates a well-structured API Automation Testing framework using REST Assured and JUnit 5 for the Workforce Management Task API.
a.Validates end-to-end Task lifecycle
b.Ensures correctness of Task creation, updating, and commenting
c.Shows how to build sequential API tests using extracted IDs
d.Ideal for backend testing, QA automation, and portfolio demonstration

