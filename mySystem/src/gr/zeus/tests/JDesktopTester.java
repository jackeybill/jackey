/*
 * JDesktopTester.java - Test application for WindowManager
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
package gr.zeus.tests;

import gr.zeus.ui.JIConsole;
import gr.zeus.ui.mdi.WindowManager;
import gr.zeus.util.GUIUtils;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class JDesktopTester extends javax.swing.JFrame {

    // an internal frame containing the console
    private JIConsole m_consoleInternalFrame = new JIConsole();

    private WindowManager m_windowManager = null;


    public JDesktopTester()
    {
        initComponents();

        // same as any other internal frame, add it to the desktop
        m_consoleInternalFrame.setPreferredSize( new Dimension(400, 400) );
        m_consoleInternalFrame.setBounds(50, 50, 450, 450);
        //m_consoleInternalFrame.setTitle( "Debug Window");
        m_consoleInternalFrame.setVisible( true );
        m_desktopPane.add(m_consoleInternalFrame, JLayeredPane.DEFAULT_LAYER);

        init_console();

        // open some frames BEFORE we attach our window manager, to prove
        // we can handle even those frames...
        JInternalFrame jif1 = new JInternalFrame();
        jif1.setTitle( null );
        jif1.getContentPane().add(new JLabel("null title"));
        jif1.setResizable( true );
        jif1.setMaximizable( true );
        jif1.setClosable( true );
        jif1.setIconifiable( true );
        jif1.setDefaultCloseOperation( JInternalFrame.DISPOSE_ON_CLOSE );
        jif1.setPreferredSize( new Dimension(200, 200) );
        jif1.setBounds(0, 0, 200, 200);
        jif1.setVisible( true );
        m_desktopPane.add(jif1, JLayeredPane.DEFAULT_LAYER);

        JInternalFrame jif2 = new JInternalFrame();
        jif2.setTitle( "" );
        jif2.getContentPane().add(new JLabel("empty title"));
        jif2.setResizable( true );
        jif2.setMaximizable( true );
        jif2.setClosable( true );
        jif2.setIconifiable( true );
        jif2.setDefaultCloseOperation( JInternalFrame.HIDE_ON_CLOSE );
        jif2.setPreferredSize( new Dimension(200, 200) );
        jif2.setBounds(20, 20, 220, 220);
        jif2.setVisible( true );
        m_desktopPane.add(jif2, JLayeredPane.DEFAULT_LAYER);

        // attach our window manager
        m_windowManager = new WindowManager(m_desktopPane, m_windowMenu);

        // display the default properties
        m_outlineDragModeMenuItem.setSelected(
                m_windowManager.getOutlineDragMode() );
        m_deiconifiablePolicyMenuItem.setSelected(
                m_windowManager.getDeiconifiablePolicy() );
        m_closePolicyMenuItem.setSelected( m_windowManager.getClosePolicy() );
        autoPositionMenuItem.setSelected(
                m_windowManager.getAutoPositionPolicy() );

        GUIUtils.maximizeJFrame( this );
    }


    /**
     * configure and start the JIConsole
     */
    private void init_console()
    {
        // just to demonstrate we are about to redirect the streams...
        System.out.println("Hello and goodbye from stdout");
        System.err.println("Hello and goodbye from stderr");

        JIConsole.getConsole().setBackgroundColor( Color.DARK_GRAY );
        JIConsole.getConsole().setForegroundColor( Color.YELLOW );
        JIConsole.getConsole().setSelectedTextColor( Color.DARK_GRAY );
        JIConsole.getConsole().setSelectionColor( Color.YELLOW );

        // if you want to modify the text/buttons/title/etc of JConsole,
        // or force it to autosave and clear the console's messages
        // you can do it here ... before starting the console!
        JIConsole.getConsole().setAppendFirstTime( false );
        JIConsole.getConsole().setAutoSave( true );

        // clear the console messages (only the textarea) when maxchars are
        // reached, because a lot of messages can slow down the textarea panel.
        //JIConsole.getConsole().setMaxChars( 10 * 1024 );

        // finally start the console
        JIConsole.getConsole().startConsole();

        // demonstrate that our console is up and running...
        System.out.println(
                "This is the very first message logged to JConsole (stdout)");
        System.err.println(
                "This is the very first message logged to JConsole (stderr)");
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        m_desktopPane = new javax.swing.JDesktopPane();
        javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem newJInternalFrameMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem countFramesMenuItem = new javax.swing.JMenuItem();
        showConsoleMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        m_windowMenu = new javax.swing.JMenu();
        m_outlineDragModeMenuItem = new javax.swing.JCheckBoxMenuItem();
        m_deiconifiablePolicyMenuItem = new javax.swing.JCheckBoxMenuItem();
        autoPositionMenuItem = new javax.swing.JCheckBoxMenuItem();
        m_closePolicyMenuItem = new javax.swing.JCheckBoxMenuItem();
        javax.swing.JSeparator jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JMenuItem cascadeMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem tileHMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem tileVMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem tileMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem nextMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem previousMenuItem = new javax.swing.JMenuItem();
        javax.swing.JSeparator jSeparator2 = new javax.swing.JSeparator();
        javax.swing.JMenuItem minimizeMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem minimizeAllMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem restoreMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem restoreAllMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem maximizeMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem maximizeAllMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem resetMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem resetAllMenuItem = new javax.swing.JMenuItem();
        javax.swing.JSeparator jSeparator3 = new javax.swing.JSeparator();
        javax.swing.JMenuItem hideMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem hideAllMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem closeMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem closeAllMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WindowManager Tester");
        getContentPane().add(m_desktopPane, java.awt.BorderLayout.CENTER);

        fileMenu.setText("File");

        newJInternalFrameMenuItem.setText("Open");
        newJInternalFrameMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                newJInternalFrameMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(newJInternalFrameMenuItem);

        countFramesMenuItem.setText("Count Frames");
        countFramesMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                countFramesMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(countFramesMenuItem);

        showConsoleMenuItem.setText("Show Console");
        showConsoleMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                showConsoleMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(showConsoleMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        m_windowMenu.setText("Window");

        m_outlineDragModeMenuItem.setText("Outline Drag Mode");
        m_outlineDragModeMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_outlineDragModeMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(m_outlineDragModeMenuItem);

        m_deiconifiablePolicyMenuItem.setText("De-iconify during cascade / tile");
        m_deiconifiablePolicyMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_deiconifiablePolicyMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(m_deiconifiablePolicyMenuItem);

        autoPositionMenuItem.setText("Auto Position Frames");
        autoPositionMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                autoPositionMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(autoPositionMenuItem);

        m_closePolicyMenuItem.setText("Default Close Operation vs Close");
        m_closePolicyMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_closePolicyMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(m_closePolicyMenuItem);
        m_windowMenu.add(jSeparator1);

        cascadeMenuItem.setText("Cascade");
        cascadeMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cascadeMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(cascadeMenuItem);

        tileHMenuItem.setText("Tile Horizontal");
        tileHMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                tileHMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(tileHMenuItem);

        tileVMenuItem.setText("Tile Vertical");
        tileVMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                tileVMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(tileVMenuItem);

        tileMenuItem.setText("Tile");
        tileMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                tileMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(tileMenuItem);

        nextMenuItem.setText("Next Window");
        nextMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nextMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(nextMenuItem);

        previousMenuItem.setText("Previous Window");
        previousMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                previousMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(previousMenuItem);
        m_windowMenu.add(jSeparator2);

        minimizeMenuItem.setText("Minimize");
        minimizeMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                minimizeMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(minimizeMenuItem);

        minimizeAllMenuItem.setText("Minimize All");
        minimizeAllMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                minimizeAllMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(minimizeAllMenuItem);

        restoreMenuItem.setText("Restore");
        restoreMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                restoreMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(restoreMenuItem);

        restoreAllMenuItem.setText("Restore All");
        restoreAllMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                restoreAllMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(restoreAllMenuItem);

        maximizeMenuItem.setText("Maximize");
        maximizeMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                maximizeMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(maximizeMenuItem);

        maximizeAllMenuItem.setText("Maximize All");
        maximizeAllMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                maximizeAllMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(maximizeAllMenuItem);

        resetMenuItem.setText("Reset Window");
        resetMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                resetMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(resetMenuItem);

        resetAllMenuItem.setText("Reset All Windows");
        resetAllMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                resetAllMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(resetAllMenuItem);
        m_windowMenu.add(jSeparator3);

        hideMenuItem.setText("Hide");
        hideMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                hideMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(hideMenuItem);

        hideAllMenuItem.setText("Hide All");
        hideAllMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                hideAllMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(hideAllMenuItem);

        closeMenuItem.setText("Close");
        closeMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                closeMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(closeMenuItem);

        closeAllMenuItem.setText("Close All");
        closeAllMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                closeAllMenuItemActionPerformed(evt);
            }
        });
        m_windowMenu.add(closeAllMenuItem);

        menuBar.add(m_windowMenu);

        setJMenuBar(menuBar);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-800)/2, (screenSize.height-600)/2, 800, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void showConsoleMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_showConsoleMenuItemActionPerformed
    {//GEN-HEADEREND:event_showConsoleMenuItemActionPerformed

        // same as any other internal frame, just make it visible
        m_consoleInternalFrame.setVisible( true );

    }//GEN-LAST:event_showConsoleMenuItemActionPerformed

    private void autoPositionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoPositionMenuItemActionPerformed

        m_windowManager.setAutoPositionPolicy(
                autoPositionMenuItem.isSelected() );

    }//GEN-LAST:event_autoPositionMenuItemActionPerformed

    private void m_closePolicyMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_closePolicyMenuItemActionPerformed
    {//GEN-HEADEREND:event_m_closePolicyMenuItemActionPerformed

        m_windowManager.setClosePolicy( m_closePolicyMenuItem.isSelected() );

    }//GEN-LAST:event_m_closePolicyMenuItemActionPerformed

    private void hideAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideAllMenuItemActionPerformed

        m_windowManager.hideAll();

    }//GEN-LAST:event_hideAllMenuItemActionPerformed

    private void m_outlineDragModeMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_outlineDragModeMenuItemActionPerformed
    {//GEN-HEADEREND:event_m_outlineDragModeMenuItemActionPerformed

        m_windowManager.setOutlineDragMode(
                m_outlineDragModeMenuItem.isSelected() );

    }//GEN-LAST:event_m_outlineDragModeMenuItemActionPerformed

    private void resetAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetAllMenuItemActionPerformed

        m_windowManager.resetAll();

    }//GEN-LAST:event_resetAllMenuItemActionPerformed

    private void resetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetMenuItemActionPerformed

        m_windowManager.reset();

    }//GEN-LAST:event_resetMenuItemActionPerformed

    private void maximizeAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maximizeAllMenuItemActionPerformed

        m_windowManager.maximizeAll();

    }//GEN-LAST:event_maximizeAllMenuItemActionPerformed

    private void restoreAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restoreAllMenuItemActionPerformed

        m_windowManager.restoreAll();

    }//GEN-LAST:event_restoreAllMenuItemActionPerformed

    private void countFramesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_countFramesMenuItemActionPerformed

        JOptionPane.showMessageDialog(this,
            "All Frames: " + m_windowManager.countFrames() +
            " Visible Frames: " + m_windowManager.countVisibleFrames(),
            "Info", JOptionPane.INFORMATION_MESSAGE);

        System.out.println("All Frames: " + m_windowManager.countFrames() +
            " Visible Frames: " + m_windowManager.countVisibleFrames());

    }//GEN-LAST:event_countFramesMenuItemActionPerformed

    private void m_deiconifiablePolicyMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_deiconifiablePolicyMenuItemActionPerformed
    {//GEN-HEADEREND:event_m_deiconifiablePolicyMenuItemActionPerformed

        m_windowManager.setDeiconifiablePolicy(
                m_deiconifiablePolicyMenuItem.isSelected() );

    }//GEN-LAST:event_m_deiconifiablePolicyMenuItemActionPerformed

    private void maximizeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maximizeMenuItemActionPerformed

        m_windowManager.maximize();

    }//GEN-LAST:event_maximizeMenuItemActionPerformed

    private void restoreMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restoreMenuItemActionPerformed

        m_windowManager.restore();

    }//GEN-LAST:event_restoreMenuItemActionPerformed

    private void minimizeAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizeAllMenuItemActionPerformed

        m_windowManager.minimizeAll();

    }//GEN-LAST:event_minimizeAllMenuItemActionPerformed

    private void minimizeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizeMenuItemActionPerformed

        m_windowManager.minimize();

    }//GEN-LAST:event_minimizeMenuItemActionPerformed

    private void closeAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeAllMenuItemActionPerformed

        m_windowManager.closeAll();

    }//GEN-LAST:event_closeAllMenuItemActionPerformed

    private void closeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuItemActionPerformed

        m_windowManager.close();

    }//GEN-LAST:event_closeMenuItemActionPerformed

    private void hideMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hideMenuItemActionPerformed

        m_windowManager.hide();

    }//GEN-LAST:event_hideMenuItemActionPerformed

    private void previousMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousMenuItemActionPerformed

        m_windowManager.selectPrevious();

    }//GEN-LAST:event_previousMenuItemActionPerformed

    private void nextMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextMenuItemActionPerformed

        m_windowManager.selectNext();

    }//GEN-LAST:event_nextMenuItemActionPerformed

    private void tileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tileMenuItemActionPerformed

        m_windowManager.tile();

    }//GEN-LAST:event_tileMenuItemActionPerformed

    private void tileVMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tileVMenuItemActionPerformed

        m_windowManager.tileVertically();

    }//GEN-LAST:event_tileVMenuItemActionPerformed

    private void tileHMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tileHMenuItemActionPerformed

        m_windowManager.tileHorizontally();

    }//GEN-LAST:event_tileHMenuItemActionPerformed

    private void cascadeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cascadeMenuItemActionPerformed

        m_windowManager.cascade();

    }//GEN-LAST:event_cascadeMenuItemActionPerformed

    private void newJInternalFrameMenuItemActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_newJInternalFrameMenuItemActionPerformed
    {//GEN-HEADEREND:event_newJInternalFrameMenuItemActionPerformed

        JInternalFrameGenerator gen = new JInternalFrameGenerator(
                                            new JFrame(), true);
        gen.setVisible(true);

        int ret = gen.getReturnStatus();
        if( ret == gen.RET_OK )
        {
            JInternalFrame jif = gen.getNewFrame();
            m_desktopPane.add(jif, JLayeredPane.DEFAULT_LAYER);
            jif.toFront();
        }

        System.out.println("Created new JInternalFrame");

    }//GEN-LAST:event_newJInternalFrameMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed

        System.out.println("Exiting...");
        System.exit(0);

    }//GEN-LAST:event_exitMenuItemActionPerformed


    /**
     * setup our look and feel
     */
    private static void init_lnf()
    {
        try
        {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName() );

            // NOTE: since the JConsolePane is actually a singleton, if we use
            // any LOOK & FEEL other than the default, we also need to invoke
            // this method to update the console's lnf...
            JIConsole.getConsole().updateLNF();
        }
        catch( Exception e )
        {
            System.err.println("Cannot initialize the look & feel");
            e.printStackTrace();
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        init_lnf();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JDesktopTester().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem autoPositionMenuItem;
    private javax.swing.JCheckBoxMenuItem m_closePolicyMenuItem;
    private javax.swing.JCheckBoxMenuItem m_deiconifiablePolicyMenuItem;
    private javax.swing.JDesktopPane m_desktopPane;
    private javax.swing.JCheckBoxMenuItem m_outlineDragModeMenuItem;
    private javax.swing.JMenu m_windowMenu;
    private javax.swing.JMenuItem showConsoleMenuItem;
    // End of variables declaration//GEN-END:variables

}
