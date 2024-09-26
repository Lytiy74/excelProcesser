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

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.excelprocessor.ExcelProcess;
import org.example.excelprocessor.ExcelProcessorImpl;
import org.example.util.io.ExcelFileWriter;
import org.example.util.io.JsonFileReader;
import org.example.strategy.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.ResourceFiles.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int SHEET_INDEX = 0;
    private static final String INPUT_FILE_PATH = "опис.xlsx";
    private static final String OUTPUT_FILE_PATH = "result.xlsx";

    /**
     *
     * @param args [0] - File name, [1] - Sheet index or name, [2] - Operations to do
     */
    public static void main(String[] args) {
        logger.info("Start processing");
        try {
            createResourceFiles();
            processExcelFiles(args);
        } catch (IOException | URISyntaxException e) {
            logger.error("An error occurred during processing", e);
        }
    }

    private static void processExcelFiles(String[] args) throws IOException, URISyntaxException {
        Path jarDir = Paths.get(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent());
        Path inputFilePath = Path.of(args.length > 0 && args[0] != null ? args[0] : INPUT_FILE_PATH);
        long startedAt = System.currentTimeMillis();

        JsonFileReader reader = new JsonFileReader();
        HashMap<String, List<String>> stringStringHashMap = reader.readJsonObjectArrayToMap(jarDir.resolve(COLUMN_NAME_JSON_FILE.getFileName()).toString());

        try (Workbook workbook = new XSSFWorkbook(inputFilePath.toString())) {
            ExcelProcess excelProcess = new ExcelProcessorImpl(workbook, SHEET_INDEX, stringStringHashMap);
            ExcelProcessorContext context = new ExcelProcessorContext(excelProcess);
            ExcelProcessorStrategy strategy;

            for (String arg : args){
                switch(arg) {
                    case "collectProducts" -> strategy = new CollectProductStrategy(context);
                    case "processComposition" -> strategy = new ProcessCompositionStrategy(context);
                    case "write" -> strategy = new WriteToSheetProductPositionsStrategy(context);
                    default -> {
                        continue;
                    }
                }
                strategy.execute(excelProcess);
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

