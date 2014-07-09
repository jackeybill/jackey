/*
 * SimpleTimerNanos.java
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
 * A very simple timer for timing java method calls and other processes
 * (JDK 5, nanos implementation).
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.06
 */
public final class SimpleTimerNanos {

    /**
     * Used to get the call time.
     */
    private long m_nanos = 0L;


    /**
     * Constructor.
     */
    public SimpleTimerNanos()
    {
    }


    /**
     * Resets the timer.
     */
    public synchronized void reset()
    {
        m_nanos = 0L;
    }


    /**
     * Starts timing a job.
     */
    public synchronized void start()
    {
        // Get current time in milliseconds
        m_nanos = System.nanoTime();
    }


    /**
     * Gets elapsed time in nanoseconds.
     * <p>
     * @return  The elapsed time in nanoseconds.
     */
    public long elapsedNanos()
    {
        // Get elapsed time in nanoseconds
        long elapsed = System.nanoTime() - m_nanos;
        return( elapsed );
    }


    /**
     * Gets elapsed time in milliseconds.
     * <p>
     * @return  The elapsed time in milliseconds.
     */
    public double elapsedMillis()
    {
        double elapsed = (double) elapsedNanos() / 1000000.0;
        return( elapsed );
    }


    /**
     * Gets elapsed time in seconds.
     * <p>
     * @return  The elapsed time in seconds.
     */
    public double elapsedSeconds()
    {
        // Get elapsed time in seconds
        double elapsed = elapsedMillis() / 1000.0;
        return( elapsed );
    }

}
