package br.com.carlohcs.api.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

// This will be doing integration tests
// We will be testing the whole application
// by running the server and making requests automatically without the need of a browser
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerIT {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Nested
    class RegisterMessage {
        @Test
        void registerMessage() {
            given()
                    .body("{\"username\": \"carlohcs\", \"content\": \"Hello, World!\"}")
                    .contentType("application/json")
            .when()
                    .post("/messages")
            .then()
                    .log().all()
                    .statusCode(201);
        }
    }
}
