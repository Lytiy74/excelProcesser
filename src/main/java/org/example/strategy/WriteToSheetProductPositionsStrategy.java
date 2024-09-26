package org.example.strategy;

import org.example.excelprocessor.ExcelProcess;

public class WriteToSheetProductPositionsStrategy implements ExcelProcessorStrategy{
    private final ExcelProcessorContext context;

    public WriteToSheetProductPositionsStrategy(ExcelProcessorContext context) {
        this.context = context;
    }

    @Override
    public void execute(ExcelProcess process) {
        process.addMapOfProductsToSheet(context.getProductPositionHashMap());
    }
}
