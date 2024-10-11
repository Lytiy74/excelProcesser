package org.example.product.productprocess.composition;


import org.example.util.JaccardCalculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * This class is responsible for translating material codes to their corresponding names.
 * It uses a dictionary provided in a JSON file and a Jaccard calculation algorithm to find the best match for unknown material codes.
 */
class MaterialTranslator implements IMaterialTranslator {
    private static final Logger logger = LoggerFactory.getLogger(MaterialTranslator.class);
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
    public LinkedHashMap<String, Integer> translateMaterial(LinkedHashMap<String, Integer> stringIntegerLinkedHashMap) {
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

    /**
     * Translates a material code to its corresponding name using a dictionary and a Jaccard calculation algorithm.
     * If the material code is not found in the dictionary, the method will attempt to find the best match using the Jaccard algorithm.
     *
     * @param inputString The material code to be translated.
     * @return The corresponding name of the material code. If no match is found, the input material code is returned.
     */
    @Override
    public String translateMaterial(String inputString) {
        String translated = materialTranslationMap.get(inputString);
        if (translated == null) {
            logger.debug("Cant find translations in map for '{}', going to jaccardCalculation", inputString);
            translated = JaccardCalculation.findBestMatch(inputString, materialTranslationMap);
            materialTranslationMap.put(inputString, translated);
        }
        logger.debug("'{}' translated to '{}'", inputString, translated);
        return translated == null ? inputString : translated;
    }
}
