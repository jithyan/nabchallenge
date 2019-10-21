package com.jithyan.nabchallengebff.rest;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CryptoCurrencyControllerIntegrationTest {
   @LocalServerPort
   private int port;

   @Test
   @DisplayName("v1/available-cryptos returns correct names in DB in ascending order")
   public void availableCryptosReturnsCorrectNamesInDbInAscendingOrder() {
      RestAssured.port = this.port;
      when().get("/nab-challenge/v1/available-cryptos").then().statusCode(200).body("records",
            equalTo(Arrays.asList("BTC", "ETC", "LTC")));
   }
}
