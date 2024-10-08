package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.product.ProductPosition;

import java.util.HashMap;

public abstract class AbstractExcelProductWriter implements IExcelProductWriter{
    @Override
    public void writeProductToSheet(ProductPosition productPosition, Sheet sheet) {

    }

    @Override
    public void writeProductToSheet(ProductPosition productPosition) {

    }

    @Override
    public void writeProductToSheet(HashMap<String, ProductPosition> productPositionHashMap, Sheet sheet) {

    }

    @Override
    public void writeProductToSheet(HashMap<String, ProductPosition> productPositionHashMap) {

    }

    @Override
    public void writeProductToRow(ProductPosition productPosition, Row row) {

    }
}
