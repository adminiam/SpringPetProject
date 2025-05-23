package com.example.securityproject.controllerTest;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserContextControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";

        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.stalecheck", true));
    }

    @Test
    public void testAuthenticationAndContext() {
        Response authResponse = given()
                .contentType("application/json")
                .body("{\"username\": \"user1\", \"password\": \"12345678\"}")
                .when()
                .post("/api/auth/login");

        assertEquals(200, authResponse.getStatusCode(), "Аутентификация не удалась");

        String token = authResponse.jsonPath().getString("token");
        assertNotNull(token, "Токен не получен");

        Response trackingResponse = given()
                .header("Authorization", "Bearer " + token)
                .cookies(authResponse.getCookies())
                .when()
                .log().all()
                .get("/user/getContext");

        trackingResponse.then().log().body();

        assertEquals(200, trackingResponse.getStatusCode(), "Не удалось получить контекст");

        String responseBody = trackingResponse.getBody().asString();
        assertNotNull(responseBody, "Ответное тело пустое");
    }

}