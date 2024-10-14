package org.example.product.productprocess;

import org.example.product.ProductPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ProductProcess {
    private static final Logger logger = LoggerFactory.getLogger(ProductProcess.class);
    private static final String[] SIZE_CLOTHES_CHARTS = new String[]{"xxs", "xs", "s", "m", "l", "xl", "2xl", "xxl", "3xl", "xxxl"};
    private static final String[] SIZE_SHOES_CHARTS = new String[]{"36", "37", "38", "39", "40", "41", "42", "43", "44"};

    /**
     * This method merges two ProductPosition objects if they have the same HS code.
     * If the HS codes are different, it appends "-duplicate" to the article of the second product.
     *
     * @param existedProduct The first ProductPosition object to be merged.
     * @param newProduct     The second ProductPosition object to be merged.
     * @return The merged ProductPosition object. If the HS codes are different, it returns the second object with "-duplicate" appended to the article.
     */
    public static ProductPosition mergeDuplications(ProductPosition existedProduct, ProductPosition newProduct) {

        logger.info("Merging products: {} and {}", existedProduct, newProduct);
        if (isValidToMerge(existedProduct, newProduct)) {
            String newSize = mergeSize(existedProduct.getSizes(), newProduct.getSizes());
            existedProduct.setSizes(newSize);
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

    private static String mergeSize(String sizes1, String sizes2) {
        logger.trace("Merging sizes: {} and {}", sizes1, sizes2);
        List<String> sizesList1 = Arrays.asList(sizes1.split("([- ])"));
        List<String> sizesList2 = Arrays.asList(sizes2.split("([- ])"));

        String[] combinedSizeCharts = Stream.concat(Arrays.stream(SIZE_CLOTHES_CHARTS), Arrays.stream(SIZE_SHOES_CHARTS))
                .toArray(String[]::new);

        List<String> mergedSizes = new ArrayList<>();

        for (String size : combinedSizeCharts) {
            if (sizesList1.contains(size) || sizesList2.contains(size)) {
                mergedSizes.add(size);
            }
        }

        String result = String.join("-", mergedSizes);
        logger.trace("Merged size: {}", result);
        return result;
    }

    private static boolean isValidToMerge(ProductPosition existedProduct, ProductPosition newProduct) {
        return existedProduct.getHsCode().equals(newProduct.getHsCode())
                && existedProduct.getCountryOrigin().equals(newProduct.getCountryOrigin());
    }
}
