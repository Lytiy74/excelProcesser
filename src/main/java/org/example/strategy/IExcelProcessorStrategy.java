package org.example.strategy;

import org.example.excelprocessor.IExcelProcessor;

public interface IExcelProcessorStrategy {
    ExcelProcessorContext context = null;
    void execute(IExcelProcessor process);
}
