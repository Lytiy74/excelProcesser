package org.example.ExcelProcesser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.Util.JsonFileReader;

import java.util.*;

class ExcelColumnIdentifier {
    /**
     * The path to the JSON file containing column names and their categories.
     */
    private static final String COLUMNS_NAMES_JSON_PATH = "src/main/resources/columnNames.json";
    private LinkedHashMap<String, List<String>> columnsJson;
    private List<String> columnsHeaders;

    ExcelColumnIdentifier() {
        // Initialize the JsonFileReader
        JsonFileReader reader = new JsonFileReader();

        // Read column names and their categories from the JSON file
        String jsonString = reader.read(COLUMNS_NAMES_JSON_PATH);
        columnsJson = reader.readJsonStringToLinkedHashMapList(jsonString);
        columnsHeaders = parseToHeaders(columnsJson);
    }

    private List<String> parseToHeaders(LinkedHashMap<String, List<String>> columnsJson) {
        List<String> headers = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : columnsJson.entrySet()) {
            headers.add(entry.getKey());
        }
        return headers;
    }

    List<String> getColumnsHeaders() {
        return columnsHeaders;
    }

    HashMap<String, Integer> identifyColumns(Sheet sheet) {
        HashMap<String, Integer> columns = new HashMap<>();
        ArrayList<String> categories = parseJsonColumnsToArrayList(columnsJson);
        ArrayList<String> inputHeaders = readHeaderColumns(sheet);
        for (String category : categories){
            String bestMatch = findBestMatch(category,inputHeaders);
            columns.put(bestMatch,category.indexOf(bestMatch));
        }
        return columns;
    }

    private String findBestMatch(String category, ArrayList<String> inputHeaders) {
    }

    private ArrayList<String> readHeaderColumns(Sheet sheet) {
        ArrayList<String> headers = new ArrayList<>();
        Row row = sheet.getRow(0);
        for (Cell cell : row){
            headers.add(cell.getStringCellValue().toLowerCase());
        }
        return headers;
    }

    private ArrayList<String> parseJsonColumnsToArrayList(LinkedHashMap<String, List<String>> columnsJson) {
        ArrayList<String> inputSource = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : columnsJson.entrySet()) {
            inputSource.add(entry.getKey());
        }
        return inputSource;
    }


}
