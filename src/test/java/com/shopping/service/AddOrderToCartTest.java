package com.shopping.service;

import com.shopping.dao.MenuDao;
import com.shopping.dao.SpecialOffersDao;
import com.shopping.model.CartOrderItem;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.shopping.service.testdata.TestData.carrots;
import static com.shopping.service.testdata.TestData.carrotsProductId;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 *  The tests for the CartService AddOrderToCart() method
 *
 * Created by Mir on 29/09/2016.
 */
@RunWith(JMockit.class)
public class AddOrderToCartTest {
    private CartService cartService;

    // @Injectable mocks only this particular instance, whereas @Mock will mock all instances
    @Injectable private MenuDao mockMenuDao;
    @Injectable private SpecialOffersDao mockSpecialOffersDao;

    @Before
    public void setup () throws Exception {
        cartService = new CartServiceImpl (mockMenuDao, mockSpecialOffersDao);
    }

    @Test (expected=IllegalArgumentException.class)
    public void shouldRaisedExceptionIfProductNotInMenu () {
        new Expectations() {{
            mockMenuDao.findMenuItemByKey ("xxxxxxx");
            result = null;
            times = 1;
        }};
        CartOrderItem item = cartService.addOrderToCart("xxxxxxx", 4);
    }

    @Test
    public void shouldAddANewProductOrderToCartOk () {
        new Expectations() {{
            mockMenuDao.findMenuItemByKey (carrotsProductId);
            result = carrots;
            maxTimes = 2;
        }};
        CartOrderItem item = cartService.addOrderToCart(carrotsProductId, 4);
        CartOrderItem foundItem = cartService.findCartItemByProductName(carrotsProductId);
        assertThat("Expected to find an order for carrots in the cart!", foundItem, is(notNullValue()));
        assertThat("Expected to find the order for carrots but got something else!", foundItem, equalTo(item));
    }

    @Test
    public void AddingToTheQuantityOfAnExistingOrderShouldWorkCorrectly () {
        new Expectations() {{
            mockMenuDao.findMenuItemByKey (carrotsProductId);
            result = carrots;
            maxTimes = 4;
        }};

        CartOrderItem item;
        item =  cartService.addOrderToCart(carrotsProductId, 4);
        item =  cartService.addOrderToCart(carrotsProductId, 2);
        item =  cartService.addOrderToCart(carrotsProductId, 5);

        CartOrderItem foundItem = cartService.findCartItemByProductName(carrotsProductId);
        assertThat("Expected to find the order for carrots but got something else!", foundItem, equalTo(item));
        assertThat("Expected to find correct quantity for carrots!", foundItem.getQuantity(), equalTo(11));
    }

    @Test (expected=IllegalArgumentException.class)
    public void shouldRaiseAnExceptionIfQuantityIsLessThanOne () {
        new Expectations() {{
            mockMenuDao.findMenuItemByKey (carrotsProductId);
            result = carrots;
            maxTimes = 1;
        }};
        CartOrderItem item = cartService.addOrderToCart(carrotsProductId, 0);
    }

    @Test (expected=IllegalArgumentException.class)
    public void shouldRaiseAnExceptionIfQuantityOfAnExistingOrderIsChangedToLessThanZero () {
        new Expectations() {{
            mockMenuDao.findMenuItemByKey (carrotsProductId);
            result = carrots;
            maxTimes = 2;
        }};
        cartService.addOrderToCart(carrotsProductId, 4);
        cartService.addOrderToCart(carrotsProductId, -5);
    }

    @Test
    public void shouldRemoveExistingOrderItemIfQuantityIsRedcuedToZero () {
        CartOrderItem item;
        new Expectations() {{
            mockMenuDao.findMenuItemByKey (carrotsProductId);
            result = carrots;
            maxTimes = 3;
        }};
        item = cartService.addOrderToCart(carrotsProductId, 4);
        item = cartService.addOrderToCart(carrotsProductId, -4);
        assertThat ("Expected to find an order for carrots in the cart!", item, is(nullValue()));
        CartOrderItem foundItem = cartService.findCartItemByProductName(carrotsProductId);
        assertThat("Expected to find the order for carrots in the cart but got something else!",
                foundItem, is(nullValue()));
    }
}