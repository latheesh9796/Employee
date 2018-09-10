package com.ideas2it.application.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;

/**
 * <p>
 * DateUtil is a class that hold methods that are related to Date objects
 * and methods involving Date operations like validation.
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class DateUtil {
    private static final String INCORRECT_DATE = "Date entered was incorrect ";

    /**
     * <p>
     * convertStringtoDate method is used to convert
     * String Object to Date Object and can also be used as
     * a date validator
     * </p>
     *
     * @param stringDate             String Object
     *
     * @return date                  date in Date format
     */
    public static Date convertStringToDate(String stringDate) {
        Date date = null;
        try {
            date = 
                new SimpleDateFormat(Constants.DATE_FORMAT).parse(stringDate);
            return date;
        } catch (ParseException exception) {
            date = null;
        }
        return date;
    }

    /**
     * <p>
     * convertStringtoDate method is used to convert
     * Date object to string in dd-MM-yyyy format.
     * </p>
     *
     * @param date                   Date Object
     * 
     * @return                       date in String format
     */
    public static String convertDateToString(Date date) {
         String DATE_FORMAT_NOW = "yyyy-MM-dd";
         SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
         return sdf.format(date);
    }

    /**
     * <p>
     * yearDifference method is used to calculate
     * difference of years between current time and given input date
     * </p>
     *
     * @param fromDate               String Object
     * 
     * @return years                 Difference of input date and current date
     *                               in years
     */
    public static int getYearDifference(String fromDate) {
        int years;
        try {
            SimpleDateFormat simpleDateFormatObject = new
                                   SimpleDateFormat(Constants.DATE_FORMAT);	
            Date date = simpleDateFormatObject.parse(fromDate);
            Date sysdate = new Date();
            long milliSeconds = System.currentTimeMillis() -
                                             date.getTime();
            years = (int)((long)milliSeconds / (1000.0*60*60*24*365));
        } catch (ParseException e) {
            years = -1;
        }
        return years;
    }

    /**
     * <p>
     * yearDifference method is used to calculate
     * difference of years between two given dates
     * </p>
     *
     * @param fromDate               String Object containing first date
     * @param toDate                 String Object containing second date
     * 
     * @return years                 Difference of two dates in number of years
     */
    public static int getYearDifference(String fromDate, String toDate) {
        int years = 0;
        try {
            SimpleDateFormat simpleDateFormatObject = new
                                   SimpleDateFormat(Constants.DATE_FORMAT);	
            Date firstDate = simpleDateFormatObject.parse(fromDate);
            Date secondDate = simpleDateFormatObject.parse(toDate);
            long milliSeconds = secondDate.getTime() - firstDate.getTime();
            years = (int)((long)milliSeconds / (1000.0*60*60*24*365));
            if (years < 0) {
                return (years*-1);
            }
        } catch (ParseException e) {
            years = -1;
        }
        return years;
    }

    /**
     * <p>
     * getDateMinusYears method is used to return
     * a string date which is result of subtracting
     * current date with the given years
     * </p>
     *
     * @param years                  Number of years to subtract
     * 
     * @return date                  Date which is subtracted from given
     *                               current date for given years
     */
    public static String getDateMinusYears(int years) {
        LocalDate today = LocalDate.now();
        LocalDate date = today.plusYears(-1*years);
        return date.toString();
    }

    /**
     * <p>
     * Private Constructor will prevent
     * the instantiation of this class directly
     * </p>
     */
    private DateUtil() {
    }
}
