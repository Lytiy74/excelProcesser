package org.example.Product.ProductProcess.Size;

import org.example.Product.Gender;
import org.example.Product.ProductPosition;

public class SizeProcessImpl implements SizeProcess{

    @Override
    public String generateSize(ProductPosition productPosition) {
        String productName = productPosition.getProductName();
        Gender gender = productPosition.getGender();
        int quantity = productPosition.getQuantity();
        String composition = "";

        return "";
    }

    @Override
    public String convertSizeCharts(String size) {
        return "";
    }
}
