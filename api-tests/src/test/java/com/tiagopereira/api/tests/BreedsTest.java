package com.tiagopereira.api.tests;

import com.tiagopereira.api.base.TestBase;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

public class BreedsTest extends TestBase {

    @Test
    @DisplayName("GET /breeds/list/all → contrato básico")
    void listAllBreeds_contratoBasico() {
        given()
            .spec(req)
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .body("message", notNullValue())
            .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(
                    "schemas/breeds-list-all.schema.json"));
    }

    @Test
    @DisplayName("GET /breeds/list/all → validação estrutural completa")
    void listAllBreeds_validacaoEstrutural() {
        given()
            .spec(req)
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .body("message", instanceOf(Map.class))
            .body("message.size()", greaterThan(50)); // API tem 100+ raças
    }

    @Test
    @DisplayName("GET /breeds/list/all → raças conhecidas existem")
    void listAllBreeds_racasConhecidas() {
        given()
            .spec(req)
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .body("message.containsKey('bulldog')", is(true))
            .body("message.containsKey('hound')", is(true))
            .body("message.containsKey('pug')", is(true))
            .body("message.containsKey('labrador')", is(true))
            .body("message.containsKey('terrier')", is(true));
    }

    @Test
    @DisplayName("GET /breeds/list/all → raças com sub-raças")
    void listAllBreeds_racasComSubRacas() {
        given()
            .spec(req)
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .body("message.bulldog", hasItems("boston", "english", "french"))
            .body("message.hound", hasItems("afghan", "basset", "blood"))
            .body("message.terrier.size()", greaterThan(10))
            .body("message.spaniel", not(empty()));
    }

    @Test
    @DisplayName("GET /breeds/list/all → raças sem sub-raças")
    void listAllBreeds_racasSemSubRacas() {
        given()
            .spec(req)
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .body("message.pug", empty())
            .body("message.beagle", empty())
            .body("message.boxer", empty())
            .body("message.labrador", empty());
    }

    @Test
    @DisplayName("GET /breeds/list/all → performance aceitável")
    void listAllBreeds_performance() {
        given()
            .spec(req)
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .time(lessThan(2000L)); // < 2 segundos
    }

    @Test
    @DisplayName("GET /breeds/list/all → headers corretos")
    void listAllBreeds_headers() {
        given()
            .spec(req)
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .header("Content-Type", containsString("application/json"))
            .header("Access-Control-Allow-Origin", "*");
    }

    @Test
    @DisplayName("GET /breeds/list/all → consistência de dados")
    void listAllBreeds_consistenciaDados() {
        Response response = given()
            .spec(req)
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .extract().response();

        Map<String, List<String>> breeds = response.jsonPath().get("message");
        
        assertAll("Validações de consistência",
            () -> assertTrue(breeds.size() > 50, "Deve ter mais de 50 raças"),
            () -> breeds.forEach((breed, subBreeds) -> {
                assertTrue(breed.matches("^[a-z]+$"), 
                    "Raça '" + breed + "' deve conter apenas letras minúsculas");
                subBreeds.forEach(subBreed -> 
                    assertTrue(subBreed.matches("^[a-z]+$"), 
                        "Sub-raça '" + subBreed + "' deve conter apenas letras minúsculas")
                );
            })
        );
    }

    @Test
    @DisplayName("GET /breeds/list/all → validação de raças específicas")
    void listAllBreeds_racasEspecificas() {
        given()
            .spec(req)
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            // Validações específicas baseadas na resposta real
            .body("message.african", hasItem("wild"))
            .body("message.australian", hasItems("kelpie", "shepherd"))
            .body("message.mastiff", hasItems("bull", "english", "tibetan"))
            .body("message.poodle", hasItems("standard", "miniature", "toy"));
    }
}
