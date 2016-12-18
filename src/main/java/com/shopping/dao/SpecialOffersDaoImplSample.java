package com.shopping.dao;

import com.shopping.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.shopping.util.Utils.string2Date;

/**
 * Author Mir on 30/09/2016.
 *
 * This is to serve as an example implementation of the interface SpecialOffersDao. It sets up
 * the special offers data using hard-coded sample data. A proper implementation should read this data
 * from a data source such as a XML file or a database.
 *
 */
@Service
@PropertySource(value= {"classpath:application.properties"})
public class SpecialOffersDaoImplSample implements SpecialOffersDao {

    private final String  DATE_FORMAT = "dd-MMM-yyyy";

    private static final String ERROR_MSG =
            "The Special Offers Map object is not set up. Use the setup service provided to set it up first!";

    private Map<String, SpecialOffer> offersMap;

    public SpecialOffersDaoImplSample () throws Exception {
        setupSepcialOffers ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOffer(String productKey, SpecialOffer so) {
        if (offersMap == null) {
            throw new RuntimeException (ERROR_MSG);
        }

        if (StringUtils.isEmpty(productKey)) {
            throw new IllegalArgumentException ("The product Name to be added as a special offer is null!");
        }
        // check if there already is a special offer with the same product name and throw an exception if the case
        String nameUppercase = productKey.toUpperCase();
        if (offersMap.containsKey(nameUppercase)) {
            throw new IllegalArgumentException ("Error: duplicate productName found when adding offer for product " +
                    so.getProductName() + ". There already is another offer defined for this product!");
        } else {
            offersMap.put(nameUppercase, so);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpecialOffer findMatchingLiveOffer(MenuItem menuItem) {
        if (menuItem == null || menuItem == null || menuItem.getProductName() == null) {
            return null;
        }
        String productKey = menuItem.getProductName().toUpperCase();
        SpecialOffer matchingSo = findOfferByProductKey(productKey);
        if (matchingSo == null) {
            return null;
        }

        // check if the found offer is in date (i.e. not expired or started yet)
        Date now = new Date();
        boolean starCheckSuccess = matchingSo.getStartDate() == null ? true :
                matchingSo.getStartDate().compareTo(now) <= 0 ? true : false;
        boolean endCheckSuccess = matchingSo.getEndDate() == null ? true :
                matchingSo.getEndDate().compareTo(now) > 0 ? true : false;
        if ( starCheckSuccess && endCheckSuccess) {
            return matchingSo;
        }
        else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpecialOffer findOfferByProductKey(String productKey) {
        if (offersMap == null) {
            throw new RuntimeException (ERROR_MSG);
        }
        if (StringUtils.isEmpty(productKey)) {
            return null;
        }
        return offersMap.get(productKey.toUpperCase());
    }

    /**
     *  Adds sample special offers to the <code>offersMap</code> property
     *
     * @throws Exception if exceptions are thrown when loading the sample data
     */
    private void setupSepcialOffers() throws Exception {

        offersMap = new LinkedHashMap<>();
        offersMap.put (
                "APPLES",
                new SpecialOfferStandard(
                        "Apples 10% off",             // offer title
                        "Apples",                     // The product this offer applies to
                        string2Date("27-SEP-2016", DATE_FORMAT), // offer startDate
                        string2Date("7-OCT-2017", DATE_FORMAT),  // offer endDate
                        10                         // percentage discount
                )
        );

        offersMap.put (
                "BREAD",
                new SpecialOfferConditional(
                        "Bread 50% off for 2 tins of soup",
                        "Bread",
                        string2Date("27-SEP-2016", DATE_FORMAT),
                        string2Date("7-OCT-2017", DATE_FORMAT),
                        10,
                        new OfferCondition("Soup", 2)  // offer dependent on 2 tins of soup
                )
        );

        offersMap.put (
                "MILK",
                new SpecialOfferConditional (
                        "Milk buy one get one free",
                        "Milk",
                        string2Date("27-SEP-2016", DATE_FORMAT),
                        string2Date("7-OCT-2017", DATE_FORMAT),
                        100,
                        new OfferCondition("Milk", 2)
                )
        );
    }
}