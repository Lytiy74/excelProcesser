package org.example.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductMeta {
    private String productName;
    private ProductType productType;
    private int weightInGrams;

    public ProductMeta(@JsonProperty("name") String productName,
                       @JsonProperty("category")  ProductType productType,
                       @JsonProperty("weight") int weightInGrams) {
        this.productName = productName;
        this.productType = productType;
        this.weightInGrams = weightInGrams;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public int getWeightInGrams() {
        return weightInGrams;
    }

    public void setWeightInGrams(int weightInGrams) {
        this.weightInGrams = weightInGrams;
    }
}
