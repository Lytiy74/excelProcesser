package org.lytiy.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.lytiy.cargo.product.productprocess.category.ProductCategorizer;
import org.lytiy.cargo.product.ProductPosition;

import java.util.HashMap;

public class ExcelProductBuilder extends AbstractExcelProductBuilder implements IExcelProductBuilder{
    public ExcelProductBuilder(ICellValueExtractor cellValueExtractor, HashMap<String, Integer> identifiedColumns, ProductCategorizer productCategorizer) {
        super(cellValueExtractor, identifiedColumns, productCategorizer);
    }

    @Override
    public ProductPosition buildProductPositionFromRom(Row row) {
        return super.buildProductPositionFromRom(row);
    }
}
