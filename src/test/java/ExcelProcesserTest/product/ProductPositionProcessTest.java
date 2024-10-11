package ExcelProcesserTest.product;

import org.example.product.Gender;
import org.example.product.ProductPosition;
import org.example.product.productprocess.ProductProcess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductPositionProcessTest {

    @Test
    void mergeTwoProductWithAllSameFields(){
        ProductPosition.Builder builder1 = ProductPosition.newBuilder();
        builder1.setArticle("article1")
                .setProductName("name1")
                .setSizes("xs")
                .setTradeMark("tradeMark1")
                .setCountryOrigin("country1")
                .setQuantity(1)
                .setComposition("100% cotton")
                .setGender(Gender.UNISEX)
                .setHsCode("hscode1")
                .setBruttoWeight(1)
                .setPrice(1);

        ProductPosition productPosition1 = builder1.build();
        ProductPosition.Builder builder2 = ProductPosition.newBuilder();
        builder2.setArticle("article1")
                .setProductName("name1")
                .setSizes("xs")
                .setTradeMark("tradeMark1")
                .setCountryOrigin("country1")
                .setQuantity(1)
                .setComposition("100% cotton")
                .setGender(Gender.UNISEX)
                .setHsCode("hscode1")
                .setBruttoWeight(1)
                .setPrice(1);
        ProductPosition productPosition2 = builder2.build();

        ProductPosition.Builder builder3 = ProductPosition.newBuilder();
        builder3.setArticle("article1")
                .setProductName("name1")
                .setSizes("xs")
                .setTradeMark("tradeMark1")
                .setCountryOrigin("country1")
                .setQuantity(2)
                .setComposition("100% cotton")
                .setGender(Gender.UNISEX)
                .setHsCode("hscode1")
                .setBruttoWeight(2)
                .setPrice(2);
        ProductPosition expected = builder3.build();

        ProductPosition result = ProductProcess.mergeDuplications(productPosition1, productPosition2);
        Assertions.assertEquals(expected.getArticle(), result.getArticle(), "Article should match");
        Assertions.assertEquals(expected.getProductName(), result.getProductName(), "Product name should match");
        Assertions.assertEquals(expected.getSizes(), result.getSizes(), "Sizes should match");
        Assertions.assertEquals(expected.getTradeMark(), result.getTradeMark(), "Trade mark should match");
        Assertions.assertEquals(expected.getCountryOrigin(), result.getCountryOrigin(), "Country of origin should match");
        Assertions.assertEquals(expected.getQuantity(), result.getQuantity(), "Quantity should be merged");
        Assertions.assertEquals(expected.getComposition(), result.getComposition(), "Composition should match");
        Assertions.assertEquals(expected.getGender(), result.getGender(), "Gender should match");
        Assertions.assertEquals(expected.getHsCode(), result.getHsCode(), "HS Code should match");
        Assertions.assertEquals(expected.getBruttoWeight(), result.getBruttoWeight(), "Brutto weight should be merged");
        Assertions.assertEquals(expected.getPrice(), result.getPrice(), "Price should be merged");
    }

    @Test
    void mergeTwoProductWithAllSameFieldsButDifferentProductName(){
        ProductPosition.Builder builder1 = ProductPosition.newBuilder();
        builder1.setArticle("article1")
                .setProductName("name1")
                .setSizes("xs")
                .setTradeMark("tradeMark1")
                .setCountryOrigin("country1")
                .setQuantity(1)
                .setComposition("100% cotton")
                .setGender(Gender.UNISEX)
                .setHsCode("hscode1")
                .setBruttoWeight(1)
                .setPrice(1);

        ProductPosition productPosition1 = builder1.build();
        ProductPosition.Builder builder2 = ProductPosition.newBuilder();
        builder2.setArticle("article1")
                .setProductName("name2")
                .setSizes("xs")
                .setTradeMark("tradeMark1")
                .setCountryOrigin("country1")
                .setQuantity(1)
                .setComposition("100% cotton")
                .setGender(Gender.UNISEX)
                .setHsCode("hscode1")
                .setBruttoWeight(1)
                .setPrice(1);
        ProductPosition productPosition2 = builder2.build();

        ProductPosition.Builder builder3 = ProductPosition.newBuilder();
        builder3.setArticle("article1")
                .setProductName("name1")
                .setSizes("xs")
                .setTradeMark("tradeMark1")
                .setCountryOrigin("country1")
                .setQuantity(2)
                .setComposition("100% cotton")
                .setGender(Gender.UNISEX)
                .setHsCode("hscode1")
                .setBruttoWeight(2)
                .setPrice(2);
        ProductPosition expected = builder3.build();

        ProductPosition result = ProductProcess.mergeDuplications(productPosition1, productPosition2);
        Assertions.assertEquals(expected.getArticle(), result.getArticle(), "Article should match");
        Assertions.assertEquals(expected.getProductName(), result.getProductName(), "Product name should match");
        Assertions.assertEquals(expected.getSizes(), result.getSizes(), "Sizes should match");
        Assertions.assertEquals(expected.getTradeMark(), result.getTradeMark(), "Trade mark should match");
        Assertions.assertEquals(expected.getCountryOrigin(), result.getCountryOrigin(), "Country of origin should match");
        Assertions.assertEquals(expected.getQuantity(), result.getQuantity(), "Quantity should be merged");
        Assertions.assertEquals(expected.getComposition(), result.getComposition(), "Composition should match");
        Assertions.assertEquals(expected.getGender(), result.getGender(), "Gender should match");
        Assertions.assertEquals(expected.getHsCode(), result.getHsCode(), "HS Code should match");
        Assertions.assertEquals(expected.getBruttoWeight(), result.getBruttoWeight(), "Brutto weight should be merged");
        Assertions.assertEquals(expected.getPrice(), result.getPrice(), "Price should be merged");
    }

    @Test
    void mergeTwoProductWithAllSameFieldsButDifferentSizes(){
        ProductPosition.Builder builder1 = ProductPosition.newBuilder();
        builder1.setArticle("article1")
                .setProductName("name1")
                .setSizes("xs")
                .setTradeMark("tradeMark1")
                .setCountryOrigin("country1")
                .setQuantity(1)
                .setComposition("100% cotton")
                .setGender(Gender.UNISEX)
                .setHsCode("hscode1")
                .setBruttoWeight(1)
                .setPrice(1);

        ProductPosition productPosition1 = builder1.build();
        ProductPosition.Builder builder2 = ProductPosition.newBuilder();
        builder2.setArticle("article1")
                .setProductName("name2")
                .setSizes("s")
                .setTradeMark("tradeMark1")
                .setCountryOrigin("country1")
                .setQuantity(1)
                .setComposition("100% cotton")
                .setGender(Gender.UNISEX)
                .setHsCode("hscode1")
                .setBruttoWeight(1)
                .setPrice(1);
        ProductPosition productPosition2 = builder2.build();

        ProductPosition.Builder builder3 = ProductPosition.newBuilder();
        builder3.setArticle("article1")
                .setProductName("name1")
                .setSizes("xs")
                .setTradeMark("tradeMark1")
                .setCountryOrigin("country1")
                .setQuantity(2)
                .setComposition("100% cotton")
                .setGender(Gender.UNISEX)
                .setHsCode("hscode1")
                .setBruttoWeight(2)
                .setPrice(2);
        ProductPosition expected = builder3.build();

        ProductPosition result = ProductProcess.mergeDuplications(productPosition1, productPosition2);
        Assertions.assertEquals(expected.getArticle(), result.getArticle(), "Article should match");
        Assertions.assertEquals(expected.getProductName(), result.getProductName(), "Product name should match");
        Assertions.assertEquals(expected.getSizes(), result.getSizes(), "Sizes should match");
        Assertions.assertEquals(expected.getTradeMark(), result.getTradeMark(), "Trade mark should match");
        Assertions.assertEquals(expected.getCountryOrigin(), result.getCountryOrigin(), "Country of origin should match");
        Assertions.assertEquals(expected.getQuantity(), result.getQuantity(), "Quantity should be merged");
        Assertions.assertEquals(expected.getComposition(), result.getComposition(), "Composition should match");
        Assertions.assertEquals(expected.getGender(), result.getGender(), "Gender should match");
        Assertions.assertEquals(expected.getHsCode(), result.getHsCode(), "HS Code should match");
        Assertions.assertEquals(expected.getBruttoWeight(), result.getBruttoWeight(), "Brutto weight should be merged");
        Assertions.assertEquals(expected.getPrice(), result.getPrice(), "Price should be merged");
    }
}
