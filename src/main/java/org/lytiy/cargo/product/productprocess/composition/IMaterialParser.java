package org.lytiy.cargo.product.productprocess.composition;

import java.util.LinkedHashMap;

interface IMaterialParser {
    LinkedHashMap<String, Integer> parseStringCompositionToMap(String composition);
}
