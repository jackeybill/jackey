/*
 * AutoResizeTableColumns.java
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

import java.awt.Component;
import java.awt.FontMetrics;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

/**
 * <b>
 * NOTE: This class needs a lot of testing in order to check all possible
 * use cases! Please use <code>setDebugMode(true)</code> and reports any bugs or
 * malfunctions to <a href="mailto:gregkotsaftis@yahoo.com">
 *gregkotsaftis@yahoo.com</a>
 * </b><p>
 *
 * This class can be used to dynamically resize a <code>JTable</code>, every
 * time it's data changes, based on header size and row data. It can also lock
 * any or all of the table's columns (no manual resize possible). Please note
 * that this class respects hidden columns (columns with all sizes set to zero)
 * and excludes them from the resize. Also take into consideration that if
 * performance is a must, you should avoid adding and removing single rows
 * in the table's model, to avoid multiple tableChanged() events leading to
 * multiple executions of this class. Instead you should store your data in
 * Vectors and pass them on to the table's model in a single step. Finally, you
 * can use the method <code>setDebugMode(true)</code> in order to understand
 * how this class functions and the results of your own application; note that
 * if you enable debuging, the performance will degrade by a major degree!
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.50
 */
public final class AutoResizeTableColumns implements TableModelListener {

    public static final int DEFAULT_COLUMN_PADDING = 5;

    private JTable m_table = null;
    private TableModel m_model = null;
    private int m_columnPadding = 0;
    private boolean m_includeHeaders = false;
    private boolean m_includeRows = false;
    private boolean[] m_lockColumns = null;
    private boolean m_debugMode = false;


    /**
     * Constructor. Uses default values (includes headers and rows calculations,
     * does not lock columns and column padding is set to 5 pixels).
     * <p>
     * @param tbl               The table instance.
     */
    public AutoResizeTableColumns(JTable tbl)
    {
        this(tbl, false);
    }


    /**
     * Constructor. Uses default values (includes headers and rows calculations,
     * column padding is set to 5 pixels).
     * <p>
     * @param tbl               The table instance.
     * @param allColumnsLock    true/false to lock/unlock all columns
     */
    public AutoResizeTableColumns(JTable tbl, boolean allColumnsLock)
    {
        this(tbl, tbl.getModel(), DEFAULT_COLUMN_PADDING, true, true,
            getBooleanArray(tbl.getColumnCount(), allColumnsLock) );
    }


    /**
     * Constructor.
     * <p>
     * @param tbl               The table instance.
     * @param mdl               The table model.
     * @param columnPadding     The pixels to use for column padding.
     * @param includeHeaders    true/false to include headers in calculation.
     * @param includeRows       true/false to include rows in calculation.
     * @param lockColumns       An array that contains the lock status of each
     *                          individual column.
     */
    public AutoResizeTableColumns(JTable tbl, TableModel mdl, int columnPadding,
            boolean includeHeaders, boolean includeRows, boolean[] lockColumns)
    {
        m_table = tbl;
        m_model = mdl;
        m_columnPadding = columnPadding;
        m_includeHeaders = includeHeaders;
        m_includeRows = includeRows;
        m_lockColumns = lockColumns;

        m_model.addTableModelListener( this );
    }


    /**
     * Sets debug mode.
     * <b>WARNING! Enabling this will slow down your application!</b>
     * <p>
     * @param debugMode         true/false enable or disable the debug output.
     */
    public void setDebugMode(boolean debugMode)
    {
        m_debugMode = debugMode;
    }


