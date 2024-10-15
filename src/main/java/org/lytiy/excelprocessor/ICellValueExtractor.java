package org.lytiy.excelprocessor;

import org.apache.poi.ss.usermodel.Cell;

public interface ICellValueExtractor {
    String getStringCellValue(Cell cell);

    Integer getIntegerCellValue(Cell cell);

    Double getDoubleCellValue(Cell cell);
}