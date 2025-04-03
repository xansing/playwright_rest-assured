package com.mike.gorest.Tests;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.mike.gorest.Tags;
import com.mike.gorest.APIs.UserApi;
import org.junit.jupiter.api.Tag;
import com.mike.gorest.utils.TestDataReader;

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
        Map<String, String> userData = TestDataReader.getUserData("mikeTest");
        String name = userData.get("name");
        String gender = userData.get("gender");
        String status = userData.get("status");
        String email = "mike" + UUID.randomUUID() + "@test.com";
        //UUID.randomUUID() para asegurar que sea muy poco probable colision de datos existentes
        

        userApi.createUSer(name, email, gender, status).then().statusCode(201)
        .body("id", notNullValue()).body("email", equalTo(email)).body("name", equalTo(name));
    }

    //Objetivo del test: Crear un usuario y despues con su ID consultar ese ID y validar el codigo de respuesta, ID, email y nombre
    @Tag(Tags.API)
    @Tag(Tags.REGRESSION)
    @Test
    public void getUserById_shouldReturnUserDetails(){
        Map<String, String> userData = TestDataReader.getUserData("mikeCreation");
        String name = userData.get("name");
        String gender = userData.get("gender");
        String status = userData.get("status");
        String email = "mike" + UUID.randomUUID() + "@test.com";
        //UUID.randomUUID() para asegurar que sea muy poco probable colision de datos existentes

        //Crear el usuario y retener el ID
        Response creaResponse = userApi.createUSer(name, email, gender, status);
        creaResponse.then().statusCode(201);
        int userId= creaResponse.jsonPath().getInt("id");
        
        //utilizando el ID valido que devuelve los valores que espero
        userApi.getUserById(userId).then().statusCode(200).body("id", equalTo(userId))
        .body("email", equalTo(email)).body("name", equalTo(name));
    }
}

