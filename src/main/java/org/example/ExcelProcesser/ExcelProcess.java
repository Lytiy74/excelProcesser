package org.example.ExcelProcesser;

import org.apache.poi.ss.usermodel.*;
import org.example.Product.Gender;
import org.example.Product.ProductPosition;
import org.example.ProductProcess.ProductProcess;

import java.util.HashMap;
import java.util.List;

/**
 * This class processes an Excel workbook to retrieve column indexes based on predefined categories.
 */
public class ExcelProcess {
    private final Workbook workbook;
    private final Sheet sheet;
    private final ProductProcess productProcess;
    private final CellValueExtractor cellValueExtractor;
    private final HashMap<String, Integer> identifiedColumns;
    private final ExcelColumnIdentifier columnIdentifier;


    public ExcelProcess(Workbook workbook, int sheetIndex, HashMap<String, List<String>> targetColumns) {
        this.workbook = workbook;
        this.sheet = workbook.getSheetAt(sheetIndex);
        columnIdentifier = new ExcelColumnIdentifier();
        identifiedColumns = columnIdentifier.identifyColumns(workbook.getSheetAt(sheetIndex).getRow(0),targetColumns);
        this.productProcess = new ProductProcess();
        this.cellValueExtractor = new CellValueExtractor(identifiedColumns);
    }

    public void addProductsToSheet(HashMap<String, ProductPosition> products, List<String> headers) {
        Sheet sheet1 = this.workbook.createSheet("Processed");
        int rowIndex = 0;

        // Create header row
        Row headerRow = sheet1.createRow(rowIndex);
        for (int i = 0; i < headers.size(); i++) {
            headerRow.createCell(i).setCellValue(headers.get(i));
        }
        rowIndex++; // Move to the next row for product data

        // Populate product data
        for (String key : products.keySet()) {
            Row row = sheet1.createRow(rowIndex);
            row.createCell(headers.indexOf("article")).setCellValue(products.get(key).getArticle());
            row.createCell(headers.indexOf("productName")).setCellValue(products.get(key).getProductName());
            row.createCell(headers.indexOf("sizes")).setCellValue(products.get(key).getSizes());
            row.createCell(headers.indexOf("tradeMark")).setCellValue(products.get(key).getTradeMark());
            row.createCell(headers.indexOf("countryOrigin")).setCellValue(products.get(key).getCountryOrigin());
            row.createCell(headers.indexOf("quantity")).setCellValue(products.get(key).getQuantity());
            row.createCell(headers.indexOf("composition")).setCellValue(products.get(key).getComposition());
            row.createCell(headers.indexOf("gender")).setCellValue(products.get(key).getGender().toString());
            row.createCell(headers.indexOf("hsCode")).setCellValue(products.get(key).getHsCode());
            row.createCell(headers.indexOf("bruttoWeight")).setCellValue(products.get(key).getBruttoWeight());
            row.createCell(headers.indexOf("price")).setCellValue(products.get(key).getPrice());
            rowIndex++;
        }
    }

    public HashMap<String, ProductPosition> collectProducts() {
        HashMap<String, ProductPosition> products = new HashMap<>();
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            ProductPosition product = buildProductPosition(row);
            if (!products.containsKey(product.getArticle())) {
                products.put(product.getArticle(), product);
            } else {
                ProductPosition product2 = products.get(product.getArticle());
                products.put(product2.getArticle(), productProcess.mergeDuplications(product, product2));
            }
        }
        return products;
    }


    private ProductPosition buildProductPosition(Row row) {
        return ProductPosition.newBuilder()
                .setArticle(cellValueExtractor.getCellValue(row, "article"))
                .setProductName(cellValueExtractor.getCellValue(row, "productName").toLowerCase())
                .setSizes(cellValueExtractor.getCellValue(row, "sizes"))
                .setTradeMark(cellValueExtractor.getCellValue(row, "tradeMark"))
                .setCountryOrigin(cellValueExtractor.getCellValue(row, "countryOrigin"))
                .setQuantity(cellValueExtractor.getIntegerCellValue(row, "quantity"))
                .setComposition(cellValueExtractor.getCellValue(row, "composition"))
                .setGender(Gender.fromString(cellValueExtractor.getCellValue(row, "gender")))
                .setHsCode(cellValueExtractor.getCellValue(row, "hsCode"))
                .setBruttoWeight(cellValueExtractor.getIntegerCellValue(row, "bruttoWeight"))
                .setPrice(cellValueExtractor.getDoubleCellValue(row, "price"))
                .build();
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public HashMap<String, Integer> getIdentifiedColumns() {
        return identifiedColumns;
    }

    public Sheet getSheet() {
        return sheet;
    }
}
