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

public class AdminControllerTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.stalecheck", true));
    }

    private Response loginAsAdmin() {
        Response authResponse = given()
                .contentType("application/json")
                .body("{\"username\": \"user1\", \"password\": \"12345678\"}")
                .when()
                .post("/api/auth/login");

        assertEquals(200, authResponse.getStatusCode(), "Аутентификация не удалась");

        String tokenCookie = authResponse.getCookie("token");
        assertNotNull(tokenCookie, "Cookie с токеном не получена");

        return authResponse;
    }

    @Test
    public void testAccessAdminPanel() {
        Response authResponse = loginAsAdmin();

        Response response = given()
                .cookies(authResponse.getCookies())
                .when()
                .log().all()
                .get("/adminPanel");

        response.then().log().body();
        assertEquals(200, response.getStatusCode(), "Доступ к админ-панели не получен");
    }

    @Test
    public void testCreateUser() {
        Response authResponse = loginAsAdmin();

        Response response = given()
                .cookies(authResponse.getCookies())
                .contentType("application/json")
                .body("{\"userName\": \"user5\", \"password\": \"Zxcvb122\", \"role\": \"USER\"}")
                .when()
                .log().all()
                .post("/adminPanel/createClient");

        response.then().log().body();
        assertEquals(200, response.getStatusCode(), "Не удалось создать пользователя");
    }

    @Test
    public void testUpdateUserRole() {
        Response authResponse = loginAsAdmin();

        Response response = given()
                .cookies(authResponse.getCookies())
                .contentType("application/json")
                .body("{\"userName\": \"user3\", \"role\": \"ADMIN\"}")
                .when()
                .log().all()
                .post("/adminPanel/updateClient");

        response.then().log().body();
        assertEquals(200, response.getStatusCode(), "Не удалось обновить пользователя");
    }

    @Test
    public void testDeleteUser() {
        Response authResponse = loginAsAdmin();

        Response response = given()
                .cookies(authResponse.getCookies())
                .contentType("application/json")
                .body("{\"userName\": \"user5\"}")
                .when()
                .log().all()
                .post("/adminPanel/deleteClient");

        response.then().log().body();
        assertEquals(200, response.getStatusCode(), "Не удалось удалить пользователя");
    }

    @Test
    public void testDeleteAll() {
        Response authResponse = loginAsAdmin();

        Response response = given()
                .cookies(authResponse.getCookies())
                .contentType("application/json")
                .body("{\"userName\": \"user5\"}")
                .when()
                .log().all()
                .post("/adminPanel/deleteClient");

        response.then().log().body();
        assertEquals(200, response.getStatusCode(), "Не удалось выполнить массовое удаление");
    }
}
