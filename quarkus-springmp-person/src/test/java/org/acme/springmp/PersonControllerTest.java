package org.acme.springmp;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PersonControllerTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/person")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}