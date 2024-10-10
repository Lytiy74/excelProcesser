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
import org.example.excelprocessor.*;
import org.example.product.productprocess.composition.IMaterialProcess;
import org.example.product.productprocess.composition.MaterialProcessImpl;
import org.example.strategy.*;
import org.example.util.io.ExcelFileWriter;
import org.example.util.io.JsonFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.example.ResourceFiles.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final int DEFAULT_SHEET_INDEX = 0;
    private static final String DEFAULT_INPUT_FILE_PATH = "опис.xlsx";
    private static final String DEFAULT_OUTPUT_FILE_PATH = "result.xlsx";

    /**
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
        Path inputFilePath = getInputFilePath(args);
        long startedAt = System.currentTimeMillis();

        HashMap<String, List<String>> targetColumnsMap = loadJsonMapping();

        try (Workbook workbook = new XSSFWorkbook(inputFilePath.toString())) {
            IExcelColumnIdentifier columnIdentifier = new ExcelColumnIdentifierImpl();
            Sheet sheet = workbook.getSheetAt(DEFAULT_SHEET_INDEX);
            int headRowIndex = columnIdentifier.findAndGetNumberOfHeaderRow(sheet, targetColumnsMap);
            HashMap<String, Integer> identifiedColumns = columnIdentifier.identifyColumns(sheet.getRow(headRowIndex), targetColumnsMap);
            IExcelProductBuilder productBuilder = new ExcelProductBuilder(new ExcelCellValueExtractorImpl(), identifiedColumns);
            IExcelProductReader productReader = new ExcelProductReader(productBuilder, headRowIndex);
            IExcelProductWriter productWriter = new ExcelProductWriter(identifiedColumns.keySet().stream().toList());
            IMaterialProcess IMaterialProcess = new MaterialProcessImpl();
            ExcelProcessingContext context = new ExcelProcessingContext.Builder()
                    .workbook(workbook)
                    .sheet(sheet)
                    .targetColumns(targetColumnsMap)
                    .identifiedColumns(identifiedColumns)
                    .productBuilder(productBuilder)
                    .productReader(productReader)
                    .productWriter(productWriter)
                    .materialProcess(IMaterialProcess)
                    .build();
            for (String arg : args) {
                try {
                    // Перетворюємо аргумент у відповідну операцію з enum
                    Operation operation = Operation.valueOf(arg.toUpperCase());
                    IExcelProcessingStrategy strategy = getStrategy(operation, context);
                    // Виконання стратегії
                    if (strategy == null) continue;
                    strategy.execute(context);  // Передаємо контекст, якщо потрібно
                } catch (IllegalArgumentException e) {
                    logger.error("Invalid operation: " + arg);
                }
            }
            // Write output file
            writeOutput(workbook);
        }

        logger.info("Finished processing. Wasted time = {}s", (System.currentTimeMillis() - startedAt) / 1000);
    }

    private static IExcelProcessingStrategy getStrategy(Operation arg, ExcelProcessingContext context) {
        switch (arg) {
            case COLLECT_PRODUCTS -> new ExcelProductReadingStrategy(context.getProductReader());
            case PROCESS_PRODUCTS -> new ExcelProductProcessStrategy(context.getMaterialProcess());
            case SAVE_RESULTS -> new ExcelProductWriteStrategy(context.getProductWriter());
        }
        return null;
    }


    private static Path getInputFilePath(String[] args) {
        return args.length > 0 && args[0] != null ? Path.of(args[0]) : Path.of(DEFAULT_INPUT_FILE_PATH);
    }

    private static HashMap<String, List<String>> loadJsonMapping() throws URISyntaxException, IOException {
        Path jarDir = getJarDirectory();
        JsonFileReader reader = new JsonFileReader();
        return reader.readJsonObjectArrayToMap(jarDir.resolve(COLUMN_NAME_JSON_FILE.getFileName()).toString());
    }

    private static void writeOutput(Workbook workbook) throws IOException {
        ExcelFileWriter writer = new ExcelFileWriter();
        writer.write(workbook, DEFAULT_OUTPUT_FILE_PATH);
    }

    private static Path getJarDirectory() throws URISyntaxException {
        return Paths.get(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent());
    }

    private static void createResourceFiles() throws URISyntaxException {
        Path jarDir = getJarDirectory();
        for (ResourceFiles resource : ResourceFiles.values()) {
            copyResourceIfNotExists(jarDir, resource);
        }
    }

    private static void copyResourceIfNotExists(Path jarDir, ResourceFiles resource) {
        try (InputStream resourceStream = Main.class.getResourceAsStream("/" + resource.getFileName())) {
            if (resourceStream == null) {
                logger.info("No resource found: " + resource.getFileName());
                return;
            }

            Path outputPath = jarDir.resolve(resource.getFileName());
            if (Files.exists(outputPath)) {
                logger.info("File already exists: " + outputPath + ". Skipping copy.");
                return;
            }

            Files.copy(resourceStream, outputPath);
            logger.info("File added to: " + outputPath);
        } catch (IOException e) {
            logger.error("Failed to copy resource: " + resource.getFileName(), e);
        }
    }
}
