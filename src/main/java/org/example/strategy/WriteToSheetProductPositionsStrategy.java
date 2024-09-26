package org.example.strategy;

import org.example.excelprocessor.IExcelProcessor;

public class WriteToSheetProductPositionsStrategy implements IExcelProcessorStrategy {
    private final ExcelProcessorContext context;

    public WriteToSheetProductPositionsStrategy(ExcelProcessorContext context) {
        this.context = context;
    }

    @Override
    public void execute(IExcelProcessor process) {
        process.addMapOfProductsToSheet(context.getProductPositionHashMap());
    }
}
