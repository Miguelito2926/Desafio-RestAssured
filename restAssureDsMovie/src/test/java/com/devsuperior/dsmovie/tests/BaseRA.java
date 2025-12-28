package com.devsuperior.dsmovie.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseRA {

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
    }
}
