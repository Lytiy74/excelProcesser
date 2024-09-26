package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.product.ProductPosition;

import java.util.HashMap;

/**
 * This interface provides methods for processing Excel files.
 */
public interface ExcelProcess {
    void addProductToSheet(ProductPosition productPosition, Sheet sheet);
    /**
     * Adds a list of products to the specified sheet in the Excel file.
     *
     * @param products A HashMap containing product names as keys and their corresponding positions as values.
     */
    void addMapOfProductsToSheet(HashMap<String, ProductPosition> products, Sheet sheet);
    void addMapOfProductsToSheet(HashMap<String, ProductPosition> products);

    /**
     * Collects products from the Excel file and returns them as a HashMap.
     *
     * @return A HashMap containing product names as keys and their corresponding positions as values.
     */
    HashMap<String, ProductPosition> collectProducts();

    /**
     * Returns the Apache POI Workbook object representing the Excel file.
     *
     * @return The Apache POI Workbook object.
     */
    Workbook getWorkbook();

    /**
     * Returns a HashMap containing the identified columns in the Excel file.
     *
     * @return A HashMap with column names as keys and their corresponding indices as values.
     */
    HashMap<String, Integer> getIdentifiedColumns();

    /**
     * Returns the Apache POI Sheet object representing the current sheet in the Excel file.
     *
     * @return The Apache POI Sheet object.
     */
    Sheet getSheet();
}
