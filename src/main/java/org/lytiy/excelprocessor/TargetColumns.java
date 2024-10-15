package org.lytiy.excelprocessor;

public enum TargetColumns {
    ARTICLE("article"),
    PRODUCT_NAME("productName"),
    SIZES("sizes"),
    TRADE_MARK("tradeMark"),
    COUNTRY_ORIGIN("countryOrigin"),
    QUANTITY("quantity"),
    COMPOSITION("composition"),
    GENDER("gender"),
    HS_CODE("hsCode"),
    BRUTTO_WEIGHT("bruttoWeight"),
    PRICE("price");
    private final String columnName;

    TargetColumns(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
