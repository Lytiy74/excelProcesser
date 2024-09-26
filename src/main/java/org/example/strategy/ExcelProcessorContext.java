package org.example.strategy;

import org.example.excelprocessor.ExcelProcess;
import org.example.product.ProductPosition;

import java.util.HashMap;

public class ExcelProcessorContext {
    private ExcelProcessorStrategy excelProcessorStrategy;
    private HashMap<String, ProductPosition> productPositionHashMap;
    private final ExcelProcess excelProcess;
    public ExcelProcessorContext(ExcelProcess excelProcess) {
        this.excelProcess = excelProcess;

    }
    public void process() {
        excelProcessorStrategy.execute(excelProcess);
    }

    public ExcelProcessorStrategy getExcelProcessorStrategy() {
        return excelProcessorStrategy;
    }

    public void setExcelProcessorStrategy(ExcelProcessorStrategy excelProcessorStrategy) {
        this.excelProcessorStrategy = excelProcessorStrategy;
    }

    public HashMap<String, ProductPosition> getProductPositionHashMap() {
        return productPositionHashMap;
    }

    public void setProductPositionHashMap(HashMap<String, ProductPosition> productPositionHashMap) {
        this.productPositionHashMap = productPositionHashMap;
    }

    public ExcelProcess getExcelProcess() {
        return excelProcess;
    }
}
