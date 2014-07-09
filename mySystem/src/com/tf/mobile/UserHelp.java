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
	private String st[] = {"按键名","用途"};
	private String data[][] = {
			{"原料进入","用于添加原料进入事项记录"},
			{"原料退料","用于在原料已经收入库后再行退还供应商记录"},
			{"原料库存","用于查询现在的所有原料库存记录"},
			{"生产开单","用于输入生产所耗的原材料"},
			{"生产退料","用于生产中所剩好原材料退库的添加记录"},
			{"生产废料","用于添加所有废料记录"},
			{"成品进入","用于添加成品记录"},
			{"成品库存","用于显示成品库存记录"},
			{"成品发货","用于增加成品发货记录"},
			{"成品返修","用于增加成品返修记录"},
			{"成品退货","用于增加成品退货记录"},
			{"操作记录","用于显示系统操作记录"},
			{"废料记录","用于显示废料处理记录"},
			{"出货记录","用于显示所有出货记录"},
			{"原料记录","用于显示发生原料记录"},
			{"成品记录","用于记录成品生产情况"},
			};;
	/**
	 * 
	 */
	public UserHelp() {
		// TODO 自动生成构造函数存根
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
