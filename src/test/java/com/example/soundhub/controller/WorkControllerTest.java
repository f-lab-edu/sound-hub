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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import com.example.soundhub.presentation.dto.request.WorkRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WorkControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@AfterEach
	public void afterEach() {
		jdbcTemplate.update("TRUNCATE TABLE work");
	}

	@Test
	public void addWork_ShouldAddWorkAndReturnWorkId() throws Exception {
		// Given
		String token = authenticateAndGetToken();
		WorkRequest.addWork request = WorkRequest.addWork.builder()
			.name("userName")
			.workType("ALBUM")
			.youtubeUrl("www.youtube.com")
			.build();

		MockMultipartFile file = new MockMultipartFile(
			"썸네일 이미지",
			"thumbnail.png",
			MediaType.IMAGE_PNG_VALUE,
			"thumbnail".getBytes()
		);

		ExtractableResponse<Response> response = RestAssured.given()
			.header("Authorization", "Bearer " + token)
			.body(request)
			.body(file)
			.contentType(ContentType.MULTIPART)
			.accept(String.valueOf(MediaType.APPLICATION_JSON))
			.when()
			.post("/work/add")
			.then()
			.log().all()
			.extract();

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.body().as(Long.class)).isGreaterThan(0);
	}

	private String authenticateAndGetToken() {
		return "mocked_token";
	}
}