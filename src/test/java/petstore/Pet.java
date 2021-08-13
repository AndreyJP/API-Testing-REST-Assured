package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

public class Pet {
    //Pet entity address
    String uri = "https://petstore.swagger.io/v2/pet";

    public String readJson(String jsonPath) throws IOException {

        return new String(Files.readAllBytes(Paths.get(jsonPath)));
    }

    @Test(priority = 1)
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
                .body("name", is("Buck"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("sta"))
        ;
    }

    @Test(priority = 2)
    public void readPet(){
        String petId = "202120222023123";

        String categoryName =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Buck"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("sta"))
        .extract()
                .path("category.name")
        ;
        System.out.println("Token: " + categoryName);
    }

    @Test(priority = 3)
    public void updatePet() throws IOException {
        String jsonBody = readJson("data/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Buck"))
                .body("status", is("unavailable"))
        ;
    }

    @Test(priority = 4)
    public void deletePet(){
        String petId = "202120222023123";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("202120222023123"))
        ;
    }
}
