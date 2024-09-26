package org.example.strategy;

import org.example.excelprocessor.IExcelProcessor;

public class CollectProductStrategy implements IExcelProcessorStrategy {
    ExcelProcessorContext context;
    public CollectProductStrategy(ExcelProcessorContext context) {
        this.context = context;
    }

    @Override
    public void execute(IExcelProcessor process) {
        context.setProductPositionHashMap(process.collectProducts());
    }
}
