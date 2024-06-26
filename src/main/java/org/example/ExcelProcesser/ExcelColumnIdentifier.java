package org.example.ExcelProcesser;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.example.Util.JaccardCalculation;

import java.util.*;

/**
 * This class is responsible for identifying the columns in an Excel row based on a given target column map.
 */
class ExcelColumnIdentifier {

    /**
     * Identifies the columns in the source row and returns a map of identified categories and their respective column indices.
     *
     * @param sourceRow The row from which to identify columns.
     * @param targetColumns A map of target column categories and their corresponding column names.
     * @return A map of identified categories and their respective column indices.
     */
    public HashMap<String, Integer> identifyColumns(Row sourceRow, HashMap<String, List<String>> targetColumns){
        // Invert the target column map for efficient lookup
        HashMap<String, String> columnKeyMap =  invertColumnMap(targetColumns);

        // Extract column names from the source row
        List<String> sourceColumns = extractColumnNames(sourceRow);

        // Initialize a map to store the identified columns
        HashMap<String, Integer> identifiedColumns = new HashMap<>();

        // Iterate through the source columns
        for (String column : sourceColumns){
            // Find the best match category for the current column
            String bestMatchCategory = findBestMatch(column, columnKeyMap);

            // Add the identified category and its column index to the map
            identifiedColumns.put(bestMatchCategory, sourceColumns.indexOf(column));

            // Print the column and its identified category
            System.out.println(column + " -> " +bestMatchCategory);
        }

        // Return the map of identified columns
        return identifiedColumns;
    }

    /**
     * Extracts column names from the source row.
     *
     * @param sourceRow The row from which to extract column names.
     * @return A list of column names.
     */
    private List<String> extractColumnNames(Row sourceRow) {
        List<String> sourceColumns = new ArrayList<>();

        // Iterate through the cells in the row
        for (Cell cell : sourceRow){
            // Get the cell value and convert it to lowercase
            String cellValue = cell.getStringCellValue().toLowerCase();

            // Add the cell value to the list of column names
            sourceColumns.add(cellValue);
        }

        // Return the list of column names
        return sourceColumns;
    }

    /**
     * Inverts the target column map for efficient lookup.
     *
     * @param targetColumns The original target column map.
     * @return The inverted target column map.
     */
    private HashMap<String, String> invertColumnMap(HashMap<String, List<String>> targetColumns) {
        HashMap<String, String> columnKeyMap = new HashMap<>();

        // Iterate through the categories in the target column map
        for (String key : targetColumns.keySet()) {
            // Iterate through the column names for the current category
            for (String value : targetColumns.get(key)) {
                // Add the column name and its category to the inverted map
                columnKeyMap.put(value, key);
            }
        }

        // Return the inverted target column map
        return columnKeyMap;
    }

    /**
     * Finds the best match category for a given column name based on the Jaccard similarity score.
     *
     * @param columnName The name of the column to find a match for.
     * @param columnKeyMap The inverted target column map.
     * @return The best match category for the given column name.
     */
    private String findBestMatch(String columnName, HashMap<String, String> columnKeyMap){
        // Convert the column name to a set of characters
        Set<Character> columnSet = stringToCharSet(columnName);

        // Initialize variables to store the best match category and score
        String bestCategory = "UNKNOWN";
        double bestScore = Double.MIN_VALUE;

        // Iterate through the entries in the inverted column map
        for(Map.Entry<String, String> entry : columnKeyMap.entrySet()){
            // Get the possible column name and its category
            String possibleName = entry.getKey();
            String category = entry.getValue();

            // Convert the possible column name to a set of characters
            Set<Character> possibleSet =stringToCharSet(possibleName);

            // Calculate the Jaccard similarity score between the column name and the possible column name
            double score = JaccardCalculation.jaccardSimilarity(columnSet, possibleSet);

            // If the score is higher than the current best score, update the best match category and score
            if (score > bestScore){
                bestScore = score;
                bestCategory = category;
            }
        }

        // Return the best match category
        return bestCategory;
    }

    /**
     * Converts a string to a set of characters.
     *
     * @param str The string to convert.
     * @return A set of characters.
     */
    private Set<Character> stringToCharSet(String str) {
        Set<Character> charSet = new HashSet<>();

        // Iterate through the characters in the string
        for (char c : str.toCharArray()) {
            // Add the character to the set
            charSet.add(c);
        }

        // Return the set of characters
        return charSet;
    }
}
