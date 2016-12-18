package com.shopping.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Author Mir on 30/09/2016.
 * *
 * The class represents a product item in the shopping menu
 *
 */
public class MenuItem {
    private String productName;
    private double price;
    private String info;

    public MenuItem( String productName, double price, String productInfo) {
        if (StringUtils.isEmpty(productName)) {
            throw new IllegalArgumentException("MenuItem productName is null!");
        }
        this.productName = productName;
        this.price = price;
        this.info = productInfo;
    }

    @Override
    public String toString () {
        return "{productName=\"" + productName + "\"" + " price=\"" + price + "\"" +  " info=\"" + info + "\"}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MenuItem)) {
            return false;
        }
        MenuItem item = (MenuItem) o;
        return this.productName.equals(item.productName) && this.price == item.price && this.info.equals(item.info);
    }

    // Getters and Setters
    public String getProductName() {
        return productName;
    }
    public double getPrice() { return price;}
    public String getInfo () {
        return info;
    }
}