package org.example.Product;

/**
 * This class represents a product position in an inventory or order.
 * It contains various attributes such as article, product name, sizes, trademark, country of origin,
 * quantity, composition, gender, HS code, brutto weight, and price.
 *
 * The class also provides a builder pattern for creating instances of ProductPosition.
 */
public class ProductPosition {
    private String article;
    private String productName;
    private String sizes;
    private String tradeMark;
    private String countryOrigin;
    private int quantity;
    private String composition;
    private Gender gender;
    private String hsCode;
    private double bruttoWeight;
    private double price;

    /**
     * Default constructor for ProductPosition.
     * Initializes all attributes to default values.
     */
    public ProductPosition(){};

    /**
     * Constructor for ProductPosition.
     * Initializes all attributes with the provided values.
     *
     * @param article the article of the product
     * @param productName the name of the product
     * @param sizes the sizes of the product
     * @param tradeMark the trademark of the product
     * @param countryOrigin the country of origin of the product
     * @param quantity the quantity of the product
     * @param composition the composition of the product
     * @param gender the gender of the product
     * @param hsCode the HS code of the product
     * @param bruttoWeight the brutto weight of the product
     * @param price the price of the product
     */
    public ProductPosition(String article, String productName,
                           String sizes, String tradeMark,
                           String countryOrigin, int quantity,
                           String composition, Gender gender,
                           String hsCode, double bruttoWeight, double price) {
        this.article = article;
        this.productName = productName;
        this.sizes = sizes;
        this.tradeMark = tradeMark;
        this.countryOrigin = countryOrigin;
        this.quantity = quantity;
        this.composition = composition;
        this.gender = gender;
        this.hsCode = hsCode;
        this.bruttoWeight = bruttoWeight;
        this.price = price;
    }

    // getters and setters for all attributes

    // builder pattern for creating instances of ProductPosition
    public static Builder newBuilder(){
        return new ProductPosition().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        // builder methods for setting attributes

        public Builder setArticle(String article) {
            ProductPosition.this.article = article;
            return this;
        }

        // other builder methods for setting attributes

        public ProductPosition build() {
            return  ProductPosition.this;
        }
    }

    /**
     * Returns a string representation of the ProductPosition object.
     *
     * @return a string representation of the ProductPosition object
     */
    @Override
    public String toString() {
        return "ProductPosition{" +
                "article='" + article + '\'' +
                ", productName='" + productName + '\'' +
                ", sizes='" + sizes + '\'' +
                ", tradeMark='" + tradeMark + '\'' +
                ", countryOrigin='" + countryOrigin + '\'' +
                ", quantity=" + quantity +
                ", composition='" + composition + '\'' +
                ", gender=" + gender +
                ", hsCode='" + hsCode + '\'' +
                ", bruttoWeight=" + bruttoWeight +
                ", price=" + price +
                '}';
    }
}
