package com.tiagopereira.api.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public abstract class TestBase {

    protected static RequestSpecification req;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://dog.ceo/api";

        req = new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI)
                .addHeader("Accept", "application/json")
                .log(LogDetail.URI)
                .log(LogDetail.METHOD)
                .build();

        // Loga req/resp automaticamente quando uma asserção falhar
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
