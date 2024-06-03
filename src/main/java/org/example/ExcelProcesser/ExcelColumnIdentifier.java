package org.example.ExcelProcesser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.Util.JsonFileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelColumnIdentifier {

    /**
     * The path to the JSON file containing column names and their categories.
     */
    private static final String COLUMNS_NAMES_JSON_PATH = "src/main/resources/columnNames.json";

    /**
     * Processes the given Excel workbook to retrieve column indexes based on predefined categories.
     *
     * @param sheet The Excel sheet to be processed.
     * @return The processed Excel sheet.
     */
     HashMap<String, Integer> identifyColumns(Sheet sheet) {
        // Initialize the JsonFileReader
        JsonFileReader reader = new JsonFileReader();

        // Read column names and their categories from the JSON file
        HashMap<String, List<String>> map = reader.readJsonToHashMapList(COLUMNS_NAMES_JSON_PATH);

        // Parse the column names and their categories into a separate HashMap
        HashMap<String, String> categoriesOfColumn = parseColumnsToCategories(map);

        // Initialize a HashMap to store the column indexes with default values of -1
        HashMap<String, Integer> columnIndexes = initializeColumnIndexesWithDefaultValues(map);

        // Retrieve the column indexes from the first row of the sheet
        return getColumnIndexesFromSheet(sheet, columnIndexes, categoriesOfColumn);
    }

    /**
     * Parses the given map of column categories to their corresponding column names
     * and returns a new HashMap that maps each column name to its category.
     *
     * @param map A HashMap that maps column categories to their corresponding column names.
     *            The keys are the categories of columns, and the values are lists of column names.
     *
     * @return A HashMap that maps each column name to its category.
     *         The keys are the column names, and the values are the categories of columns.
     */
    private HashMap<String, String> parseColumnsToCategories(HashMap<String, List<String>> map) {
        // Initialize a new HashMap to store the column names and their corresponding categories
        HashMap<String, String> categoriesOfColumn = new HashMap<>();

        // Iterate over each entry in the given map
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            // For each column name in the current category, add an entry to the categoriesOfColumn map
            for (String string : stringListEntry.getValue()) {
                categoriesOfColumn.put(string.toLowerCase(), stringListEntry.getKey());
            }
        }

        // Return the HashMap that maps each column name to its category
        return categoriesOfColumn;
    }

    /**
     * Initializes a HashMap with column categories as keys and default values of -1.
     *
     * @param map A HashMap that maps column categories to their corresponding column names.
     *            The keys are the categories of columns, and the values are lists of column names.
     *
     * @return A HashMap that stores the column indexes. The keys are the categories of columns,
     *         and the values are the corresponding column indexes. The default value for each category
     *         is -1, indicating that a column index has not been found yet.
     */
    private HashMap<String, Integer> initializeColumnIndexesWithDefaultValues(HashMap<String, List<String>> map) {
        HashMap<String, Integer> columnIndexes = new HashMap<>();

        // Iterate over each category in the map
        for (String key : map.keySet()) {
            // Add the category to the columnIndexes map with a default value of -1
            columnIndexes.put(key, -1);
        }

        // Return the initialized columnIndexes map
        return columnIndexes;
    }

    /**
     * Retrieves the column indexes from the first row of the given sheet.
     *
     * @param sheet The sheet from which to retrieve the column indexes.
     * @param columnIndexes A HashMap that stores the column indexes. The keys are the categories of columns, and the values are the corresponding column indexes.
     * @param categoriesOfColumn A HashMap that maps column names to their categories. The keys are the column names, and the values are the categories of columns.
     */
    private HashMap<String, Integer> getColumnIndexesFromSheet(Sheet sheet, HashMap<String, Integer> columnIndexes, HashMap<String, String> categoriesOfColumn) {
        // Iterate over each cell in the first row of the sheet
        for (Cell cell : sheet.getRow(0)) {
            // Get the category of the current column
            String category = categoriesOfColumn.get(cell.getStringCellValue().toLowerCase());
            // If the category exists in the columnIndexes map, update its value with the current column index
            if (category != null) {
                columnIndexes.put(category, cell.getColumnIndex());
            }
        }
        return columnIndexes;
    }
}
