package com.shopping.dao;

import com.shopping.model.MenuItem;
import com.shopping.model.SpecialOffer;

/**
 * Author Mir on 30/09/2016.
 */
public interface SpecialOffersDao {

    /**
     * Finds the special offer identified by the given product key
     *
     * @param productKey the product key used to identify the special offer
     * @return the special offer object
     */
    SpecialOffer findOfferByProductKey(String productKey);

    /**
     * This determines if for a given product on the menu (specified by parameter MenuItem), there exists
     * a special offer defined for that product.
     *
     * The following checks are used to find a matching special offer:
     *      i) the special offer is associated with the product being purchased
     *      ii) the special offer is active, i.e. it has has a start date of before "now" and an end date of after "now"
     *
     * @param menuItem the menu item being ordered
     * @return the SpecialOffer object found
     */
    SpecialOffer findMatchingLiveOffer(MenuItem menuItem);

    /**
     * Adds a special offer to the list of special offers
     *
     * @param productKey the product key to be used to identify the special offer
     * @param so the special offer to be addedd
     */
    void addOffer(String productKey, SpecialOffer so);
}
