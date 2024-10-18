package org.lytiy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.poi.ss.usermodel.Workbook;
import org.lytiy.strategy.*;
import org.lytiy.util.io.CsvFileReader;
import org.lytiy.util.io.ExcelFileWriter;
import org.lytiy.util.io.JsonFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

@CommandLine.Command(name = "ExcelProcessor", mixinStandardHelpOptions = true, version = "1.0",
        description = "Processes Excel files with specified operations")
public class Main implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    protected static final int DEFAULT_SHEET_INDEX = 0;

    @CommandLine.Parameters(index = "0", description = "Input Excel file path")
    protected static String INPUT_FILE_PATH = "опис.xlsx";

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output Excel file path")
    protected static String OUTPUT_FILE_PATH = "result.xlsx";

    @CommandLine.Parameters(index = "1..*", description = "Operations to perform (e.g., COLLECT_PRODUCTS, PROCESS_PRODUCTS, SAVE_RESULTS)")
    private static List<Operation> operations;


    @Override
    public Integer call() {
        logger.info("Start processing");
        try {
            createResourceFiles();
            processExcelFiles(operations);
        } catch (IOException | URISyntaxException e) {
            logger.error("An error occurred during processing", e);
        }
        return 0;
    }
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    private static void processExcelFiles(List<Operation> args) throws IOException, URISyntaxException {
        Path inputFilePath = getInputFilePath();
        Path jarDir = getJarDirectory();

        JsonFileReader jsonFileReader = new JsonFileReader();
        CsvFileReader csvFileReader = new CsvFileReader();

        ExcelProcessingContext context = ExcelContextInitializer.initialize(inputFilePath, jarDir, jsonFileReader, csvFileReader);
        long startedAt = System.currentTimeMillis();

        for (Operation operation : args) {
            IExcelProcessingStrategy strategy = StrategyFactory.getStrategy(operation, context);
            if (strategy != null) strategy.execute(context);
        }

        writeOutput(context.getOutWorkbook());
        logger.info("Finished processing. Wasted time = {}s", (System.currentTimeMillis() - startedAt) / 1000);
    }

    private static Path getInputFilePath() {
        return Path.of(INPUT_FILE_PATH);
    }

    private static void writeOutput(Workbook workbook) throws IOException {
        ExcelFileWriter writer = new ExcelFileWriter();
        writer.write(workbook, OUTPUT_FILE_PATH);
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

