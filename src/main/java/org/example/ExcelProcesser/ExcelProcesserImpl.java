package org.example.ExcelProcesser;

import org.apache.poi.ss.usermodel.*;
import org.example.Product.Gender;
import org.example.Product.ProductPosition;
import org.example.Product.ProductProcess.ProductProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * This class processes an Excel workbook to retrieve
 * column indexes based on predefined categories.
 */
public class ExcelProcesserImpl implements ExcelProcess {
    private static final Logger logger = LoggerFactory.getLogger(ExcelProcesserImpl.class);
    private final Workbook workbook;
    private final Sheet sheet;
    private final ProductProcess productProcess;
    private final CellValueExtractor cellValueExtractor;
    private final HashMap<String, Integer> identifiedColumns;
    private final ExcelColumnIdentifier columnIdentifier;

    /**
     * Constructor for ExcelProcess class.
     *
     * @param workbook The Apache POI Workbook object representing the Excel workbook.
     * @param sheetIndex The index of the sheet within the workbook to process.
     * @param targetColumns A HashMap containing the predefined categories and their corresponding column names.
     */
    public ExcelProcesserImpl(Workbook workbook, int sheetIndex, HashMap<String, List<String>> targetColumns) {
        logger.info("Initializing ExcelProcesserImpl...");
        this.workbook = workbook;
        this.sheet = workbook.getSheetAt(sheetIndex);
        columnIdentifier = new ExcelColumnIdentifier();
        identifiedColumns = columnIdentifier.identifyColumns(workbook.getSheetAt(sheetIndex).getRow(0),targetColumns);
        this.productProcess = new ProductProcess();
        this.cellValueExtractor = new CellValueExtractor(identifiedColumns);
        logger.info("ExcelProcesserImpl initialized successfully.");
    }
    /**
     * Adds the processed products to a new sheet in the workbook.
     *
     * @param products A HashMap containing the article numbers as keys and ProductPosition objects as values.
     * @param headers A List containing the column headers for the new sheet.
     */
    @Override
    public void addProductsToSheet(HashMap<String, ProductPosition> products, List<String> headers) {
        logger.info("Adding products to sheet...");
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
            logger.info("Adding product with article: {}, to row {}", key, rowIndex);
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
            logger.info("Product processed successfully.");
        }
        logger.info("Products added to sheet successfully.");
    }
    /**
     * Collects the products from the Excel sheet and returns them as a HashMap.
     *
     * @return A HashMap containing the article numbers as keys and ProductPosition objects as values.
     */
    @Override
    public HashMap<String, ProductPosition> collectProducts() {
        logger.info("Collecting products...");
        HashMap<String, ProductPosition> products = new HashMap<>();
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            logger.info("Processing row {}...", rowIndex);
            Row row = sheet.getRow(rowIndex);
            ProductPosition product = buildProductPosition(row);
            if (!products.containsKey(product.getArticle())) {
                logger.info("Adding product with article number: {}", product.getArticle());
                products.put(product.getArticle(), product);
            } else {
                logger.info("Founded duplicate of article {}, try to merge", product.getArticle());
                ProductPosition product2 = products.get(product.getArticle());
                products.put(product2.getArticle(), productProcess.mergeDuplications(product, product2));
            }
        }
        logger.info("Products collected successfully.");
        return products;
    }

    /**
     * Builds a ProductPosition object from the data in a given row.
     *
     * @param row The Apache POI Row object containing the data.
     * @return A ProductPosition object representing the data in the row.
     */
    private ProductPosition buildProductPosition(Row row) {
        logger.info("Building ProductPosition object...");
        ProductPosition product = ProductPosition.newBuilder()
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
        logger.info("ProductPosition object built successfully.");
        return product;
    }
    @Override
    public Workbook getWorkbook() {
        return workbook;
    }
    @Override
    public HashMap<String, Integer> getIdentifiedColumns() {
        return identifiedColumns;
    }
    @Override
    public Sheet getSheet() {
        return sheet;
    }
}
