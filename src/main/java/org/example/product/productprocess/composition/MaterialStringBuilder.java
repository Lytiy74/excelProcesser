package org.example.product.productprocess.composition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

class MaterialStringBuilder {
    private static final Logger logger = LoggerFactory.getLogger(MaterialStringBuilder.class);

    /**
     * This method builds a string representation of the material composition.
     * The composition is represented as a percentage of each material in the input map.
     *
     * @param linkedHashMap A LinkedHashMap containing the materials and their respective percentages.
     *                      The keys are the material names (String), and the values are the percentages (Integer).
     * @return A string representation of the material composition.
     * The string is formatted as "percentage% materialName percentage% materialName ..."
     * The materials are ordered by their insertion order in the LinkedHashMap.
     */
    String buildCompositionString(LinkedHashMap<String, Integer> linkedHashMap) {
        logger.debug("Building material composition string");
        StringBuilder result = new StringBuilder();
        for (Iterator<Map.Entry<String, Integer>> iterator = linkedHashMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Integer> entry = iterator.next();
            result.append(String.format("%d%% %s", entry.getValue(), entry.getKey()));
            if (iterator.hasNext()) result.append(" ");
        }
        logger.debug("Material composition string built successfully. Returning '{}'", result);
        return result.toString();
    }
}