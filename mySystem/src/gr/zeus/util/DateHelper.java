/*
 * DateHelper.java
 *
 * Copyright (c) 2004-2011 Gregory Kotsaftis
 * gregkotsaftis@yahoo.com
 * http://zeus-jscl.sourceforge.net/
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package gr.zeus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Helper methods for <code>java.util.Date</code>
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.05
 */
public final class DateHelper {

    /**
     * Subtracts 'days' from a calendar taking into consideration
     * the month, year and leap years change as well.
     * <p>
     * @param cal   The <code>Calendar</code>.
     * @param days  The number of days.
     */
    public static void subDaysFromDate(Calendar cal, int days)
    {
        if( cal == null )
            return;

        while( days > 0 )
        {
            int day_of_year = cal.get(Calendar.DAY_OF_YEAR);
            if( day_of_year==1 )
            {
                cal.roll(Calendar.YEAR, false);
            }

            cal.roll(Calendar.DAY_OF_YEAR, false);
            days--;
        }
    }


    /**
     * Adds 'days' to a calendar taking into consideration
     * the month, year and leap years change as well.
     * <p>
     * @param cal   The <code>Calendar</code>.
     * @param days  The number of days.
     */
    public static void addDaysToDate(Calendar cal, int days)
    {
        if( cal == null )
            return;

        while( days > 0 )
        {
            int day_of_year = cal.get(Calendar.DAY_OF_YEAR);

            int days_in_year = 365;
            if( isLeapYear(cal.get(Calendar.YEAR)) )
            {
                days_in_year = 366;
            }

            if( day_of_year == days_in_year )
            {
                cal.roll(Calendar.YEAR, true);
            }

            cal.roll(Calendar.DAY_OF_YEAR, true);
            days--;
        }
    }


    /**
     * Leap years occur in years exactly divisible by four,
     * <i>except</i> those years ending in 00 are leap years
     * only if they are divisible by 400.
     * <p>
     * @param year  The year number.
     * <p>
     * @return      <code>true</code> if it is a leap year.
     */
    public static boolean isLeapYear(int year)
    {
        boolean isLeapYear = false;

        if( year > 0 && (year % 4) == 0 )
        {
            isLeapYear = true;

            if( (year % 1000) == 0 ) // year is '00
            {
                if( (year % 400) != 0 )
                {
                    isLeapYear = false;
                }
            }
        }

        return( isLeapYear );
    }


    /**
     * Parses a string into a date. String should be in
     * <code>SimpleDateFormat</code> format.
     * e.g. <code>java.util.Date d = parseDate(myDate, "dd/MM/yyyy");</code>
     * <p>
     * @param myDate    The date string.
     * @param pattern   The pattern to use.
     * <p>
     * @return          The <code>Date</code>.
     * <p>
     * @throws ParseException
     */
    public static java.util.Date parseDate(String myDate, String pattern)
        throws ParseException
    {
        if( myDate==null || pattern==null )
            throw new ParseException("Null arguments!", 0);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( pattern );
        java.util.Date uDate = simpleDateFormat.parse( myDate );

        return( uDate );
    }


    /**
     * Converts a date to a string based on a
     * <code>SimpleDateFormat</code> pattern.
     * e.g. <code>String s = dateToString(uDate, "dd/MM/yyyy");</code>
     * <p>
     * @param uDate     The date string.
     * @param pattern   The pattern to use.
     * <p>
     * @return          The string of the date or <code>null</code> on error.
     */
    public static String dateToString(java.util.Date uDate, String pattern)
    {
        if( uDate==null || pattern==null )
          return( null );

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( pattern );
        String myDate = simpleDateFormat.format( uDate );

        return( myDate );
    }


    /**
     * Checks a string to see if it contains a valid date in
     * <code>SimpleDateFormat</code>.
     * <p>
     * @param dateToCheck   The date string to check.
     * @param pattern       The pattern to use.
     * <p>
     * @return              <code>true</code> if it contains a valid date in
     *                      <code>SimpleDateFormat</code>.
     */
    public static boolean isDateValid(String dateToCheck, String pattern)
    {
        if( dateToCheck==null || pattern==null )
          return( false );

        boolean result = false;
        try
        {
            java.util.Date in = parseDate(dateToCheck, pattern);
            String out = dateToString(in, pattern);
            if( dateToCheck.compareTo(out) == 0 )
            {
                result = true;
            }
        }
        catch(Exception e)
        {
            // result is already false
        }

        return( result );
    }


