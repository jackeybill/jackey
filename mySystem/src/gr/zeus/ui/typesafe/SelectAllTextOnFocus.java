/*
 * SelectAllTextOnFocus.java
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
package gr.zeus.ui.typesafe;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.text.JTextComponent;

/**
 * This class is a simple FocusListener that when enabled performs a selectAll()
 * to any JTextComponent.
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.20
 */
public final class SelectAllTextOnFocus implements FocusListener {

    /**
     * Enable/Disable flag.
     */
    private boolean m_enabled = false;


    /**
     * Constructor.
     */
    public SelectAllTextOnFocus()
    {
    }


    /**
     * Gets status flag.
     * <p>
     * @return      true/false.
     */
    public boolean isEnabled()
    {
        return( m_enabled );
    }


    /**
     * Sets status flag.
     * <p>
     * @param f     true/false.
     */
    public void setEnabled(boolean f)
    {
        m_enabled = f;
    }


    /**
     * Focus gained event.
     * <p>
     * @param evt   The event.
     */
    public void focusGained(FocusEvent evt)
    {
        if( m_enabled && (evt.getComponent() instanceof JTextComponent) )
        {
            ((JTextComponent)evt.getComponent()).selectAll();
        }
    }


    /**
     * Focus lost event.
     * <p>
     * @param evt   The event.
     */
    public void focusLost(FocusEvent evt)
    {
        /* do nothing */
    }

}
