package com.tiagopereira.api.tests;

import com.tiagopereira.api.base.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BreedNotFoundTest extends TestBase {

    // ========== TESTES PARA /breed/{breed}/images ==========
    
    @Test
    @DisplayName("GET /breed/{breed}/images com breed inexistente → 404 Not Found")
    void breedInexistente_deveRetornar404() {
        String invalid = "doesnotexist";

        given()
            .spec(req)
            .pathParam("breed", invalid)
        .when()
            .get("/breed/{breed}/images")
        .then()
            .statusCode(404)
            .body("status", equalTo("error"))
            .body("message", containsStringIgnoringCase("breed not found"));
    }

    @ParameterizedTest(name = "GET /breed/{0}/images → 400 Bad Request para entrada inválida")
    @ValueSource(strings = { "", " ", "123", "breed@invalid", "breed with spaces" })
    void breedInvalido_deveRetornar400(String invalidBreed) {
        given()
            .spec(req)
            .pathParam("breed", invalidBreed)
        .when()
            .get("/breed/{breed}/images")
        .then()
            .statusCode(400)
            .body("status", equalTo("error"))
            .body("message", notNullValue());
    }

    @Test
    @DisplayName("GET /breed/{breed}/images com sub-raça inexistente → 404 Not Found")
    void subRacaInexistente_deveRetornar404() {
        given()
            .spec(req)
        .when()
            .get("/breed/bulldog/invalidsubbreed/images")
        .then()
            .statusCode(404)
            .body("status", equalTo("error"))
            .body("message", containsStringIgnoringCase("not found"));
    }

    // ========== TESTES PARA /breeds/list/all ==========
    
    @Test
    @DisplayName("POST /breeds/list/all → 405 Method Not Allowed")
    void breedsListAll_metodoInvalido() {
        given()
            .spec(req)
        .when()
            .post("/breeds/list/all")
        .then()
            .statusCode(405)
            .header("Allow", containsString("GET"));
    }

    @Test
    @DisplayName("GET /breeds/list/all com parâmetros desnecessários → deve ignorar (200)")
    void breedsListAll_parametrosDesnecessarios() {
        given()
            .spec(req)
            .queryParam("invalid", "param")
        .when()
            .get("/breeds/list/all")
        .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .body("message", notNullValue());
    }

    // ========== TESTES PARA /breeds/image/random ==========
    
    @Test
    @DisplayName("POST /breeds/image/random → 405 Method Not Allowed")
    void randomImage_metodoInvalido() {
        given()
            .spec(req)
        .when()
            .post("/breeds/image/random")
        .then()
            .statusCode(405)
            .header("Allow", containsString("GET"));
    }

    @Test
    @DisplayName("GET /breeds/image/random com parâmetros desnecessários → deve ignorar")
    void randomImage_parametrosDesnecessarios() {
        given()
            .spec(req)
            .queryParam("breed", "pug")
            .queryParam("count", "5")
        .when()
            .get("/breeds/image/random")
        .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .body("message", startsWith("https://"));
    }

    // ========== TESTES DE ENDPOINTS INEXISTENTES ==========
    
    @Test
    @DisplayName("GET /endpoint/inexistente → 404")
    void endpointInexistente_deve404() {
        given()
            .spec(req)
        .when()
            .get("/endpoint/inexistente")
        .then()
            .statusCode(404);
    }

    @Test
    @DisplayName("GET /breeds/invalid/path → 404 Not Found")
    void pathInvalido_deveRetornar404() {
        given()
            .spec(req)
        .when()
            .get("/breeds/invalid/path")
        .then()
            .statusCode(404);
    }
}
