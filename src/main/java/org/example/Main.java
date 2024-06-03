package org.example;


import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Composition.MaterialProcess;
import org.example.ExcelProcesser.ExcelProcess;
import org.example.Product.ProductPosition;

import java.io.IOException;
import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) throws IOException {
        ExcelProcess excelProcess = new ExcelProcess(new XSSFWorkbook("src/main/resources/test2.xlsx"),0);
        HashMap<String, ProductPosition> productPositionHashMap = excelProcess.collectProducts();
        MaterialProcess materialProcess = new MaterialProcess();
        for (String key : productPositionHashMap.keySet()) {
            System.out.println(productPositionHashMap.get(key));
        }
        for (String key : productPositionHashMap.keySet()) {
           ProductPosition productPosition = productPositionHashMap.get(key);
           productPosition.setComposition(materialProcess.generateCompositionString(productPosition.getComposition()));
            System.out.println(productPositionHashMap.get(key));
        }

    }
}