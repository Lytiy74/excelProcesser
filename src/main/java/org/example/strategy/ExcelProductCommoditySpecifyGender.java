package org.example.strategy;

import org.example.product.Gender;
import org.example.product.ProductPosition;
import org.example.product.productprocess.ICommodityProcess;

import java.util.HashMap;
import java.util.Map;

public class ExcelProductCommoditySpecifyGender implements IExcelProcessingStrategy{
    private ICommodityProcess commodityProcess;

    public ExcelProductCommoditySpecifyGender(ICommodityProcess commodityProcess) {
        this.commodityProcess = commodityProcess;
    }

    @Override
    public void execute(ExcelProcessingContext context) {
        HashMap<String, ProductPosition> productPositionHashMap = context.getProductPositionHashMap();
        for (Map.Entry<String, ProductPosition> stringProductPositionEntry : productPositionHashMap.entrySet()) {
            ProductPosition productPosition = stringProductPositionEntry.getValue();
            String commodity = commodityProcess.getCommoditiesDescription(productPosition.getHsCode());
            Gender gender = commodityProcess.getGenderByCommodity(commodity);
            productPosition.setGender(gender);
        }
    }
}
