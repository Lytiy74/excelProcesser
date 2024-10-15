package org.lytiy.excelprocessor;

import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;
import java.util.function.Consumer;

class ExcelCellValueSetterImpl implements ICellValueSetter {
    private final Row row;
    private final ICellValueExtractor cellValueExtractor;
    private final HashMap<String, Integer> identifiedColumns;

    public ExcelCellValueSetterImpl(Row row, ICellValueExtractor cellValueExtractor, HashMap<String, Integer> identifiedColumns) {
        this.row = row;
        this.cellValueExtractor = cellValueExtractor;
        this.identifiedColumns = identifiedColumns;
    }

    @Override
    public void setCellValue(String columnName, Consumer<String> setter) {
        if (identifiedColumns.containsKey(columnName)) {
            int columnIndex = identifiedColumns.get(columnName);
            String value = cellValueExtractor.getStringCellValue(row.getCell(columnIndex));
            setter.accept(value);
        } else {
            setter.accept("N/A");
        }
    }

    @Override
    public void setIntegerCellValue(String columnName, Consumer<Integer> setter) {
        if (identifiedColumns.containsKey(columnName)) {
            int columnIndex = identifiedColumns.get(columnName);
            Integer value = cellValueExtractor.getIntegerCellValue(row.getCell(columnIndex));
            setter.accept(value);
        } else {
            setter.accept(0);
        }
    }

    @Override
    public void setDoubleCellValue(String columnName, Consumer<Double> setter) {
        if (identifiedColumns.containsKey(columnName)) {
            int columnIndex = identifiedColumns.get(columnName);
            Double value = cellValueExtractor.getDoubleCellValue(row.getCell(columnIndex));
            setter.accept(value);
        } else {
            setter.accept(0.0);
        }
    }
}