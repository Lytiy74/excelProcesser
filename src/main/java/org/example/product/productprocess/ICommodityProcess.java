package org.example.product.productprocess;

import org.example.product.Gender;

public interface ICommodityProcess {
    String getCommoditiesDescription(String commodity);
    Gender getGenderByCommodity(String commodity);
}
