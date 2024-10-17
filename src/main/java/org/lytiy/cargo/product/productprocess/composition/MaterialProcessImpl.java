package org.lytiy.cargo.product.productprocess.composition;

import org.lytiy.util.ITranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is responsible for processing material compositions.
 * It reads a JSON file containing material compositions, parses the composition strings,
 * translates them into a standard format, and builds a formatted composition string.
 */
public class MaterialProcessImpl implements IMaterialProcess {
    private static final Logger logger = LoggerFactory.getLogger(MaterialProcessImpl.class);

    /**
     * The path to the JSON file containing material compositions.
     */
    /**
     * The parser for parsing the composition strings.
     */
    private final MaterialParser parser;

    /**
     * The translator for translating material names.
     */
    private final ITranslator translator;

    /**
     * The builder for building the formatted composition string.
     */
    private final MaterialStringBuilder materialStringBuilder;

    /**
     * Constructs a new MaterialProcess object.
     *
     */
    public MaterialProcessImpl(ITranslator translator) {
        logger.info("Initializing MaterialProcessImpl");
        parser = new MaterialParser();
        this.translator = translator;
        materialStringBuilder = new MaterialStringBuilder();
        logger.info("MaterialProcessImpl initialized successfully");
    }

    /**
     * Generates a formatted composition string based on the given composition string.
     *
     * @param composition the composition string to be processed.
     * @return the formatted composition string.
     */
    @Override
    public String generateCompositionString(String composition) {
        logger.debug("Generating composition string for input: {}", composition);
        LinkedHashMap<String, Integer> compositionMap = parser.parseStringCompositionToMap(composition.toLowerCase());
        if (compositionMap.isEmpty()) return composition;

        LinkedHashMap<String, Integer> translatedMap = new LinkedHashMap<>();
        for (String key : compositionMap.keySet()) {
            String translated = translator.translate(key);
            if (!translatedMap.containsKey(translated)) {
                translatedMap.put(translated, compositionMap.get(key));
            } else {
                translatedMap.put(translated, translatedMap.get(translated) + compositionMap.get(key));
            }
        }

        return materialStringBuilder.buildCompositionString(getSortedLinkedHashMap(translatedMap));
    }

    public String generateSortedCompositionString(String composition){
        LinkedHashMap<String, Integer> compositionMap = parser.parseStringCompositionToMap(composition.toLowerCase());
        if (compositionMap.isEmpty()) return composition;
        return materialStringBuilder.buildCompositionString(getSortedLinkedHashMap(compositionMap));
    }

    @Override
    public String translateComposition(String composition) {
        return translator.translate(composition.toLowerCase());
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
