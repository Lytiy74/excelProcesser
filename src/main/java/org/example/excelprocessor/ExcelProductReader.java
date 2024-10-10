package org.example.excelprocessor;

public class ExcelProductReader extends AbstractExcelProductReader implements IExcelProductReader{
    public ExcelProductReader(IExcelProductBuilder productBuilder, int headerRowIndex) {
        super(productBuilder, headerRowIndex);
    }
}
