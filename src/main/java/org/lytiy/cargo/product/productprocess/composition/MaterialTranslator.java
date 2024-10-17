package org.lytiy.cargo.product.productprocess.composition;


import org.lytiy.util.ITranslator;
import org.lytiy.util.JaccardCalculation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * This class is responsible for translating material codes to their corresponding names.
 * It uses a dictionary provided in a JSON file and a Jaccard calculation algorithm to find the best match for unknown material codes.
 */
@Deprecated(since = "17.10.2024")
class MaterialTranslator implements ITranslator {
    private static final Logger logger = LoggerFactory.getLogger(MaterialTranslator.class);
    private static HashMap<String, String> materialTranslationMap;

    MaterialTranslator(HashMap<String, String> translationMap) {
        materialTranslationMap = translationMap;
    }
    /**
     * Translates a material code to its corresponding name using a dictionary and a Jaccard calculation algorithm.
     * If the material code is not found in the dictionary, the method will attempt to find the best match using the Jaccard algorithm.
     *
     * @param inputString The material code to be translated.
     * @return The corresponding name of the material code. If no match is found, the input material code is returned.
     */
    @Deprecated(since = "17.10.2024")
    @Override
    public String translate(String inputString) {
        String translated = materialTranslationMap.get(inputString);
        if (translated == null) {
            logger.debug("Cant find translations in map for '{}', going to jaccardCalculation", inputString);
            translated = JaccardCalculation.findBestMatch(inputString, materialTranslationMap);
            translated = translated.contains("N/A") ? translated.concat(" (" + inputString + ")") : translated;
            materialTranslationMap.put(inputString, translated);
        }
        logger.debug("'{}' translated to '{}'", inputString, translated);
        return translated;
    }
}
