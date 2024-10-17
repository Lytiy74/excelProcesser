package org.lytiy.strategy;


import org.lytiy.cargo.product.ProductPosition;
import org.lytiy.cargo.product.productprocess.countryOrigin.ICountryProcess;

import java.util.HashMap;

public class ExcelProductTranslateCountryOrigin implements IExcelProcessingStrategy{
    private final ICountryProcess countryProcess;

    public ExcelProductTranslateCountryOrigin(ICountryProcess countryProcess) {
        this.countryProcess = countryProcess;
    }

    @Override
    public void execute(ExcelProcessingContext context) {
        HashMap<String, ProductPosition> productPositionHashMap = context.getProductPositionHashMap();
        for (ProductPosition productPosition : productPositionHashMap.values()) {
            String country = productPosition.getCountryOrigin();
            String translatedCountry = countryProcess.translateCountry(country);
            productPosition.setCountryOrigin(translatedCountry);
        }
    }
}
