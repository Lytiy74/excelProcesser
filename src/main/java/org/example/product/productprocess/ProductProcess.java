package org.example.product.productprocess;

import org.example.product.ProductPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductProcess {
    private static final Logger logger = LoggerFactory.getLogger(ProductProcess.class);
    /**
     * This method merges two ProductPosition objects if they have the same HS code.
     * If the HS codes are different, it appends "-duplicate" to the article of the second product.
     *
     * @param existedProduct The first ProductPosition object to be merged.
     * @param newProduct The second ProductPosition object to be merged.
     * @return The merged ProductPosition object. If the HS codes are different, it returns the second object with "-duplicate" appended to the article.
     */
    public static ProductPosition mergeDuplications(ProductPosition existedProduct, ProductPosition newProduct) {

        logger.info("Merging products: {} and {}", existedProduct, newProduct);
        if (isValidToMerge(existedProduct, newProduct)) {
            existedProduct.setQuantity(existedProduct.getQuantity() + newProduct.getQuantity());
            existedProduct.setBruttoWeight(existedProduct.getBruttoWeight() + newProduct.getBruttoWeight());
            existedProduct.setPrice(existedProduct.getPrice() + newProduct.getPrice());
            logger.info("Merged product: {}", existedProduct);
            return existedProduct;
        }
        logger.info("Duplications found,cant merge, appending '-duplicate' to article of product2: {}", newProduct);
        newProduct.setArticle(newProduct.getArticle() + "-duplicate");
        return newProduct;
    }

    private static boolean isValidToMerge(ProductPosition existedProduct, ProductPosition newProduct) {
        return existedProduct.getHsCode().equals(newProduct.getHsCode())
                && existedProduct.getCountryOrigin().equals(newProduct.getCountryOrigin());
    }
}
