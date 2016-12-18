package com.shopping.service.testdata;

import com.shopping.dao.MenuDao;
import com.shopping.dao.MenuDaoImplSample;

/**
 * Created by Mir on 29/09/2016.
 */
public class MenuTestData extends TestData {

    // Setups a menu from the test data
   public static MenuDao setup () {
       MenuDao menuDao = new MenuDaoImplSample();
       menuDao.addMenuItem (carrotsProductId, carrots);
       menuDao.addMenuItem (onionsProductId, onions);
       menuDao.addMenuItem (potatoesProductId, potatoes);
       menuDao.addMenuItem (satsumasProductId, satsumas);
       menuDao.addMenuItem (parsleyProductId, parsley);
       menuDao.addMenuItem (cabbagesProductId, cabbages);
       return menuDao;
    }

    // Sets up an empty menu
    public static MenuDao setupWithNoTestData() {
        MenuDao menuDao = new MenuDaoImplSample();
        return menuDao;
    }
}
