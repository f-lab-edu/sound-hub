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


import static org.assertj.core.api.Assertions.assertThat;
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
        // Given
        UserRequest.join request = UserRequest.join.builder().name("John Doe").loginId("johnDoe").password("password").build();

        String expectedResponse = "John Doe";

        // When
        ExtractableResponse<Response> createResponse = RestAssured.
                given()
                .log().all().body(request).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                when().
                post("/users/join").
                then().
                log().all().extract();

        // Then
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(createResponse.body().asString()).isEqualTo(expectedResponse);
    }
}
