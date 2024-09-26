package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for extracting cell values from an Apache POI Row object.
 */
public class ExcelCellValueExtractor implements ICellValueExtractor {
    private static final Logger logger = LoggerFactory.getLogger(ExcelCellValueExtractor.class);

    /**
     * An instance of DataFormatter used for formatting cell values.
     */
    private final DataFormatter formatter;

    /**
     * Constructs a new PoiCellValueExtractor object.
     */
    public ExcelCellValueExtractor() {
        logger.info("Initializing PoiCellValueExtractor...");
        this.formatter = new DataFormatter();
        logger.info("PoiCellValueExtractor initialized successfully.");
    }

    /**
     * Retrieves the cell value as a String.
     *
     * @param cell The cell from which to extract the value.
     * @return The cell value as a String, or "N/A" if the cell is null.
     */
    @Override
    public String getStringCellValue(Cell cell) {
        throwIfCellIsNull(cell);
        logger.debug("Retrieving cell value, cell address {}", cell.getAddress());
        return formatter.formatCellValue(cell);
    }

    /**
     * Retrieves the cell value as an Integer.
     *
     * @param cell The cell from which to extract the value.
     * @return The cell value as an Integer, or -1 if the cell is null.
     */
    @Override
    public Integer getIntegerCellValue(Cell cell) {
        throwIfCellIsNull(cell);
        logger.debug("Retrieving cell value, cell address {}", cell.getAddress());
        return (int) cell.getNumericCellValue();
    }

    /**
     * Retrieves the cell value as a Double.
     *
     * @param cell The cell from which to extract the value.
     * @return The cell value as a Double, or -1.0 if the cell is null.
     */
    @Override
    public Double getDoubleCellValue(Cell cell) {
        throwIfCellIsNull(cell);
        logger.debug("Retrieving cell value, cell address {}", cell.getAddress());
        return cell.getNumericCellValue();
    }

    private static void throwIfCellIsNull(Cell cell) {
        if (cell == null) {
            logger.warn("Cell is null, throwing exception");
            throw new IllegalArgumentException("Cell can't be null");
        }
    }
}