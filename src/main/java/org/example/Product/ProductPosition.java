package org.example.Product;

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

    public ProductPosition(){};

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

    public static Builder newBuilder(){
        return new ProductPosition().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder setArticle(String article) {
            ProductPosition.this.article = article;
            return this;
        }

        public Builder setProductName(String productName){
            ProductPosition.this.productName = productName;
            return this;
        }

        public Builder setSizes(String sizes){
            ProductPosition.this.sizes = sizes;
            return this;
        }
        public Builder setTradeMark(String tradeMark){
            ProductPosition.this.tradeMark = tradeMark;
            return this;
        }
        public Builder setCountryOrigin(String countryOrigin){
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
            return  ProductPosition.this;
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
}
