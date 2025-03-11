package com.rbhatt.selenium.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class JsonDataReader {
    
    private final ObjectMapper objectMapper;
    
    public JsonDataReader() {
        this.objectMapper = new ObjectMapper();
    }
    
    public List<HashMap<String, String>> readData(String filePath) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        return objectMapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
    }
}
