package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.example.util.JaccardCalculation;
import org.example.util.MapConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class ExcelColumnIdentifierImpl implements IExcelColumnIdentifier {

    @Override
    public HashMap<String, Integer> identifyColumns(Row source, HashMap<String, List<String>> targetColumns) {
        if (!(source instanceof Row)) {
            throw new IllegalArgumentException("Source must be of type Row");
        }
        Row sourceRow = (Row) source;

        HashMap<String, String> columnKeyMap = MapConverter.invertColumnMap(targetColumns);
        List<String> sourceColumns = extractColumnNames(sourceRow);

        HashMap<String, Integer> identifiedColumns = new HashMap<>();
        for (String column : sourceColumns) {
            String bestMatchCategory = JaccardCalculation.findBestMatch(column, columnKeyMap);
            identifiedColumns.put(bestMatchCategory, sourceColumns.indexOf(column));
        }

        return identifiedColumns;
    }

    public int findAndGetNumberOfHeaderRow(Sheet sheet, HashMap<String, List<String>> targetColumns) {
        return JaccardCalculation.findBestMatchRow(sheet, targetColumns);
    }

    private List<String> extractColumnNames(Row sourceRow) {
        List<String> sourceColumns = new ArrayList<>();
        for (Cell cell : sourceRow) {
            String cellValue = cell.getStringCellValue().toLowerCase();
            sourceColumns.add(cellValue);
        }
        return sourceColumns;
    }
}