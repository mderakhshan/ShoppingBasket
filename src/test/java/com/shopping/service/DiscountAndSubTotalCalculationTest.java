package com.shopping.service;

import com.shopping.service.testdata.MenuTestData;
import com.shopping.service.testdata.SpecialOffersTestData;
import org.junit.Before;
import org.junit.Test;

import static com.shopping.service.testdata.TestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 *  The tests here cover getDiscount(), getSubTotal() and getTotalPrice().
 *
 * Created by Mir on 29/09/2016.
 */
public class DiscountAndSubTotalCalculationTest {
    private CartService cartService;

    @Before
    public void setup () throws Exception {
        // Setup Sample Data for Menu and Special Offers
        cartService = new CartServiceImpl (MenuTestData.setup(), SpecialOffersTestData.setup());
    }

    @Test
    public void shouldReturnCorrectTotalsForSampleTestData () throws Exception {
        cartService.addOrderToCart(carrotsProductId, 4);
        cartService.addOrderToCart(potatoesProductId, 3);
        cartService.addOrderToCart(onionsProductId, 2);
        cartService.addOrderToCart(satsumasProductId, 2);

        cartService.checkout();

        assertThat("Cart sub-total isn't correct!", cartService.getSubTotal(), equalTo(8.05));
        assertThat("Cart total special offer discount isn't correct",  cartService.getDiscountTotal(), equalTo(1.09));
        assertThat("Cart total price isn't correct!", cartService.getTotalPrice(), equalTo(6.96));
    }
}