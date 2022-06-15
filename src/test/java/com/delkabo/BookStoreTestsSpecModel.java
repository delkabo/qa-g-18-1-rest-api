package com.delkabo;

import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import models.Credentials;
import models.GenerateTokenResponse;
import models.lombok.CredentialsLombok;
import models.lombok.GenerateTokenResponseLombok;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static listener.CustomAllureListener.withCustomTemplates;
import static models.Specs.request;
import static models.Specs.responseSpec;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class BookStoreTestsSpecModel {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://demoqa.com/";
    }

    @Test
    void getBookWithSomeLogsTestSpec() {
        given()
                .spec(request)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .log().status()
                .log().body()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void generatedTokenTest() {
        String data = "{ \"userName\": \"alex\", " +
                "\"password\": \"asdsad#frew_DFS2\" }";

        given()
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", greaterThan(10));
    }

    @Test
    void generatedTokenTestSpec() {

        String data = "{ \"userName\": \"alex\", " +
                "\"password\": \"asdsad#frew_DFS2\" }";

        Credentials credentials = new Credentials();
        credentials.setUserName("alex");
        credentials.setPassword("asdsad#frew_DFS2");

        given()
                .spec(request)
                .body(data)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpec)
                .log().status()
                .log().body()
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", greaterThan(10));

    }

    @Test
    @Description("получить токен")
    void getTokenTest() {
        String data = "{ \"userName\": \"alex\", " +
                "\"password\": \"asdsad#frew_DFS2\" }";

        String token =
                given()
                        .contentType(JSON)
                        .body(data)
                        .log().uri()
                        .log().body()
                        .when()
                        .post("/Account/v1/GenerateToken")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .body("status", is("Success"))
                        .body("result", is("User authorized successfully."))
                        .extract().path("token");

        System.out.println("Token: " + token);
    }

    @Test
    void generatedTokenWithAllureListenerTest() {
        String data = "{ \"userName\": \"alex\", " +
                "\"password\": \"asdsad#frew_DFS2\" }";

//        RestAssured.filters(new AllureRestAssured()); move to BeforeAll

        given()
                .filter(new AllureRestAssured())
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", greaterThan(10));
    }

    @Test
    void generatedTokenWithCustomAllureListenerTest() {
        String data = "{ \"userName\": \"alex\", " +
                "\"password\": \"asdsad#frew_DFS2\" }";

//        RestAssured.filters(new AllureRestAssured()); move to BeforeAll

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(data)
                .log().uri()
                .log().body()
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."))
                .body("token.size()", greaterThan(10));
    }

    @Test
    void generatedTokenWithModelTest() {

        Credentials credentials = new Credentials();
        credentials.setUserName("alex");
        credentials.setPassword("asdsad#frew_DFS2");

        GenerateTokenResponse tokenResponse = new GenerateTokenResponse();

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(credentials)
                .log().uri()
                .log().body()
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/GeneratedToken_response_scheme.json"))
                .extract().as(GenerateTokenResponse.class);

        assertThat(tokenResponse.getStatus()).isEqualTo("Success");
        assertThat(tokenResponse.getResult()).isEqualTo("User authorized successfully.");
        assertThat(tokenResponse.getExpires()).hasSizeGreaterThan(10);
        assertThat(tokenResponse.getToken()).hasSizeGreaterThan(10).startsWith("eyJ");
    }

    @Test
    void generatedTokenWithLombokTest() {

        CredentialsLombok credentials = new CredentialsLombok();
        credentials.setUserName("alex");
        credentials.setPassword("asdsad#frew_DFS2");

        GenerateTokenResponseLombok tokenResponse = new GenerateTokenResponseLombok();

        given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(credentials)
                .log().uri()
                .log().body()
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/GeneratedToken_response_scheme.json"))
                .extract().as(GenerateTokenResponseLombok.class);

        assertThat(tokenResponse.getStatus()).isEqualTo("Success");
        assertThat(tokenResponse.getResult()).isEqualTo("User authorized successfully.");
        assertThat(tokenResponse.getExpires()).hasSizeGreaterThan(10);
        assertThat(tokenResponse.getToken()).hasSizeGreaterThan(10).startsWith("eyJ");
    }


}

