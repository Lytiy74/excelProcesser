package org.lytiy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.Workbook;
import org.lytiy.strategy.*;
import org.lytiy.util.io.CsvFileReader;
import org.lytiy.util.io.ExcelFileWriter;
import org.lytiy.util.io.JsonFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    protected static final int DEFAULT_SHEET_INDEX = 0;
    protected static final String DEFAULT_INPUT_FILE_PATH = "опис.xlsx";
    protected static final String DEFAULT_OUTPUT_FILE_PATH = "result.xlsx";

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
        Path jarDir = getJarDirectory();

        JsonFileReader jsonFileReader = new JsonFileReader();
        CsvFileReader csvFileReader = new CsvFileReader();

        ExcelProcessingContext context = ExcelContextInitializer.initialize(inputFilePath, jarDir, jsonFileReader, csvFileReader);
        long startedAt = System.currentTimeMillis();

        for (String arg : args) {
            Operation operation;
            try {
                operation = Operation.valueOf(arg.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.error("Invalid operation: " + arg);
                continue;
            }
            IExcelProcessingStrategy strategy = StrategyFactory.getStrategy(operation, context);
            if (strategy != null) strategy.execute(context);
        }

        writeOutput(context.getOutWorkbook());
        logger.info("Finished processing. Wasted time = {}s", (System.currentTimeMillis() - startedAt) / 1000);
    }

    private static Path getInputFilePath(String[] args) {
        return args.length > 0 && args[0] != null ? Path.of(args[0]) : Path.of(DEFAULT_INPUT_FILE_PATH);
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
            Path dataDir = jarDir.resolve("data");
            if (!Files.exists(dataDir)) {
                Files.createDirectories(dataDir);
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

