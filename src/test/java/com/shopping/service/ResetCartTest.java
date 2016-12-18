package com.shopping.service;

import com.shopping.service.testdata.MenuTestData;
import com.shopping.service.testdata.SpecialOffersTestData;
import org.junit.Before;
import org.junit.Test;

import static com.shopping.service.testdata.MenuTestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 *  The tests for the CartService AddItemToCart() method
 *
 * Created by Mir on 29/09/2016.
 */
public class ResetCartTest {
    private CartService cartService;

    @Before
    public void setup () throws Exception {
        cartService = new CartServiceImpl (MenuTestData.setup(), SpecialOffersTestData.setup());
    }

    @Test
    public void resetCartShouldWorkAsExpected  () {
        cartService.addOrderToCart(carrotsProductId, 2);
        cartService.addOrderToCart(potatoesProductId, 3);
        assertThat (cartService.getCart().noOfProducts(), is(2));
        cartService.resetCart();
        assertThat (cartService.getCart().noOfProducts(), is(0));
    }
}