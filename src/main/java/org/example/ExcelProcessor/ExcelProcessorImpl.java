package org.example.ExcelProcessor;

import org.apache.poi.ss.usermodel.*;
import org.example.Product.Gender;
import org.example.Product.ProductPosition;
import org.example.Product.ProductProcess.ProductProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import static org.example.ExcelProcessor.TargetColumns.*;

/**
 * This class processes an Excel workbook to retrieve
 * column indexes based on predefined categories.
 */
public class ExcelProcessorImpl implements ExcelProcess {
    private static final Logger logger = LoggerFactory.getLogger(ExcelProcessorImpl.class);
    private final Workbook workbook;
    private final Sheet sheet;
    private final ProductProcess productProcess;
    private final CellValueExtractor cellValueExtractor;
    private final HashMap<String, Integer> identifiedColumns;
    private final List<String> targetColumns;
    private final int headerRowIndex;

    /**
     * Constructor for ExcelProcess class.
     *
     * @param workbook The Apache POI Workbook object representing the Excel workbook.
     * @param sheetIndex The index of the sheet within the workbook to process.
     * @param targetColumns A HashMap containing the predefined categories and their corresponding column names.
     */
    public ExcelProcessorImpl(Workbook workbook, int sheetIndex, HashMap<String, List<String>> targetColumns) {
        logger.info("Initializing ExcelProcesserImpl...");
        this.workbook = workbook;
        this.sheet = workbook.getSheetAt(sheetIndex);
        ExcelColumnIdentifier columnIdentifier = new ExcelColumnIdentifier();
        this.headerRowIndex = columnIdentifier.findAndGetNumberOfHeaderRow(sheet,targetColumns);
        this.identifiedColumns = columnIdentifier.identifyColumns(workbook.getSheetAt(sheetIndex).getRow(headerRowIndex),targetColumns);
        this.productProcess = new ProductProcess();
        this.cellValueExtractor = new CellValueExtractor();
        this.targetColumns = targetColumns.keySet().stream().toList();
        logger.info("ExcelProcesserImpl initialized successfully.");
    }
    @Override
    public void addProductToSheet(ProductPosition product, Sheet sheet){
        logger.info("Adding product to sheet...");
        if (sheet.getLastRowNum() == -1) addHeaderRow(sheet.createRow(0));
        Row row = sheet.createRow(sheet.getLastRowNum()+1);
        addProductPositionToRow(product, row);
    }

