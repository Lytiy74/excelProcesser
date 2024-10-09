package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.product.Gender;
import org.example.product.ProductPosition;
import org.example.product.productprocess.ProductProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

import static org.example.excelprocessor.TargetColumns.*;

/**
 * ExcelProcessorImpl is a concrete implementation of the ExcelProcessor interface.
 * This class provides functionalities to process Excel sheets containing product information.
 * It supports adding individual products or a collection of products to specific sheets,
 * collecting product data from sheets, and retrieving workbook and sheet details.
 */
@Deprecated(since = "08.10.2024")
public class ExcelProcessorImpl implements IExcelProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ExcelProcessorImpl.class);
    private final Workbook workbook;
    private final Sheet sheet;
    private final ProductProcess productProcess;
    private final ICellValueExtractor cellValueExtractor;
    private final HashMap<String, Integer> identifiedColumns;
    private final List<String> targetColumns;
    private final int headerRowIndex;

    public ExcelProcessorImpl(Workbook workbook, int sheetIndex, HashMap<String, List<String>> targetColumns) {
        logger.info("Initializing ExcelProcessorImpl...");
        this.workbook = workbook;
        this.sheet = workbook.getSheetAt(sheetIndex);
        ExcelColumnIdentifierImpl columnIdentifier = new ExcelColumnIdentifierImpl();
        this.headerRowIndex = columnIdentifier.findAndGetNumberOfHeaderRow(sheet, targetColumns);
        this.identifiedColumns = columnIdentifier.identifyColumns(sheet.getRow(headerRowIndex), targetColumns);
        this.productProcess = new ProductProcess();
        this.cellValueExtractor = new ExcelCellValueExtractorImpl();
        this.targetColumns = targetColumns.keySet().stream().toList();
        logger.info("ExcelProcessorImpl initialized successfully.");
    }
    @Deprecated(since = "08.10.2024")
    @Override
    public void addProductToSheet(ProductPosition product, Sheet sheet) {
        logger.info("Adding product to sheet...");
        if (sheet.getLastRowNum() == -1) addHeaderRow(sheet.createRow(0));
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        addProductPositionToRow(product, row);
    }
    @Deprecated(since = "08.10.2024")
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
    @Deprecated(since = "08.10.2024")
    @Override
    public void addMapOfProductsToSheet(HashMap<String, ProductPosition> products, Sheet sheet) {
        logger.info("Adding products to sheet...");
        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex);
        addHeaderRow(headerRow);
        rowIndex++;
        for (String key : products.keySet()) {
            logger.info("Adding product with article: {}, to row {}", key, rowIndex);
            Row row = sheet.createRow(rowIndex);
            addProductPositionToRow(products.get(key), row);
            rowIndex++;
            logger.info("Product processed successfully.");
        }
        logger.info("Products added to sheet successfully.");
    }
    @Deprecated(since = "08.10.2024")
    @Override
    public void addMapOfProductsToSheet(HashMap<String, ProductPosition> products) {
        if (workbook.getSheet("Processed") != null) {
            workbook.removeSheetAt(workbook.getSheetIndex("Processed"));
        }
        Sheet sheet = workbook.createSheet("Processed");
        addMapOfProductsToSheet(products, sheet);
    }

    @Deprecated(since = "08.10.2024")
    @Override
    public HashMap<String, ProductPosition> collectProducts() {
        logger.info("Collecting products...");
        HashMap<String, ProductPosition> products = new HashMap<>();
        for (int rowIndex = headerRowIndex + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            logger.info("Processing row {}...", rowIndex);
            Row row = sheet.getRow(rowIndex);
            ProductPosition product = buildProductPositionFromRow(row);
            if (!products.containsKey(product.getArticle())) {
                logger.info("Adding product with article number: {}", product.getArticle());
                products.put(product.getArticle(), product);
            } else {
                logger.info("Found duplicate of article {}, attempting to merge", product.getArticle());
                ProductPosition existingProduct = products.get(product.getArticle());
                ProductPosition mergedProduct = productProcess.mergeDuplications(existingProduct, product);
                products.put(mergedProduct.getArticle(), mergedProduct);
            }
        }
        logger.info("Products collected successfully.");
        return products;
    }
    @Deprecated(since = "08.10.2024")
    private ProductPosition buildProductPositionFromRow(Row row) {
        logger.info("Building ProductPosition object...");
        ICellValueSetter cellValueSetter = new ExcelCellValueSetterImpl(row, cellValueExtractor, identifiedColumns);
        ProductPosition.Builder productBuilder = ProductPosition.newBuilder();

        cellValueSetter.setCellValue(ARTICLE.getColumnName(), productBuilder::setArticle);
        cellValueSetter.setCellValue(PRODUCT_NAME.getColumnName(), name -> productBuilder.setProductName(name.toLowerCase()));
        cellValueSetter.setCellValue(SIZES.getColumnName(), productBuilder::setSizes);
        cellValueSetter.setCellValue(TRADE_MARK.getColumnName(), productBuilder::setTradeMark);
        cellValueSetter.setCellValue(COUNTRY_ORIGIN.getColumnName(), productBuilder::setCountryOrigin);
        cellValueSetter.setIntegerCellValue(QUANTITY.getColumnName(), productBuilder::setQuantity);
        cellValueSetter.setCellValue(COMPOSITION.getColumnName(), productBuilder::setComposition);
        cellValueSetter.setCellValue(GENDER.getColumnName(), gender -> productBuilder.setGender(Gender.fromString(gender)));
        cellValueSetter.setCellValue(HS_CODE.getColumnName(), productBuilder::setHsCode);
        cellValueSetter.setIntegerCellValue(BRUTTO_WEIGHT.getColumnName(), productBuilder::setBruttoWeight);
        cellValueSetter.setDoubleCellValue(PRICE.getColumnName(), productBuilder::setPrice);

        ProductPosition product = productBuilder.build();
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
