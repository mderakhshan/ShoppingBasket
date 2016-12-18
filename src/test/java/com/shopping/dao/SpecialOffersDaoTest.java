package com.shopping.dao;


import com.shopping.model.SpecialOffer;
import com.shopping.service.testdata.SpecialOffersTestData;
import org.junit.Before;
import org.junit.Test;

import static com.shopping.service.testdata.SpecialOffersTestData.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;


/**
 * Created by Mir on 30/09/2016.
 */
public class SpecialOffersDaoTest {
    SpecialOffersDao specialOffersDao;

    @Before
    public void setup () throws Exception {
        specialOffersDao = SpecialOffersTestData.setup();
    }

    //
    // Tests ford the findOfferByProductKey() method
    //
    @Test
    public void findOfferByProductKeyShouldReturnOffer () {
        SpecialOffer so = specialOffersDao.findOfferByProductKey(carrotsProductId);
        assertThat ("No offer for carrots found", so, is(notNullValue()));
        assertThat ("Expected offer for carrots but got something else!", so.getProductName(), is(carrotsProductId));
    }

    @Test
    public void findOfferByProductKeyShouldReturnNullIfOfferNotFound () {
        SpecialOffer so = specialOffersDao.findOfferByProductKey("xxxxx");
        assertThat ("A matching offer was found!", so, is(nullValue()));
    }


    //
    // Tests for addOffer() method
    //
    public void addOfferShouldWorkOkIfOfferDoesNotAlreadyExists () throws Exception {
        specialOffersDao = SpecialOffersTestData.setupWithNoTestData();

        specialOffersDao.addOffer(carrotsProductId, standardOfferCarrots);
        SpecialOffer foundOffer = specialOffersDao.findOfferByProductKey(carrotsProductId);
        assertThat ("Expected to find the standard offer for carrots but got something else!",
                foundOffer, equalTo(standardOfferCarrots));

        specialOffersDao.addOffer(potatoesProductId, conditionalOfferPotatoes);
        foundOffer = specialOffersDao.findOfferByProductKey(potatoesProductId);
        assertThat ("Expected to find the conditional offer for potatoes but got something else!",
                foundOffer, equalTo(conditionalOfferPotatoes));
    }

    public void addOfferShouldRaisExceptionIfOfferAlreadyExists () throws Exception {
        specialOffersDao = SpecialOffersTestData.setupWithNoTestData();
        specialOffersDao.addOffer(carrotsProductId, standardOfferCarrots);
        specialOffersDao.addOffer(carrotsProductId, standardOfferCarrots);
    }

    //
    // Tests for the findMatchingLiveOffer() method
    //
    @Test
    public void findMatchingLiveOfferShouldWorkOkIOfferExistsForAMenuItem () {
        SpecialOffer so = specialOffersDao.findMatchingLiveOffer(carrots);
        assertThat ("Couldn't find the offer for carrots!", so, is(notNullValue()));
        assertThat ("Expected offer for carrots but got something else!", so,  equalTo(standardOfferCarrots));
    }

    @Test
    public void findMatchingCandidateOfferShouldReturnNullIfNoOfferExistsForMenuItem () {
        SpecialOffer so = specialOffersDao.findMatchingLiveOffer(satsumas);
        assertThat ("Expected to find no offers for satsumas", so, is(nullValue()));
    }

    @Test
    public void findMatchingLiveOfferReturnOfferIfStartDateIsInThePast () {
        SpecialOffer so = specialOffersDao.findMatchingLiveOffer(carrots);
        assertThat ("Expected offer for carrots but got something else!", so.getProductName(), is(carrotsProductId));
    }

    @Test
    public void findMatchingCandidateOfferShouldReturnNullIfOfferNotStartedYet  () {
        SpecialOffer so = specialOffersDao.findMatchingLiveOffer(parsley);
        assertThat ("Expected no offer found for parsley as its start date is in the future!", so, is(nullValue()));
    }

    @Test
    public void findMatchingCandidateOfferShouldReturnNullIfOfferHasExppired  () {
        SpecialOffer so = specialOffersDao.findMatchingLiveOffer(cabbages);
        assertThat ("Expected no offer found for cabbages as it has an end date start in the past!",
                so, is(nullValue()));
    }
}