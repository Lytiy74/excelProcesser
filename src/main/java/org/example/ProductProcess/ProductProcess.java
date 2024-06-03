package org.example.ProductProcess;

import org.example.Product.ProductPosition;

public class ProductProcess {
    public ProductPosition mergeDuplications(ProductPosition product1, ProductPosition product2) {
        if (product1.getHsCode().equals(product2.getHsCode())){
            product1.setQuantity(product1.getQuantity() + product2.getQuantity());
            product1.setBruttoWeight(product1.getBruttoWeight() + product2.getBruttoWeight());
            product1.setPrice(product1.getPrice() + product2.getPrice());
            return product1;
        }
        product2.setArticle(product2.getArticle()+"-duplicate");
        return product2;
    }
}
