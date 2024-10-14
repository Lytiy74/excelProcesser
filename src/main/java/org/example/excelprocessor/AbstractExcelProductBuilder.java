package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.example.product.Gender;
import org.example.product.ProductCategorizer;
import org.example.product.ProductPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static org.example.excelprocessor.TargetColumns.*;


public abstract class AbstractExcelProductBuilder implements IExcelProductBuilder {
    private static final Logger logger = LoggerFactory.getLogger(AbstractExcelProductBuilder.class);
    private final ICellValueExtractor cellValueExtractor;
    private final HashMap<String,Integer> identifiedColumns;
    private final ProductCategorizer productCategorizer;

    protected AbstractExcelProductBuilder(ICellValueExtractor cellValueExtractor, HashMap<String, Integer> identifiedColumns, ProductCategorizer productCategorizer) {
        this.cellValueExtractor = cellValueExtractor;
        this.identifiedColumns = identifiedColumns;
        this.productCategorizer = productCategorizer;
    }


    @Override
    public ProductPosition buildProductPositionFromRom(Row row) {
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
        product.setProductType(productCategorizer.categorizeProduct(product.getProductName()));
        logger.info("ProductPosition object built successfully.");
        return product;
    }
}
