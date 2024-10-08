package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.example.product.ProductPosition;

public interface IExcelProductBuilder {
    ProductPosition buildProductPositionFromRom(Row row);
}
