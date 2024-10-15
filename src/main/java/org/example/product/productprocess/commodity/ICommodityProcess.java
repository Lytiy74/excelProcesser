package org.example.product.productprocess.commodity;

import org.example.product.Gender;

public interface ICommodityProcess {
    String getCommoditiesDescription(String commodity);
    Gender getGenderByCommodity(String commodity);
}
