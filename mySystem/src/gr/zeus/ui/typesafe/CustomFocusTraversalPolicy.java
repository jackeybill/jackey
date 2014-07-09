/*
 * CustomFocusTraversalPolicy.java
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

import java.awt.Component;
import java.awt.Container;
import javax.swing.LayoutFocusTraversalPolicy;

/**
 * This class creates a custom focus policy for Swing components by extending
 * LayoutFocusTraversalPolicy. The focus policy is based upon an array of
 * components. These components are focused as they are placed in the array:
 * array[0] --> array[1] --> ... ---> array[last] ---> array[0]
 * Only components in the array are focused, all other components only gain
 * focus if you place the mouse pointer inside. This is a pretty clean and
 * solid solution to the Swing Focus Issue, at least until Sun provides us
 * with a better one.
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.20
 */
public final class CustomFocusTraversalPolicy
        extends LayoutFocusTraversalPolicy {

    /**
     * The components array.
     */
    private Component[] m_allComponents = null;


    /**
     * Constructor.
     * <p>
     * @param all           The array with the components.
     */
    public CustomFocusTraversalPolicy(Component[] all)
    {
        m_allComponents = (all!=null) ? all : new Component[]{};
    }


    /**
     * Gets the next focusable component.
     * <p>
     * @param aContainer    The swing container, e.g. a JFrame.
     * @param aComponent    The component to calculate focus.
     * <p>
     * @return              The next focusable component.
     */
    public Component getComponentAfter(Container aContainer,
            Component aComponent)
    {
        Component ret = null;
        int index = -1;

        for(int i=0; i<m_allComponents.length; i++)
        {
            if( aComponent == m_allComponents[i] )
            {
                if( i == m_allComponents.length - 1 )
                {
                    index = 0;
                    ret = m_allComponents[ 0 ];
                }
                else
                {
                    index = i + 1;
                    ret = m_allComponents[ i + 1 ];
                }

                break;
            }
        }

        // if we found the next component but is disabled
        // find next enabled component.
        if( ret != null && !ret.isEnabled() )
        {
            int j = index;
            for(;;)
            {
                // get next component
                if( j == m_allComponents.length - 1 )
                {
                    j = 0;
                }
                else
                {
                    j++;
                }

                if( j == index )
                {
                    // none found!
                    break;
                }

                if( m_allComponents[j].isEnabled() )
                {
                    // found a component
                    ret = m_allComponents[j];
                    break;
                }
            }
        }
        //---

        return( (ret != null) ? ret : super.getComponentAfter(aContainer,
                aComponent) );
    }


    /**
     * Gets the previous focusable component.
     * <p>
     * @param aContainer    The swing container, e.g. a JFrame.
     * @param aComponent    The component to calculate focus.
     * <p>
     * @return              The previous focusable component.
     */
    public Component getComponentBefore(Container aContainer,
            Component aComponent)
    {
        Component ret = null;
        int index = -1;

        for(int i=0; i<m_allComponents.length; i++)
        {
            if( aComponent == m_allComponents[i] )
            {
                if( i == 0 )
                {
                    index = m_allComponents.length - 1;
                    ret = m_allComponents[ m_allComponents.length - 1 ];
                }
                else
                {
                    index = i - 1;
                    ret = m_allComponents[ i - 1 ];
                }

                break;
            }
        }

        // if we found the next component but is disabled
        // find next enabled component.
        if( ret != null && !ret.isEnabled() )
        {
            int j = index;
            for(;;)
            {
                // get next component
                if( j == 0 )
                {
                    j = m_allComponents.length - 1;
                }
                else
                {
                    j--;
                }

                if( j == index )
                {
                    // none found!
                    break;
                }

                if( m_allComponents[j].isEnabled() )
                {
                    // found a component
                    ret = m_allComponents[j];
                    break;
                }
            }
        }
        //---

        return( (ret != null) ? ret : super.getComponentBefore(aContainer,
                aComponent) );
    }


    /**
     * Gets the first focusable component.
     * <p>
     * @param aContainer    The swing container, e.g. a JFrame.
     * <p>
     * @return              The first focusable component.
     */
    public Component getFirstComponent(Container aContainer)
    {
        Component ret = null;
        for(int i=0; i<m_allComponents.length; i++)
        {
            Component c = m_allComponents[ i ];

            // if component is disabled, continue to find the
            // next enabled component.
            if( c.isEnabled() )
            {
                ret = c;
                break;
            }
        }

        return( (ret != null) ? ret : super.getFirstComponent(aContainer) );
    }


    /**
     * Gets the last focusable component.
     * <p>
     * @param aContainer    The swing container, e.g. a JFrame.
     * <p>
     * @return              The last focusable component.
     */
    public Component getLastComponent(Container aContainer)
    {
        Component ret = null;
        if( m_allComponents.length > 0 )
        {
            for(int i=m_allComponents.length-1; i>=0; i--)
            {
                Component c = m_allComponents[ i ];

                // if component is disabled, continue to find the
                // next enabled component.
                if( c.isEnabled() )
                {
                    ret = c;
                    break;
                }
            }
        }

        return( (ret != null) ? ret : super.getLastComponent(aContainer) );
    }

}
