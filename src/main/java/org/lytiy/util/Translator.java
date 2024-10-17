package org.lytiy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class Translator implements ITranslator{

    private static final Logger logger = LoggerFactory.getLogger(Translator.class);
    private final HashMap<String, String> translationsMap;

    public Translator(HashMap<String, String> translationsMap) {
        this.translationsMap = translationsMap;
    }

    @Override
    public String translate(String inputString) {
        String translated = translationsMap.get(inputString);
        if (translated == null) {
            logger.debug("Cant find translations in map for '{}', going to jaccardCalculation", inputString);
            translated = JaccardCalculation.findBestMatch(inputString, translationsMap);
            translated = translated.contains("N/A") ? translated.concat(" (" + inputString + ")") : translated;
            translationsMap.put(inputString, translated);
        }
        logger.debug("'{}' translated to '{}'", inputString, translated);
        return translated;
    }
}
