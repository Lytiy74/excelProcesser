package org.example.strategy;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.excelprocessor.IExcelProductBuilder;
import org.example.excelprocessor.IExcelProductReader;
import org.example.excelprocessor.IExcelProductWriter;
import org.example.product.ProductPosition;
import org.example.product.productprocess.composition.IMaterialProcess;

import java.util.HashMap;
import java.util.List;

public class ExcelProcessingContext {
    private final Workbook workbook;
    private final Workbook outWorkbook;
    private final Sheet sheet;
    private final HashMap<String, List<String>> targetColumns;
    private final HashMap<String, Integer> identifiedColumns;
    private final IExcelProductReader productReader;
    private final IExcelProductBuilder productBuilder;
    private final IExcelProductWriter productWriter;
    private final IMaterialProcess IMaterialProcess;
    private HashMap<String, ProductPosition> productPositionHashMap;

    private ExcelProcessingContext(Builder builder) {
        this.workbook = builder.workbook;
        this.outWorkbook = builder.outWorkbook;
        this.sheet = builder.sheet;
        this.targetColumns = builder.targetColumns;
        this.identifiedColumns = builder.identifiedColumns;
        this.productReader = builder.productReader;
        this.productBuilder = builder.productBuilder;
        this.productWriter = builder.productWriter;
        this.IMaterialProcess = builder.IMaterialProcess;
        this.productPositionHashMap = builder.productPositionHashMap;
    }

    public static class Builder {
        private Workbook workbook;
        private Workbook outWorkbook;
        private Sheet sheet;
        private HashMap<String, List<String>> targetColumns;
        private HashMap<String, Integer> identifiedColumns;
        private IExcelProductReader productReader;
        private IExcelProductBuilder productBuilder;
        private IExcelProductWriter productWriter;
        private IMaterialProcess IMaterialProcess;
        private HashMap<String, ProductPosition> productPositionHashMap;

        public Builder workbook(Workbook workbook) {
            this.workbook = workbook;
            return this;
        }
        public Builder outWorkbook(Workbook outWorkbook) {
            this.outWorkbook = outWorkbook;
            return this;
        }

        public Builder sheet(Sheet sheet) {
            this.sheet = sheet;
            return this;
        }

        public Builder targetColumns(HashMap<String, List<String>> targetColumns) {
            this.targetColumns = targetColumns;
            return this;
        }

        public Builder identifiedColumns(HashMap<String, Integer> identifiedColumns) {
            this.identifiedColumns = identifiedColumns;
            return this;
        }

        public Builder productReader(IExcelProductReader productReader) {
            this.productReader = productReader;
            return this;
        }

        public Builder productBuilder(IExcelProductBuilder productBuilder) {
            this.productBuilder = productBuilder;
            return this;
        }

        public Builder productWriter(IExcelProductWriter productWriter) {
            this.productWriter = productWriter;
            return this;
        }

        public Builder materialProcess(IMaterialProcess IMaterialProcess){
            this.IMaterialProcess = IMaterialProcess;
            return this;
        }

        public Builder productPositionMap(HashMap<String, ProductPosition> productPositionHashMap){
            this.productPositionHashMap = productPositionHashMap;
            return this;
        }

        public ExcelProcessingContext build() {
            return new ExcelProcessingContext(this);
        }
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public Workbook getOutWorkbook() {
        return outWorkbook;
    }

    public Sheet getSheet() {
        return sheet;
    }

    public HashMap<String, List<String>> getTargetColumns() {
        return targetColumns;
    }

    public HashMap<String, Integer> getIdentifiedColumns() {
        return identifiedColumns;
    }

    public IExcelProductReader getProductReader() {
        return productReader;
    }

    public IExcelProductBuilder getProductBuilder() {
        return productBuilder;
    }

    public IExcelProductWriter getProductWriter() {
        return productWriter;
    }

    public IMaterialProcess getMaterialProcess() {
        return IMaterialProcess;
    }

    public HashMap<String, ProductPosition> getProductPositionHashMap() {
        return productPositionHashMap;
    }

    public void setProductPositionHashMap(HashMap<String, ProductPosition> productPositionHashMap) {
        this.productPositionHashMap = productPositionHashMap;
    }
}

