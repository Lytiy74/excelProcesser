package org.example.strategy;

import org.example.excelprocessor.IExcelProductWriter;

public class ExcelProductWriteStrategy implements IExcelProcessingStrategy{
    private final IExcelProductWriter excelProductWriter;

    public ExcelProductWriteStrategy(IExcelProductWriter excelProductWriter) {
        this.excelProductWriter = excelProductWriter;
    }

    @Override
    public void execute(ExcelProcessingContext context) {
        excelProductWriter.writeProductsToSheet(context.getProductPositionHashMap(), context.getSheet());
    }
}
