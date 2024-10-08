package org.example.excelprocessor;

import org.example.product.productprocess.ProductProcess;

import java.util.HashMap;
import java.util.List;

public class ExcelProductReader extends AbstractExcelProductReader implements IExcelProductReader{
    public ExcelProductReader(IExcelProductBuilder productBuilder, ProductProcess productProcess, HashMap<String, List<String>> targetColumns) {
        super(productBuilder, productProcess, targetColumns);
    }
}
