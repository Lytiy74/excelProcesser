package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for extracting cell values from an Apache POI Row object.
 * It uses a HashMap to map column names to their respective indices.
 */
class CellValueExtractor {
    private static final Logger logger = LoggerFactory.getLogger(CellValueExtractor.class);

    /**
     * An instance of DataFormatter used for formatting cell values.
     */
    private final DataFormatter formatter;

    /**
     * Constructs a new CellValueExtractor object.
     *
     */
    CellValueExtractor() {
        logger.info("Initializing CellValueExtractor...");
        this.formatter = new DataFormatter();
        logger.info("CellValueExtractor initialized successfully.");
    }

    /**
     * Retrieves the cell value as a String.
     *
     * @return The cell value as a String, or "N/A" if the column name is not found in the identifiedColumns map.
     */
    String getStringCellValue(Cell cell) {
        throwIfCellIsNull(cell);
        logger.debug("Retrieving cell value, cell address {}",cell.getAddress());
        return formatter.formatCellValue(cell);
    }

    /**
     * Retrieves the cell value as an Integer.
     *
     * @return The cell value as an Integer, or -1 if the column name is not found in the identifiedColumns map.
     */
    Integer getIntegerCellValue(Cell cell) {
        throwIfCellIsNull(cell);
        logger.debug("Retrieving cell value, cell address {}",cell.getAddress());
        return (int)cell.getNumericCellValue();
    }

    /**
     * Retrieves the cell value as a Double.
     *
     * @return The cell value as a Double, or -1.0 if the column name is not found in the identifiedColumns map.
     */
    Double getDoubleCellValue(Cell cell) {
        throwIfCellIsNull(cell);
        logger.debug("Retrieving cell value, cell address {}",cell.getAddress());
        return cell.getNumericCellValue();
    }

    private static void throwIfCellIsNull(Cell cell) {
        if (cell == null) {
            logger.warn("Cell is null, throwing exception");
            throw new IllegalArgumentException("Cell can`t be null");
        }
    }
}
