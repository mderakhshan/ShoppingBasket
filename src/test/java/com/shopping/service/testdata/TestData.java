package com.shopping.service.testdata;

import com.shopping.model.*;

import static com.shopping.util.Utils.string2Date;

/**
 * Created by Mir on 30/09/2016.
 */
public class TestData {

    protected static final String DATE_FORMAT = "dd-MMM-yyyy";

    //
    // Menu Item Test Data
    //
    public static final String carrotsProductId = "Carrots";
    public static final MenuItem carrots =  new MenuItem(carrotsProductId, 0.60, "price is per bag");

    public static final String onionsProductId = "Onions";
    public static final MenuItem onions =  new MenuItem (onionsProductId, 0.50, "price is per bag");

    public static final String potatoesProductId = "Potatoes";
    public static final MenuItem potatoes =  new MenuItem (potatoesProductId, 0.75, "price is per bag");

    public static final String satsumasProductId = "Satsumas";
    public static final MenuItem satsumas =  new MenuItem (satsumasProductId, 1.20, "price is per bag");

    public static final String parsleyProductId = "Parsley";
    public static final MenuItem parsley =  new MenuItem (parsleyProductId, 0.90, "price is per bunch");

    public static final String cabbagesProductId = "Cabbages";
    public static final MenuItem cabbages =  new MenuItem (cabbagesProductId, 0.60, "price is per item");

    //
    // Special Offers Test Data
    //
    public static final SpecialOffer offerNotStartedYetParsley =  new SpecialOfferStandard(
            "Parsley 20% off",
            parsleyProductId,
            string2Date("20-NOV-2016", DATE_FORMAT),
            string2Date("27-NOV-2016", DATE_FORMAT),
            20
    );

    public static final SpecialOffer offerExpiredCabbages =  new SpecialOfferStandard(
            "Cabbages 30% off",
            cabbagesProductId,
            string2Date("10-SEP-2016", DATE_FORMAT),
            string2Date("17-SEP-2016", DATE_FORMAT),
            30
    );

    public static final SpecialOffer standardOfferCarrots =  new SpecialOfferStandard(
            "Carrots 10% off",
            carrotsProductId,
            string2Date("27-SEP-2016", DATE_FORMAT),
            string2Date("7-OCT-2017", DATE_FORMAT),
            10
    );

    public static final SpecialOffer conditionalOfferPotatoes = new SpecialOfferConditional(
            "Potatoes buy 1 get 1 free",
            potatoesProductId,
            string2Date("27-SEP-2016", DATE_FORMAT),
            string2Date("10-OCT-2017", DATE_FORMAT),
            100,
            new OfferCondition("Potatoes", 2)
    );

    public static final SpecialOffer conditionalOfferOnions = new SpecialOfferConditional (
            "Onions 20% off with every 2 bags of potatoes",
            onionsProductId,
            string2Date("27-SEP-2016", DATE_FORMAT),
            string2Date("10-OCT-2017", DATE_FORMAT),
            20,
            new OfferCondition("Potatoes", 2)
    );
}