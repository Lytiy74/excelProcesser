package org.example.Util.IO;

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
    public String read(String filePath) throws IOException {
            return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public HashMap<String, String> readJsonToHashMap(String filePath) throws IOException {
        HashMap<String, String> map;
            String jsonContent = read(filePath);
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(jsonContent, new TypeReference<>() {});
        return map;
    }


    // Метод для читання JSON-файлу і перетворення його на мапу
    public HashMap<String, List<String>> readJsonObjectArrayToMap(String filePath) throws IOException {
        LinkedHashMap<String, List<String>> map;
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        ObjectMapper objectMapper = new ObjectMapper();
        map = objectMapper.readValue(jsonContent, new TypeReference<LinkedHashMap<String, List<String>>>() {});
        return map;
    }
}
