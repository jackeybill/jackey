/*
 * ProgressBarCellRenderer.java
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
package gr.zeus.ui.table;

import java.awt.Color;
import java.awt.Component;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * Progress Bar Cell Renderer for JTable. Original code from:
 * <a href="http://www.senun.com/Left/Programming/Java_old/Examples_swing/
 *SwingExamples.html">http://www.senun.com/Left/Programming/Java_old/
 *Examples_swing/SwingExamples.html</a>
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.02
 */
public final class ProgressBarCellRenderer extends JProgressBar
    implements TableCellRenderer {

    /**
     * Used to get colours.
     */
    private Hashtable m_limitColors;

    /**
     * Used to get values.
     */
    private int[] m_limitValues;


    /**
     * Constructor.
     */
    public ProgressBarCellRenderer()
    {
        this(false, true, 0, 100, new Hashtable(), Color.WHITE);
    }


    /**
     * Constructor.
     * <p>
     * @param paintNum      <code>true</code> or <code>false</code>.
     * @param paintBorder   <code>true</code> or <code>false</code>.
     * @param min           Minimum value.
     * @param max           Maximum value.
     * @param limitColors   <code>Hashtable</code> of colors.
     * @param bg            The <code>Color</code>.
     */
    public ProgressBarCellRenderer(boolean paintNum, boolean paintBorder,
        int min, int max, Hashtable limitColors, Color bg)
    {
        super(SwingConstants.HORIZONTAL, min, max);
        setStringPainted( paintNum );
        setBorderPainted( paintBorder );
        setBackground( bg );
        setLimits( limitColors );
    }


    /**
     * Gets the table cell renderer component.
     * <p>
     * @param table         The <code>JTable</code>.
     * @param value         The <code>Object</code>.
     * @param isSelected    <code>true</code> if selected.
     * @param hasFocus      <code>true</code> if has focus.
     * @param row           The row number.
     * @param column        The column number.
     * <p>
     * @return              The table cell renderer <code>Component</code>.
     */
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column)
    {
        int n = 0;

        if( !(value instanceof Number) )
        {
            String str;
            if( value instanceof String )
            {
                str = (String) value;
            }
            else
            {
                str = value.toString();
            }

            try
            {
                n = Integer.valueOf(str).intValue();
            }
            catch(NumberFormatException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            n = ((Number) value).intValue();
        }

        Color color = getColor( n );
        if( color != null )
        {
            setForeground( color );
        }

        setValue( n );

        return( this );
    }


    /**
     * Sets the limits.
     * <p>
     * @param limitColors   <code>Hashtable</code>.
     */
    public void setLimits(Hashtable limitColors)
    {
        m_limitColors = limitColors;

        if( m_limitColors!=null )
        {
            int i=0;
            int n = m_limitColors.size();
            m_limitValues = new int[n];
            Enumeration e = m_limitColors.keys();
            while( e.hasMoreElements() )
            {
                m_limitValues[i++] = ((Integer) e.nextElement()).intValue();
            }

            sort( m_limitValues );
        }
    }


    /**
     * Gets the colour.
     * <p>
     * @param value     The value to get the colour from.
     * <p>
     * @return          The <code>Color</code>.
     */
    private Color getColor(int value)
    {
        Color color = null;
        if( m_limitValues != null )
        {
            for(int i=0; i<m_limitValues.length; i++)
            {
                if( m_limitValues[i] < value )
                {
                    color = (Color) m_limitColors.get(
                                        new Integer( m_limitValues[i] ) );
                }
            }
        }

        return( color );
    }


    /**
     * Sort method.
     * <p>
     * @param a     Array to sort.
     */
    private static void sort(int[] a)
    {
        int n = a.length;
        for(int i=0; i<n-1; i++)
        {
            int k = i;
            for(int j=i+1; j<n; j++)
            {
                if( a[j] < a[k] )
                {
                    k = j;
                }
            }

            int tmp = a[i];
            a[i] = a[k];
            a[k] = tmp;
        }
    }

}
