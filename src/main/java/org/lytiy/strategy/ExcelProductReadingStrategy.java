package org.lytiy.strategy;

import org.lytiy.excelprocessor.IExcelProductReader;


public class ExcelProductReadingStrategy implements IExcelProcessingStrategy{
    private final IExcelProductReader productReader;

    public ExcelProductReadingStrategy(IExcelProductReader productReader) {
        this.productReader = productReader;
    }

    @Override
    public void execute(ExcelProcessingContext context) {
        context.setProductPositionHashMap(productReader.getProductsMapFromSheet(context.getSheet()));
    }
}
