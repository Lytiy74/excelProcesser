package org.example.Product;

import java.util.Objects;

/**
 * This class represents a product position in an inventory or order.
 * It contains various attributes such as article, product name, sizes, trademark, country of origin,
 * quantity, composition, gender, HS code, brutto weight, and price.
 * <p>
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
    public ProductPosition() {
    }

    /**
     * Constructor for ProductPosition.
     * Initializes all attributes with the provided values.
     *
     * @param article       the article of the product
     * @param productName   the name of the product
     * @param sizes         the sizes of the product
     * @param tradeMark     the trademark of the product
     * @param countryOrigin the country of origin of the product
     * @param quantity      the quantity of the product
     * @param composition   the composition of the product
     * @param gender        the gender of the product
     * @param hsCode        the HS code of the product
     * @param bruttoWeight  the brutto weight of the product
     * @param price         the price of the product
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
    public String getArticle() {
        return article;
    }

    public String getProductName() {
        return productName;
    }

    public String getSizes() {
        return sizes;
    }

    public String getTradeMark() {
        return tradeMark;
    }

    public String getCountryOrigin() {
        return countryOrigin;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getComposition() {
        return composition;
    }

    public Gender getGender() {
        return gender;
    }

    public String getHsCode() {
        return hsCode;
    }

    public double getBruttoWeight() {
        return bruttoWeight;
    }

    public double getPrice() {
        return price;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public void setTradeMark(String tradeMark) {
        this.tradeMark = tradeMark;
    }

    public void setCountryOrigin(String countryOrigin) {
        this.countryOrigin = countryOrigin;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }

    public void setBruttoWeight(double bruttoWeight) {
        this.bruttoWeight = bruttoWeight;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static Builder newBuilder() {
        return new ProductPosition().new Builder();
    }

    // builder pattern for creating instances of ProductPosition
    public class Builder {
        private Builder() {

        }

        // builder methods for setting attributes
        public Builder setArticle(String article) {
            ProductPosition.this.article = article;
            return this;
        }

        public Builder setProductName(String productName) {
            ProductPosition.this.productName = productName;
            return this;
        }

        public Builder setSizes(String sizes) {
            ProductPosition.this.sizes = sizes;
            return this;
        }

        public Builder setTradeMark(String tradeMark) {
            ProductPosition.this.tradeMark = tradeMark;
            return this;
        }

        public Builder setCountryOrigin(String countryOrigin) {
            ProductPosition.this.countryOrigin = countryOrigin;
            return this;
        }

        public Builder setQuantity(int quantity) {
            ProductPosition.this.quantity = quantity;
            return this;
        }

        public Builder setComposition(String composition) {
            ProductPosition.this.composition = composition;
            return this;
        }

        public Builder setGender(Gender gender) {
            ProductPosition.this.gender = gender;
            return this;
        }

        public Builder setHsCode(String hsCode) {
            ProductPosition.this.hsCode = hsCode;
            return this;
        }

        public Builder setBruttoWeight(double bruttoWeight) {
            ProductPosition.this.bruttoWeight = bruttoWeight;
            return this;
        }

        public Builder setPrice(double price) {
            ProductPosition.this.price = price;
            return this;
        }

        public ProductPosition build() {
            return ProductPosition.this;
        }
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPosition that = (ProductPosition) o;
        return quantity == that.quantity && Double.compare(bruttoWeight, that.bruttoWeight) == 0
                && Double.compare(price, that.price) == 0
                && Objects.equals(article, that.article)
                && Objects.equals(productName, that.productName)
                && Objects.equals(sizes, that.sizes)
                && Objects.equals(tradeMark, that.tradeMark)
                && Objects.equals(countryOrigin, that.countryOrigin)
                && Objects.equals(composition, that.composition)
                && gender == that.gender
                && Objects.equals(hsCode, that.hsCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, productName, sizes, tradeMark, countryOrigin, quantity, composition, gender, hsCode, bruttoWeight, price);
    }
}
