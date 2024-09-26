package org.example.util.io;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelFileWriter {
    private static final Logger logger = LoggerFactory.getLogger(ExcelFileWriter.class);
    public void write(Workbook workbook, String path) throws IOException {
        logger.debug("Write workbook to '{}'", path);
        OutputStream fileOut = new FileOutputStream(path);
        workbook.write(fileOut);
    }
}
