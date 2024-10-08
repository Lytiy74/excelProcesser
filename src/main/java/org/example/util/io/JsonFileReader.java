package org.example.util.io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class JsonFileReader implements Reader {
    private static final Logger logger = LoggerFactory.getLogger(JsonFileReader.class);
    private final ObjectMapper objectMapper;

    public JsonFileReader() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String read(String filePath) throws IOException {
        logger.debug("Reading file from '{}'", filePath);
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            logger.debug("Successfully read file content from '{}'", filePath);
            return content;
        } catch (IOException e) {
            logger.error("Failed to read file from '{}'", filePath, e);
            throw e;
        }
    }

    @Override
    public <T> T readJson(String filePath, TypeReference<T> typeReference) throws IOException {
        logger.debug("Reading JSON file from '{}'", filePath);
        try {
            String jsonContent = read(filePath);
            T result = objectMapper.readValue(jsonContent, typeReference);
            logger.debug("Successfully parsed JSON content from '{}'", filePath);
            return result;
        } catch (IOException e) {
            logger.error("Failed to parse JSON file from '{}'", filePath, e);
            throw e;
        }
    }

    public HashMap<String, String> readJsonToHashMap(String filePath) throws IOException {
        logger.debug("Reading JSON file into HashMap from '{}'", filePath);
        TypeReference<HashMap<String, String>> typeReference = new TypeReference<>() {};
        return readJson(filePath, typeReference);
    }

    public HashMap<String, List<String>> readJsonObjectArrayToMap(String filePath) throws IOException {
        logger.debug("Reading JSON file into HashMap<String, List<String>> from '{}'", filePath);
        TypeReference<LinkedHashMap<String, List<String>>> typeReference = new TypeReference<>() {};
        return readJson(filePath, typeReference);
    }
}