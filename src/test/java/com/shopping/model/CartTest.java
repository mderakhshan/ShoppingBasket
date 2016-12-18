package com.shopping.model;

import com.shopping.service.CartService;
import com.shopping.service.CartServiceImpl;
import com.shopping.service.testdata.MenuTestData;
import com.shopping.service.testdata.SpecialOffersTestData;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static com.shopping.service.testdata.TestData.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


/**
 * Created by Mir on 01/10/2016.
 */
public class CartTest {

    private CartService cartService;
    private Cart cart;


    @Before
    public void setup () throws Exception {
        cartService = new CartServiceImpl (MenuTestData.setup(), SpecialOffersTestData.setup());
        cart = cartService.getCart();
    }

    // Scenario: Carrots 10% off
    @Test
    public void calculateSpecialOfferDiscountShouldWorkOkWithStandardOffers () throws Exception {
        CartOrderItem order = cartService.addOrderToCart(carrotsProductId, 4);
        BigDecimal discount = cart.calculateSpecialOfferDiscount(order, standardOfferCarrots);
        assertThat (discount.doubleValue(), is (0.24));
    }

    // Test Scenario: Potatoes buy 1 get 1 free
    @Test
    public void calculateSpecialOfferDiscountShouldWorkOkWithOfferConditionOnSameProduct () throws Exception {
        CartOrderItem order;

        order = cartService.addOrderToCart(potatoesProductId, 1);
        BigDecimal discount = cart.calculateSpecialOfferDiscount(order, conditionalOfferPotatoes);
        assertThat (discount.doubleValue(), is (0.0));        // 1 potatoes -> 0 potatoes free

        order = cartService.addOrderToCart(potatoesProductId, 1);
        discount = cart.calculateSpecialOfferDiscount(order, conditionalOfferPotatoes);
        assertThat (discount.doubleValue(), is (0.75));       // 2 potatoes -> 1 potatoes free

        order = cartService.addOrderToCart(potatoesProductId, 1);
        discount = cart.calculateSpecialOfferDiscount(order, conditionalOfferPotatoes);
        assertThat (discount.doubleValue(), is (0.75));       // 3 potatoes -> 1 potatoes free

        order = cartService.addOrderToCart(potatoesProductId, 1);
        discount = cart.calculateSpecialOfferDiscount(order, conditionalOfferPotatoes);
        assertThat (discount.doubleValue(), is (1.50));       // 4 potatoes -> 2 potatoes free
    }

    // Test Scenario: Onions 30% off with every 2 bags of potatoes
    @Test
    public void calculateSpecialOfferDiscountShouldWorkOkWithOfferConditionOnDifferentProduct () throws Exception {
        CartOrderItem onionsOrder, potatoesOrder;

        onionsOrder= cartService.addOrderToCart(onionsProductId, 1);
        BigDecimal discount = cart.calculateSpecialOfferDiscount(onionsOrder, conditionalOfferOnions);
        assertThat (discount.doubleValue(), is (0.0));       // 1 onions, 0 potatoes -> 0 onions discount

        potatoesOrder = cartService.addOrderToCart(potatoesProductId, 1);
        discount = cart.calculateSpecialOfferDiscount(onionsOrder, conditionalOfferOnions);
        assertThat (discount.doubleValue(), is (0.0));       // 1 onions, 1 potatoes -> 0 onions discount

        potatoesOrder = cartService.addOrderToCart(potatoesProductId, 1);
        discount = cart.calculateSpecialOfferDiscount(onionsOrder, conditionalOfferOnions);
        assertThat (discount.doubleValue(), is (0.1));      // 1 onions, 2 potatoes -> 1 onions discount

        onionsOrder= cartService.addOrderToCart(onionsProductId, 1);
        discount = cart.calculateSpecialOfferDiscount(onionsOrder, conditionalOfferOnions);
        assertThat (discount.doubleValue(), is (0.1));     // 2 onions, 2 potatoes -> 1 onions discount

        potatoesOrder = cartService.addOrderToCart(potatoesProductId, 4);
        discount = cart.calculateSpecialOfferDiscount(onionsOrder, conditionalOfferOnions);
        assertThat (discount.doubleValue(), is (0.2));      // 2 onions, 4 potatoes -> 2 onions discount

        onionsOrder= cartService.addOrderToCart(onionsProductId, 2);
        potatoesOrder = cartService.addOrderToCart(potatoesProductId, 4);
        discount = cart.calculateSpecialOfferDiscount(onionsOrder, conditionalOfferOnions);
        assertThat (discount.doubleValue(), is (0.4));     // 3 onions, 4 potatoes -> 2 onions discount
    }
}