package com.shopping.dao;


import com.shopping.model.MenuItem;
import com.shopping.service.testdata.MenuTestData;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Before;
import org.junit.Test;
import static com.shopping.service.testdata.SpecialOffersTestData.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by Mir on 30/09/2016.
 */
public class MenuDaoTest {
    private MenuDao menuDao;

    @Before
    public void setup () throws Exception {
        menuDao = MenuTestData.setup();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMenuItemShouldRaiseExecptionIfItemAlreadyOnMenu () {
        menuDao.addMenuItem (carrotsProductId, carrots);
        menuDao.addMenuItem (carrotsProductId, carrots);
    }

    @Test
    public void addMenuItemShouldWorkOkIfItemNotOnMenu () {
        menuDao = MenuTestData.setupWithNoTestData();
        MenuItem foundItem = menuDao.findMenuItemByKey(carrotsProductId);
        assertThat("Carrots is already on the menu!", foundItem, is(nullValue()));

        menuDao.addMenuItem (carrotsProductId, carrots);
        foundItem = menuDao.findMenuItemByKey(carrotsProductId);
        assertThat ("Carrots is not on the menu!", foundItem, is(notNullValue()));
        assertThat ("Expected Carrots but got something else!", foundItem, equalTo(carrots));
    }

    @Test (expected=IllegalArgumentException.class)
    public void addMenuItemShouldRaiseAnExceptionIfItemIsAlreadyOnMenu () {
        menuDao = MenuTestData.setupWithNoTestData();

        menuDao.addMenuItem (carrotsProductId, carrots);
        assertThat("Carrots is not on the menu!", menuDao.findMenuItemByKey(carrotsProductId), is(notNullValue()));

        menuDao.addMenuItem (carrotsProductId, carrots); // This should raise an exception
    }

    @Test
    public void getProductNamesShouldWorkOk () {
        menuDao = MenuTestData.setup();
        String[] productNames = menuDao.getProductNames();
        assertThat(productNames, is(notNullValue()));
        assertThat ("Carrots is not in the list!", ArrayUtils.contains(productNames, carrotsProductId), is(true));
        assertThat ("Onions is not in the list!", ArrayUtils.contains(productNames, onionsProductId), is(true));
        assertThat ("Potatoes is not in the list!", ArrayUtils.contains(productNames, potatoesProductId), is(true));
        assertThat ("Pasrley is not in the list", ArrayUtils.contains(productNames, parsleyProductId), is(true));
        assertThat ("Cabbages is not in the list!", ArrayUtils.contains(productNames, cabbagesProductId), is(true));
    }
}