package org.lytiy.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.lytiy.cargo.product.ProductPosition;

import java.util.HashMap;

public interface IExcelProductReader {
    HashMap<String, ProductPosition> getProductsMapFromSheet(Sheet sheet);
    ProductPosition buildProductFromRow(Row row);
}
