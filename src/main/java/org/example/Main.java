package org.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ExcelProcessor.ExcelProcessorImpl;
import org.example.Product.ProductPosition;
import org.example.Product.ProductProcess.Composition.MaterialProcess;
import org.example.Product.ProductProcess.Composition.MaterialProcessImpl;
import org.example.Util.IO.ExcelFileWriter;
import org.example.Util.IO.JsonFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.ResourceFiles.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int SHEET_INDEX = 0;
    private static final String INPUT_FILE_PATH = "опис.xlsx";
    private static final String OUTPUT_FILE_PATH = "result.xlsx";

    public static void main(String[] args) {
        logger.info("Start processing");
        try {
            createResourceFiles();
            processExcelFiles();
        } catch (IOException | URISyntaxException e) {
            logger.error("An error occurred during processing", e);
        }
    }

    private static void processExcelFiles() throws IOException, URISyntaxException {
        Path jarDir = Paths.get(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent());
        Path inputFilePath = jarDir.resolve(INPUT_FILE_PATH);
        long startedAt = System.currentTimeMillis();

        JsonFileReader reader = new JsonFileReader();
        MaterialProcess materialProcess = new MaterialProcessImpl();
        HashMap<String, List<String>> stringStringHashMap = reader.readJsonObjectArrayToMap(jarDir.resolve(COLUMN_NAME_JSON_FILE.getFileName()).toString());

        try (Workbook workbook = new XSSFWorkbook(inputFilePath.toString())) {
            ExcelProcessorImpl excelProcess = new ExcelProcessorImpl(workbook, SHEET_INDEX, stringStringHashMap);
            HashMap<String, ProductPosition> productPositionHashMap = excelProcess.collectProducts();

            for (String article : productPositionHashMap.keySet()) {
                ProductPosition productPosition = productPositionHashMap.get(article);
                String comp = materialProcess.generateCompositionString(productPosition.getComposition());
                productPosition.setComposition(comp);
            }
            if (workbook.getSheet("Processed") != null) {
                workbook.removeSheetAt(workbook.getSheetIndex("Processed"));
            }
            Sheet sheet = workbook.createSheet("Processed");
            for (String article : productPositionHashMap.keySet()) {
                excelProcess.addProductToSheet(productPositionHashMap.get(article), sheet);
            }
            ExcelFileWriter writer = new ExcelFileWriter();
            writer.write(workbook, OUTPUT_FILE_PATH);
        }

        logger.info("Finished processing. Wasted time = {}s", (System.currentTimeMillis() - startedAt) / 1000);
    }

    private static void createResourceFiles() throws URISyntaxException {
        Path jarDir = Paths.get(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent());

        for (ResourceFiles resource : ResourceFiles.values()) {
            try (InputStream resourceStream = Main.class.getResourceAsStream("/" + resource.getFileName())) {
                if (resourceStream == null) {
                    logger.info("No resource found: " + resource.getFileName());
                    continue;
                }

                Path outputPath = jarDir.resolve(resource.getFileName());

                if (Files.exists(outputPath)) {
                    logger.info("File already exists: " + outputPath + ". Skipping copy.");
                    continue;
                }

                Files.copy(resourceStream, outputPath);
                logger.info("File added to: " + outputPath);
            } catch (IOException e) {
                logger.error("Failed to copy resource: " + resource.getFileName(), e);
            }
        }
    }
}