    /**
     * Implementation of TableModelListener.
     * <p>
     * @param tableModelEvent   The event.
     */
    public void tableChanged(TableModelEvent tableModelEvent)
    {
        printDebug("AutoResizeTableColumns::tableChanged(): started");

        int numColumns = m_table.getColumnCount();
        int numRows = m_model.getRowCount();

        printDebug("table columns: " + numColumns + " rows: " + numRows);

        // if no columns, nothing to do...
        if( numColumns > 0 )
        {
            // array to hold max widths for each column
            int[] columnsWidths = new int[ numColumns ];

            TableColumnModel tcMdl = m_table.getTableHeader().getColumnModel();

            int inter_cell_spacing = m_table.getIntercellSpacing().width;
            printDebug("inter cell spacing=" + inter_cell_spacing);

            int column_margin = tcMdl.getColumnMargin();
            printDebug("column margin=" + column_margin);

            int increment = m_columnPadding +
                            2 * inter_cell_spacing +
                            2 * column_margin;
            printDebug("total increment for each column=" + increment);

            for(int col=0; col<numColumns; col++)
            {
                printDebug("processing column: " + col);

                int max1 = 0; // for column header
                int max2 = 0; // for rows

                TableColumn tCol = tcMdl.getColumn( col );

                // respect hidden columns
                if( tCol.getMaxWidth()==0 &&
                    tCol.getMinWidth()==0 &&
                    tCol.getPreferredWidth()==0 )
                {
                    printDebug("\t" +
                        "this table column is hidden will be ignored");
                    max1 = 0;
                    max2 = 0;
                }
                else /* here we go... */
                {
                    // do we take the headers' sizes into consideration?
                    if( m_includeHeaders )
                    {
                        printDebug("\t" + "checking table header");

                        // minimize debug output to a single line
                        String col_debug = "\t";

                        TableCellRenderer tCelRndr = tCol.getHeaderRenderer();
                        if( tCelRndr != null )
                        {
                            col_debug += "FOUND header renderer; ";

                            Component comp =
                                tCelRndr.getTableCellRendererComponent(m_table,
                                    tCol.getHeaderValue(),
                                    false, false, -1, col);

                            col_debug += "renderer component is ";

                            if( comp instanceof JTextComponent )
                            {
                                col_debug += "a JTextComponent";

                                max1 = increment +
                                       getTextPixels((JTextComponent)comp);
                            }
                            else
                            {
                                col_debug += "NOT a JTextComponent";

                                max1 = increment +
                                       comp.getPreferredSize().width;
                            }
                        }
                        else
                        {
                            col_debug += "NO header renderer found; ";

                            Object headerValue = tCol.getHeaderValue();

                            if( headerValue instanceof String )
                            {
                                col_debug += "HeaderValue is a String";

                                String txt = (String)headerValue;
                                JLabel tmpLabel = new JLabel( txt );

                                max1 = increment + getTextPixels(tmpLabel, txt);
                            }
                            else
                            {
                                col_debug += "UNKNOWN HeaderValue type!";

                                // unable to figure out the pixels
                                max1 = increment + 0;
                            }
                        }

                        printDebug( col_debug );
                        printDebug("\t" +
                            "calculated size based on column header: " +
                            max1);
                    } // END OF HEADERS

                    // do we take the rows into consideration?
                    if( m_includeRows && numRows > 0 )
                    {
                        printDebug("\t" + "checking table rows");

                        for(int row=0; row<numRows; row++)
                        {
                            // minimize debug output to a single line
                            String row_debug = "\t" + "\t" + "row: " + row;

                            int tmpSize = 0;

                            TableCellRenderer tCelRndr =
                                m_table.getCellRenderer(row, col);

                            Component comp =
                                tCelRndr.getTableCellRendererComponent(m_table,
                                    m_model.getValueAt(row, col),
                                    false, false, row, col);

                            if( comp instanceof JTextComponent )
                            {
                                tmpSize = increment +
                                          getTextPixels((JTextComponent)comp);

                                row_debug += " size: " + tmpSize;
                                row_debug += " FOUND cell renderer;";
                                row_debug += " renderer component is";
                                row_debug += " a JTextComponent";
                            }
                            else
                            {
                                tmpSize = increment +
                                          comp.getPreferredSize().width;

                                row_debug += " size: " + tmpSize;
                                row_debug += " FOUND cell renderer;";
                                row_debug += " renderer component is";
                                row_debug += " NOT a JTextComponent";
                            }

                            printDebug( row_debug );

                            // calculate max of all rows in a column
                            max2 = Math.max(max2, tmpSize);
                        }

                        printDebug("\t" +
                            "calculated size based on column's rows: " +
                            max2);
                    } // END OF ROWS

                } /* END OF ELSE HIDDEN COLUMNS */

                columnsWidths[col] = Math.max(max1, max2);

                if( columnsWidths[col] > 0 )
                {
                    printDebug("resizing column: " + col + " size: " +
                        columnsWidths[col]);

                    resizeTableColumn(tCol, columnsWidths[col],
                        m_lockColumns[col]);
                }
                else
                {
                    printDebug("column: " + col + " was not resized");
                }

            } /* END OF COLUMNS LOOP */

        } /* END OF IF COLUMNS > 0 */

        printDebug("AutoResizeTableColumns::tableChanged(): finished");
    }


    private int getTextPixels(JTextComponent c)
    {
        FontMetrics f = c.getFontMetrics( c.getFont() );
        int pixels = f.stringWidth( c.getText() );

        return( pixels );
    }


    private int getTextPixels(Component c, String s)
    {
        FontMetrics f = c.getFontMetrics( c.getFont() );
        int pixels = f.stringWidth( s );

        return( pixels );
    }


    private void resizeTableColumn(TableColumn tc, int pixels, boolean lock)
    {
        tc.setPreferredWidth( pixels );

        if( lock )
        {
            tc.setMinWidth( pixels );
            tc.setMaxWidth( pixels );
            tc.setResizable( false );
        }
    }


    private void printDebug(String s)
    {
        if( m_debugMode )
        {
            System.out.println( s );
        }
    }


    private static boolean[] getBooleanArray(int size, boolean value)
    {
        boolean[] flags = new boolean[size];
        for(int i=0; i<size; i++)
        {
            flags[i] = value;
        }
        return( flags );
    }

}
