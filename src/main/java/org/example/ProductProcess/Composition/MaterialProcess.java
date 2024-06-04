package org.example.ProductProcess.Composition;

import org.example.Util.JsonFileReader;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MaterialProcess {
    private static final String COMPOSITION_JSON_FILE_PATH = "src/main/resources/composition.json";
    private final MaterialParser parser;
    private final MaterialTranslator translator;
    private final MaterialStringBuilder materialStringBuilder;
    public MaterialProcess(){
        HashMap<String , String> stringStringHashMap = new JsonFileReader().readJsonToHashMap(COMPOSITION_JSON_FILE_PATH);
        parser = new MaterialParser();
        translator = new MaterialTranslator(stringStringHashMap);
        materialStringBuilder = new MaterialStringBuilder();
    }
    public String generateCompositionString(String composition) {
        LinkedHashMap<String, Integer> compositionMap = parser.parseStringCompositionToMap(composition);
        LinkedHashMap<String, Integer> translatedMap = translator.translateMaterial(compositionMap);
        return materialStringBuilder.buildCompositionString(translatedMap);
    }
}
