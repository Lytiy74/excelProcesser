package org.lytiy.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.lytiy.product.ProductPosition;

public interface IExcelProductBuilder {
    ProductPosition buildProductPositionFromRom(Row row);
}
