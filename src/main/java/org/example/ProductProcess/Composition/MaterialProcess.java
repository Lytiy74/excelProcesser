package org.example.ProductProcess.Composition;

import org.example.Util.IO.JsonFileReader;
import org.example.Util.MapConverter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class MaterialProcess {
    private static final String COMPOSITION_JSON_FILE_PATH = "src/main/resources/compositionNew.json";
    private final MaterialParser parser;
    private final MaterialTranslator translator;
    private final MaterialStringBuilder materialStringBuilder;

    public MaterialProcess() {
        HashMap<String, List<String>> stringStringHashMap = new JsonFileReader().readJsonObjectArrayToMap(COMPOSITION_JSON_FILE_PATH);
        parser = new MaterialParser();
        translator = new MaterialTranslator(MapConverter.invertColumnMap(stringStringHashMap));
        materialStringBuilder = new MaterialStringBuilder();
    }

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
