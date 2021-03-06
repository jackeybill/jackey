/*
 * JConsoleTester.java - Test application for JConsole
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

import gr.zeus.ui.JConsole;
import gr.zeus.ui.JMessage;
import gr.zeus.util.GUIUtils;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class JConsoleTester extends JFrame {

    // an new frame containing the console
    private JConsole m_consoleFrame = new JConsole();

    private boolean m_realExit = true;


    public JConsoleTester()
    {
        initComponents();

        GUIUtils.centerOnScreen( this );
    }


    /**
     * Just to be able to use the JTester which is a wrapper for all
     * the demos. If we use the JTester we also setExitMode( false ),
     * else the default is for each individual test to System.exit()
     * by it's own...
     */
    public void setExitMode(boolean exit)
    {
        m_realExit = exit;
    }


    /**
     * provide our own exit() method in order to be able to
     * save the trace file when our application aborts it's normal
     * execution and we don't have the time to see the JConsole for
     * error details...
     */
    private void myExit(int code)
    {
        if( code!=0 )
        {
            // dump all messages printed to a new logfile if you wish!
            try
            {
                JConsole.getConsole().dumpConsole("dump.log", false);
            }
            catch(IOException ex)
            {
                JMessage.showErrorMessage(this, "Error saving logfile!", ex);
            }

            JMessage.showErrorMessage(this, "Application Error!\nCheck dump.log for details!");

        }

        // actually not really needed if we are to invoke exit() next...
        JConsole.getConsole().destroyConsole();

        // ...just to demonstrate the actual restore of stdout, stderr
        System.out.println("Hello again from stdout");
        System.err.println("Hello again from stderr");

        if( m_realExit )
        {
            System.exit( code );
        }
        else
        {
            setVisible( false );
            dispose();
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        javax.swing.JPanel panel1 = new javax.swing.JPanel();
        javax.swing.JButton helloButton = new javax.swing.JButton();
        javax.swing.JButton errorButton = new javax.swing.JButton();
        javax.swing.JButton exceptionButton = new javax.swing.JButton();
        javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem consoleMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem errorExitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem normalExitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Sample Application to demonstrate JConsole");

        helloButton.setText("hello");
        helloButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                helloButtonActionPerformed(evt);
            }
        });
        panel1.add(helloButton);

        errorButton.setText("error");
        errorButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                errorButtonActionPerformed(evt);
            }
        });
        panel1.add(errorButton);

        exceptionButton.setText("exception");
        exceptionButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exceptionButtonActionPerformed(evt);
            }
        });
        panel1.add(exceptionButton);

        getContentPane().add(panel1, java.awt.BorderLayout.SOUTH);

        fileMenu.setText("File");

        consoleMenuItem.setText("Show Console");
        consoleMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                consoleMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(consoleMenuItem);

        errorExitMenuItem.setText("Exit with error code");
        errorExitMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                errorExitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(errorExitMenuItem);

        normalExitMenuItem.setText("Normal Exit");
        normalExitMenuItem.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                normalExitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(normalExitMenuItem);

        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exceptionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exceptionButtonActionPerformed

        // fake an exception
        String numS = "this is not a number!";

        // this will raise an runtime exception
        int num = Integer.parseInt( numS );

        // actually never executed...
        System.out.println("num=" + num);

    }//GEN-LAST:event_exceptionButtonActionPerformed

    private void errorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_errorButtonActionPerformed

        System.err.println("Hello new stderr!");

    }//GEN-LAST:event_errorButtonActionPerformed

    private void helloButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helloButtonActionPerformed

        System.out.println("Hello new stdout!");

    }//GEN-LAST:event_helloButtonActionPerformed

    private void normalExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalExitMenuItemActionPerformed

        myExit( 0 );

    }//GEN-LAST:event_normalExitMenuItemActionPerformed

    private void errorExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_errorExitMenuItemActionPerformed

        myExit( 1 );

    }//GEN-LAST:event_errorExitMenuItemActionPerformed

    private void consoleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_consoleMenuItemActionPerformed

        // just like any other frame
        m_consoleFrame.setVisible( true );

    }//GEN-LAST:event_consoleMenuItemActionPerformed


    /**
     * configure and start the JConsole
     */
    private static void init_console()
    {
        // just to demonstrate we are about to redirect the streams...
        System.out.println("Hello and goodbye from stdout");
        System.err.println("Hello and goodbye from stderr");

        // if you want to modify the text/buttons/title/etc of JConsole,
        // or force it to autosave and clear the console's messages
        // you can do it here ... before starting the console!
        JConsole.getConsole().setAppendFirstTime( false );
        JConsole.getConsole().setAutoSave( true );

        // clear the console messages (only the textarea) when maxchars are
        // reached, because a lot of messages can slow down the textarea panel.
        //s_console.setMaxChars( 10 * 1024 );

        // finally start the console
        JConsole.getConsole().startConsole();

        // demonstrate that our console is up and running...
        System.out.println(
                "This is the very first message logged to JConsole (stdout)");
        System.err.println(
                "This is the very first message logged to JConsole (stderr)");
    }


    /**
     * setup our look and feel
     */
    private static void init_lnf()
    {
        try
        {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName() );

            // NOTE: since the JConsole is actually a singleton, if we use
            // any LOOK & FEEL other than the default, we also need to invoke
            // this method to update the console's lnf...
            JConsole.getConsole().updateLNF();
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

        // since the actual console is only one (singleton), we might as well
        // configure it here...
        init_console();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JConsoleTester().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
