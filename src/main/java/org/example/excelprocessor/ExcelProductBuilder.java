package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.example.product.ProductPosition;

import java.util.HashMap;

public class ExcelProductBuilder extends AbstractExcelProductBuilder implements IExcelProductBuilder{
    public ExcelProductBuilder(ICellValueExtractor cellValueExtractor, HashMap<String, Integer> identifiedColumns) {
        super(cellValueExtractor, identifiedColumns);
    }

    @Override
    public ProductPosition buildProductPositionFromRom(Row row) {
        return super.buildProductPositionFromRom(row);
    }
}
