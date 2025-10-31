package com.tiagopereira.api.tests;

import com.tiagopereira.api.base.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BreedImagesTest extends TestBase {

    @ParameterizedTest(name = "GET /breed/{0}/images → deve retornar lista de imagens")
    @ValueSource(strings = { "hound", "pug", "bulldog" })
    void imagesByBreed_ok(String breed) {
        given()
            .spec(req)
            .pathParam("breed", breed)
        .when()
            .get("/breed/{breed}/images")
        .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .body("message.size()", greaterThan(0))
            .body("message[0]", startsWith("http"));
    }

    @Test
    @DisplayName("GET /breeds/image/random → deve retornar imagem aleatória")
    void randomBreedImage_ok() {
        given()
            .spec(req)
        .when()
            .get("/breeds/image/random")
        .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .body("message", startsWith("https://"))
            .body("message", containsString("images.dog.ceo"))
            .body("message", endsWith(".jpg"));
    }

    @Test
    @DisplayName("GET /breeds/image/random → validação completa da URL")
    void randomBreedImage_validacaoCompleta() {
        given()
            .spec(req)
        .when()
            .get("/breeds/image/random")
        .then()
            .statusCode(200)
            .body("status", equalTo("success"))
            .body("message", matchesPattern("^https://images\\.dog\\.ceo/breeds/.+\\.(jpg|jpeg|png)$"));
    }

    @Test
    @DisplayName("GET /breeds/image/random → performance aceitável")
    void randomBreedImage_performance() {
        given()
            .spec(req)
        .when()
            .get("/breeds/image/random")
        .then()
            .statusCode(200)
            .time(lessThan(3000L)); // < 3 segundos para buscar imagem
    }
}
