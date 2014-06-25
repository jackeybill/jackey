/*
 * OutLookToolBar.java
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
package gr.zeus.ui;

import java.awt.Component;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * Takes a simple <code>JToolBar</code> and transforms it into a
 * microsoft outlook like toolbar.
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.04
 */
public final class OutLookToolBar {

    /**
     * The toolbar to transform.
     */
    private JToolBar m_toolbar = null;


    /**
     * Constructor.
     * <p>
     * @param t     The <code>JToolBar</code>.
     */
    public OutLookToolBar(JToolBar t)
    {
        m_toolbar = t;

        if( m_toolbar == null )
        {
            throw new NullPointerException(
                "JToolBar instance required for OutLookToolBar is NULL!");
        }

        m_toolbar.addContainerListener( new MyContainerListener() );
    }


    /**
     * Private class to handle button add/remove.
     */
    private final class MyContainerListener implements ContainerListener {

        /**
         * Constructor, manually invoke transform() the first time.
         */
        public MyContainerListener()
        {
            transform();
        }


        /**
         * Transforms the toolbar.
         */
        private void transform()
        {
            if( m_toolbar != null )
            {
                int count = m_toolbar.getComponentCount();

                for(int i=0; i<count; i++)
                {
                    Component c = m_toolbar.getComponentAtIndex( i );

                    if( c instanceof JButton )
                    {
                        JButton b = (JButton) c;
                        b.setBorderPainted( false );
                        b.setFocusPainted( false );
                        b.addMouseListener( new MouseOver() );
                    }
                }
            }
        }


        /**
         * Called when we add a button in the toolbar.
         * Invokes transform() again to update the toolbar.
         * <p>
         * @param e     The <code>ContainerEvent</code>.
         */
        public void componentAdded(ContainerEvent e)
        {
            transform();
        }


        /**
         * Called when we remove a button from the toolbar.
         * Invokes transform() again to update the toolbar.
         * <p>
         * @param e     The <code>ContainerEvent</code>.
         */
        public void componentRemoved(ContainerEvent e)
        {
            m_toolbar.repaint();
        }

    }


    /**
     * Private class to handle the mouseover behavior.
     */
    private static final class MouseOver implements MouseListener {

        /**
         * Paints the buttons border when the mouse enters the button area.
         * <p>
         * @param e     The <code>MouseEvent</code>.
         */
        public void mouseEntered(MouseEvent e)
        {
            Component c = e.getComponent();
            if( c instanceof JButton )
            {
                JButton b = (JButton) c;
                b.setBorderPainted( true );
            }
        }


        /**
         * Removes the buttons border when the mouse exits the button area.
         * <p>
         * @param e     The <code>MouseEvent</code>.
         */
        public void mouseExited(MouseEvent e)
        {
            Component c = e.getComponent();
            if( c instanceof JButton )
            {
                JButton b = (JButton) c;
                b.setBorderPainted( false );
            }
        }


        /**
         * Unused.
         * <p>
         * @param e     The <code>MouseEvent</code>.
         */
        public void mouseClicked(MouseEvent e)
        {
        }


        /**
         * Unused.
         * <p>
         * @param e     The <code>MouseEvent</code>.
         */
        public void mousePressed(MouseEvent e)
        {
        }


        /**
         * Unused.
         * <p>
         * @param e     The <code>MouseEvent</code>.
         */
        public void mouseReleased(MouseEvent e)
        {
        }

    }

}
