package org.lytiy.excelprocessor;

import java.util.function.Consumer;

public interface ICellValueSetter {
    void setCellValue(String columnName, Consumer<String> setter);

    void setIntegerCellValue(String columnName, Consumer<Integer> setter);

    void setDoubleCellValue(String columnName, Consumer<Double> setter);
}