package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ExcelProcesser.ExcelProcess;
import org.example.ExcelProcesser.ExcelProcesserImpl;
import org.example.Product.ProductPosition;
import org.example.Product.ProductProcess.Composition.MaterialProcess;
import org.example.Product.ProductProcess.Composition.MaterialProcessImpl;
import org.example.Util.IO.ExcelFileWriter;
import org.example.Util.IO.JsonFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int SHEET_INDEX = 0;
    private static final String INPUT_FILE_PATH = "src/main/resources/опис.xlsx";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/result.xlsx";
    private static final String COLUMN_NAME_JSON_FILE_PATH = "src/main/resources/columnNames.json";

    public static void main(String[] args) throws IOException {
        logger.info("Start processing");
        long startedAt = System.currentTimeMillis();
        JsonFileReader reader = new JsonFileReader();
        MaterialProcess materialProcess = new MaterialProcessImpl();
        HashMap<String, List<String>> stringStringHashMap = reader.readJsonObjectArrayToMap(COLUMN_NAME_JSON_FILE_PATH);
        Workbook workbook = new XSSFWorkbook(INPUT_FILE_PATH);
        ExcelProcess excelProcess = new ExcelProcesserImpl(workbook, SHEET_INDEX, stringStringHashMap);
        HashMap<String, ProductPosition> productPositionHashMap = excelProcess.collectProducts();
        for (String article : productPositionHashMap.keySet()) {
            ProductPosition productPosition = productPositionHashMap.get(article);
            String comp = materialProcess.generateCompositionString(productPosition.getComposition());
            productPosition.setComposition(comp);
        }
        Sheet sheet = workbook.createSheet("Processed");
        for (String article : productPositionHashMap.keySet()){
            excelProcess.addProductToSheet(productPositionHashMap.get(article), sheet);
        }
        ExcelFileWriter writer = new ExcelFileWriter();
        writer.write(workbook, OUTPUT_FILE_PATH);
        logger.info("Finished processing. Wasted time = {}s", (System.currentTimeMillis() - startedAt)/1000);
    }
}
