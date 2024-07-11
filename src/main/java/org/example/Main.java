package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.ExcelProcesser.ExcelProcess;
import org.example.ExcelProcesser.ExcelProcesserImpl;
import org.example.Product.ProductPosition;
import org.example.Product.ProductProcess.Composition.MaterialProcess;
import org.example.Product.ProductProcess.Composition.MaterialProcessImpl;
import org.example.Util.IO.ExcelFileWriter;
import org.example.Util.IO.JsonFileReader;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws IOException {
        JsonFileReader reader = new JsonFileReader();
        MaterialProcess materialProcess = new MaterialProcessImpl();
        HashMap<String, List<String>> stringStringHashMap = reader.readJsonObjectArrayToMap("src/main/resources/columnNames.json");
        Workbook workbook = new XSSFWorkbook("src/main/resources/опис.xlsx");
        ExcelProcess excelProcess = new ExcelProcesserImpl(workbook, 0, stringStringHashMap);
        HashMap<String, ProductPosition> productPositionHashMap = excelProcess.collectProducts();
        for (String article : productPositionHashMap.keySet()) {
            ProductPosition productPosition = productPositionHashMap.get(article);
            String comp = materialProcess.generateCompositionString(productPosition.getComposition());
            productPosition.setComposition(comp);
        }
        excelProcess.addProductsToSheet(productPositionHashMap, stringStringHashMap.keySet().stream().toList());
        ExcelFileWriter writer = new ExcelFileWriter();
        writer.write(workbook, "src/main/resources/result.xlsx");

    }
}
