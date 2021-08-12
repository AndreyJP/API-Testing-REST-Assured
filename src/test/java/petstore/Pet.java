package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class Pet {
    //Pet entity address
    String uri = "https://petstore.swagger.io/v2/pet";

    public String readJson(String jsonPath) throws IOException {

        return new String(Files.readAllBytes(Paths.get(jsonPath)));
    }

    @Test
    public void createPet() throws IOException {
        String jsonBody = readJson("data/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
        ;
    }
}
