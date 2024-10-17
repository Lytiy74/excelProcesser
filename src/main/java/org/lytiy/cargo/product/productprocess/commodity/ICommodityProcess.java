package org.lytiy.cargo.product.productprocess.commodity;

import org.lytiy.cargo.product.Gender;

public interface ICommodityProcess {
    String getCommoditiesDescription(String commodity);
    Gender getGenderByCommodity(String commodity);
}
