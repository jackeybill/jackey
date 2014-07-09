/*
 * CurrentDateHelper.java
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

import java.util.Calendar;

/**
 * Helper methods for current date and time.
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.0
 */
public final class CurrentDateHelper {

    /**
     * Gets the current date.
     * <p>
     * @return  The current date in <code>java.util.Date</code> object.
     */
    public static java.util.Date getCurrentDate()
    {
        return( Calendar.getInstance().getTime() );
    }


    /**
     * Gets the current date in long number format.
     * <p>
     * @return  The current date in long number format.
     */
    public static long getCurrentDateLong()
    {
        return( Calendar.getInstance().getTimeInMillis() );
    }


    /**
     * Gets the current year, e.g. 2004.
     * <p>
     * @return  The current year.
     */
    public static int getCurrentYear()
    {
        return( Calendar.getInstance().get(Calendar.YEAR) );
    }


    /**
     * Gets the current month, JANUARY = 1,......,DECEMBER = 12.
     * <p>
     * @return  The current month.
     */
    public static int getCurrentMonth()
    {
        return( Calendar.getInstance().get(Calendar.MONTH) + 1 );
    }


    /**
     * Gets the current day in the month (1...31).
     * <p>
     * @return  The current day in a month.
     */
    public static int getCurrentDay()
    {
        return( Calendar.getInstance().get(Calendar.DAY_OF_MONTH) );
    }


    /**
     * Gets the current hour in 24 hours format.
     * <p>
     * @return  The current hour in 24 hours format.
     */
    public static int getCurrentHour()
    {
        return( Calendar.getInstance().get(Calendar.HOUR_OF_DAY) );
    }


    /**
     * Gets the current minutes in hour.
     * <p>
     * @return  The current minutes in hour.
     */
    public static int getCurrentMinutes()
    {
        return( Calendar.getInstance().get(Calendar.MINUTE) );
    }


    /**
     * Gets the current seconds in hour.
     * <p>
     * @return  The current seconds in hour.
     */
    public static int getCurrentSeconds()
    {
        return( Calendar.getInstance().get(Calendar.SECOND) );
    }

}
