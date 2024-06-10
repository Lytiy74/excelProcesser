package org.example.Util;

import java.util.HashMap;
import java.util.List;

public class MapConverter {
    public static HashMap<String, String> invertColumnMap(HashMap<String, List<String>> targetColumns) {
        HashMap<String, String> columnKeyMap = new HashMap<>();
        for (String key : targetColumns.keySet()) {
            for (String value : targetColumns.get(key)) {
                columnKeyMap.put(value, key);
            }
        }
        return columnKeyMap;
    }
}
