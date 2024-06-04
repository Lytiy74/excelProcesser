package org.example.ExcelProcesser;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;

class CellValueExtractor {
    private final HashMap<String, Integer> identifiedColumns;
    private final DataFormatter formatter;

    CellValueExtractor(HashMap<String, Integer> identifiedColumns) {
        this.identifiedColumns = identifiedColumns;
        this.formatter = new DataFormatter();
    }

    String getCellValue(Row row, String columnName) {
        int columnIndex = identifiedColumns.getOrDefault(columnName, -1);
        return columnIndex != -1 ? formatter.formatCellValue(row.getCell(columnIndex)) : "N/A";
    }

    Integer getIntegerCellValue(Row row, String columnName) {
        int columnIndex = identifiedColumns.getOrDefault(columnName, -1);
        return columnIndex != -1 ? ((int) row.getCell(columnIndex).getNumericCellValue()) : -1;
    }

    Double getDoubleCellValue(Row row, String columnName) {
        int columnIndex = identifiedColumns.getOrDefault(columnName, -1);
        return columnIndex != -1 ? row.getCell(columnIndex).getNumericCellValue() : -1;
    }
}
