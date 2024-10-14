package org.example;

public enum ResourceFiles {
    COLUMN_NAME_JSON_FILE("columnNames.json"),
    COMPOSITION_JSON_FILE("compositionNew.json"),
    CLOTHES_NAMES_JSON_FILE("productMetas.json"),
    CLOTHES_SIZE_JSON_FILE("clothesSize.json"),
    HARMONIZED_CODES_JSON_FILE("harmonizedCodes.json");
    private String fileName;

    ResourceFiles(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
