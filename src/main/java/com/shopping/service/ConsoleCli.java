package com.shopping.service;

import com.shopping.dao.MenuDao;
import com.shopping.dao.SpecialOffersDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 *  * Author Mir on 30/09/2016.
 *
 *  A command line service to run the app in standalone mode
 *
 */

@Service
public class ConsoleCli {
    private final CartService cartService;
    private final MenuDao menuDao;
    private final SpecialOffersDao specialOffersDao;

    @Autowired
    public ConsoleCli(CartService cartService, MenuDao menuDao, SpecialOffersDao specialOffersDao) {
        this.cartService = cartService;
        this.menuDao = menuDao;
        this.specialOffersDao = specialOffersDao;
    }

    public void runCli (String[] args) throws Exception {
        String[] products = args;
        if (args.length == 0) {
            System.out.println ("\nMenu: [" + String.join(", ", menuDao.getProductNames()) + "]");
            System.out.print ("Enter your order as a list of products (e.g. milk bread), or \"q\" to quit: ");
            Scanner in = new Scanner (System.in);
            while (true) {
                String inputStr = null;
                while (StringUtils.isEmpty(inputStr)) {
                    inputStr = in.nextLine().trim();
                }
                if ("q".equals(inputStr.toLowerCase())) {
                    break;
                }
                else {
                    processOrders(inputStr.trim().split(" "));
                    System.out.print("\nEnter another list, or \"q\" to quit: ");
                }
            }
        }
    }

    private void processOrders (String[] products) {
        cartService.resetCart();
        for (String p : products) {
            try {
                cartService.addOrderToCart(p, 1); // add one of this product to the Cart
            }
            catch (Exception e) {
                System.out.println (e.getMessage() + " This order is ignored.");
            }
        }
        String costSummary = cartService.checkout();
        System.out.println(costSummary);
    }
}
