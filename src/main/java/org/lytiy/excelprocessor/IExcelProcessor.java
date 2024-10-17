package org.lytiy.excelprocessor;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.lytiy.cargo.product.ProductPosition;

import java.util.HashMap;

/**
 * ExcelProcessor is an interface for processing Excel sheets containing product information.
 * It provides methods to add individual products as well as collections of products to specific sheets,
 * collect product data from sheets, and retrieve workbook and sheet details.
 */
@Deprecated(since = "08.10.2024")
public interface IExcelProcessor {
    void addProductToSheet(ProductPosition product, Sheet sheet);

    void addMapOfProductsToSheet(HashMap<String, ProductPosition> products, Sheet sheet);


    void addMapOfProductsToSheet(HashMap<String, ProductPosition> products);

    HashMap<String, ProductPosition> collectProducts();

    Workbook getWorkbook();

    HashMap<String, Integer> getIdentifiedColumns();

    Sheet getSheet();
}