package com.example.securityproject.controllerTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void login() {
        Map<String, String> request = new HashMap<>();
        request.put("username", "user1");
        request.put("password", "12345678");

        ResponseEntity<String> response = restTemplate.postForEntity("/api/auth/login", request, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
