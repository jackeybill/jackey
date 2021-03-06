/*
 * JConsole.java
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

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * The JFrame edition of <b>JConsolePane</b>.<br>
 * {@link JConsolePane}
 * <p>
 * @author Gregory Kotsaftis
 * @since 1.40
 */
public final class JConsole extends JFrame {

    /**
     * The single instance of JConsolePane.
     */
    private static JConsolePane s_consolePane = JConsolePane.getConsolePane();


    /**
     * Returns the instance of the <code>JConsolePane</code> for further
     * configuration.
     */
    public static JConsolePane getConsole()
    {
        return( s_consolePane );
    }


    /**
     * Constructor.
     * Creates a new JFrame and adds to it the single instance of JConsolePane.
     * Finally hides the frame until it is required to be seen.
     */
    public JConsole()
    {
        initComponents();

        setIconImage( new ImageIcon(JConsole.class.getResource(
            "/gr/zeus/res/console.gif")).getImage() );

        // attach a listener to watch for close button pressed
        s_consolePane.addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent evt)
            {
                // hide this frame also
                setVisible(false);
            }
        });

        // show console when frame is shown
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent evt)
            {
                // usually not needed unless you have manually invoked:
                // getConsole().hideConsole() for some reason.
                // Make sure the console is visible when this frame
                // is made visible.
                s_consolePane.setVisible(true);
            }
        });

        // no need for the console to be hidden inside a frame. We will control
        // the frame visibility instead.
        s_consolePane.setVisible(true);

        getContentPane().add(s_consolePane, BorderLayout.CENTER);
        pack();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("gr/zeus/res/jconsole"); // NOI18N
        setTitle(bundle.getString("titleMsg")); // NOI18N

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
