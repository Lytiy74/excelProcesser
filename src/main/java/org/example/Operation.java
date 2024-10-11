package org.example;

public enum Operation {
    COLLECT_PRODUCTS,
    SORT_PRODUCT_COMPOSITION,
    TRANSLATE_PRODUCT_COMPOSITION,
    TRANSLATE_PRODUCT_COUNTRY,
    PROCESS_PRODUCT_COMPOSITION, // INCLUDES SORT_PRODUCT_COMPOSITION , TRANSLATE_PRODUCT_COMPOSITION
    PROCESS_PRODUCT_NAMES,
    PROCESS_PRODUCT_SIZES,
    PROCESS_PRODUCT_GENDER,
    PROCESS_PRODUCT_HS_CODE,
    PROCESS_PRODUCT_WEIGHT,
    /* PROCESS_PRODUCTS
    INCLUDES PROCESS_PRODUCT_COMPOSITION, PROCESS_PRODUCT_NAMES,
    PROCESS_PRODUCT_SIZES, TRANSLATE_PRODUCT_COUNTRY,
    PROCESS_PRODUCT_GENDER, PROCESS_PRODUCT_CODE, PROCESS_PRODUCT_WEIGHT
     */
    PROCESS_PRODUCTS,
    SAVE_RESULTS
}
