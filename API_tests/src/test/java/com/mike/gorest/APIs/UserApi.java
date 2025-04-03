package com.mike.gorest.APIs;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;





public class UserApi {
    private static final String BASE_URL = "https://gorest.co.in/public/v2";
    
    //Tenes que setear como variable entorno local el nombre "GOREST_TOKEN" con tu token de Go rest para que lo pueda consumir
    
    private String getToken() {
        String token = System.getenv("GOREST_TOKEN");
        if (token == null) {
            throw new IllegalStateException("GOREST_TOKEN is not set.");
        }
        return token;
    }


    
    

    public Response getUsers() {
        RestAssured.baseURI = BASE_URL;
        System.out.println("TOKEN ACTUAL: " + getToken());

        return given().header("Authorization", getToken()).when().get("/users");
    }


    //Crea un usuario con los datos name,email,gender,status
    public Response createUSer(String name, String email, String gender, String status){
        RestAssured.baseURI = BASE_URL;
        System.out.println("TOKEN ACTUAL: " + getToken());
        String requestBody = String.format("""
        {
            "name": "%s", "email": "%s", "gender": "%s", "status": "%s"
        }
        """, name, email, gender, status);

        return given().header("Authorization", getToken()).contentType("application/json").body(requestBody).when().post("/users");
    }

    //retorna el contenido de un usuario buscado por userID [retorna: userID,name,email,gender,status]
    public Response getUserById(int userId){
        System.out.println("TOKEN ACTUAL: " + getToken());
        RestAssured.baseURI = BASE_URL;
        return given().header("Authorization",getToken()).when().get("/users/" + userId);
    }
    

}
