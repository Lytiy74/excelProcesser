package org.example;

public enum ResourceFiles {
    COLUMN_NAME_JSON_FILE("columnNames.json"),
    COMPOSITION_JSON_FILE("compositionNew.json"),
    CLOTHES_NAMES_JSON_FILE("clothesNames.json"),
    CLOTHES_SIZE_JSON_FILE("clothesSize.json");
    private String fileName;

    ResourceFiles(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
