package org.example.Util.IO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Util.Reader;

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


    // Метод для читання JSON-файлу і перетворення його на мапу
    public HashMap<String, List<String>> readJsonObjectArrayToMap(String filePath) {
        LinkedHashMap<String, List<String>> map;
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(jsonContent, new TypeReference<LinkedHashMap<String, List<String>>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
