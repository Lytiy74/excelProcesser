package org.example;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ExcelProcesser.ExcelProcess;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws IOException {
        ExcelProcess excelProcess = new ExcelProcess(new XSSFWorkbook("src/main/resources/test3result.xlsx"),0);

    }
}
