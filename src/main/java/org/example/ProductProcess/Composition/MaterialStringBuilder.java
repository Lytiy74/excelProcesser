package org.example.ProductProcess.Composition;

import java.util.LinkedHashMap;
import java.util.Map;

class MaterialStringBuilder {
    String buildCompositionString(LinkedHashMap<String, Integer> linkedHashMap) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : linkedHashMap.entrySet()) {
            result.append(String.format("%d%% %s ", entry.getValue(), entry.getKey()));
        }
        return result.toString();
    }
}