/*
 * JInternalFrameGenerator.java - Used by JDesktopTester.java to create frames.
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

import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;

public class JInternalFrameGenerator extends JDialog {

    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;

    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;

    private int returnStatus = RET_CANCEL;

    private static int s_counter = 0;


    public JInternalFrameGenerator(Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();

        // set JDialog's default button
        getRootPane().setDefaultButton( m_okButton );

        s_counter++;
    }


    public final JInternalFrame getNewFrame()
    {
        int[] options = new int[] {
            JInternalFrame.DISPOSE_ON_CLOSE,
            JInternalFrame.HIDE_ON_CLOSE,
            JInternalFrame.DO_NOTHING_ON_CLOSE
        };
        String[] options_s = new String[] {
            "DISPOSE_ON_CLOSE",
            "HIDE_ON_CLOSE",
            "DO_NOTHING_ON_CLOSE"
        };

        JInternalFrame jif = new JInternalFrame();

        jif.setPreferredSize( new Dimension(600, 300) );
        jif.setMaximumSize( new Dimension(600, 300) );
        jif.setMinimumSize( new Dimension(600, 300) );
        jif.setBounds(10, 10, 600, 300);

        jif.setResizable( m_resizableCheckBox.isSelected() );
        jif.setMaximizable( m_maximizableCheckBox.isSelected() );
        jif.setClosable( m_closableCheckBox.isSelected() );
        jif.setIconifiable( m_iconifiableCheckBox.isSelected() );
        jif.setDefaultCloseOperation(
                options[m_closeOperationComboBox.getSelectedIndex()] );
        jif.setVisible( m_visibleCheckBox.isSelected() );

        String s1 = jif.isResizable() ? "resizable " : "";
        String s2 = jif.isMaximizable() ? "maximizable " : "";
        String s3 = jif.isClosable() ? "closable " : "";
        String s4 = jif.isIconifiable() ? "iconifiable " : "";
        String s5 = options_s[ m_closeOperationComboBox.getSelectedIndex() ];

        jif.setTitle(String.valueOf(s_counter) + ": " + s1 + s2 + s3 + s4 + s5);

        return( jif );
    }


    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus()
    {
        return( returnStatus );
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        m_resizableCheckBox = new javax.swing.JCheckBox();
        m_maximizableCheckBox = new javax.swing.JCheckBox();
        m_closableCheckBox = new javax.swing.JCheckBox();
        m_iconifiableCheckBox = new javax.swing.JCheckBox();
        m_visibleCheckBox = new javax.swing.JCheckBox();
        javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
        m_closeOperationComboBox = new javax.swing.JComboBox();
        m_cancelButton = new javax.swing.JButton();
        m_okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        m_resizableCheckBox.setSelected(true);
        m_resizableCheckBox.setText("Resizable");
        getContentPane().add(m_resizableCheckBox);
        m_resizableCheckBox.setBounds(10, 10, 63, 18);

        m_maximizableCheckBox.setSelected(true);
        m_maximizableCheckBox.setText("Maximizable");
        getContentPane().add(m_maximizableCheckBox);
        m_maximizableCheckBox.setBounds(10, 40, 75, 18);

        m_closableCheckBox.setSelected(true);
        m_closableCheckBox.setText("Closable");
        getContentPane().add(m_closableCheckBox);
        m_closableCheckBox.setBounds(10, 70, 58, 18);

        m_iconifiableCheckBox.setSelected(true);
        m_iconifiableCheckBox.setText("Iconifiable");
        getContentPane().add(m_iconifiableCheckBox);
        m_iconifiableCheckBox.setBounds(10, 100, 67, 18);

        m_visibleCheckBox.setSelected(true);
        m_visibleCheckBox.setText("Visible");
        getContentPane().add(m_visibleCheckBox);
        m_visibleCheckBox.setBounds(10, 130, 47, 18);

        jLabel2.setText("DefaultCloseOperation");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(190, 10, 190, 14);

        m_closeOperationComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DISPOSE_ON_CLOSE", "HIDE_ON_CLOSE", "DO_NOTHING_ON_CLOSE" }));
        getContentPane().add(m_closeOperationComboBox);
        m_closeOperationComboBox.setBounds(190, 30, 190, 21);

        m_cancelButton.setText("Cancel");
        m_cancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_cancelButtonActionPerformed(evt);
            }
        });
        getContentPane().add(m_cancelButton);
        m_cancelButton.setBounds(310, 160, 44, 21);

        m_okButton.setText("OK");
        m_okButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                m_okButtonActionPerformed(evt);
            }
        });
        getContentPane().add(m_okButton);
        m_okButton.setBounds(250, 160, 26, 21);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-405)/2, (screenSize.height-220)/2, 405, 220);
    }// </editor-fold>//GEN-END:initComponents

    private void m_cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_m_cancelButtonActionPerformed

        doClose(RET_CANCEL);

    }//GEN-LAST:event_m_cancelButtonActionPerformed

    private void m_okButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_m_okButtonActionPerformed
    {//GEN-HEADEREND:event_m_okButtonActionPerformed

         doClose(RET_OK);

    }//GEN-LAST:event_m_okButtonActionPerformed


    private void doClose(int retStatus)
    {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton m_cancelButton;
    private javax.swing.JCheckBox m_closableCheckBox;
    private javax.swing.JComboBox m_closeOperationComboBox;
    private javax.swing.JCheckBox m_iconifiableCheckBox;
    private javax.swing.JCheckBox m_maximizableCheckBox;
    private javax.swing.JButton m_okButton;
    private javax.swing.JCheckBox m_resizableCheckBox;
    private javax.swing.JCheckBox m_visibleCheckBox;
    // End of variables declaration//GEN-END:variables

}