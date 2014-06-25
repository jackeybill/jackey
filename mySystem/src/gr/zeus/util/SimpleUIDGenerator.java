/*
 * SimpleUIDGenerator.java
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
 * Generates unique ID's within this JVM.
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.0
 */
public final class SimpleUIDGenerator {

    /**
     * Used to generate the unique ID.
     */
    private static long m_nextUID = 0;


    /**
     * Always returns a unique number > 0 for the existing VM.
     * This method IS thread safe.
     * <p>
     * @return  A unique number > 0 for the existing VM.
     */
    public static synchronized long getNextUID()
    {
        m_nextUID++;
        return( m_nextUID );
    }

}
