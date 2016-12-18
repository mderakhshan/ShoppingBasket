package com.shopping.model;

import java.math.BigDecimal;
import java.util.Date;

public class SpecialOfferStandard extends SpecialOffer{

    public SpecialOfferStandard(String offerTitle, String productName,
                                Date startDate, Date endDate, int percentageDiscount) {
        super (offerTitle, productName, startDate, endDate, percentageDiscount);
    }

    @Override
    public BigDecimal calcDiscount(CartOrderItem order, Cart cart) {
        return order.calcOfferDiscount(order.getQuantity());
    }
}
