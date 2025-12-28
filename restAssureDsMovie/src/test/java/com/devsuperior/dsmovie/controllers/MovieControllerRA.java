package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.BaseRA;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import com.devsuperior.dsmovie.tests.TokenUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class MovieControllerRA extends BaseRA {
	
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
		given()
				.when()
				.get("/movies")
				.then()
				.statusCode(200);
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {
		given()
				.param("title", "Star")
				.when()
				.get("/movies")
				.then()
				.statusCode(200)
				.body("content", not(empty()));
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {
		given()
				.when()
				.get("/movies/1")
				.then()
				.statusCode(200)
				.body("id", equalTo(1));
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {
		given()
				.when()
				.get("/movies/1000")
				.then()
				.statusCode(404);
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException, JSONException {
		String token = TokenUtil.obtainAccessToken("maria@gmail.com", "123456");

		JSONObject json = new JSONObject();
		json.put("title", "");
		json.put("score", 0.0);
		json.put("count", 0);
		json.put("image", "https://img.com");

		given()
				.header("Authorization", "Bearer " + token)
				.contentType(ContentType.JSON)
				.body(json.toString())
				.when()
				.post("/movies")
				.then()
				.statusCode(422);
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
		String token = TokenUtil.obtainAccessToken("alex@gmail.com", "123456");

		JSONObject json = new JSONObject();
		json.put("title", "Novo Filme");
		json.put("score", 0.0);
		json.put("count", 0);
		json.put("image", "https://img.com");

		given()
				.header("Authorization", "Bearer " + token)
				.contentType(ContentType.JSON)
				.body(json.toString())
				.when()
				.post("/movies")
				.then()
				.statusCode(403);
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
		JSONObject json = new JSONObject();
		json.put("title", "Novo Filme");
		json.put("score", 0.0);
		json.put("count", 0);
		json.put("image", "https://img.com");

		given()
				.header("Authorization", "Bearer token_invalido")
				.contentType(ContentType.JSON)
				.body(json.toString())
				.when()
				.post("/movies")
				.then()
				.statusCode(401);
	}
}
