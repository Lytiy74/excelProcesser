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
import org.example.excelprocessor.IExcelProcessor;
import org.example.excelprocessor.ExcelProcessorImpl;
import org.example.util.io.ExcelFileWriter;
import org.example.util.io.JsonFileReader;
import org.example.strategy.*;
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

        HashMap<String, List<String>> stringStringHashMap = loadJsonMapping();

        try (Workbook workbook = new XSSFWorkbook(inputFilePath.toString())) {
            IExcelProcessor excelProcessor = setupExcelProcessor(workbook, stringStringHashMap);
            executeStrategies(args, excelProcessor);
            writeOutput(workbook);
        }

        logger.info("Finished processing. Wasted time = {}s", (System.currentTimeMillis() - startedAt) / 1000);
    }

    private static void executeStrategies(String[] args, IExcelProcessor excelProcessor) throws IOException, URISyntaxException {
        ExcelProcessorContext context = new ExcelProcessorContext(excelProcessor);

        for (String arg : args) {
            IExcelProcessorStrategy strategy = getStrategy(arg, context);
            if (strategy != null) {
                strategy.execute(excelProcessor);
            }
        }
    }

    private static IExcelProcessorStrategy getStrategy(String arg, ExcelProcessorContext context) throws IOException, URISyntaxException {
        return switch (arg) {
            case "collectProducts" -> new CollectProductStrategy(context);
            case "processComposition" -> new ProcessCompositionStrategy(context);
            case "write" -> new WriteToSheetProductPositionsStrategy(context);
            default -> null;
        };
    }

    private static Path getInputFilePath(String[] args) {
        return args.length > 0 && args[0] != null ? Path.of(args[0]) : Path.of(DEFAULT_INPUT_FILE_PATH);
    }

    private static HashMap<String, List<String>> loadJsonMapping() throws URISyntaxException, IOException {
        Path jarDir = getJarDirectory();
        JsonFileReader reader = new JsonFileReader();
        return reader.readJsonObjectArrayToMap(jarDir.resolve(COLUMN_NAME_JSON_FILE.getFileName()).toString());
    }

    private static IExcelProcessor setupExcelProcessor(Workbook workbook, HashMap<String, List<String>> stringStringHashMap) {
        return new ExcelProcessorImpl(workbook, DEFAULT_SHEET_INDEX, stringStringHashMap);
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