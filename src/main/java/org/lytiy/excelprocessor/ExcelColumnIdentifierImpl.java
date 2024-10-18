package org.lytiy.excelprocessor;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.lytiy.util.JaccardCalculation;
import org.lytiy.util.MapConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExcelColumnIdentifierImpl implements IExcelColumnIdentifier {

    private static final Logger logger = LoggerFactory.getLogger(ExcelColumnIdentifierImpl.class);

    @Override
    public HashMap<String, Integer> identifyColumns(Row source, HashMap<String, List<String>> targetColumns) {
        HashMap<String, String> columnKeyMap = MapConverter.invertColumnMap(targetColumns);
        List<String> sourceColumns = extractColumnNames(source);

        HashMap<String, Integer> identifiedColumns = new HashMap<>();
        JaccardCalculation jaccardCalculation = new JaccardCalculation();
        for (int i = 0; i < sourceColumns.size(); i++) {
            String column = sourceColumns.get(i);
            String bestMatchCategory = jaccardCalculation.findBestMatch(column, columnKeyMap);
            if (bestMatchCategory.equals("N/A")) continue;
            identifiedColumns.put(bestMatchCategory, i);
        }
        logger.debug("Identified columns {}", identifiedColumns);
        return identifiedColumns;
    }

    public int findAndGetNumberOfHeaderRow(Sheet sheet, HashMap<String, List<String>> targetColumns) {
        return JaccardCalculation.findBestMatchRow(sheet, targetColumns);
    }

    private List<String> extractColumnNames(Row sourceRow) {
        List<String> sourceColumns = new ArrayList<>();
        for (Cell cell : sourceRow) {
            String cellValue = "N/A";
            switch (cell.getCellType()){
                case _NONE, BLANK, BOOLEAN, ERROR -> {
                }
                case NUMERIC -> cellValue = String.valueOf(cell.getNumericCellValue());
                case STRING -> cellValue = cell.getStringCellValue();
                case FORMULA -> cellValue = String.valueOf(cell.getCellFormula());
            }

            sourceColumns.add(cellValue);
        }
        return sourceColumns;
    }
}
