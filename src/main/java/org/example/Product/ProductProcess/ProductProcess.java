package org.example.Product.ProductProcess;

import org.example.Product.ProductPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductProcess {
    private static final Logger logger = LoggerFactory.getLogger(ProductProcess.class);
    /**
     * This method merges two ProductPosition objects if they have the same HS code.
     * If the HS codes are different, it appends "-duplicate" to the article of the second product.
     *
     * @param product1 The first ProductPosition object to be merged.
     * @param product2 The second ProductPosition object to be merged.
     * @return The merged ProductPosition object. If the HS codes are different, it returns the second object with "-duplicate" appended to the article.
     */
    public ProductPosition mergeDuplications(ProductPosition product1, ProductPosition product2) {
        logger.info("Merging products: {} and {}", product1, product2);
        if (product1.getHsCode().equals(product2.getHsCode())
                && product1.getCountryOrigin().equals(product2.getCountryOrigin())) {
            product1.setQuantity(product1.getQuantity() + product2.getQuantity());
            product1.setBruttoWeight(product1.getBruttoWeight() + product2.getBruttoWeight());
            product1.setPrice(product1.getPrice() + product2.getPrice());
            logger.info("Merged product: {}", product1);
            return product1;
        }
        logger.info("Duplications found,cant merge, appending '-duplicate' to article of product2: {}", product2);
        product2.setArticle(product2.getArticle() + "-duplicate");
        return product2;
    }
}
