package org.example.Product.ProductProcess.Composition;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

class MaterialStringBuilder {
    /**
 * This method builds a string representation of the material composition.
 * The composition is represented as a percentage of each material in the input map.
 *
 * @param linkedHashMap A LinkedHashMap containing the materials and their respective percentages.
 *                      The keys are the material names (String), and the values are the percentages (Integer).
 *
 * @return A string representation of the material composition.
 *         The string is formatted as "percentage% materialName percentage% materialName ..."
 *         The materials are ordered by their insertion order in the LinkedHashMap.
 */
String buildCompositionString(LinkedHashMap<String, Integer> linkedHashMap) {
    StringBuilder result = new StringBuilder();
    for (Iterator<Map.Entry<String, Integer>> iterator = linkedHashMap.entrySet().iterator(); iterator.hasNext(); ) {
        Map.Entry<String, Integer> entry = iterator.next();
        result.append(String.format("%d%% %s", entry.getValue(), entry.getKey()));
        if (iterator.hasNext()) result.append(" ");
    }
    return result.toString();
}
}