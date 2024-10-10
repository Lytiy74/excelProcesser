package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.example.product.ProductPosition;
import org.example.product.productprocess.ProductProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public abstract class AbstractExcelProductReader implements IExcelProductReader {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExcelProductReader.class);
    private final IExcelProductBuilder productBuilder;
    private final int headerRowIndex;

    protected AbstractExcelProductReader(IExcelProductBuilder productBuilder, int headerRowIndex) {
        this.productBuilder = productBuilder;
        this.headerRowIndex = headerRowIndex;
    }


    @Override
    public HashMap<String, ProductPosition> getProductsMapFromSheet(Sheet sheet) {
        logger.info("Collecting Products");
        HashMap<String, ProductPosition> products = new HashMap<>();
        for (int rowIndex = headerRowIndex + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            //todo Reduce quantity of logs in loop
            logger.info("Processing row {}...", rowIndex);
            Row row = sheet.getRow(rowIndex);
            ProductPosition product = buildProductFromRow(row);
            if (!products.containsKey(product.getArticle())) {
                logger.info("Adding product with article number: {}", product.getArticle());
                products.put(product.getArticle(), product);
            } else {
                logger.info("Found duplicate of article {}, attempting to merge", product.getArticle());
                ProductPosition existingProduct = products.get(product.getArticle());
                products.put(existingProduct.getArticle(), ProductProcess.mergeDuplications(product, existingProduct));
            }
        }
        logger.info("Products collected successfully.");
        return products;
    }

    @Override
    public ProductPosition buildProductFromRow(Row row) {
        logger.info("Building ProductPosition object...");
        ProductPosition product = productBuilder.buildProductPositionFromRom(row);
        logger.info("ProductPosition object built successfully.");
        return product;
    }



}
