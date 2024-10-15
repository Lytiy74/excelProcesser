package org.lytiy.product.productprocess.commodity;

import org.lytiy.product.Gender;

public interface ICommodityProcess {
    String getCommoditiesDescription(String commodity);
    Gender getGenderByCommodity(String commodity);
}
