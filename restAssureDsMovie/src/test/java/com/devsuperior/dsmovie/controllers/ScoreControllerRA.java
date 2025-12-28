package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.BaseRA;
import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ScoreControllerRA extends BaseRA {
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
		String token = TokenUtil.obtainAccessToken("alex@gmail.com", "123456");

		JSONObject json = new JSONObject();
		json.put("movieId", 1000);
		json.put("score", 4.0);

		given()
				.header("Authorization", "Bearer " + token)
				.contentType(ContentType.JSON)
				.body(json.toString())
				.when()
				.put("/scores")
				.then()
				.statusCode(404);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		String token = TokenUtil.obtainAccessToken("alex@gmail.com", "123456");

		JSONObject json = new JSONObject();
		json.put("movieId", 1000);
		json.put("score", 4.0);

		given()
				.header("Authorization", "Bearer " + token)
				.contentType(ContentType.JSON)
				.body(json.toString())
				.when()
				.put("/scores")
				.then()
				.statusCode(404);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
		String token = TokenUtil.obtainAccessToken("alex@gmail.com", "123456");

		JSONObject json = new JSONObject();
		json.put("score", 4.0);

		given()
				.header("Authorization", "Bearer " + token)
				.contentType(ContentType.JSON)
				.body(json.toString())
				.when()
				.put("/scores")
				.then()
				.statusCode(422);
	}
}
