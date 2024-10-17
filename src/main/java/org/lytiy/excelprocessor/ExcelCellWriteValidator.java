package org.lytiy.excelprocessor;

import org.apache.poi.ss.usermodel.*;
import org.lytiy.cargo.product.Gender;

public class ExcelCellWriteValidator {
    public static void validateAndWrite(Cell cell, Object value) {
        if (value == null || value.toString().contains("N/A") || value.toString().contains("duplicate")) {
            CellStyle style = cell.getSheet().getWorkbook().createCellStyle();
            style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = cell.getSheet().getWorkbook().createFont();
            font.setColor(IndexedColors.RED.getIndex());
            style.setFont(font);
            cell.setCellStyle(style);
        }

        if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if(value instanceof Gender){
            cell.setCellValue(((Gender) value).getTitle());
        }else {
            cell.setCellValue(value.toString());
        }
    }
}
