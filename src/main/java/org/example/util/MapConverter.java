package org.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (inputMap == null) {
            logger.error("Input map is null");
            throw new IllegalArgumentException("Input map cannot be null");
        }
        logger.debug("Inverting column map. Map size = {}", inputMap.size());

        HashMap<String, String> columnKeyMap = new HashMap<>();
        try {
            for (Map.Entry<String, List<String>> entry : inputMap.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                if (values != null) {
                    for (String value : values) {
                        columnKeyMap.put(value, key);
                    }
                }
            }
            logger.debug("Successfully inverted column map. Inverted map size = {}", columnKeyMap.size());
        } catch (Exception e) {
            logger.error("Error occurred while inverting column map", e);
            throw e;
        }

        return columnKeyMap;
    }
}