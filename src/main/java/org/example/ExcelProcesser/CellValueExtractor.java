package org.example.ExcelProcesser;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * This class is responsible for extracting cell values from an Apache POI Row object.
 * It uses a HashMap to map column names to their respective indices.
 */
class CellValueExtractor {
    private static final Logger logger = LoggerFactory.getLogger(CellValueExtractor.class);
    //TODO 16:00 26.06.2024 REFACTOR A LOGIC OF METHODS, Change signature -> methods must receive a certain cell to extract value
    //TODO NO identifiedColumns map
    /**
     * A HashMap that maps column names to their respective indices.
     */
    private final HashMap<String, Integer> identifiedColumns;

    /**
     * An instance of DataFormatter used for formatting cell values.
     */
    private final DataFormatter formatter;

    /**
     * Constructs a new CellValueExtractor object.
     *
     * @param identifiedColumns A HashMap that maps column names to their respective indices.
     */
    CellValueExtractor(HashMap<String, Integer> identifiedColumns) {
        logger.info("Initializing CellValueExtractor...");
        this.identifiedColumns = identifiedColumns;
        this.formatter = new DataFormatter();
        logger.info("CellValueExtractor initialized successfully.");
    }

    /**
     * Retrieves the cell value as a String.
     *
     * @param row The Apache POI Row object from which to extract the cell value.
     * @param columnName The name of the column from which to extract the cell value.
     * @return The cell value as a String, or "N/A" if the column name is not found in the identifiedColumns map.
     */
    String getCellValue(Row row, String columnName) {
        logger.debug("Retrieving cell value for column '{}' in row {}.", columnName, row.getRowNum());
        int columnIndex = identifiedColumns.getOrDefault(columnName, -1);
        return columnIndex != -1 ? formatter.formatCellValue(row.getCell(columnIndex)) : "N/A";
    }

    /**
     * Retrieves the cell value as an Integer.
     *
     * @param row The Apache POI Row object from which to extract the cell value.
     * @param columnName The name of the column from which to extract the cell value.
     * @return The cell value as an Integer, or -1 if the column name is not found in the identifiedColumns map.
     */
    Integer getIntegerCellValue(Row row, String columnName) {
        logger.debug("Retrieving cell value for column '{}' in row {}.", columnName, row.getRowNum());
        int columnIndex = identifiedColumns.getOrDefault(columnName, -1);
        return columnIndex != -1 ? ((int) row.getCell(columnIndex).getNumericCellValue()) : -1;
    }

    /**
     * Retrieves the cell value as a Double.
     *
     * @param row The Apache POI Row object from which to extract the cell value.
     * @param columnName The name of the column from which to extract the cell value.
     * @return The cell value as a Double, or -1.0 if the column name is not found in the identifiedColumns map.
     */
    Double getDoubleCellValue(Row row, String columnName) {
        logger.debug("Retrieving cell value for column '{}' in row {}.", columnName, row.getRowNum());
        int columnIndex = identifiedColumns.getOrDefault(columnName, -1);
        return columnIndex != -1 ? row.getCell(columnIndex).getNumericCellValue() : -1.0;
    }
}
