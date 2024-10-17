package org.lytiy.strategy;

import org.lytiy.cargo.product.ProductPosition;
import org.lytiy.cargo.product.productprocess.composition.IMaterialProcess;

import java.util.Map;

public class ExcelSortCompositionStrategy implements IExcelProcessingStrategy{
    private final IMaterialProcess materialProcess;

    public ExcelSortCompositionStrategy(IMaterialProcess materialProcess) {
        this.materialProcess = materialProcess;
    }

    @Override
    public void execute(ExcelProcessingContext context) {
        for (Map.Entry<String, ProductPosition> stringProductPositionEntry : context.getProductPositionHashMap().entrySet()) {
            ProductPosition productPosition = stringProductPositionEntry.getValue();
            String composition = productPosition.getComposition();
            productPosition.setComposition(materialProcess.generateSortedCompositionString(composition));
        }
    }
}
