package com.shopping.model;

import java.math.BigDecimal;
import java.util.Date;

public class SpecialOfferConditional extends SpecialOffer{

    private OfferCondition offerCondition; // if type is CONDITIONAL, then the offer condition

    public SpecialOfferConditional(String offerTitle, String productName,
                                   Date startDate, Date endDate, int percentageDiscount, OfferCondition offerCondition) {
        super (offerTitle, productName, startDate, endDate, percentageDiscount);
        this.offerCondition = offerCondition;
    }

    @Override
    public BigDecimal calcDiscount(CartOrderItem order, Cart cart) {
        if (offerCondition == null) {
            throw new RuntimeException("Error: Special offer is of type CONDITIONAL but offerCondition is null!");
        }
        BigDecimal discount = BigDecimal.ZERO;
        // Search the cart to see if the offer condition is met
        for (CartOrderItem item : cart.getCartItemsList()) {
            if (item.getMenuItem().getProductName().equals(offerCondition.getProductName()) &&
                    item.getQuantity() >= offerCondition.getQualifyingQuantiy()) {
                // divide the order quantity in the cart by the quantity set by the offer condition
                // in order to determine how many should be discounted. For example, if bought 3 bags
                // of Apples and 5 bags of onions, and the offer condition for Apples specifies 2 bags of onions,
                // then 5/2 gives 2, meaning we should discount 2 of the 3 bags of Apples.
                int quantityTobeDiscounted = item.getQuantity() / offerCondition.getQualifyingQuantiy();
                discount = order.calcOfferDiscount(quantityTobeDiscounted);
                break;
            }
        }
        return discount;
    }
}