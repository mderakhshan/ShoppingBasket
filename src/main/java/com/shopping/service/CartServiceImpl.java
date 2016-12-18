package com.shopping.service;

import com.shopping.dao.MenuDao;
import com.shopping.dao.SpecialOffersDao;
import com.shopping.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Locale;

import static com.shopping.util.Utils.BigDecimal2LocalCurrencyString;

/**
 *  Author Mir on 30/09/2016.
 *
 * This class provides implementation for the method APIs defined by the cart service interface.
 *
 * @see com.shopping.service.CartService
 *
 */
@Service
public class CartServiceImpl implements CartService {

    private Cart cart;
    private final MenuDao menuDao;
    private final SpecialOffersDao specialOffersDao;

    @Autowired
    public CartServiceImpl (MenuDao menuDao, SpecialOffersDao specialOffersDao) {
        this.menuDao = menuDao;
        this.specialOffersDao = specialOffersDao;
        cart = new Cart();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartOrderItem addOrderToCart(String productKey, int quantity) {
        MenuItem menuItem = menuDao.findMenuItemByKey(productKey);
        if (menuItem == null) {
            throw new IllegalArgumentException("Error: Product key " + productKey + " is unknown!");
        }

        CartOrderItem order = new CartOrderItem (menuItem, quantity);

        cart.setCheckedOut(false); // set the checkedout status to false as the cart is being updated

        // Check to see if the given product is already in the cart
        CartOrderItem itemFound = findCartItemByProductName(order.getMenuItem().getProductName());
        if (itemFound != null) { // Found the same product in the cart, so just update quantity existing line item
            return (updateCartItem (itemFound, order.getQuantity()));
        }
        else {
            // add item to the cart
            if (order.getQuantity() <= 0) {
                throw new IllegalArgumentException("Error: Order quantity is less than 1!");
            }
            cart.setSubTotal( cart.getSubTotal().add (order.calcPurchaseCost()));
            cart.getCartItemsList().add(order);
            return (order);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeOrderFromCart(String productKey) {
        Iterator<CartOrderItem> iterator = cart.getCartItemsList().iterator();
        while (iterator.hasNext()) {
            CartOrderItem item = iterator.next();
            if (item.getMenuItem().getProductName().toUpperCase().equals(productKey.toUpperCase())) {
                // update cart subTotal to reflect the removal
                cart.setSubTotal(cart.getSubTotal().subtract(item.calcPurchaseCost()));
                // remove from the cart
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Updates an existing product order in the card with additional quantity
     *
     * @param order the product order to be udated
     * @param additionalQuantity the quantity to be added to this order
     * @return the new CardOrderItem object reflecting the new quantity
     */
    public CartOrderItem updateCartItem(CartOrderItem order, int additionalQuantity) {
        if (order == null) {
            throw new IllegalArgumentException("The Cart item object to be updated is null!");
        }
        int totalQuantity = order.getQuantity() + additionalQuantity;
        if (totalQuantity < 0 ) {
            throw new IllegalArgumentException("The quantity cannot be reduced to a number less than 0!");
        }

        cart.setSubTotal(cart.getSubTotal().subtract(order.calcPurchaseCost()));
        order.setQuantity (totalQuantity);
        cart.setSubTotal(cart.getSubTotal().add (order.calcPurchaseCost()));
        if (totalQuantity == 0) {
            removeOrderFromCart(order.getMenuItem().getProductName());
            return null;
        }
        return order;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CartOrderItem findCartItemByProductName(String productName) {
        if (productName == null) {
            throw new IllegalArgumentException("Error: Invalid null value for argument productNmae!");
        }
        CartOrderItem foundItem = null;
        for (CartOrderItem item : cart.getCartItemsList()) {
            if (item.getMenuItem().getProductName().toUpperCase().equals(productName.toUpperCase())) {
                foundItem = item;
                break;
            }
        }
        return foundItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetCart() {
        cart = new Cart();

    }

    @Override
    public Cart getCart() {
        return cart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String checkout() {
        if (!cart.isCheckedOut()) {
            cart.setDiscountTotal(BigDecimal.ZERO);
            for (CartOrderItem order : cart.getCartItemsList()) {
                String productKey = order.getMenuItem().getProductName();
                SpecialOffer so =
                        specialOffersDao.findMatchingLiveOffer(order.getMenuItem());
                BigDecimal discount = cart.calculateSpecialOfferDiscount(order, so);
                cart.setDiscountTotal(cart.getDiscountTotal().add(discount));
            }
            cart.setCheckedOut(true);
        }
        return cartSummary();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCheckedOut() {
        return cart.isCheckedOut();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSubTotal() {
        return cart.getSubTotal().doubleValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDiscountTotal() {
        checkout(); // special offer discounts are only calculated when cart is checked out
        return cart.getDiscountTotal().doubleValue();
    }

    /**
     * Creates a user-friendly string from the cart content, providing a summary of the Sub-Total,
     * Discount Total (for the special offers) and Total amounts.
     *
     * @return a String containing a summary of the shopping cost
     */
    private String cartSummary() {
        StringBuilder sb = new StringBuilder ();
        sb.append ("Sub-total:\t")
                .append (BigDecimal2LocalCurrencyString(cart.getSubTotal()))
                .append("\n");

        boolean discount = false;
        for (CartOrderItem item : cart.getCartItemsList()) {
            SpecialOffer so = item.getSpecialOffer();
            if (so != null) {
                if (item.getSpecialOfferDiscount() != BigDecimal.ZERO) {
                    discount = true;
                    sb.append(so.getTitle()).append(":\t -")
                            .append(BigDecimal2LocalCurrencyString(item.getSpecialOfferDiscount()))
                            .append("\n");
                }
            }
        }
        if (!discount) {
            sb.append ("(No offers available)\n");
            sb.append ("Total price:\t");
        }
        else {
            sb.append ("Price:\t");
        }
        sb.append (BigDecimal2LocalCurrencyString(cart.getTotalPrice()));

        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public double getTotalPrice() {
        return cart.getTotalPrice().doubleValue();
    };}
