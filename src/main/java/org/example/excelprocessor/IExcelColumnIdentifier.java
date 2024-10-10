package org.example.excelprocessor;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.*;

public interface IExcelColumnIdentifier {
    /**
     * Identifies the columns in the source and returns a map of identified categories and their respective column indices.
     *
     * @param source The source from which to identify columns.
     * @param targetColumns A map of target column categories and their corresponding column names.
     * @return A map of identified categories and their respective column indices.
     */
    HashMap<String, Integer> identifyColumns(Row source, HashMap<String, List<String>> targetColumns);
    int findAndGetNumberOfHeaderRow(Sheet sheet, HashMap<String, List<String>> targetColumns);
}