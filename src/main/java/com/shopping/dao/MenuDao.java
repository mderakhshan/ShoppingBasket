package com.shopping.dao;

import com.shopping.model.MenuItem;

/**
 * Author Mir on 30/09/2016.
 *
 * Dao methods for Menu
 */
public interface MenuDao {

   /**
    * Adds a product item to the menu with the given product key as its unique id
    *
    * @param productKey is the unique id for the product item to be added
    * @param item the item to be added
    */
    void addMenuItem(String productKey, MenuItem item);

    /**
     * Finds and returns the menu item identified by the given product key
     *
     * @param productKey the key to be used to find the menu Item
     * @return the MenuItem object matching the product
     */
    MenuItem findMenuItemByKey (String productKey);

    /**
     * Returns a String array of the product names on the menu
     *
     * @return the String array of product names
     */
    String[] getProductNames ();
}