    /**
     * Parses a string into a date. String should be in
     * <code>SimpleDateFormat</code> format. Returns only the year of the date
     * or -1 on error.
     * <p>
     * <b>NOTE:</b> only 'yyyy' is supported!
     * <p>
     * @param dateToCheck   The date string to check.
     * @param pattern the   The pattern to use.
     * <p>
     * @return              The date or -1 on error.
     */
    public static int getYearForDate(String dateToCheck, String pattern)
    {
        if( isDateValid(dateToCheck, pattern) &&
            dateToCheck.length()==pattern.length() )
        {
            int index = pattern.indexOf("yyyy"); // only "yyyy" is supported
            if( index==-1 )
            {
                return( -1 );
            }

            String year_str = dateToCheck.substring(index, index+4);
            int year = -1;
            try
            {
                Integer i = new Integer( year_str );
                year = i.intValue();
            }
            catch(Exception e)
            {
                // year is already -1
            }

            return( year );
        }
        else
        {
            return( -1 );
        }
    }


    /**
     * Parses a string into a date. String should be in
     * <code>SimpleDateFormat</code> format. Returns only the month of the date
     * or -1 on error.
     * <p>
     * <b>NOTE:</b> only 'MM' is supported!
     * <p>
     * @param dateToCheck   The date string to check.
     * @param pattern       The pattern to use.
     * <p>
     * @return              The date or -1 on error.
     */
    public static int getMonthForDate(String dateToCheck, String pattern)
    {
        if( isDateValid(dateToCheck, pattern) &&
            dateToCheck.length()==pattern.length() )
        {
            int index = pattern.indexOf("MM"); // only "MM" is supported
            if( index==-1 )
            {
                return( -1 );
            }

            String month_str = dateToCheck.substring(index, index+2);
            int month = -1;
            try
            {
                Integer i = new Integer( month_str );
                month = i.intValue();

                // month starts from 0...11
                month--;
            }
            catch(Exception e)
            {
                // month is already -1
            }

            return( month );
        }
        else
        {
            return( -1 );
        }
    }


    /**
     * Parses a string into a date. String should be in
     * <code>SimpleDateFormat</code> format. Returns only the day of the date
     * or -1 on error.
     * <p>
     * <b>NOTE:</b> only 'dd' is supported!
     * <p>
     * @param dateToCheck   The date string to check.
     * @param pattern       The pattern to use.
     * <p>
     * @return              The day for the date or -1 on error.
     */
    public static int getDayForDate(String dateToCheck, String pattern)
    {
        if( isDateValid(dateToCheck, pattern) &&
            dateToCheck.length()==pattern.length() )
        {
            int index = pattern.indexOf("dd"); // only "dd" is supported
            if( index==-1 )
            {
                return( -1 );
            }

            String day_str = dateToCheck.substring(index, index+2);
            int day = -1;
            try
            {
                Integer i = new Integer( day_str );
                day = i.intValue();
            }
            catch(Exception e)
            {
                // day is already -1
            }

            return( day );
        }
        else
        {
            return( -1 );
        }
    }


    /**
     * Gets the name of a day based on a date and current locale.
     * <p>
     * @param dt        The date.
     * @param fullname  Fetch complete day's name or the short one.
     * <p>
     * @return          A string with the name of the day.
     */
    public static String getDayNameForDate(java.util.Date dt, boolean fullname)
    {
        // For formatting, if the number of pattern letters is 4 or more,
        // the full form is used; otherwise a short or abbreviated form is used
        // if available. (extracted from Sun's Javadoc)
        final String fullFormat = "EEEE";
        final String smallFormat = "EEE";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                fullname ? fullFormat : smallFormat );

        String dayName = simpleDateFormat.format( dt );

        return( dayName );
    }

}
