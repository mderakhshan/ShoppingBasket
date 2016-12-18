package com.shopping.service;

import com.shopping.model.Cart;
import com.shopping.model.CartOrderItem;

/**
 *
 * Author Mir on 30/09/2016.
 *
 * This is the interface class that provides method APIs for cart related operations.
 *
 */
public interface CartService {

    /**
     * Adds a product order to the cart and returns the CartOderItem object that was added to the cart. It is possible that the
     * product has already been placed in the cart, in which case the order quantity is updated by adding the quantity supplied
     * and the CartOderItem object returned will contain the new total quantity.
     *
     * Note: A negative integer quantity is allowed in oder to reduce quantity if desired. Also, if total quantity is
     * reduced to zero then the order is removed from the Cart and a null CartOrderItem returned.
     *
     *
     * @param productKey the id of the product on the menu
     * @param quantity the quantity of the product being ordered
     * @return the CartOrderItem added, or null if order is removed from the cart because quantity has been reduced to 0
     * a null is returned
     */
    CartOrderItem addOrderToCart(String productKey, int quantity);

    /**
     * Removes the order item for the given product from the cart
     *
     * @param productKey - The name of the product to be removed from the order list in the cart
     */
    void removeOrderFromCart(String productKey);

    CartOrderItem findCartItemByProductName(String productName);

    /**
     * Resets the Cart by removing all orders from it
     */
    void resetCart();

    /**
     * Reurns the Cart object
     *
     * @return the Cart object
     */
    Cart getCart();
    /**
     * Once shopping is finished, this method should be used to determine to checkout the cart.
     * As part of the checkout process, it should determine any special offer discounts and
     * apply them to the cart
     *
     * @return a user-friendly string providing a summary of the Sub-Total, Discount Total (for the
     * special offers) and Total amounts.
     */
    String checkout ();

    /**
     * Indicates if the cart has been checked out or not
     *
     * @return boolean true if checked out, false otherwise
     */
    boolean isCheckedOut();

    /**
     * returns cart sub-total (not including special offer discounts)
     *
     *  @return the cart sub-total
     */
    double getSubTotal ();

    /**
     * returns the total discount due to special offers
     *
     * @return the total discount
     */
    double getDiscountTotal ();

    /**
     * Total Cart price
     *
     * @return the total cost
     */
    double getTotalPrice();
}