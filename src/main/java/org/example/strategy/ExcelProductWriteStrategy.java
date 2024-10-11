package org.example.strategy;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.excelprocessor.IExcelProductWriter;

public class ExcelProductWriteStrategy implements IExcelProcessingStrategy {
    private final IExcelProductWriter excelProductWriter;

    public ExcelProductWriteStrategy(IExcelProductWriter excelProductWriter) {
        this.excelProductWriter = excelProductWriter;
    }

    @Override
    public void execute(ExcelProcessingContext context) {
        Sheet processedSheet = context.getOutWorkbook().createSheet("Processed");
        excelProductWriter.writeProductsToSheet(context.getProductPositionHashMap(), processedSheet);
    }
}
