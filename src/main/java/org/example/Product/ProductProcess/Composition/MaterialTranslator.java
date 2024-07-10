package org.example.Product.ProductProcess.Composition;


import org.example.Util.JaccardCalculation;

import java.util.HashMap;
import java.util.LinkedHashMap;

class MaterialTranslator {
    private static HashMap<String, String> materialTranslationMap;

    MaterialTranslator(HashMap<String, String> translationMap) {
        materialTranslationMap = translationMap;
    }

    /**
     * This method translates the composition map from material codes to their corresponding names.
     *
     * @param stringIntegerLinkedHashMap A LinkedHashMap containing the composition data. The keys are the unique material codes (converted to lowercase)
     *                                   and the values are their corresponding percentages.
     * @return A LinkedHashMap where the keys are the unique material names (converted to lowercase) and the values are their corresponding percentages.
     * The LinkedHashMap is sorted in descending order based on the percentages.
     * If the same material appears multiple times in the input HashMap, their percentages are summed up.
     */
    LinkedHashMap<String, Integer> translateMaterial(LinkedHashMap<String, Integer> stringIntegerLinkedHashMap) {
        // Initialize a LinkedHashMap to store the translated composition data
        LinkedHashMap<String, Integer> translatedComposition = new LinkedHashMap<>();

        // Iterate over the entries in the input LinkedHashMap
        for (String key : stringIntegerLinkedHashMap.keySet()) {
            // Translate the material code to its name using the dictionary
            String translated = materialTranslationMap.get(key);
            String translatedMaterial = translated == null ? key : translated;

            // Add the translated material and its corresponding percentage to the translated LinkedHashMap
            translatedComposition.put(translatedMaterial, stringIntegerLinkedHashMap.get(key));
        }

        // Return the translated LinkedHashMap
        return translatedComposition;
    }
    String translateMaterial(String inputString){
        String translated = JaccardCalculation.findBestMatch(inputString, materialTranslationMap);
        System.out.println("Best match for " + inputString + " ---> " + translated);
        return translated == null ? inputString : translated;
    }
}
