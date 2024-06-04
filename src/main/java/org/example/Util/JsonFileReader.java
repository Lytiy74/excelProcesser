package org.example.Util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonFileReader implements Reader {
    @Override
    public String read(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HashMap<String, String> readJsonToHashMap(String filePath) {
        HashMap<String, String> map;
        try {
            String jsonContent = read(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(jsonContent, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
    public LinkedHashMap<String, String> readJsonToLinkedHashMap(String filePath) {
        LinkedHashMap<String, String> map;
        try {
            String jsonContent = read(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(jsonContent, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    public HashMap<String, List<String>> readJsonToHashMapList(String filePath) {
        HashMap<String, List<String>> map;
        try {
            String jsonContent = read(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(jsonContent, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
    public HashMap<String, List<String>> readJsonStringToHashMapList(String jsonString) {
        HashMap<String, List<String>> map;
        try {
            String jsonContent = jsonString;
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(jsonContent, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
    public LinkedHashMap<String, List<String>> readJsonStringToLinkedHashMapList(String jsonString) {
        LinkedHashMap<String, List<String>> map;
        try {
            String jsonContent = jsonString;
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(jsonContent, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
