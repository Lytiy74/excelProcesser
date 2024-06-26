package org.example.Product;

/**
 * Enum representing different gender types.
 */
public enum Gender {

    MALE("чол"),

    FEMALE("жін"),

    BOYS("хлопчаче"),

    GIRLS("дівчаче"),

    UNISEX("унісекс"),

    UNSPECIFIED("N/A");

    private String title;

    /**
     * Constructor for Gender enum.
     * @param title title of the gender
     */
    Gender(String title){
        this.title = title;
    }

    /**
     * Converts a string to a Gender enum.
     * @param genderValue string to convert
     * @return corresponding Gender enum or UNSPECIFIED if not found
     */
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

    /**
     * Returns the title of the gender.
     * @return title of the gender
     */
    public String getTitle() {
        return title;
    }
}
