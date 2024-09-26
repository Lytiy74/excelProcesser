package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

public class MapConverter {
    private static final Logger logger = LoggerFactory.getLogger(MapConverter.class);
    /**
 * This method inverts a given HashMap where the key is a String and the value is a List of Strings.
 * The inverted map will have the values from the original map as keys and the corresponding keys from the original map as values.
 * If there are duplicate values in the original map, only the last occurrence of each value will be present in the inverted map.
 *
 * @param inputMap The original HashMap to invert.
 * @return A new HashMap where the values from the original map are keys and the corresponding keys from the original map are values.
 */
public static HashMap<String, String> invertColumnMap(HashMap<String, List<String>> inputMap) {
    logger.debug("Inverting column map. Map size = {}", inputMap.size());
    HashMap<String, String> columnKeyMap = new HashMap<>();
    for (String key : inputMap.keySet()) {
        for (String value : inputMap.get(key)) {
            columnKeyMap.put(value, key);
        }
    }
    return columnKeyMap;
}
}
