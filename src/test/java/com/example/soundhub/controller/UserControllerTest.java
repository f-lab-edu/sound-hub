package com.example.soundhub.controller;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.example.soundhub.presentation.dto.request.UserRequest;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		// 필요한 초기 데이터 삽입
		jdbcTemplate.update("INSERT INTO users (id, username, password) VALUES (1, 'testuser', 'password')");
	}

	@AfterEach
	public void afterEach() {
		jdbcTemplate.update("TRUNCATE TABLE users");
	}

	@Test
	public void join_ShouldRegisterUserAndReturnUserName() throws Exception {
		// Given
		UserRequest.join request = UserRequest.join.builder()
			.name("John Doe")
			.loginId("johnDoe")
			.password("password")
			.build();
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

	@Test
	public void login_ShouldAuthenticateUserAndReturnTokenInfo() throws Exception {
		// Given
		UserRequest.login request = new UserRequest.login("johnDoe", "password");

		UserRequest.join registerRequest = UserRequest.join.builder()
			.name("John Doe")
			.loginId("johnDoe")
			.password("password")
			.build();

		RestAssured.given()
			.log().all()
			.body(registerRequest)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.post("/users/join")
			.then()
			.statusCode(HttpStatus.OK.value());

		// When
		ExtractableResponse<Response> loginResponse = RestAssured
			.given()
			.log().all()
			.body(request)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when()
			.post("/users/login")
			.then()
			.log().all()
			.extract();

		// Then
		assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
