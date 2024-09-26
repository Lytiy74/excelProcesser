package org.example.strategy;

import org.example.excelprocessor.ExcelProcess;

public interface ExcelProcessorStrategy {
    ExcelProcessorContext context = null;
    void execute(ExcelProcess process);
}
