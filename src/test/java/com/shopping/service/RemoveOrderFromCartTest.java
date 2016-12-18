package com.shopping.service;

import com.shopping.dao.MenuDao;
import com.shopping.dao.SpecialOffersDao;
import com.shopping.model.CartOrderItem;
import com.shopping.service.testdata.MenuTestData;
import com.shopping.service.testdata.SpecialOffersTestData;
import org.junit.Before;
import org.junit.Test;

import static com.shopping.service.testdata.MenuTestData.carrotsProductId;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * The tests for the CartService RemoveOrderFromCart() method
 *
 * Created by Mir on 29/09/2016.
 */
public class RemoveOrderFromCartTest {
    private MenuDao menuDao;
    private SpecialOffersDao specialOffersDao;
    private CartService cartService;

    @Before
    public void setup () throws Exception {
        menuDao = MenuTestData.setup();
        specialOffersDao = SpecialOffersTestData.setup();
        cartService = new CartServiceImpl (menuDao, specialOffersDao);
    }

    @Test
    public void shouldRemoveExistingCartItemOk () {
        CartOrderItem item = cartService.addOrderToCart(carrotsProductId, 2);
        cartService.removeOrderFromCart(carrotsProductId);
        CartOrderItem foundItem = cartService.findCartItemByProductName(carrotsProductId);
        assertThat("The order item wasn't removed from the the cart!", foundItem, is(nullValue()));
    }

    @Test
    public void shouldNotGenerateErrorIfProductNotInCart () {
        CartOrderItem foundItem = cartService.findCartItemByProductName(carrotsProductId);
        assertThat("Expected the product not to be in the cart!", foundItem, (nullValue()));
        cartService.removeOrderFromCart(carrotsProductId);
    }
}