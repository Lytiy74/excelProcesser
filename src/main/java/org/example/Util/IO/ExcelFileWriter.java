package org.example.Util.IO;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelFileWriter {
    public void write(Workbook workbook, String path) throws IOException {
        OutputStream fileOut = new FileOutputStream(path);
        workbook.write(fileOut);
    }
}
