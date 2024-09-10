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
    public static final String ARTICLE = "article";
    public static final String PRODUCT_NAME = "productName";
    public static final String SIZES = "sizes";
    public static final String TRADE_MARK = "tradeMark";
    public static final String COUNTRY_ORIGIN = "countryOrigin";
    public static final String QUANTITY = "quantity";
    public static final String COMPOSITION = "composition";
    public static final String GENDER = "gender";
    public static final String HS_CODE = "hsCode";
    public static final String BRUTTO_WEIGHT = "bruttoWeight";
    public static final String PRICE = "price";
    private final Workbook workbook;
    private final Sheet sheet;
    private final ProductProcess productProcess;
    private final CellValueExtractor cellValueExtractor;
    private final HashMap<String, Integer> identifiedColumns;
    private final ExcelColumnIdentifier columnIdentifier;
    private final List<String> targetColumns;

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
        this.columnIdentifier = new ExcelColumnIdentifier();
        this.identifiedColumns = columnIdentifier.identifyColumns(workbook.getSheetAt(sheetIndex).getRow(0),targetColumns);
        this.productProcess = new ProductProcess();
        this.cellValueExtractor = new CellValueExtractor(identifiedColumns);
        this.targetColumns = targetColumns.keySet().stream().toList();
        logger.info("ExcelProcesserImpl initialized successfully.");
    }
    @Override
    public void addProductToSheet(ProductPosition product, Sheet sheet){
        logger.info("Adding product to sheet...");
        if (sheet.getLastRowNum() == -1) addHeaderRow(sheet.createRow(0));
        Row row = sheet.createRow(sheet.getLastRowNum()+1);
        row.createCell(targetColumns.indexOf(ARTICLE)).setCellValue(product.getArticle());
        row.createCell(targetColumns.indexOf(PRODUCT_NAME)).setCellValue(product.getProductName());
        row.createCell(targetColumns.indexOf(SIZES)).setCellValue(product.getSizes());
        row.createCell(targetColumns.indexOf(TRADE_MARK)).setCellValue(product.getTradeMark());
        row.createCell(targetColumns.indexOf(COUNTRY_ORIGIN)).setCellValue(product.getCountryOrigin());
        row.createCell(targetColumns.indexOf(QUANTITY)).setCellValue(product.getQuantity());
        row.createCell(targetColumns.indexOf(COMPOSITION)).setCellValue(product.getComposition());
        row.createCell(targetColumns.indexOf(GENDER)).setCellValue(product.getGender().getTitle());
        row.createCell(targetColumns.indexOf(HS_CODE)).setCellValue(product.getHsCode());
        row.createCell(targetColumns.indexOf(BRUTTO_WEIGHT)).setCellValue(product.getBruttoWeight());
        row.createCell(targetColumns.indexOf(PRICE)).setCellValue(product.getPrice());
    }

    private void addHeaderRow(Row row) {
        for (int i = 0; i < targetColumns.size(); i++) {
            row.createCell(i).setCellValue(targetColumns.get(i));
        }
    }

    /**
     * Adds the processed products to a new sheet in the workbook.
     *
     * @param products A HashMap containing the article numbers as keys and ProductPosition objects as values.
     */
    @Override
    public void addMapOfProductsToSheet(HashMap<String, ProductPosition> products, Sheet sheet) {
        logger.info("Adding products to sheet...");
        int rowIndex = 0;

        // Create header row
        Row headerRow = sheet.createRow(rowIndex);
        addHeaderRow(headerRow);
        rowIndex++; // Move to the next row for product data

        // Populate product data
        for (String key : products.keySet()) {
            logger.info("Adding product with article: {}, to row {}", key, rowIndex);
            Row row = sheet.createRow(rowIndex);
            row.createCell(targetColumns.indexOf(ARTICLE)).setCellValue(products.get(key).getArticle());
            row.createCell(targetColumns.indexOf(PRODUCT_NAME)).setCellValue(products.get(key).getProductName());
            row.createCell(targetColumns.indexOf(SIZES)).setCellValue(products.get(key).getSizes());
            row.createCell(targetColumns.indexOf(TRADE_MARK)).setCellValue(products.get(key).getTradeMark());
            row.createCell(targetColumns.indexOf(COUNTRY_ORIGIN)).setCellValue(products.get(key).getCountryOrigin());
            row.createCell(targetColumns.indexOf(QUANTITY)).setCellValue(products.get(key).getQuantity());
            row.createCell(targetColumns.indexOf(COMPOSITION)).setCellValue(products.get(key).getComposition());
            row.createCell(targetColumns.indexOf(GENDER)).setCellValue(products.get(key).getGender().getTitle());
            row.createCell(targetColumns.indexOf(HS_CODE)).setCellValue(products.get(key).getHsCode());
            row.createCell(targetColumns.indexOf(BRUTTO_WEIGHT)).setCellValue(products.get(key).getBruttoWeight());
            row.createCell(targetColumns.indexOf(PRICE)).setCellValue(products.get(key).getPrice());
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
                .setArticle(cellValueExtractor.getCellValue(row, ARTICLE))
                .setProductName(cellValueExtractor.getCellValue(row, PRODUCT_NAME).toLowerCase())
                .setSizes(cellValueExtractor.getCellValue(row, SIZES))
                .setTradeMark(cellValueExtractor.getCellValue(row, TRADE_MARK))
                .setCountryOrigin(cellValueExtractor.getCellValue(row, COUNTRY_ORIGIN))
                .setQuantity(cellValueExtractor.getIntegerCellValue(row, QUANTITY))
                .setComposition(cellValueExtractor.getCellValue(row, COMPOSITION))
                .setGender(Gender.fromString(cellValueExtractor.getCellValue(row, GENDER)))
                .setHsCode(cellValueExtractor.getCellValue(row, HS_CODE))
                .setBruttoWeight(cellValueExtractor.getIntegerCellValue(row, BRUTTO_WEIGHT))
                .setPrice(cellValueExtractor.getDoubleCellValue(row, PRICE))
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
