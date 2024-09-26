package org.example.util.io;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelFileWriter implements Writer<Workbook> {
    private static final Logger logger = LoggerFactory.getLogger(ExcelFileWriter.class);

    @Override
    public void write(Workbook workbook, String path) throws IOException {
        logger.debug("Writing workbook to '{}'", path);
        try (OutputStream fileOut = new FileOutputStream(path)) {
            workbook.write(fileOut);
            logger.debug("Successfully wrote workbook to '{}'", path);
        } catch (IOException e) {
            logger.error("Failed to write workbook to '{}'", path, e);
            throw e;
        }
    }
}