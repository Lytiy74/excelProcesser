package org.lytiy.excelprocessor;

import java.util.List;

public class ExcelProductWriter extends AbstractExcelProductWriter implements IExcelProductWriter{
    public ExcelProductWriter(List<String> targetColumns) {
        super(targetColumns);
    }
}
