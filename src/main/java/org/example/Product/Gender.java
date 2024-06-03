package org.example.Product;

public enum Gender {
    MALE("чол"),
    FEMALE("жін"),
    BOYS("хлопчаче"),
    GIRLS("дівчаче"),
    UNISEX("унісекс"),
    UNSPECIFIED("N/A");
    private String title;
    Gender(String title){
        this.title = title;
    }
    public static Gender fromString(String genderValue) {
        if (genderValue != null) {
            switch (genderValue.toLowerCase()) {
                case "male":
                    return MALE;
                case "female":
                    return FEMALE;
                default:
                    return UNSPECIFIED;
            }
        }
        return UNSPECIFIED;
    }
    public String getTitle() {
        return title;
    }
}
