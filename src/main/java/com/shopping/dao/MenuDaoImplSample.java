package com.shopping.dao;

import com.shopping.model.MenuItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Author Mir on 30/09/2016.
 *
 * This is to serve as an example implementation of the interface MenuDao. It sets up the menu data
 * using hard-coded sample data. A proper implementation should read this data from a data source such as
 * a XML file or a database.
 *
 */
@Service
public class MenuDaoImplSample implements MenuDao {

    private static final String SETUP_ERROR_MSG =
            "The Menu object is not set up. Use the setup service povided to set it up first!";

    private Map<String, MenuItem> menu;

    // Set up the menu on bean construction
    public MenuDaoImplSample () {
        setupMenu ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMenuItem (String productKey, MenuItem item) {
        if (menu == null) {
            throw new RuntimeException (SETUP_ERROR_MSG);
        }
        if (StringUtils.isEmpty(productKey)) {
            throw new IllegalArgumentException ("The product Name to be added to the menu is null!");
        }
        // check if there already is a menu item with the same product name and throw an exception if the case
        String nameUppercase = productKey.toUpperCase();
        if (menu.containsKey(nameUppercase)) {
            throw new IllegalArgumentException ("Error when adding a product to the menu: " +
                    "found duplicate productName when processing menu item " + item);
        } else {
            menu.put(nameUppercase, item);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItem findMenuItemByKey(String productKey) {
        if (menu == null) {
            throw new RuntimeException (SETUP_ERROR_MSG);
        }
        if (StringUtils.isEmpty(productKey)) {
            return null;
        }
        return menu.get(productKey.toUpperCase());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getProductNames() {
        String[] names = new String[menu.size()];
        int i = 0;
        for (Map.Entry<String, MenuItem> entry : menu.entrySet()) {
            names [i++] = entry.getValue().getProductName();
        }
        return names;
    }

    /**
     *  Sets up sample menu data
     */
    public void setupMenu () {
        this.menu = new HashMap<>();
        addMenuItem ("SOUP", new MenuItem ("Soup", 0.65, "price is per tin"));
        addMenuItem ("BREAD", new MenuItem ("Bread", 0.80, "price is per loaf"));
        addMenuItem ("MILK", new MenuItem ("Milk", 1.30, "price is per bottle"));
        addMenuItem ("CHEESE", new MenuItem ("Cheese", 1.30, "price is per pack"));
        addMenuItem ("APPLES", new MenuItem ("Apples", 1.00, "price is per bag"));
    }
}
