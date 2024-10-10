package org.example.strategy;

import org.example.excelprocessor.IExcelProductReader;


public class ExcelProductReadingStrategy implements IExcelProcessingStrategy{
    private IExcelProductReader productReader;

    public ExcelProductReadingStrategy(IExcelProductReader productReader) {
        this.productReader = productReader;
    }

    @Override
    public void execute(ExcelProcessingContext context) {
        context.setProductPositionHashMap(productReader.getProductsMapFromSheet(context.getSheet()));
    }
}
