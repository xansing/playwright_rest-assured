package com.mike.gorest.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

//lee el contenido del archivo "src/user-data.json", se puede mejorar parametrizando el archivo que deberia leer

public class TestDataReader {
    private static final ObjectMapper mapper = new ObjectMapper();
    public static Map<String, String> getUserData(String key) {
        try {
            Map<String, Map<String, String>> data = mapper.readValue(
                new File("src/user-data.json"),
                Map.class
            );
            return data.get(key);
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo", e);
        }
    }
}
