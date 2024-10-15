package org.example.product.productprocess.composition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class MaterialParser implements IMaterialParser {
    private static final Logger logger = LoggerFactory.getLogger(MaterialParser.class);
    private static final Pattern COMPOSITION_PATTERN_WITH_MULTIPLE = Pattern.compile("(((?<percentage>\\d+)%?(\\p{Zs}|\\s)*(?<material>\\pL\\s*\\pL+))((?=.*lining:)?))");
    private static final Pattern COMPOSITION_PATTER_REVERSED = Pattern.compile("^(?<material>\\pL+(?:\\s+\\pL+)*)\\s+(?<percentage>\\d+)%?(\\s+(?<material2>\\pL+(?:\\s+\\pL+)*)\\s+(?<percentage2>\\d+)%?)*");
    private static final Pattern COMPOSITION_PATTERN_SIMPLE = Pattern.compile("((?<percentage>\\d+)%?(\\p{Zs}|\\s)*(?<material>\\pL+\\s*\\pL+))");

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
    @Override
    public LinkedHashMap<String, Integer> parseStringCompositionToMap(String string) {
        logger.debug("Parsing composition string: {}", string);
        // Initialize a HashMap to store the composition data
        HashMap<String, Integer> compositionMap = new HashMap<String, Integer>();

        int totalPercentage = 0;

        // Use a Matcher to find all occurrences of the composition pattern in the input string
       Matcher matcher = getMatcher(string);
       matcher.reset();
        // Iterate over the matches
        while (matcher.find()) {
            try {
                // Extract the material name and percentage from the match
                String material = matcher.group("material").toLowerCase().trim();
                int percentage = Integer.parseInt(matcher.group("percentage"));
                totalPercentage += percentage;

                // If the material is not already in the composition map, add it with its percentage
                compositionMap.merge(material, percentage, Integer::sum);
            } catch (NumberFormatException e) {
                logger.error("Error parsing percentage: {}", matcher.group("percentage"), e);
            } catch (Exception e) {
                logger.error("Unexpected error while parsing material: {}", matcher.group("material"), e);
            }
            if (totalPercentage >= 100) break;
        }

        // Return the sorted LinkedHashMap of composition data
        logger.debug("Parsed composition data: {}", compositionMap);
        return getSortedCompositionLinkedHashMap(compositionMap);
    }


    private static Matcher getMatcher(String string) {
        Matcher matcher = COMPOSITION_PATTER_REVERSED.matcher(string);
        if (matcher.find()) {
            return matcher; // Return if the first pattern matches
        }

        matcher = COMPOSITION_PATTERN_WITH_MULTIPLE.matcher(string);
        if (matcher.find()) {
            return matcher; // Return if the second pattern matches
        }

        matcher = COMPOSITION_PATTERN_SIMPLE.matcher(string);
        if (matcher.find()) {
            return matcher; // Return if the third pattern matches
        }

        return matcher; // Return matcher (no match will have group data)
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
    public LinkedHashMap<String, Integer> getSortedCompositionLinkedHashMap(Map<String, Integer> compositionMap) {
        logger.debug("Sorting composition data by percentage: {}", compositionMap);
        return
                new LinkedHashMap<>(
                        compositionMap.entrySet().stream()
                                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                        (e1, e2) -> e1, LinkedHashMap::new))
                );
    }

}