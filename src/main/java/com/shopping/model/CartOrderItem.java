package com.shopping.model;

import java.math.BigDecimal;

import static com.shopping.util.Utils.roundUpTwoDecimalPlaces;

/**
 * Author Mir on 30/09/2016.
 *
 * This is the class for a line item in the cart representing a product order
 *
 */
public class CartOrderItem {
    // the item on the menu that is ordered
    private MenuItem menuItem;

    // quantity of the order
    private int quantity;

    // the special offer applicable to this order item. This is determined at the time of
    // cart checkout when special offers are processed against all the purchases in the cart.
    // The reson for this is that special offers may be conditional on other purchases in
    // the cart.
    private SpecialOffer specialOffer;
    private BigDecimal specialOfferDiscount;

    public CartOrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.specialOffer = null;
        specialOfferDiscount = BigDecimal.ZERO;
    }

    public BigDecimal calcPurchaseCost() { // order line item cost before applying discount
        BigDecimal cost = new BigDecimal(menuItem.getPrice());
        cost = cost.multiply(new BigDecimal(quantity));
        return roundUpTwoDecimalPlaces(cost);
    }

    /**
     *
     *
     * @return the total discount amount to be applied in double.
     */
    /**
     * Calculates the total special offer discount amount to be applied to the product purchase.
     * This discount will be deducted from sub-total on checkout
     *
     * @param discountQuantity the quantity to be discounted at
     * @return the total discount amount for the given quantity in double.
     */
    public BigDecimal calcOfferDiscount(int discountQuantity) {
        if (this.specialOffer != null) {
            int actualDiscountQuantity;
            if (this.quantity < discountQuantity) {
                actualDiscountQuantity = this.quantity; // can only discount up to the quantity in the cart
            }
            else {
                actualDiscountQuantity = discountQuantity;
            }

            BigDecimal discountB =  new BigDecimal(specialOffer.getPercentageDiscount());
            BigDecimal hundredB = new BigDecimal(100);
            discountB = discountB.divide(hundredB);
            BigDecimal discountQuantityB = new BigDecimal(actualDiscountQuantity);
            BigDecimal priceB = new BigDecimal(menuItem.getPrice());
            discountB = discountB.multiply(discountQuantityB).multiply(priceB);
            discountB = roundUpTwoDecimalPlaces(discountB);
            return discountB;
        }
        else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CartOrderItem)) {
            return false;
        }
        CartOrderItem item = (CartOrderItem) o;
        return this.menuItem.equals(item.menuItem) && this.quantity == item.quantity;
    }


    // Getters and Setters
    public MenuItem getMenuItem() {
        return menuItem;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public SpecialOffer getSpecialOffer() {
        return specialOffer;
    }
    public void setSpecialOffer(SpecialOffer specialOffer) {
        this.specialOffer = specialOffer;
    }
    public BigDecimal getSpecialOfferDiscount() { return specialOfferDiscount; }
    public void setSpecialOfferDiscount(BigDecimal specialOfferDiscount) { this.specialOfferDiscount = specialOfferDiscount; }
}