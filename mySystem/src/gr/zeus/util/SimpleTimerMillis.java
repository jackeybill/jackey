/*
 * SimpleTimerMillis.java
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

/**
 * A very simple timer for timing java method calls and other processes.
 * Millisecond precision.
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.05
 */
public final class SimpleTimerMillis {

    /**
     * Used to time the calls.
     */
    private long m_millis = 0L;


    /**
     * Constructor.
     */
    public SimpleTimerMillis()
    {
    }


    /**
     * Resets the timer.
     */
    public synchronized void reset()
    {
        m_millis = 0L;
    }


    /**
     * Starts timing a job.
     */
    public synchronized void start()
    {
        // Get current time in milliseconds
        m_millis = System.currentTimeMillis();
    }


    /**
     * Get elapsed time in milliseconds.
     * <p>
     * @return  The elapsed time in milliseconds.
     */
    public long elapsedMillis()
    {
        // Get elapsed time in milliseconds
        long elapsed = System.currentTimeMillis() - m_millis;
        return( elapsed );
    }


    /**
     * Get elapsed time in seconds.
     * <p>
     * @return  The elapsed time in seconds.
     */
    public double elapsedSeconds()
    {
        // Get elapsed time in seconds
        double elapsed = (double) elapsedMillis() / 1000.0;
        return( elapsed );
    }

}
