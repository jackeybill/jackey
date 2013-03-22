package com.tf.mobile;
import java.awt.AlphaComposite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
public class createUserTable extends JPanel{
	private final static long serialVersionUID = 2300323156L;
	private javax.swing.table.DefaultTableModel m = null;
	private static Object data[][] = null;
	private static String st[] = null;
	public createUserTable(Vector v,String sql) {
		// TODO 自动生成构造函数存根
		/*
		 * 此方法有很多JAVA的内部错误，勿用
		 */
		java.awt.Toolkit tools = this.getToolkit();
		java.awt.Dimension dimension = tools.getScreenSize();
		Vector  vbody = DBUtil.getTable(sql);
		m = new DefaultTableModel(vbody,v);
		JTable table = new JTable(m);
		TableRowSorter trs = new TableRowSorter(m);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(30);
		table.setRowMargin(5);
		table.setShowGrid(true);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.doLayout();
		table.setSize(dimension);
		table.setVisible(true);
		javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(table);
		//this.setBackground(java.awt.Color.PINK);
		this.setLayout(new java.awt.BorderLayout());
		this.add(scrollPane,"Center");
		this.setSize(480,320);
		this.validate();
	}
	public createUserTable(String str[],String sql){
		
		java.awt.Toolkit tools = this.getToolkit();
		java.awt.Dimension dimension = tools.getScreenSize();
		data = USeDB.getData(sql);
		st = str;
		 TableModel dataModel = new AbstractTableModel() { 
             public int getColumnCount() { return st.length; } 
             public int getRowCount() { return data.length;} 
             public Object getValueAt(int row, int col) {return data[row][col];} 
             public String getColumnName(int column) {return st[column];} 
             public Class getColumnClass(int c) {return getColumnName(c).getClass();} 
 	    public boolean isCellEditable(int row, int col) {return col > 1 && row > 1;} 
             public void setValueAt(Object aValue, int row, int column) { data[row][column] = aValue; } 
          }; 
         TableRowSorter trs = new TableRowSorter(dataModel);
		JTable table = new JTable(dataModel);
		table.setRowSorter(trs);
		table.setOpaque(true);
		//table.setColumnModel(tc);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(30);
		table.setRowMargin(5);
		table.setShowGrid(true);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		//table.setForeground(java.awt.Color.white);
		table.doLayout();
		table.setSize(dimension);
		table.setVisible(true);
		javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(table);
		this.setLayout(new java.awt.BorderLayout());
		this.add(scrollPane,"Center");
		this.setSize(480,320);
		this.validate();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(!isOpaque()){return;}
		Graphics2D g2d = (Graphics2D) g;
		//int rule = AlphaComposite.SRC_OVER;
		AlphaComposite opaque = AlphaComposite.SrcOver;
		//AlphaComposite blend = AlphaComposite.getInstance(rule, 0.6f);
		//AlphaComposite set = AlphaComposite.Src;
		int width = getWidth();
		int height = getHeight();
		GradientPaint gradientPaint = new GradientPaint(0,0,java.awt.Color.green,width/2,height/2,java.awt.Color.yellow,false);
		g2d.setComposite(opaque);
		g2d.setPaint(gradientPaint);
		g2d.fillRect(0, 0, width,height);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
	}
	//public Dimension getMaximumSize() { 
   //     return new Dimension(getPreferredSize().width, super.getMaximumSize().height); 
   // } 
}
