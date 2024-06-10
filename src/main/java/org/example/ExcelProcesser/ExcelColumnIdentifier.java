package org.example.ExcelProcesser;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.example.Util.JaccardCalculation;

import java.util.*;

class ExcelColumnIdentifier {
    public HashMap<String, Integer> identifyColumns(Row sourceRow, HashMap<String, List<String>> targetColumns){
        HashMap<String, String> columnKeyMap =  invertColumnMap(targetColumns);
        List<String> sourceColumns = extractColumnNames(sourceRow);

        HashMap<String, Integer> identifiedColumns = new HashMap<>();
        for (String column : sourceColumns){
            String bestMatchCategory = finbBestMatch(column, columnKeyMap);
            identifiedColumns.put(bestMatchCategory, sourceColumns.indexOf(column));
            System.out.println(column + " -> " +bestMatchCategory);
        }
        return identifiedColumns;
    }

    private List<String> extractColumnNames(Row sourceRow) {
        List<String> sourceColumns = new ArrayList<>();
        for (Cell cell : sourceRow){
            String cellValue = cell.getStringCellValue().toLowerCase();
            sourceColumns.add(cellValue);
        }
        return sourceColumns;
    }

    private HashMap<String, String> invertColumnMap(HashMap<String, List<String>> targetColumns) {
        HashMap<String, String> columnKeyMap = new HashMap<>();
        for (String key : targetColumns.keySet()) {
            for (String value : targetColumns.get(key)) {
                columnKeyMap.put(value, key);
            }
        }
        return columnKeyMap;
    }
    private String finbBestMatch(String columnName,HashMap<String, String> columnKeyMap){
        Set<String> columnSet = new HashSet<>(List.of(columnName.split("\\s+")));
        String bestCategory = "UNKNOWN";
        double bestScore = Double.MIN_VALUE;

        for(Map.Entry<String, String> entry : columnKeyMap.entrySet()){
            String possibleName = entry.getKey();
            String category = entry.getValue();
            Set<String> possibleSet = new HashSet<>(List.of(possibleName.split("\\s+")));
            double score = JaccardCalculation.jaccardSimilarity(columnSet, possibleSet);
            if (score > bestScore){
                bestScore = score;
                bestCategory = category;
            }
        }
        return bestCategory;
    }
}
