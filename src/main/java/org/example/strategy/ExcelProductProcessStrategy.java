package org.example.strategy;

import org.example.product.ProductPosition;
import org.example.product.productprocess.composition.MaterialProcess;

import java.util.HashMap;
import java.util.Map;

public class ExcelProductProcessStrategy implements IExcelProcessingStrategy{
    private MaterialProcess materialProcess;

    public ExcelProductProcessStrategy(MaterialProcess materialProcess) {
        this.materialProcess = materialProcess;
    }

    @Override
    public void execute(ExcelProcessingContext context) {
        HashMap<String, ProductPosition> productPositionHashMap = context.getProductPositionHashMap();
        for (Map.Entry<String, ProductPosition> stringProductPositionEntry : productPositionHashMap.entrySet()) {
            ProductPosition value = stringProductPositionEntry.getValue();
            value.setComposition(materialProcess.generateCompositionString(value.getComposition()));
        }
    }
}
