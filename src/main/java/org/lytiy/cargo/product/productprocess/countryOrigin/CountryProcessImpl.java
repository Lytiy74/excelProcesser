package org.lytiy.cargo.product.productprocess.countryOrigin;

import org.lytiy.util.ITranslator;

public class CountryProcessImpl implements ICountryProcess {
    private final ITranslator translator;

    public CountryProcessImpl(ITranslator translator) {
        this.translator = translator;
    }
    @Override
    public String translateCountry(String country){
        return translator.translate(country);
    }
}
