package org.lytiy;

public enum ResourceFiles {
    COLUMN_NAME_JSON_FILE("data/columnNames.json"),
    COMPOSITION_JSON_FILE("data/compositionNew.json"),
    CLOTHES_NAMES_JSON_FILE("data/productMetas.json"),
    CLOTHES_SIZE_JSON_FILE("data/clothesSize.json"),
    HARMONIZED_CODES_JSON_FILE("data/harmonizedCodes.json");
    private final String fileName;

    ResourceFiles(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
