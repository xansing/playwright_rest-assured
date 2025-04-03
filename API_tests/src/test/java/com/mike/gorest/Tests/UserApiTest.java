package com.mike.gorest.Tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mike.gorest.Tags;
import com.mike.gorest.APIs.UserApi;
import org.junit.jupiter.api.Tag;

import io.restassured.response.Response;

public class UserApiTest {
    UserApi userApi = new UserApi();

    //Objetivo del test: recibir un listado de los users validando el codigo de respuesta y que devuelva al menos uno
    @Tag(Tags.API)
    @Tag(Tags.SMOKE)
    @Test
    public void getUsers_shouldReturnListOfUsers(){
        userApi.getUsers().then().statusCode(200).body("size()", greaterThan(0));
    }


    //Objetivo del test: Crear un usuario mike + numero aleatorio@test.com y validar codigo de respuesta, un id, email el mismo que se mando y nombre igual al que se mando
    @Tag(Tags.API)
    @Tag(Tags.SMOKE)
    @Test
    public void createUsers_shouldCreateAndReturnUserData(){
    
    //use System.currentTimeMillis() en vez de UUID.randomUUID() para asegurar que sea imposible colision de datos existentes
        String email = "mike" + UUID.randomUUID() + "@test.com";

        userApi.createUSer("Mike Test", email, "male", "active").then().statusCode(201)
        .body("id", notNullValue()).body("email", equalTo(email)).body("name", equalTo("Mike Test"));
    }

    //Objetivo del test: Crear un usuario y despues con su ID consultar ese ID y validar el codigo de respuesta, ID, email y nombre
    @Tag(Tags.API)
    @Tag(Tags.REGRESSION)
    @Test
    public void getUserById_shouldReturnUserDetails(){
        //use System.currentTimeMillis() en vez de UUID.randomUUID() para asegurar que sea imposible colision de datos existentes
        String email = "mike" + UUID.randomUUID() + "@test.com";

        //Crear el usuario y retener el ID
        Response creaResponse = userApi.createUSer("Mike Creation", email, "male", "active");
        creaResponse.then().statusCode(201);
        int userId= creaResponse.jsonPath().getInt("id");
        
        //utilizando el ID valido que devuelve los valores que espero
        userApi.getUserById(userId).then().statusCode(200).body("id", equalTo(userId))
        .body("email", equalTo(email)).body("name", equalTo("Mike Creation"));
    }
}

