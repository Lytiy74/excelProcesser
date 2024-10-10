package org.example.strategy;

import org.example.product.ProductPosition;
import org.example.product.productprocess.composition.IMaterialProcess;

import java.util.HashMap;
import java.util.Map;

public class ExcelProductProcessStrategy implements IExcelProcessingStrategy{
    private IMaterialProcess IMaterialProcess;

    public ExcelProductProcessStrategy(IMaterialProcess IMaterialProcess) {
        this.IMaterialProcess = IMaterialProcess;
    }

    @Override
    public void execute(ExcelProcessingContext context) {
        HashMap<String, ProductPosition> productPositionHashMap = context.getProductPositionHashMap();
        for (Map.Entry<String, ProductPosition> stringProductPositionEntry : productPositionHashMap.entrySet()) {
            ProductPosition value = stringProductPositionEntry.getValue();
            value.setComposition(IMaterialProcess.generateCompositionString(value.getComposition()));
        }
    }
}
