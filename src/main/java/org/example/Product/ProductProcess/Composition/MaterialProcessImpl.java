package org.example.Product.ProductProcess.Composition;

import org.example.Main;
import org.example.ResourceFiles;
import org.example.Util.IO.JsonFileReader;
import org.example.Util.MapConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is responsible for processing material compositions.
 * It reads a JSON file containing material compositions, parses the composition strings,
 * translates them into a standard format, and builds a formatted composition string.
 */
public class MaterialProcessImpl implements MaterialProcess {
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
    public MaterialProcessImpl() throws IOException, URISyntaxException {
        logger.info("Initializing MaterialProcessImpl");
        HashMap<String, List<String>> stringStringHashMap;
        Path jarDir = Paths.get(new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent());
        Path inputFilePath = jarDir.resolve(ResourceFiles.COMPOSITION_JSON_FILE.getFileName());
        stringStringHashMap = new JsonFileReader().readJsonObjectArrayToMap(String.valueOf(inputFilePath));
        parser = new MaterialParser();
        translator = new MaterialTranslator(MapConverter.invertColumnMap(stringStringHashMap));
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
            String translated = translator.translateMaterial(key);
            if (!translatedMap.containsKey(translated)) {
                translatedMap.put(translated, compositionMap.get(key));
            } else {
                translatedMap.put(translated, translatedMap.get(translated) + compositionMap.get(key));
            }
        }

        String result = materialStringBuilder.buildCompositionString(getSortedLinkedHashMap(translatedMap));
        return result;
    }

    @Override
    public String translateComposition(String composition) {
        String translated = translator.translateMaterial(composition.toLowerCase());
        return translated;
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
