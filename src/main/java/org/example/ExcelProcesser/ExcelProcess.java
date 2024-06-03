package org.example.ExcelProcesser;

import org.apache.poi.ss.usermodel.*;
import org.example.Composition.MaterialProcess;
import org.example.Product.Gender;
import org.example.Product.ProductPosition;
import org.example.ProductProcess.ProductProcess;

import java.util.HashMap;

/**
 * This class processes an Excel workbook to retrieve column indexes based on predefined categories.
 */
public class ExcelProcess {
    private final Workbook workbook;
    private final Sheet sheet;
    private final HashMap<String, Integer> identifiedColumns;
    private final ProductProcess productProcess;
    private final CellValueExtractor cellValueExtractor;

    public ExcelProcess(Workbook workbook, int sheetIndex) {
        this.workbook = workbook;
        this.sheet = workbook.getSheetAt(sheetIndex);
        ExcelColumnIdentifier columnIdentifier = new ExcelColumnIdentifier();
        this.identifiedColumns = columnIdentifier.identifyColumns(workbook.getSheetAt(sheetIndex));
        this.productProcess = new ProductProcess();
        this.cellValueExtractor = new CellValueExtractor(identifiedColumns);
    }
    public Workbook processExcel(Workbook workbook) {
      Sheet sheet1 = workbook.createSheet("Processed");
        return workbook;
    }

    public HashMap<String, ProductPosition> collectProducts() {
        HashMap<String, ProductPosition> products = new HashMap<>();
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            ProductPosition product = buildProductPosition(row);
            if(!products.containsKey(product.getArticle())) {
                products.put(product.getArticle(), product);
            }else{
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
                .setComposition(cellValueExtractor.getCellValue(row,"composition"))
                .setGender(Gender.fromString(cellValueExtractor.getCellValue(row, "gender")))
                .setHsCode(cellValueExtractor.getCellValue(row,"hsCode"))
                .setBruttoWeight(cellValueExtractor.getIntegerCellValue(row,"bruttoWeight"))
                .setPrice(cellValueExtractor.getDoubleCellValue(row, "price"))
                .build();
    }


}
