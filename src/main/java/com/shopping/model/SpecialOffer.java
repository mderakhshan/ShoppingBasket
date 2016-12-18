package com.shopping.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author Mir on 30/09/2016.
 *
 * The following class provides support for the following types of special offers for a product on the
 * menu. For a given product, say Apples, the following kinds of offers are supported:
 *
 *  STANDARD: Apples 10% off for every bag
 *         Defined by setting offerType=STANDARD, percentageDiscount=10, offerCondition=null
 *
 *  CONIDITIONAL: "Apples discounted at 20% for every 2 bags of Carrots" is defined as:
 *              productName=Apples offerType=CONDITIONAL, percentageDiscount=20, offerCondition=(Carrots,2)
 *
 *  Note: Offers like "Buy X get one free" or "Buy X+1 for the price of X" also fall in the category
 *        of CONDITIONAL and are supported. For example "Buy two get the third free"
 *        or "Buy three for the price of two" may be defined as:
 *              productName=Apples offerType=CONDITIONAL, percentageDiscount=100, offerCondition=(Apples,3)
 *
 */
public abstract class SpecialOffer {
    private String title;               // title of the offer
    private Date startDate;             // date the offer starts, if null then offer starts now
    private Date endDate;               // date the offer expires, if null then no end date
    private String productName;         // name of the product to which the special offer applies for discounting
    private int percentageDiscount;     // percentage discount expressed an integer between 0 and 100

    public SpecialOffer(String offerTitle, String productName,
                        Date startDate, Date endDate, int percentageDiscount) {
        this.title = offerTitle;
        this.productName = productName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percentageDiscount = percentageDiscount;
    }

    public abstract BigDecimal calcDiscount (CartOrderItem order, Cart cart);

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SpecialOffer)) {
            return false;
        }
        SpecialOffer item = (SpecialOffer) o;
        return this.productName.equals(item.productName) &&
                this.getStartDate().equals(item.getStartDate()) &&
                this.getEndDate().equals (item.getEndDate());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + productName.hashCode();
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getEndDate().hashCode();
        return result;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }
    public String getProductName() {
        return productName;
    }
    public int getPercentageDiscount() { return percentageDiscount; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
}
