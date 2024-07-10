package org.example.Product.ProductProcess.Composition;

import org.example.Util.IO.JsonFileReader;
import org.example.Util.MapConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class is responsible for processing material compositions.
 * It reads a JSON file containing material compositions, parses the composition strings,
 * translates them into a standard format, and builds a formatted composition string.
 */
public class MaterialProcess {
    /**
     * The path to the JSON file containing material compositions.
     */
    private static final String COMPOSITION_JSON_FILE_PATH = "src/main/resources/compositionNew.json";

    /**
     * The parser for parsing the composition strings.
     */
    private final MaterialParser parser;

    /**
     * The translator for translating material names.
     */
    private final MaterialTranslator translator;

    /**
     * The builder for building the formatted composition string.
     */
    private final MaterialStringBuilder materialStringBuilder;

    /**
     * Constructs a new MaterialProcess object.
     *
     * @throws IOException if an error occurs while reading the JSON file.
     */
    public MaterialProcess() throws IOException {
        HashMap<String, List<String>> stringStringHashMap = new JsonFileReader().readJsonObjectArrayToMap(COMPOSITION_JSON_FILE_PATH);
        parser = new MaterialParser();
        translator = new MaterialTranslator(MapConverter.invertColumnMap(stringStringHashMap));
        materialStringBuilder = new MaterialStringBuilder();
    }

    /**
     * Generates a formatted composition string based on the given composition string.
     *
     * @param composition the composition string to be processed.
     * @return the formatted composition string.
     */
    public String generateCompositionString(String composition) {
        LinkedHashMap<String, Integer> compositionMap = parser.parseStringCompositionToMap(composition);
        LinkedHashMap<String, Integer> translatedMap = new LinkedHashMap<>();
        for (String key : compositionMap.keySet()) {
            String translated = translator.translateMaterial(key);
            if (!translatedMap.containsKey(translated)) {
                translatedMap.put(translated, compositionMap.get(key));
            } else {
                translatedMap.put(translated, translatedMap.get(translated) + compositionMap.get(key));
            }
        }
        return materialStringBuilder.buildCompositionString(translatedMap);
    }
}
