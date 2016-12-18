package com.shopping.service;

import com.shopping.service.testdata.MenuTestData;
import com.shopping.service.testdata.SpecialOffersTestData;
import org.junit.Before;
import org.junit.Test;

import static com.shopping.service.testdata.MenuTestData.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 *  The tests for the CartService checkout() method
 *
 * Created by Mir on 29/09/2016.
 */
public class CheckOutTest {
    private CartService cartService;

    @Before
    public void setup () throws Exception {
        cartService = new CartServiceImpl (MenuTestData.setup(), SpecialOffersTestData.setup());
    }

    @Test
    public void checkedOutStatusShouldBeFalseDuringShopping  () {
        assertThat (cartService.isCheckedOut(), is(false)); // tests the initial checkout state of the cart
        cartService.addOrderToCart(carrotsProductId, 2); // to indicate shopping is in progress
        assertThat ("Cart should have checkedOut status set to false during shopping!", cartService.isCheckedOut(), is(false));
    }

    @Test
    public void checkedOutStatusShouldBeTrueAfterShopping  () {
        cartService.checkout();
        assertThat ("Cart should have checkedOut status set to true after checkout!", cartService.isCheckedOut(), is(true));
    }

    @Test
    public void subTotalAmountShouldBeCorrect () {
        cartService.addOrderToCart(carrotsProductId, 5);
        cartService.addOrderToCart(onionsProductId, 4);
        cartService.checkout();
        assertThat("cart sub-total is incorrect!", cartService.getSubTotal(), equalTo(5.0));
    }

    @Test
    public void checkoutShouldReturnExpectedCostSummaryWhenThereAreNoApplicableSpecialOffers  () throws Exception {
        cartService = new CartServiceImpl (MenuTestData.setup(), SpecialOffersTestData.setupWithNoTestData());
        cartService.addOrderToCart(carrotsProductId, 5);
        cartService.addOrderToCart(onionsProductId, 4);

        String expected = "Sub-total:\t£5.00\n" +
                "(No offers available)\n" +
                "Total price:\t£5.00";
        String result = cartService.checkout();
        assertThat("Price summary string returned is incorrect where there are no special offers applicable!",
                result, is(expected));
    }

    @Test
    public void checkoutShouldReturnCorectCostSummaryWhenSpecialOfferDiscountsAreApplicable () throws Exception {
        cartService.addOrderToCart(carrotsProductId, 4);
        cartService.addOrderToCart(potatoesProductId, 3);
        cartService.addOrderToCart(onionsProductId, 2);
        cartService.addOrderToCart(satsumasProductId, 2);

        String expected = "Sub-total:\t£8.05\n" +
                "Carrots 10% off:\t -24p\n" +
                "Potatoes buy 1 get 1 free:\t -75p\n" +
                "Onions 20% off with every 2 bags of potatoes:\t -10p\n" +
                "Price:\t£6.96";
        String result = cartService.checkout();
        assertThat("Price summary string returned is incorrect where there are are special offer discounts",
                result, is(expected));
    }
}