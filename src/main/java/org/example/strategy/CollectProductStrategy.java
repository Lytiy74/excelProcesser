package org.example.strategy;

import org.example.excelprocessor.ExcelProcess;

public class CollectProductStrategy implements ExcelProcessorStrategy{
    ExcelProcessorContext context;
    public CollectProductStrategy(ExcelProcessorContext context) {
        this.context = context;
    }

    @Override
    public void execute(ExcelProcess process) {
        context.setProductPositionHashMap(process.collectProducts());
    }
}
