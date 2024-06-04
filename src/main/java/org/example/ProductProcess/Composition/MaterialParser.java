package org.example.ProductProcess.Composition;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class MaterialParser {
    private static final Pattern COMPOSITION_PATTERN = Pattern.compile("(\\d+)%*\\s*([A-Za-z]+)");

    /**
     * This method processes a string representing a composition of materials and their percentages.
     * The string is expected to follow the format "XX%material1 YY%material2...", where XX and YY are integers representing percentages,
     * and material1, material2,... are the names of the materials.
     * The method returns a LinkedHashMap where the keys are the unique material names (converted to lowercase) and the values are their corresponding percentages.
     * The LinkedHashMap is sorted in descending order based on the percentages.
     * If the same material appears multiple times in the input string, their percentages are summed up.
     *
     * @param string The input string representing the composition of materials and their percentages.
     * @return A LinkedHashMap where the keys are the unique material names (converted to lowercase) and the values are their corresponding percentages.
     */
    LinkedHashMap<String, Integer> parseStringCompositionToMap(String string) {
        // Initialize a HashMap to store the composition data
        HashMap<String, Integer> compositionMap = new HashMap<String, Integer>();

        // Use a Matcher to find all occurrences of the composition pattern in the input string
        Matcher matcher = COMPOSITION_PATTERN.matcher(string);

        // Iterate over the matches
        while (matcher.find()) {
            // Extract the material name and percentage from the match
            String material = matcher.group(2).toLowerCase();
            int percentage = Integer.parseInt(matcher.group(1));

            // If the material is not already in the composition map, add it with its percentage
            if (!compositionMap.containsKey(material)) {
                compositionMap.put(material, percentage);
            } else {
                // If the material is already in the composition map, add the percentage to its existing value
                compositionMap.put(material, compositionMap.get(material) + percentage);
            }
        }

        // Return the sorted LinkedHashMap of composition data
        return getSortedLinkedHashMap(compositionMap);
    }

    /**
     * This method takes a HashMap of composition data and returns a sorted LinkedHashMap.
     * The LinkedHashMap is sorted in descending order based on the percentages.
     * If the same material appears multiple times in the input HashMap, their percentages are summed up.
     *
     * @param compositionMap A HashMap containing the composition data. The keys are the unique material names (converted to lowercase)
     *                       and the values are their corresponding percentages.
     * @return A LinkedHashMap where the keys are the unique material names (converted to lowercase) and the values are their corresponding percentages.
     * The LinkedHashMap is sorted in descending order based on the percentages.
     */
    LinkedHashMap<String, Integer> getSortedLinkedHashMap(HashMap<String, Integer> compositionMap) {
        return new LinkedHashMap<>(
                compositionMap.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new))
        );
    }
}