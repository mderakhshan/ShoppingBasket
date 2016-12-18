package com.shopping.model;

/**
 * Author Mir on 30/09/2016.
 *
 * The class used for representing a product purchase condition for a special offer, e.g. buy 2 tins of soup and
 * get a loaf of bread half price
 *
 */
public class OfferCondition {
    String productName;
    int qualifyingQuantiy;

    public OfferCondition(String productName, int qualifyingQuantiy) {
        this.productName = productName;
        this.qualifyingQuantiy = qualifyingQuantiy;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQualifyingQuantiy() {
        return qualifyingQuantiy;
    }

    public void setQualifyingQuantiy(int qualifyingQuantiy) {
        this.qualifyingQuantiy = qualifyingQuantiy;
    }
}
