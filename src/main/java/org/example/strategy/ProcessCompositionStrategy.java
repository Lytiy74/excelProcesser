package org.example.strategy;

import org.example.ExcelProcessor.ExcelProcess;
import org.example.Product.ProductPosition;
import org.example.Product.ProductProcess.Composition.MaterialProcess;
import org.example.Product.ProductProcess.Composition.MaterialProcessImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class ProcessCompositionStrategy implements ExcelProcessorStrategy{
    private final MaterialProcess materialProcess;
    private final ExcelProcessorContext context;

    public ProcessCompositionStrategy(ExcelProcessorContext context) throws IOException, URISyntaxException {
        this.context = context;
        this.materialProcess = new MaterialProcessImpl();
    }

    @Override
    public void execute(ExcelProcess process) {
        HashMap<String, ProductPosition> productPositionHashMap = context.getProductPositionHashMap();
        for (String article : productPositionHashMap.keySet()) {
            ProductPosition productPosition = productPositionHashMap.get(article);
            String comp = materialProcess.generateCompositionString(productPosition.getComposition());
            productPosition.setComposition(comp);
        }
    }
}
