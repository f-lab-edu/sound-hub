package com.example.soundhub.controller;

import com.example.soundhub.presentation.dto.request.UserRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Start with a random port
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void join_ShouldRegisterUserAndReturnUserName() throws Exception {
        // Arrange
        UserRequest.join request = UserRequest.join.builder().name("John Doe").login_id("johnDoe").password("password").build();

        String expectedResponse = "John Doe";

        // Act
        ExtractableResponse<Response> createResponse = RestAssured.
                given()
                .log().all().body(request).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                when().
                post("/users/join").
                then().
                log().all().extract();

        // Assert
        assertEquals(HttpStatus.OK.value(), createResponse.statusCode());
        assertEquals(expectedResponse, createResponse.body().asString());
    }
}
