package org.example.ExcelProcesser;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.Util.JaccardCalculation;
import org.example.Util.MapConverter;

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
        HashMap<String, String> columnKeyMap = MapConverter.invertColumnMap(targetColumns);

        // Extract column names from the source row
        List<String> sourceColumns = extractColumnNames(sourceRow);

        // Initialize a map to store the identified columns
        HashMap<String, Integer> identifiedColumns = new HashMap<>();

        // Iterate through the source columns
        for (String column : sourceColumns){
            // Find the best match category for the current column
            String bestMatchCategory = JaccardCalculation.findBestMatch(column, columnKeyMap);

            // Add the identified category and its column index to the map
            identifiedColumns.put(bestMatchCategory, sourceColumns.indexOf(column));
        }

        // Return the map of identified columns
        return identifiedColumns;
    }
    public int findAndGetNumberOfHeaderRow(Sheet sheet, HashMap<String,List<String>> targetColumns){
        return JaccardCalculation.findBestMatchRow(sheet,targetColumns);
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

}
