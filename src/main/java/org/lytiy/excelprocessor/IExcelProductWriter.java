package org.lytiy.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.lytiy.cargo.product.ProductPosition;

import java.util.HashMap;

public interface IExcelProductWriter {
    // Записує один продукт у вказаний лист
    void writeProductToSheet(ProductPosition productPosition, Sheet sheet);

    // Записує колекцію продуктів у вказаний лист
    void writeProductsToSheet(HashMap<String, ProductPosition> productPositionHashMap, Sheet sheet);

    // Записує один продукт у вказаний рядок
    void writeProductToRow(ProductPosition productPosition, Row row);
}

