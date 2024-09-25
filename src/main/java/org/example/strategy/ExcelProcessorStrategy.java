package org.example.strategy;

import org.example.ExcelProcessor.ExcelProcess;

public interface ExcelProcessorStrategy {
    ExcelProcessorContext context = null;
    void execute(ExcelProcess process);
}
