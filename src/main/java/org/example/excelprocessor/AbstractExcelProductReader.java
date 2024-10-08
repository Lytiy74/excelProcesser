package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.product.ProductPosition;

import java.util.HashMap;

public abstract class AbstractExcelProductReader implements IExcelProductReader{
    @Override
    public HashMap<String, ProductPosition> getProductsMapFromSheet(Sheet sheet) {
        return null;
    }

    @Override
    public HashMap<String, ProductPosition> getProductsMapFromSheet() {
        return null;
    }

    @Override
    public ProductPosition getProduct(Row row) {
        return null;
    }

    @Override
    public ProductPosition getProduct() {
        return null;
    }
}
