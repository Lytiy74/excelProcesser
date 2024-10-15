package org.example.product.productprocess.commodity;

import org.example.product.Gender;

import java.util.HashMap;

public class ProductCommodityProcess implements ICommodityProcess {
    public static final int MAX_COMMODITY_LENGTH = 10;
    private final HashMap<String, CommodityItem> commodities;

    public ProductCommodityProcess(HashMap<String, CommodityItem> commodities) {
        this.commodities = commodities;
    }

    /**
     * Method search and returns description of commodity code in harmonized system;
     *
     * @param commodity 6 or 10 digits code of harmonized system
     * @return description of commodity or returns null if there is no description
     */

    @Override
    public String getCommoditiesDescription(String commodity) {
        //todo regex pattern to validate code
        commodity = maximizeCommodity(commodity);

        CommodityItem commodityItem = commodities.get(commodity);
        while (commodityItem == null) {
            commodity = commodity.substring(0, commodity.length() - 1);
            commodityItem = commodities.get(commodity);
        }
        return buildFullDescription(commodityItem);
    }

    private String buildFullDescription(CommodityItem commodityItem) {
        StringBuilder description = new StringBuilder();
        do {
            description.insert(0, " | " + commodityItem.getText());
            commodityItem = commodities.get(commodityItem.getParentId());
        } while (commodityItem != null);
        return description.toString();
    }

    private static String maximizeCommodity(String commodity) {
        StringBuilder commodityBuilder = new StringBuilder(commodity);
        while (commodityBuilder.length() < MAX_COMMODITY_LENGTH) {
            commodityBuilder.append("0");
        }
        return commodityBuilder.toString();
    }

    /**
     * Method search if description contains information about gender.
     *
     * @param commodity 6 or 10 digits code of harmonized system
     * @return Gender enum.
     */
    @Override
    public Gender getGenderByCommodity(String commodity) {
        String description = getCommoditiesDescription(commodity);
        if (description == null) return Gender.UNSPECIFIED;

        if (description.toLowerCase().contains("чоловіків або хлопців")) {
            return Gender.MALE;
        } else if (description.toLowerCase().contains("жінок або дівчат")) {
            return Gender.FEMALE;
        }
        return Gender.UNSPECIFIED;

    }
}
