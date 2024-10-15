package org.lytiy.strategy;

import org.lytiy.product.Gender;
import org.lytiy.product.ProductPosition;
import org.lytiy.product.productprocess.commodity.ICommodityProcess;

import java.util.HashMap;
import java.util.Map;

public class ExcelProductCommoditySpecifyGender implements IExcelProcessingStrategy{
    private final ICommodityProcess commodityProcess;

    public ExcelProductCommoditySpecifyGender(ICommodityProcess commodityProcess) {
        this.commodityProcess = commodityProcess;
    }

    @Override
    public void execute(ExcelProcessingContext context) {
        HashMap<String, ProductPosition> productPositionHashMap = context.getProductPositionHashMap();
        for (Map.Entry<String, ProductPosition> stringProductPositionEntry : productPositionHashMap.entrySet()) {
            ProductPosition productPosition = stringProductPositionEntry.getValue();
            Gender gender = commodityProcess.getGenderByCommodity(productPosition.getHsCode());
            productPosition.setGender(gender);
        }
    }
}
