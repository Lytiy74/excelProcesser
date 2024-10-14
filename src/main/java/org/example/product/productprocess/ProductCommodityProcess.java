package org.example.product.productprocess;

import org.example.product.Gender;

import java.util.HashMap;

public class ProductCommodityProcess implements ICommodityProcess {
    private HashMap<String, String> commodities;

    public ProductCommodityProcess(HashMap<String, String> commodities) {
        this.commodities = commodities;
    }

    @Override
    public String getCommoditiesDescription(String commodity) {
        String description = commodities.get(commodity);
        int length = commodity.length();
        while(commodity.length() >= 4 && description == null){
            commodity = commodity.substring(0,--length);
            description = commodities.get(commodity);
        }

        return description;
    }
    @Override
    public Gender getGenderByCommodity(String commodity) {
        String description = getCommoditiesDescription(commodity);
        if (description == null) return Gender.UNSPECIFIED;

        if (description.toLowerCase().contains("men's or boys")) {
            return Gender.MALE;
        } else if (description.toLowerCase().contains("women's or girls")) {
            return Gender.FEMALE;
        }
        return Gender.UNSPECIFIED;

    }
}
