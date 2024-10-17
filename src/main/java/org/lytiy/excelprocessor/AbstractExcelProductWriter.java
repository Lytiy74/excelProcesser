package org.lytiy.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.lytiy.cargo.product.ProductPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

import static org.lytiy.excelprocessor.TargetColumns.*;

public abstract class AbstractExcelProductWriter implements IExcelProductWriter {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExcelProductWriter.class);
    private final List<String> targetColumns;

    protected AbstractExcelProductWriter(List<String> targetColumns) {
        this.targetColumns = targetColumns;
    }

    @Override
    public void writeProductToSheet(ProductPosition productPosition, Sheet sheet) {
        logger.info("Adding product to sheet...");
        if (sheet.getLastRowNum() == -1) addHeaderRow(sheet.createRow(0));
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        writeProductToRow(productPosition, row);
    }

    private void addHeaderRow(Row row) {
        for (int i = 0; i < targetColumns.size(); i++) {
            row.createCell(i).setCellValue(targetColumns.get(i));
        }
    }


    @Override
    public void writeProductsToSheet(HashMap<String, ProductPosition> productPositionHashMap, Sheet sheet) {
        logger.info("Adding products to sheet...");
        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex);
        addHeaderRow(headerRow);
        rowIndex++;
        for (String key : productPositionHashMap.keySet()) {
            logger.info("Adding product with article: {}, to row {}", key, rowIndex);
            Row row = sheet.createRow(rowIndex);
            writeProductToRow(productPositionHashMap.get(key), row);
            rowIndex++;
            logger.info("Product processed successfully.");
        }
        logger.info("Products added to sheet successfully.");
    }


    @Override
    public void writeProductToRow(ProductPosition productPosition, Row row) {
        logger.info("Adding product {} to row {}", productPosition.getArticle(), row.getRowNum());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(ARTICLE.getColumnName())),productPosition.getArticle());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(PRODUCT_NAME.getColumnName())),productPosition.getProductName());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(SIZES.getColumnName())),productPosition.getSizes());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(TRADE_MARK.getColumnName())),productPosition.getTradeMark());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(COUNTRY_ORIGIN.getColumnName())),productPosition.getCountryOrigin());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(QUANTITY.getColumnName())),productPosition.getQuantity());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(COMPOSITION.getColumnName())),productPosition.getComposition());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(GENDER.getColumnName())),productPosition.getGender());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(HS_CODE.getColumnName())),productPosition.getHsCode());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(BRUTTO_WEIGHT.getColumnName())),productPosition.getBruttoWeight());
        ExcelCellWriteValidator.validateAndWrite(row.createCell(targetColumns.indexOf(PRICE.getColumnName())),productPosition.getPrice());
    }
}
