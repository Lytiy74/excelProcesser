package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.product.ProductPosition;

import java.util.HashMap;

public interface IExcelProductWriter {
    void writeProductToSheet(ProductPosition productPosition, Sheet sheet);

    void writeProductToSheet(ProductPosition productPosition);

    void writeProductToSheet(HashMap<String, ProductPosition> productPositionHashMap, Sheet sheet);

    void writeProductToSheet(HashMap<String, ProductPosition> productPositionHashMap);

    void writeProductToRow(ProductPosition productPosition, Row row);

}
