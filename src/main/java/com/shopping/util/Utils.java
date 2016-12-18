package com.shopping.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

/**
 * A utility class providing helper methods for other classes
 *
 * Author Mir on 30/09/2016.
 */
public class Utils {

    /**
     * Rounds a Double to two decimal places, e.g. 23.47643 produces 23.48, 12.4512 produces 12.45, etc.
     *
     * @param number the number being rounded
     * @return rounded up double
     */
    public static BigDecimal roundUpTwoDecimalPlaces(BigDecimal number) {
        return number.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Returns a string to display a double in local currency, e.g. for the Sterling currency:
     * 12.54 returns string "£12.54", 0.10 returns "10p" , -12.54 returns "-£12.54", 0.0 returns "£0.00", etc
     *
     * @param number the number converted to currency string
     * @return the string denoting the value in Sterling
     */
    public static String BigDecimal2LocalCurrencyString(BigDecimal number, Locale locale) {
        double n = number.doubleValue();
        Currency curr = Currency.getInstance(locale);

        // if currency is Pound, express less than 1 pound in pence
        // Note: We will need to handle other currency sub-units (e.g. cents) if internationalisation becomes a requirement
        if (((n < 1 && n > 0) || (n > -1 && n < 0))  && curr.getSymbol().equals("£")) {
            return (n < 0 ? "-" : "") + String.format ("%d", convertFraction2Percentage (n)) + "p";
        }
        else {
            if (n >= 0) {
                return curr.getSymbol() + String.format("%.2f", n);
            } else {
                return "-" + curr.getSymbol() + String.format("%.2f", n*-1);
            }
        }
    }
    public static String BigDecimal2LocalCurrencyString(BigDecimal number) {
        return BigDecimal2LocalCurrencyString(number, Locale.getDefault());
    }

    /**
     * Coverts the fraction part of a double to an integer ranging from 0 to 100.
     * E.g. 89.725655 will produce 72, 0.2 will produce 20, 0.23 will produce 23, etc.
     *
     * @param n the given double number
     * @return an integer between 0 and 100 representing its fraction part
     */
    public static int convertFraction2Percentage (double n) {
        BigDecimal fractionalPart = new BigDecimal(n)
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .remainder( BigDecimal.ONE )
                .multiply(new BigDecimal(100));
        return (n > 0 ?  fractionalPart.intValue() : fractionalPart.intValue() * -1);
    }

    /**
     * *  Converts a String object in the given format to a Date object
     *
     * @param dateStr the date string to be converted
     * @param format the date format, e.g. dd=MMM-yyyy
     * @return the Date object
     * @throws IllegalArgumentException if the string does not conform with the given format
     */
    public static Date string2Date (String dateStr, String format) {
        Date date;
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return date;
    }
}