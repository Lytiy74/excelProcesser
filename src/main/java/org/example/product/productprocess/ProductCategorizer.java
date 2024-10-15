package org.example.product.productprocess;

import org.example.product.ProductMeta;
import org.example.product.ProductType;

import java.util.HashMap;

public class ProductCategorizer implements IProductCategorizer{
    private final HashMap<String, ProductMeta> productMetaHashMap;

    public ProductCategorizer(HashMap<String, ProductMeta> productMetaHashMap) {
        this.productMetaHashMap = productMetaHashMap;
    }

    @Override
    public ProductType categorizeProduct(String productName){
        ProductMeta productMeta = productMetaHashMap.get(productName);
        return productMeta != null ? productMeta.getProductType() : ProductType.OTHERS;
    }
}
