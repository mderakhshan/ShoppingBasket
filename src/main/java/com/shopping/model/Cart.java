package com.shopping.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.shopping.util.Utils.roundUpTwoDecimalPlaces;

/**
 * Author Mir on 30/09/2016.
 *
 * The class for the shopping cart.
 *
 */

public class Cart {
    // the list used to track the product orders in the cart
    private List<CartOrderItem> cartItemsList = new ArrayList<>();

    // flag to indicate if the cart is checked out
    private boolean checkedOut;

    // total cost of the items in the cart before offer discounts (calculated as items are placed in the cart)
    private BigDecimal subTotal;

    // total amount of offer discounts from special offers (calculated on checkout only)
    private BigDecimal discountTotal;

    public Cart() {
        cartItemsList = new ArrayList<>();
        checkedOut = false;
        subTotal = BigDecimal.ZERO;
        discountTotal = BigDecimal.ZERO;
    }

    /**
     * Calculates discount from the given supplied special offer, if any, for the given order item
     * in the cart
     *
     * @param order the order line item in the cart
     * @param offer  the special offer to be applied to the order if deemed applicable
     * @return the discount
     */
    public BigDecimal calculateSpecialOfferDiscount(CartOrderItem order, SpecialOffer offer) {
        if (order == null) {
            throw new IllegalArgumentException("argument order is null!");
        }
        if (offer == null) { // there is no special offer associated with the order
            return BigDecimal.ZERO;
        }
        order.setSpecialOffer(offer);
        BigDecimal discount = offer.calcDiscount (order, this);
        order.setSpecialOfferDiscount(discount);

        return discount;
    }

    /**
     * Returns the number of different products placed in the cart
     *
     * @return the no of different products in the cart
     */
    public int noOfProducts () {
        return (cartItemsList == null) ? 0 : cartItemsList.size();
    }

    //
    // Getters and Setters
    //

    public BigDecimal getSubTotal() {
        return subTotal;
    }
    public void setSubTotal(BigDecimal subTotal) {  this.subTotal = subTotal; }

    public BigDecimal getDiscountTotal() {
        return roundUpTwoDecimalPlaces(discountTotal);
    }
    public void setDiscountTotal(BigDecimal discountTotal) { this.discountTotal = discountTotal; }

    public BigDecimal getTotalPrice() {
        return roundUpTwoDecimalPlaces(subTotal.subtract(discountTotal));
    }

    public List<CartOrderItem> getCartItemsList() { return cartItemsList; }
    public boolean isCheckedOut() { return checkedOut; }

    public void setCheckedOut(boolean checkedOut) { this.checkedOut = checkedOut; }
}