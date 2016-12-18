package com.shopping.util;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.shopping.util.Utils.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by Mir on 30/09/2016.
 */
public class UtilsTest {

    @Test
    public void roundUpDoubleTwoDecimalPlacesShouldWorkOk () throws Exception {
        assertThat (roundUpTwoDecimalPlaces(new BigDecimal(2.556)).doubleValue(), equalTo (2.56));
        assertThat (roundUpTwoDecimalPlaces(new BigDecimal(0.5456)).doubleValue(), equalTo (0.55));
        assertThat (roundUpTwoDecimalPlaces(BigDecimal.ZERO).doubleValue(), equalTo (0.0));
        assertThat (roundUpTwoDecimalPlaces(new BigDecimal(55.32)).doubleValue(), equalTo (55.32));
        assertThat (roundUpTwoDecimalPlaces(new BigDecimal(-55.3249)).doubleValue(), equalTo (-55.32));
    }

    @Test
    public void double2LocalCurrencyStringShouldWorkOk () throws Exception {
        assertThat (BigDecimal2LocalCurrencyString(new BigDecimal(25.32), Locale.UK), is ("£25.32"));
        assertThat (BigDecimal2LocalCurrencyString(new BigDecimal(25.3), Locale.UK), is ("£25.30"));
        assertThat (BigDecimal2LocalCurrencyString(new BigDecimal(-25.3), Locale.UK), is ("-£25.30"));
        assertThat (BigDecimal2LocalCurrencyString(BigDecimal.ZERO, Locale.UK), is ("£0.00"));
        assertThat (BigDecimal2LocalCurrencyString(new BigDecimal(0.01), Locale.UK), is ("1p"));
        assertThat (BigDecimal2LocalCurrencyString(new BigDecimal(0.1), Locale.UK), is ("10p"));
        assertThat (BigDecimal2LocalCurrencyString(new BigDecimal(0.99), Locale.UK), is ("99p"));
        assertThat (BigDecimal2LocalCurrencyString(new BigDecimal(-0.99), Locale.UK), is ("-99p"));
    }

    @Test
    public void convertFraction2PercentageWorksOk () throws Exception {
        assertThat (convertFraction2Percentage (12.245), is (24));
        assertThat (convertFraction2Percentage (0.245), is (24));
        assertThat (convertFraction2Percentage (0.2), is (20));
        assertThat (convertFraction2Percentage (0.0), is (0));
    }

    @Test (expected=IllegalArgumentException.class)
    public void string2DateShouldRaiseExceptionIfStringDoesNotConformWIthFormat () {
        Date date = string2Date ("01-04-2016", "dd-MMM-yyyy");
    }

    @Test
    public void string2DateShouldWorkOkIfStringConformsWithFormat  ()  {
        String strDate = "12-Feb-2012";
        Date date = string2Date (strDate,"dd-MMM-yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        assertThat (dateFormat.format(date), is (strDate));
    }
}