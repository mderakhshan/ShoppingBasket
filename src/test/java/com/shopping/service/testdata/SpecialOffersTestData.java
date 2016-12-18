package com.shopping.service.testdata;

import com.shopping.dao.SpecialOffersDao;
import com.shopping.dao.SpecialOffersDaoImplSample;

/**
 * Created by Mir on 29/09/2016.
 */
public class SpecialOffersTestData extends TestData {

    // Sets up a SpecialOffersDao and loads the test data
    public static SpecialOffersDao setup () throws Exception {
        SpecialOffersDao specialOffersDao = new SpecialOffersDaoImplSample();
        specialOffersDao.addOffer(carrotsProductId, standardOfferCarrots);
        specialOffersDao.addOffer(potatoesProductId, conditionalOfferPotatoes);
        specialOffersDao.addOffer (onionsProductId, conditionalOfferOnions);
        specialOffersDao.addOffer (parsleyProductId, offerNotStartedYetParsley);
        specialOffersDao.addOffer (cabbagesProductId, offerExpiredCabbages);
        return specialOffersDao;
    }
    // Setup a SpecialOffersDao without loading test data
    public static SpecialOffersDao setupWithNoTestData () throws Exception {
        SpecialOffersDao specialOffersDao = new SpecialOffersDaoImplSample();
        return specialOffersDao;
    }
}
