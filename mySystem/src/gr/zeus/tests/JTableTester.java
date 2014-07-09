/*
 * JTableTester.java - Test application for TableSorter and related classes
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

import gr.zeus.ui.table.AutoResizeTableColumns;
import gr.zeus.ui.table.ProgressBarCellRenderer;
import gr.zeus.ui.table.TableSorter;
import gr.zeus.ui.table.TableSorterIcons;
import gr.zeus.util.DateHelper;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class JTableTester extends JFrame {

    private static final String[] COLUMN_NAMES = {
        "DATE","INTEGER","FIRSTNAME","LASTNAME","FLOAT","BOOLEAN","PROGRESS"};

    // THIS IS YOUR TABLE MODEL!
    private DefaultTableModel m_tableModel = new DefaultTableModel(null,
                                                    COLUMN_NAMES) {

        public boolean isCellEditable(int row, int column)
        {
            return( false );
        }

        public Class getColumnClass(int col)
        {
            return( getValueAt(0, col).getClass() );
        }
    };

    // THIS IS A VIEW OF YOYR ACTUAL TABLE MODEL, USED FOR TABLE SORTING!
    private TableSorter m_tableSorter = new TableSorter( m_tableModel );


    public JTableTester()
    {
        initComponents();

        // set the table model
        m_table.setModel( m_tableModel );

        // no reordering of the columns
        //m_table.getTableHeader().setReorderingAllowed( false );

        // select only one row
        //m_table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        // use scrollbar
        //m_table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

        // Hiding first column in order to test resize
        //GUIUtils.hideJTableColumn( m_table.getColumnModel().getColumn(0) );

        // SORTING CONFIGURATION
        // if you wish to use the same icons regardless of column's sorting
        // priority, then use the two lines below.
        //
        //Icon ups[] = new Icon[] {TableSorterIcons.UP5_ICON};
        //Icon downs[] = new Icon[] {TableSorterIcons.DOWN5_ICON};
        //
        Icon ups[] = new Icon[] {
                TableSorterIcons.UP5_ICON, // this one will be dispayed first
                TableSorterIcons.UP7_ICON,
                TableSorterIcons.UP6_ICON,
                TableSorterIcons.UP4_ICON,
                TableSorterIcons.UP3_ICON,
                TableSorterIcons.UP2_ICON,
                TableSorterIcons.UP1_ICON
        };
        Icon downs[] = new Icon[] {
                TableSorterIcons.DOWN5_ICON, // this one will be dispayed first
                TableSorterIcons.DOWN7_ICON,
                TableSorterIcons.DOWN6_ICON,
                TableSorterIcons.DOWN4_ICON,
                TableSorterIcons.DOWN3_ICON,
                TableSorterIcons.DOWN2_ICON,
                TableSorterIcons.DOWN1_ICON
        };
        m_tableSorter.setCustomIcons(ups, downs);
        m_tableSorter.setTableHeader( m_table.getTableHeader() );
        m_table.setModel( m_tableSorter );
        //-----

        // TABLE RESIZING CONFIGURATION
        // resize but don't lock the columns
        AutoResizeTableColumns resizer = new AutoResizeTableColumns(m_table,
            m_tableModel, 32, true, true,
                new boolean[m_table.getColumnCount()]);
        // uncomment for debuging
        //resizer.setDebugMode( true );
        //-----

        // TABLE ACTUAL DATA
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Object[][] values = new Object[][]
            {
                {sdf.parse("29/11/2004"), new Integer(999),
                         "Gregory", "Kotsaftis", new Float(999.99F),
                         Boolean.FALSE, new Integer(90)},
                {sdf.parse("14/06/2003"), new Integer(888),
                         "Litsa", "Katsoni", new Float(888.88F),
                         Boolean.TRUE, new Integer(80)},
                {sdf.parse("20/07/2001"), new Integer(9),
                         "George", "Mavrakis", new Float(109.30F),
                         Boolean.TRUE, new Integer(25)},
                {sdf.parse("30/10/1999"), new Integer(190),
                         "Kostas", "Filippaios", new Float(23.27F),
                         Boolean.FALSE, new Integer(50)},
                {sdf.parse("02/02/2002"), new Integer(1258),
                         "John", "Papadopoulos", new Float(109.30F),
                         Boolean.TRUE, new Integer(100)},
                {sdf.parse("15/12/1985"), new Integer(910),
                         "Spyros", "Papadolpoulos", new Float(410.08F),
                         Boolean.FALSE, new Integer(75)},
                {sdf.parse("6/04/2000"), new Integer(5),
                         "Makis", "Triantafyllopoulos", new Float(10.00F),
                         Boolean.TRUE, new Integer(100)}
            };

            /* instead of addRow()... */
            /*m_tableModel.addRow(values[0]);
            m_tableModel.addRow(values[1]);
            m_tableModel.addRow(values[2]);
            m_tableModel.addRow(values[3]);
            m_tableModel.addRow(values[4]);
            m_tableModel.addRow(values[5]);
            m_tableModel.addRow(values[6]);*/

            /* ...use setDataVector() for performance! */
            m_tableModel.setDataVector(values, COLUMN_NAMES);
            //-----
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        // TABLE SPECIAL RENDERER FOR PROGRESSBAR
        // configuration for progress bar...
        Hashtable<Integer,Color> limitColors = new Hashtable<Integer,Color>();
        limitColors.put(new Integer( 0), Color.green);
        limitColors.put(new Integer(60), Color.yellow);
        limitColors.put(new Integer(80), Color.red);
        // COLUMN 6 contains progress bar!
        TableColumnModel model = m_table.getColumnModel();
        model.getColumn(6).setCellRenderer( new ProgressBarCellRenderer(
                true, false, 0, 100, limitColors, m_table.getBackground()) );
        //-----
    }


    /**
     * Quick and dirty data entry to table.
     */
    private void add_row()
    {
        try
        {
            java.util.Date d = DateHelper.parseDate(
                                dateTextField.getText().trim(), "dd/MM/yyyy");
            Integer i = new Integer( intTextField.getText().trim() );
            String firstname = firstnameTextField.getText().trim();
            String lastname = lastnameTextField.getText().trim();
            Float f = new Float( floatTextField.getText().trim() );
            Boolean b = new Boolean( checkBox.isSelected() );
            Integer percent = (Integer) percentSpinner.getValue();

            m_tableModel.addRow( new Object[]{d, i, firstname, lastname, f, b,
                                                percent} );
        }
        catch(Exception e)
        {
            e.printStackTrace();
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

        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        m_table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dateTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        intTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        firstnameTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lastnameTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        floatTextField = new javax.swing.JTextField();
        checkBox = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        percentSpinner = new JSpinner(
            new SpinnerNumberModel(0, 0, 100, 10)  );
        button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tester for JTable sorting, resizing and more...");

        m_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        m_table.setToolTipText("Press down and hold CTRL or SHIFT while selecting multiple columns' headers for sorting.");
        jScrollPane1.setViewportView(m_table);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setMinimumSize(new java.awt.Dimension(500, 60));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 60));
        jPanel1.setLayout(null);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("dd/MM/yyyy");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 10, 70, 20);
        jPanel1.add(dateTextField);
        dateTextField.setBounds(10, 30, 70, 21);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("int");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(90, 10, 30, 20);
        jPanel1.add(intTextField);
        intTextField.setBounds(90, 30, 30, 21);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("firstname");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(130, 10, 90, 20);
        jPanel1.add(firstnameTextField);
        firstnameTextField.setBounds(130, 30, 90, 21);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("lastname");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(230, 10, 90, 20);
        jPanel1.add(lastnameTextField);
        lastnameTextField.setBounds(230, 30, 90, 21);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("float");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(330, 10, 30, 20);
        jPanel1.add(floatTextField);
        floatTextField.setBounds(330, 30, 30, 21);
        jPanel1.add(checkBox);
        checkBox.setBounds(370, 30, 20, 17);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("percent");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(400, 10, 40, 20);
        jPanel1.add(percentSpinner);
        percentSpinner.setBounds(400, 30, 40, 21);

        button.setText("+");
        button.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonActionPerformed(evt);
            }
        });
        jPanel1.add(button);
        button.setBounds(450, 10, 40, 40);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonActionPerformed
    {//GEN-HEADEREND:event_buttonActionPerformed

        add_row();

    }//GEN-LAST:event_buttonActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        try
        {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName() );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JTableTester().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    private javax.swing.JCheckBox checkBox;
    private javax.swing.JTextField dateTextField;
    private javax.swing.JTextField firstnameTextField;
    private javax.swing.JTextField floatTextField;
    private javax.swing.JTextField intTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField lastnameTextField;
    private javax.swing.JTable m_table;
    private javax.swing.JSpinner percentSpinner;
    // End of variables declaration//GEN-END:variables

}
