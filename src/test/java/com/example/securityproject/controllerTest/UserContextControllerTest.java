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

    private Response loginAndGetCookies() {
        Response authResponse = given()
                .contentType("application/json")
                .body("{\"username\": \"user1\", \"password\": \"12345678\"}")
                .when()
                .post("/api/auth/login");

        assertEquals(200, authResponse.getStatusCode(), "Аутентификация не удалась");
        assertNotNull(authResponse.getCookie("token"), "Cookie с токеном не получена");

        return authResponse;
    }

    @Test
    public void testGetUserContext() {
        Response authResponse = loginAndGetCookies();

        Response contextResponse = given()
                .cookies(authResponse.getCookies())
                .when()
                .log().all()
                .get("/user/getContext");

        contextResponse.then().log().body();

        assertEquals(200, contextResponse.getStatusCode(), "Не удалось получить контекст");

        String responseBody = contextResponse.getBody().asString();
        assertNotNull(responseBody, "Ответное тело пустое");
    }
}
