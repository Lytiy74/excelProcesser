import org.example.Product.Gender;
import org.example.Product.ProductPosition;
import org.example.Product.ProductProcess.ProductProcess;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProductProcessTest {
    static ProductProcess productProcess;
    static ProductPosition productPosition1;
    static ProductPosition productPosition2;
    static ProductPosition productPosition3;

    @BeforeAll
    static void setUp() {
        productProcess = new ProductProcess();
        ProductPosition firstProductPosition = ProductPosition.newBuilder()
                .setArticle("test-article")
                .setProductName("штани")
                .setSizes(null)
                .setTradeMark("test-trademark")
                .setCountryOrigin("test-country")
                .setQuantity(10)
                .setComposition("test-composition")
                .setGender(Gender.UNISEX)
                .setHsCode("test-HSCODE")
                .setBruttoWeight(10)
                .setPrice(100)
                .build();
        ProductPosition secondProductPosition = ProductPosition.newBuilder()
                .setArticle("test-article")
                .setProductName("штани")
                .setSizes(null)
                .setTradeMark("test-trademark")
                .setCountryOrigin("test-country")
                .setQuantity(15)
                .setComposition("test-composition")
                .setGender(Gender.UNISEX)
                .setHsCode("test-HSCODE")
                .setBruttoWeight(15)
                .setPrice(150)
                .build();
        productPosition2 = secondProductPosition;
        productPosition1 = firstProductPosition;

    }

    @Test
    void testSameMergeProductPositions() {
        ProductPosition expected = ProductPosition.newBuilder()
                .setArticle("test-article")
                .setProductName("штани")
                .setSizes(null)
                .setTradeMark("test-trademark")
                .setCountryOrigin("test-country")
                .setQuantity(25)
                .setComposition("test-composition")
                .setGender(Gender.UNISEX)
                .setHsCode("test-HSCODE")
                .setBruttoWeight(25)
                .setPrice(250)
                .build();

        ProductPosition merged = productProcess.mergeDuplications(productPosition1, productPosition2);
        Assertions.assertEquals(expected, merged);
    }

    @Test
    void testDifferentMergeProductPositionsWithSameArticle() {
        ProductPosition differentProductPosition = ProductPosition.newBuilder()
               .setArticle("test-article")
               .setProductName("штани")
               .setSizes(null)
               .setTradeMark("test-trademark")
               .setCountryOrigin("test-different-country")
               .setQuantity(5)
               .setComposition("test-composition")
               .setGender(Gender.UNISEX)
               .setHsCode("test-HSCODE")
               .setBruttoWeight(5)
               .setPrice(50)
               .build();

        ProductPosition expected = ProductPosition.newBuilder()
                .setArticle("test-article-duplicate")
                .setProductName("штани")
                .setSizes(null)
                .setTradeMark("test-trademark")
                .setCountryOrigin("test-different-country")
                .setQuantity(5)
                .setComposition("test-composition")
                .setGender(Gender.UNISEX)
                .setHsCode("test-HSCODE")
                .setBruttoWeight(5)
                .setPrice(50)
                .build();

        ProductPosition merged = productProcess.mergeDuplications(productPosition1, differentProductPosition);
        Assertions.assertEquals(expected, merged);
    }

}
