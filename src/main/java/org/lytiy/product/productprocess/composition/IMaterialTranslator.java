package org.lytiy.product.productprocess.composition;

import java.util.LinkedHashMap;

public interface IMaterialTranslator {
    LinkedHashMap<String, Integer> translateMaterial(LinkedHashMap<String, Integer> stringIntegerLinkedHashMap);
    String translateMaterial(String inputString);
}
