/*
 * JMessageTester.java - Test application for JMessage(s)
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

import gr.zeus.ui.JMessage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class JMessageTester extends JFrame {


    public JMessageTester()
    {
        initComponents();
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        m_errorButton = new javax.swing.JButton();
        m_warningButton = new javax.swing.JButton();
        m_infoButton = new javax.swing.JButton();
        m_questionButton = new javax.swing.JButton();
        m_plainButton = new javax.swing.JButton();
        m_plainButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JMessageTester");
        getContentPane().setLayout(null);

        m_errorButton.setText("ERROR");
        m_errorButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_errorButtonActionPerformed(evt);
            }
        });
        getContentPane().add(m_errorButton);
        m_errorButton.setBounds(10, 130, 110, 21);

        m_warningButton.setText("WARNING");
        m_warningButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_warningButtonActionPerformed(evt);
            }
        });
        getContentPane().add(m_warningButton);
        m_warningButton.setBounds(10, 70, 110, 21);

        m_infoButton.setText("INFO");
        m_infoButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_infoButtonActionPerformed(evt);
            }
        });
        getContentPane().add(m_infoButton);
        m_infoButton.setBounds(10, 40, 110, 21);

        m_questionButton.setText("QUESTION");
        m_questionButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_questionButtonActionPerformed(evt);
            }
        });
        getContentPane().add(m_questionButton);
        m_questionButton.setBounds(10, 100, 110, 21);

        m_plainButton.setText("PLAIN");
        m_plainButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_plainButtonActionPerformed(evt);
            }
        });
        getContentPane().add(m_plainButton);
        m_plainButton.setBounds(10, 10, 110, 21);

        m_plainButton2.setText("PLAIN+ICO");
        m_plainButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_plainButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(m_plainButton2);
        m_plainButton2.setBounds(130, 10, 110, 21);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-310)/2, (screenSize.height-198)/2, 310, 198);
    }// </editor-fold>//GEN-END:initComponents

    private void m_plainButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_plainButton2ActionPerformed
    {//GEN-HEADEREND:event_m_plainButton2ActionPerformed

        // a fully customizable message
        JMessage.showMessageDialog(
            this,
            "A message with a custom icon.",
            "Hello...", // custom title
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            null, // use default button for this type of dialog
            new ImageIcon(JMessageTester.class.getResource( // custom icon
                "/gr/zeus/tests/res/custom.png")),
            null, // no exception functionality
            "...World", // custom OK button text
            null, // default button text
            null, // default button text
            null); // default button text

    }//GEN-LAST:event_m_plainButton2ActionPerformed

    private void m_questionButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_questionButtonActionPerformed
    {//GEN-HEADEREND:event_m_questionButtonActionPerformed

        int result = JMessage.showMessageDialog(
                            this,
                            "A question that it's default button is NO",
                            JOptionPane.QUESTION_MESSAGE,
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JMessage.JMessageButtonEnum.NO
                        );

        switch( result ) {
            case JOptionPane.CANCEL_OPTION:
                System.out.println("CANCEL"); break;

            case JOptionPane.YES_OPTION:
                System.out.println("YES"); break;

            case JOptionPane.NO_OPTION:
                System.out.println("NO"); break;

            case JOptionPane.CLOSED_OPTION:
                System.out.println("CLOSED"); break;

            default:
                System.out.println("Unknown button!"); break;
        }

    }//GEN-LAST:event_m_questionButtonActionPerformed

    private void m_warningButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_warningButtonActionPerformed
    {//GEN-HEADEREND:event_m_warningButtonActionPerformed

        String s = "100.24";
        try
        {
            int num = Integer.parseInt(s);
        }
        catch(Exception ex)
        {
            /* All messages should begin with '<html>' and end with '</html>',
             * if not, JMessage automatically converts the plain text to html.
             * You can also mix plain text and html text together!
             * '\n' is transformed to '<br>'
             */
            JMessage.showMessageDialog(
                this,
                "A warning message with an <font color=\"#0066ff\">"+
                    "<i>exception</i></font>." + "\n" +
                    "Cannot parse <code>java.lang.Integer</code> number<br>" +
                    "<b>" + ex.getMessage() + "</b>",
                JOptionPane.WARNING_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                ex);
        }

    }//GEN-LAST:event_m_warningButtonActionPerformed

    private void m_plainButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_plainButtonActionPerformed
    {//GEN-HEADEREND:event_m_plainButtonActionPerformed

        JMessage.showMessageDialog(
            this,
            "A plain message.",
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION);

    }//GEN-LAST:event_m_plainButtonActionPerformed

    private void m_infoButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_infoButtonActionPerformed
    {//GEN-HEADEREND:event_m_infoButtonActionPerformed

        boolean acknowledge = false;
        while( !acknowledge )
        {
            int result = JMessage.showMessageDialog(
                            this,
                            "An info message. Acknowledge?" +
                                "<br>Close the dialog to see what happens...",
                            JOptionPane.INFORMATION_MESSAGE,
                            JOptionPane.YES_NO_OPTION);

            switch( result ) {
                case JOptionPane.YES_OPTION:
                    acknowledge = true;
                    System.out.println("YES"); break;

                case JOptionPane.NO_OPTION:
                    acknowledge = true;
                    System.out.println("NO"); break;
            }
        }

    }//GEN-LAST:event_m_infoButtonActionPerformed

    private void m_errorButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_errorButtonActionPerformed
    {//GEN-HEADEREND:event_m_errorButtonActionPerformed

        try
        {
            String s = null;
            s.length();
        }
        catch(Exception ex)
        {
            JMessage.showMessageDialog(
                this,
                "You really did it this time!",
                JOptionPane.ERROR_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                ex);
        }

    }//GEN-LAST:event_m_errorButtonActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        // if you enable this, also enable the CrossPlatformLookAndFeel or
        // use JGoodies lnf
        //
        //JFrame.setDefaultLookAndFeelDecorated( true );
        //JDialog.setDefaultLookAndFeelDecorated( true );
        try
        {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName() );
            //UIManager.setLookAndFeel(
            //      UIManager.getCrossPlatformLookAndFeelClassName() );
        }
        catch( Exception e )
        {
            System.err.println("Cannot initialize the look & feel");
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new JMessageTester().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton m_errorButton;
    private javax.swing.JButton m_infoButton;
    private javax.swing.JButton m_plainButton;
    private javax.swing.JButton m_plainButton2;
    private javax.swing.JButton m_questionButton;
    private javax.swing.JButton m_warningButton;
    // End of variables declaration//GEN-END:variables

}