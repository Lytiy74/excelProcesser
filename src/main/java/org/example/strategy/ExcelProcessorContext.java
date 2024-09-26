package org.example.strategy;

import org.example.excelprocessor.IExcelProcessor;
import org.example.product.ProductPosition;

import java.util.HashMap;

public class ExcelProcessorContext {
    private IExcelProcessorStrategy excelProcessorStrategy;
    private HashMap<String, ProductPosition> productPositionHashMap;
    private final IExcelProcessor excelProcess;
    public ExcelProcessorContext(IExcelProcessor excelProcess) {
        this.excelProcess = excelProcess;

    }
    public void process() {
        excelProcessorStrategy.execute(excelProcess);
    }

    public IExcelProcessorStrategy getExcelProcessorStrategy() {
        return excelProcessorStrategy;
    }

    public void setExcelProcessorStrategy(IExcelProcessorStrategy excelProcessorStrategy) {
        this.excelProcessorStrategy = excelProcessorStrategy;
    }

    public HashMap<String, ProductPosition> getProductPositionHashMap() {
        return productPositionHashMap;
    }

    public void setProductPositionHashMap(HashMap<String, ProductPosition> productPositionHashMap) {
        this.productPositionHashMap = productPositionHashMap;
    }

    public IExcelProcessor getExcelProcess() {
        return excelProcess;
    }
}