    public void addProductPositionToRow(ProductPosition product, Row row) {
        row.createCell(targetColumns.indexOf(ARTICLE.getColumnName())).setCellValue(product.getArticle());
        row.createCell(targetColumns.indexOf(PRODUCT_NAME.getColumnName())).setCellValue(product.getProductName());
        row.createCell(targetColumns.indexOf(SIZES.getColumnName())).setCellValue(product.getSizes());
        row.createCell(targetColumns.indexOf(TRADE_MARK.getColumnName())).setCellValue(product.getTradeMark());
        row.createCell(targetColumns.indexOf(COUNTRY_ORIGIN.getColumnName())).setCellValue(product.getCountryOrigin());
        row.createCell(targetColumns.indexOf(QUANTITY.getColumnName())).setCellValue(product.getQuantity());
        row.createCell(targetColumns.indexOf(COMPOSITION.getColumnName())).setCellValue(product.getComposition());
        row.createCell(targetColumns.indexOf(GENDER.getColumnName())).setCellValue(product.getGender().getTitle());
        row.createCell(targetColumns.indexOf(HS_CODE.getColumnName())).setCellValue(product.getHsCode());
        row.createCell(targetColumns.indexOf(BRUTTO_WEIGHT.getColumnName())).setCellValue(product.getBruttoWeight());
        row.createCell(targetColumns.indexOf(PRICE.getColumnName())).setCellValue(product.getPrice());
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
            addProductPositionToRow(products.get(key), row);
            rowIndex++;
            logger.info("Product processed successfully.");
        }
        logger.info("Products added to sheet successfully.");
    }

    @Override
    public void addMapOfProductsToSheet(HashMap<String, ProductPosition> products) {
        if (workbook.getSheet("Processed") != null) {
            workbook.removeSheetAt(workbook.getSheetIndex("Processed"));
        }
        Sheet sheet = workbook.createSheet("Processed");
        addMapOfProductsToSheet(products, sheet);
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
        for (int rowIndex = headerRowIndex+1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            logger.info("Processing row {}...", rowIndex);
            Row row = sheet.getRow(rowIndex);
            ProductPosition product = buildProductPositionFromRow(row);
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
     * @param rowNumber The Apache POI Row number containing the data.
     * @return A ProductPosition object representing the data in the row.
     */
    public ProductPosition buildProductPositionFromRow(int rowNumber){
        logger.info("Collecting product from row {}...", rowNumber);
        return buildProductPositionFromRow(sheet.getRow(rowNumber));
    }

    /**
     * Builds a ProductPosition object from the data in a given row.
     *
     * @param row The Apache POI Row object containing the data.
     * @return A ProductPosition object representing the data in the row.
     */
    private ProductPosition buildProductPositionFromRow(Row row) {
        logger.info("Building ProductPosition object...");

        ProductPosition.Builder productBuilder = ProductPosition.newBuilder();

        setCellValue(row, productBuilder, ARTICLE.getColumnName(), productBuilder::setArticle);
        setCellValue(row, productBuilder, PRODUCT_NAME.getColumnName(), name -> productBuilder.setProductName(name.toLowerCase()));
        setCellValue(row, productBuilder, SIZES.getColumnName(), productBuilder::setSizes);
        setCellValue(row, productBuilder, TRADE_MARK.getColumnName(), productBuilder::setTradeMark);
        setCellValue(row, productBuilder, COUNTRY_ORIGIN.getColumnName(), productBuilder::setCountryOrigin);
        setIntegerCellValue(row, productBuilder, QUANTITY.getColumnName(), productBuilder::setQuantity);
        setCellValue(row, productBuilder, COMPOSITION.getColumnName(), productBuilder::setComposition);
        setCellValue(row, productBuilder, GENDER.getColumnName(), gender -> productBuilder.setGender(Gender.fromString(gender)));
        setCellValue(row, productBuilder, HS_CODE.getColumnName(), productBuilder::setHsCode);
        setIntegerCellValue(row, productBuilder, BRUTTO_WEIGHT.getColumnName(), productBuilder::setBruttoWeight);
        setDoubleCellValue(row, productBuilder, PRICE.getColumnName(), productBuilder::setPrice);

        ProductPosition product = productBuilder.build();
        logger.info("ProductPosition object built successfully.");
        return product;
    }

    private void setCellValue(Row row, ProductPosition.Builder productBuilder, String columnName, Consumer<String> setter) {
        if (identifiedColumns.containsKey(columnName)) {
            int columnIndex = identifiedColumns.get(columnName);
            String value = cellValueExtractor.getStringCellValue(row.getCell(columnIndex));
            setter.accept(value);
        } else {
            logger.warn("Column '{}' not found in identifiedColumns.", columnName);
            setter.accept("N/A"); // or any default value you prefer
        }
    }

    private void setIntegerCellValue(Row row, ProductPosition.Builder productBuilder, String columnName, Consumer<Integer> setter) {
        if (identifiedColumns.containsKey(columnName)) {
            int columnIndex = identifiedColumns.get(columnName);
            Integer value = cellValueExtractor.getIntegerCellValue(row.getCell(columnIndex));
            setter.accept(value);
        } else {
            logger.warn("Column '{}' not found in identifiedColumns.", columnName);
            setter.accept(0); // or any default value for Integer
        }
    }

    private void setDoubleCellValue(Row row, ProductPosition.Builder productBuilder, String columnName, Consumer<Double> setter) {
        if (identifiedColumns.containsKey(columnName)) {
            int columnIndex = identifiedColumns.get(columnName);
            Double value = cellValueExtractor.getDoubleCellValue(row.getCell(columnIndex));
            setter.accept(value);
        } else {
            logger.warn("Column '{}' not found in identifiedColumns.", columnName);
            setter.accept(0.0); // or any default value for Double
        }
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
