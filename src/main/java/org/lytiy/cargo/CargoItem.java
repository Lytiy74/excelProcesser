package org.lytiy.cargo;

import org.lytiy.cargo.product.ProductPosition;

import java.util.HashMap;

public class CargoItem {
    private HashMap<String, ProductPosition> productPositionHashMap;
    private double bruttoWeight;
    private int parcelsQuantity;
    private double invoiceSummary;

    public CargoItem(HashMap<String, ProductPosition> productPositionHashMap, double bruttoWeight, int parcelsQuantity, double invoiceSummary) {
        this.productPositionHashMap = productPositionHashMap;
        this.bruttoWeight = bruttoWeight;
        this.parcelsQuantity = parcelsQuantity;
        this.invoiceSummary = invoiceSummary;
    }


    public class Builder{
        private Builder(){};

        public Builder setProductsPositionMap(HashMap<String , ProductPosition> productPositionHashMap){
            CargoItem.this.productPositionHashMap = productPositionHashMap;
            return this;
        }

        public Builder setBruttoWeight(double bruttoWeight){
            CargoItem.this.bruttoWeight = bruttoWeight;
            return this;
        }

        public Builder setParcelsQuantity(int parcelsQuantity){
            CargoItem.this.parcelsQuantity = parcelsQuantity;
            return this;
        }

        public Builder setInvoiceSummary(double invoiceSummary){
            CargoItem.this.invoiceSummary = invoiceSummary;
            return this;
        }

        public CargoItem build(){
            return CargoItem.this;
        }
    }
}
