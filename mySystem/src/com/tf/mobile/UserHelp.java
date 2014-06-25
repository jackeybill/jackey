/**Download by http://www.codefans.net */
package com.tf.mobile;
import java.awt.AlphaComposite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 * @author aMeng
 *
 */
public class UserHelp extends JPanel{
	private String st[] = {"������","��;"};
	private String data[][] = {
			{"ԭ�Ͻ���","�������ԭ�Ͻ��������¼"},
			{"ԭ������","������ԭ���Ѿ������������˻���Ӧ�̼�¼"},
			{"ԭ�Ͽ��","���ڲ�ѯ���ڵ�����ԭ�Ͽ���¼"},
			{"��������","���������������ĵ�ԭ����"},
			{"��������","������������ʣ��ԭ�����˿����Ӽ�¼"},
			{"��������","����������з��ϼ�¼"},
			{"��Ʒ����","������ӳ�Ʒ��¼"},
			{"��Ʒ���","������ʾ��Ʒ����¼"},
			{"��Ʒ����","�������ӳ�Ʒ������¼"},
			{"��Ʒ����","�������ӳ�Ʒ���޼�¼"},
			{"��Ʒ�˻�","�������ӳ�Ʒ�˻���¼"},
			{"������¼","������ʾϵͳ������¼"},
			{"���ϼ�¼","������ʾ���ϴ����¼"},
			{"������¼","������ʾ���г�����¼"},
			{"ԭ�ϼ�¼","������ʾ����ԭ�ϼ�¼"},
			{"��Ʒ��¼","���ڼ�¼��Ʒ�������"},
			};;
	/**
	 * 
	 */
	public UserHelp() {
		// TODO �Զ����ɹ��캯�����
		java.awt.Toolkit tools = this.getToolkit();
		java.awt.Dimension dimension = tools.getScreenSize();

		 TableModel dataModel = new AbstractTableModel() { 
             public int getColumnCount() { return st.length; } 
             public int getRowCount() { return data.length;} 
             public Object getValueAt(int row, int col) {return data[row][col];} 
             public String getColumnName(int column) {return st[column];} 
             public Class getColumnClass(int c) {return getColumnName(c).getClass();} 
 	    public boolean isCellEditable(int row, int col) {return col != 5;} 
             public void setValueAt(Object aValue, int row, int column) { data[row][column] = aValue.toString(); } 
          }; 
         TableRowSorter trs = new TableRowSorter(dataModel);
		JTable table = new JTable(dataModel);
		table.setRowSorter(trs);
		//table.setColumnModel(tc);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(30);
		table.setRowMargin(5);
		table.setShowGrid(true);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		//table.setBackground(java.awt.Color.pink);
		table.doLayout();
		table.setSize(dimension);
		table.setVisible(true);
		javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(table);
		this.setLayout(new java.awt.BorderLayout());
		this.add(scrollPane,"Center");
		this.setSize(480,320);
		this.validate();
	}

}
