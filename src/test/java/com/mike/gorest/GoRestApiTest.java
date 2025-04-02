package com.mike.gorest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.UUID;

public class GoRestApiTest {

    //Tenes que setear como variable entorno local el nombre "GOREST_TOKEN" con tu token de Go rest para que lo pueda consumir
    String TOKEN = System.getenv("GOREST_TOKEN");


    //Objetivo General: es simplemente validar los proximos test que funcionen, sin contemplar buenas practicas, encapsulamiento, atomicidad, etc
    
    //Objetivo del test: recibir un listado de los users validando el codigo de respuesta y que devuelva al menos uno
    @Test
    public void getUsers_shouldReturnUsers() {
        RestAssured.baseURI = "https://gorest.co.in/public/v2";

        given()
            .header("Authorization", TOKEN)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }


    //Objetivo del test: Crear un usuario mike + numero aleatorio@test.com y validar codigo de respuesta, un id, email el mismo que se mando y nombre igual al que se mando
    @Test
    public void createUser_shouldCreateAndReturnUserData() {
    RestAssured.baseURI = "https://gorest.co.in/public/v2";

    String uniqueEmail = "mike" + UUID.randomUUID() + "@test.com";

    String requestBody = """
        {
            "name": "Mike Tester",
            "gender": "male",
            "email": "%s",
            "status": "active"
        }
        """.formatted(uniqueEmail);

    given()
        .header("Authorization", TOKEN)
        .contentType("application/json")
        .body(requestBody)
    .when()
        .post("/users")
    .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("email", equalTo(uniqueEmail))
        .body("name", equalTo("Mike Tester"));
}


//Objetivo del test: Crear un usuario y despues con su ID consultar ese ID y validar el codigo de respuesta, ID, email y nombre
@Test
public void getUserById_shouldReturnUserDetails() {
    RestAssured.baseURI = "https://gorest.co.in/public/v2";

    // Crear un nuevo usuario
    String email = "mike" + UUID.randomUUID() + "@test.com";

    String requestBody = """
        {
            "name": "Mike Detail",
            "gender": "male",
            "email": "%s",
            "status": "active"
        }
        """.formatted(email);

    // Hacer POST y obtener ID y validar status code
    Response createResponse = given()
        .header("Authorization", TOKEN)
        .contentType("application/json")
        .body(requestBody)
    .when()
        .post("/users");

    createResponse.then().statusCode(201);

    int userId = createResponse.jsonPath().getInt("id");

    // Hacer GET con ID
    given()
        .header("Authorization", TOKEN)
    .when()
        .get("/users/" + userId)
    .then()
        .statusCode(200)
        .body("id", equalTo(userId))
        .body("email", equalTo(email))
        .body("name", equalTo("Mike Detail"));
}



}
