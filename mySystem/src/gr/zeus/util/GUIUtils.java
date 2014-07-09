/*
 * GUIUtils.java
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

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JFrame;
import javax.swing.table.TableColumn;

/**
 * GUI Helper class, contains static methods that are used all the time.
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.0
 */
public final class GUIUtils {

    /**
     * Execute a task on EDT and block's it, until the task is finished.
     * If there is an exception, it is converted to a RuntimeException.
     *
     * @param task The process to run.
     */
    public static void invokeAndWait(final Runnable task)
    {
        if( EventQueue.isDispatchThread() )
        {
            task.run();
        }
        else
        {
            try
            {
                EventQueue.invokeAndWait( task );
            }
            catch(Exception ex)
            {
                throw new RuntimeException(ex.getMessage(), ex);
            }
        }
    }

    /**
     * Centers a window on screen.
     * <p>
     * @param w     The window to center.
     */
    public static void centerOnScreen(Window w)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension splashSize = w.getPreferredSize();
        w.setLocation(screenSize.width / 2 - (splashSize.width / 2),
                      screenSize.height / 2 - (splashSize.height / 2));
    }


    /**
     * Maximizes a JFrame, just like the 'maximize window' button does.
     * <p>
     * @param f     The frame to maximize.
     */
    public static void maximizeJFrame(JFrame f)
    {
        f.setExtendedState( Frame.MAXIMIZED_BOTH );
    }


    /**
     * Locks a Jtable's column width with 'pixels' size.
     * <p>
     * @param tc    	The table column.
     * @param pixels 	The desired pixels.
     */
    public static void lockJTableColumnWidth(TableColumn tc, int pixels)
    {
        if( tc!=null )
        {
            tc.setMinWidth( pixels );
            tc.setMaxWidth( pixels );
            tc.setPreferredWidth( pixels );
            tc.setResizable( false );
        }
    }


    /**
     * Hides a specific column of a JTable.
     * <p>
     * @param tc    	The table column.
     */
    public static void hideJTableColumn(TableColumn tc)
    {
        lockJTableColumnWidth(tc, 0);
    }

}
