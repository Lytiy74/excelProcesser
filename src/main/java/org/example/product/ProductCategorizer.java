package org.example.product;

import java.util.HashMap;

public class ProductCategorizer {
    HashMap<String, ProductMeta> productMetaHashMap;

    public ProductCategorizer(HashMap<String, ProductMeta> productMetaHashMap) {
        this.productMetaHashMap = productMetaHashMap;
    }

    public ProductType categorizeProduct(String productName){
        ProductMeta productMeta = productMetaHashMap.get(productName);
        return productMeta != null ? productMeta.getProductType() : ProductType.OTHERS;
    }
}
