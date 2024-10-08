package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.product.ProductPosition;

import java.util.HashMap;

public interface IExcelProductReader {
    HashMap<String, ProductPosition> getProductsMapFromSheet(Sheet sheet);
    HashMap<String, ProductPosition> getProductsMapFromSheet();
    ProductPosition getProduct(Row row);
    ProductPosition getProduct();
}
