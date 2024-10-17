package org.lytiy.cargo.product.productprocess.composition;

import java.util.LinkedHashMap;

public interface IMaterialStringBuilder {
    String buildCompositionString(LinkedHashMap<String,Integer> map);
}
