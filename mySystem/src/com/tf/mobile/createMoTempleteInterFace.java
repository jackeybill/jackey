package com.tf.mobile;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;
public class createMoTempleteInterFace extends javax.swing.JPanel{
	private static final Logger log = Logger.getLogger(createMoTempleteInterFace.class);
	private final static long serialVersionUID = 230002328992L;
	private javax.swing.JTextField id = null;
	private javax.swing.JTextArea templete = null;
	private javax.swing.JTextField keyName = null;
	private javax.swing.JTextField type = null;
	private javax.swing.JButton enter = null;
	private javax.swing.JButton update = null;
	private javax.swing.JButton del = null;
	private javax.swing.JButton clean = null;
	private javax.swing.JTable table = null;
	//private javax.swing.JButton cancle = null;
	private String sql = null;
	javax.swing.table.DefaultTableModel m = null;
	Object data[][] = null;
	String st[] = null;
	

	public createMoTempleteInterFace(){//楼层短信模版
		this.setLayout(new GridBagLayout());
		this.setSize(800, 600);
		//this.setBackground(java.awt.Color.PINK);
		
		javax.swing.JLabel stuff_id_label = new javax.swing.JLabel("楼层名称:");
		setupComponent(stuff_id_label,0,0,1,1,true);
		keyName = new javax.swing.JTextField(10);
		keyName.requestFocus();
		keyName.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar() == KeyEvent.VK_ENTER) && ((keyName.getText().trim()) != "")){
					keyName.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(keyName,1,0,1,1,true);
				
		javax.swing.JLabel stuff_name_label = new javax.swing.JLabel("短信模版：");
		setupComponent(stuff_name_label,2,0,1,1,true);
		templete = new javax.swing.JTextArea(1,30);
		templete.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (templete.getText().trim() != "")){
					templete.requestFocus();
				}
			}
		});
		setupComponent(templete,3,0,1,1,true);
		
		enter = new javax.swing.JButton("新增");
		enter.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER)){
					IsGood();
				}
			}
		});
		enter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				IsGood();
			}
		});
		setupComponent(enter,4,0,1,1,true);
		
		update = new javax.swing.JButton("修改");
		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				update();
			}
		});
		setupComponent(update,5,0,1,1,true);
		
		del = new javax.swing.JButton("删除");
		del.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				del();
			}
		});
		setupComponent(del,6,0,1,1,true);
		
		/*clean = new javax.swing.JButton("查询");
		clean.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clean();
			}
		});
		setupComponent(clean,7,0,1,1,true);*/
		
		clean = new javax.swing.JButton("清   空");
		clean.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clean();
			}
		});
		setupComponent(clean,8,0,1,1,true);
		
		//TODO add table
		setupComponent(createTable(),0,2,1600,1,false);
		/*searchTable = new JTable(data,columnNames);
        JScrollPane scrollPane = new JScrollPane(searchTable);
        searchTable.setPreferredScrollableViewportSize(new Dimension(500, 90));

		newFrame.getContentPane().add(scrollPane,BorderLayout.SOUTH);

        newFrame.getContentPane().add(topPane,BorderLayout.NORTH);
        newFrame.getContentPane().add(centerPane,BorderLayout.CENTER);
        newFrame.show();*/
	}
	
	JScrollPane createTable(){
		setDataModel();
		javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(table);
		//scrollPane.setSize(800, 600);
		//this.setLayout(new java.awt.BorderLayout());
		//this.add(scrollPane,"Center");
		//this.setSize(480,320);
		//this.validate();
		return scrollPane;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(!isOpaque()){return;}
		Graphics2D g2d = (Graphics2D) g;
		int rule = AlphaComposite.SRC_OVER;
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
	private void setupComponent(JComponent component,int gridx,int gridy,int gridwidth,int ipadx,boolean fill){
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		gridBagConstrains.insets = new java.awt.Insets(5,3,3,3);
		if(gridwidth>1)
			gridBagConstrains.gridwidth = gridwidth;
		if(ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		if(fill)
			gridBagConstrains.fill = GridBagConstraints.VERTICAL ;
		this.add(component,gridBagConstrains);
	}
	private void clean(){
		keyName.setText("");
		templete.setText("");
	}
	private void update(){
		String strID = null;
		String strKeyName = keyName.getText().trim();
		String strTemplete = templete.getText().trim();
		//String date = stock_date.getText().trim();
		int iSelectedRow = table.getSelectedRow();
		if(iSelectedRow<0){
			javax.swing.JOptionPane.showMessageDialog(null,"请选择要修改的记录！","请选择",javax.swing.JOptionPane.ERROR_MESSAGE);
		}else{
			strID = table.getValueAt(iSelectedRow, 0).toString();
			
			sql ="update mo_templete set keyName='"+strKeyName+"',templete='"+strTemplete+"' " +
					"where id="+strID;
			System.out.println(sql);
			USeDB.UpdateDB(sql);
			clean();
			setDataModel();			
		}
	}
	private void del(){
		String strID = null;
		int iSelectedRow = table.getSelectedRow();
		if(iSelectedRow<0){
			javax.swing.JOptionPane.showMessageDialog(null,"请选择要删除修改的记录！","请选择",javax.swing.JOptionPane.ERROR_MESSAGE);
		}else{
			strID = table.getValueAt(iSelectedRow, 0).toString();
			
			sql ="delete from mo_templete where id="+strID;
			System.out.println(sql);
			USeDB.UpdateDB(sql);
			clean();
			setDataModel();			
		}
	}
	private void IsGood(){
		String strKeyName = keyName.getText().trim();
		String strTemplete = templete.getText().trim();
		//String date = stock_date.getText().trim();
		String str [] = {strKeyName,strTemplete};
		for(int i =0;i<str.length;i++){
			if(str[i].isEmpty()){
				javax.swing.JOptionPane.showMessageDialog(null,"必填项，请务必输入！","必须输入",javax.swing.JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		String sql1 = "'"+strKeyName+"','"+strTemplete+"','templete'";
		sql ="insert into mo_templete(keyName,templete,type) values("+sql1+")";
		System.out.println(sql);
		USeDB.UpdateDB(sql);
		clean();
		setDataModel();
	}
	private void setDataModel(){
		String tit[] = {"ID","楼层名称","短信模版"};
		String sql = "select id,keyname,templete from mo_templete";
		data = USeDB.getData(sql);
		st = tit;
		 TableModel dataModel = new AbstractTableModel() { 
             public int getColumnCount() { return st.length; } 
             public int getRowCount() { return data.length;} 
             public Object getValueAt(int row, int col) {return data[row][col];} 
             public String getColumnName(int column) {return st[column];} 
             public Class getColumnClass(int c) {return getColumnName(c).getClass();} 
             public boolean isCellEditable(int row, int col) {return col > 1 && row > 1;} 
             public void setValueAt(Object aValue, int row, int column) { data[row][column] = aValue; }
             /*public void fireTableDataChanged() {
                 //fireTableChanged(new TableModelEvent(this));
            	 System.out.println("sdfsdf" + this.);
             }*/
          };
		if(table == null){
			TableRowSorter trs = new TableRowSorter(dataModel);
			table = new JTable(dataModel);
			table.setRowSorter(trs);			
			table.setOpaque(true);
			//table.setColumnModel(tc);
			/*table.addAncestorListener(new AncestorListener(){
				public void ancestorMoved(AncestorEvent event){
					
				};
			});*/
			table.getTableHeader().setReorderingAllowed(false);
			table.setRowHeight(30);
			table.setRowMargin(5);
			table.setShowGrid(true);
			table.setShowHorizontalLines(true);
			table.setShowVerticalLines(true);
			//table.setForeground(java.awt.Color.white);
			//table.doLayout();		
			//table.setValueAt("sssss", 3, 1);
			//table.setSize(dimension);
			//table.setSize(800, 600);
			table.setVisible(true);
			table.setPreferredScrollableViewportSize(new Dimension(860, 620));
			
			SelectionListener listener = new SelectionListener(table);
			table.getSelectionModel().addListSelectionListener(listener);
			table.getColumnModel().getSelectionModel()
			    .addListSelectionListener(listener);
			listener.setTextObject(this.keyName,this.templete);
		}else{
			table.setModel(dataModel);
		} 
		table.doLayout();		
	}
	private void IsUpdate(){
		/*String strKeyName = templete.getText().trim();
		String strTemplete = templete.getText().trim();
		String str [] = {id,name,company,people,check,unit,value,spec,color,place,date,text};
		for(int i =0;i<str.length;i++){
			if(str[i].isEmpty()){
				javax.swing.JOptionPane.showMessageDialog(null,"必填项，请务必输入！","必须输入",javax.swing.JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if(!CheckValue(str[6])){
			javax.swing.JOptionPane.showMessageDialog(null, "数量输入错误，只接收0至9之间的数字,请重新输入！","数字错误",javax.swing.JOptionPane.ERROR_MESSAGE);
			stuff_value.setText("");
			stuff_value.requestFocus();
			return;
		}
		if(!CheckDate(str[10])){
			javax.swing.JOptionPane.showMessageDialog(null,"数据录入错误格式为:YYYY/MM/DD，请你重新输入！","数据错误",javax.swing.JOptionPane.ERROR_MESSAGE);
			stock_date.setText("");
			stock_date.requestFocus();
			return;
		}
		if(!DBUtil.isExist("select stuff_ID from stuff_in where stuff_ID='"+str[0]+"'")){
			javax.swing.JOptionPane.showMessageDialog(null, "请注意更改的数据编号不存在，不能更改，请重新输入！","数据有误",javax.swing.JOptionPane.ERROR_MESSAGE);
			clean();
			return;
		}
		//sql = "'"+id+"','"+name+"','"+company+"','"+people+"','"+check+"','"+unit+"','"+value+"','"+spec+"','"+color+"','"+place+"','"+date+"','"+text+"'";
		sql ="update stuff_in set stuff_ID ='"+id+",'stuff_name='"+name+",'stuff_company='"+company+",'stuff_people='"+people+",'check_people='"
		+check+",'stuff_unit='"+unit+",'stuff_value='"+value+",'stuff_spec='"+spec+",'stuff_color='"+color+",'stuff_place='"+place+",'stock_date='"+date+",'stuff_text='"+text+
		"' where stuff_ID='"+id+"'";
		USeDB.UpdateDB(sql);
		clean();*/
		
	}
	private boolean CheckValue(String s){
		char ch[] = s.toCharArray();
		int j=0;
		for(int i=0;i<ch.length;i++){
			if((ch[i]>'\u002f') && (ch[i]<'\u0040') ){j++;}
		}
		if(j==ch.length){return true;}
		return false;
	}
	private boolean CheckDate(String st){//检效年月日
		boolean b = false;
		String token[] = st.split("/");
		if((CheckDateRexgex(st))&&(token.length == 3)){
		if((CheckValue(token[0]))&& (CheckValue(token[1])) && (CheckValue(token[2]))){
			b = ISLeapYear(token);
			}
		}
		return b;
	}
	private boolean ISLeapYear(String str[]){
		int year = Integer.parseInt(str[0]);
		int month = Integer.parseInt(str[1]);
		int day = Integer.parseInt(str[2]);
		boolean  b = false;
		if((month>0) && (month<13)){
			if((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)){
				if(day>0 && day<32){b = true;}}
			else if((month == 4) || (month ==6 ) || (month == 9) || (month == 11)){
				if(day>0 && day<31){b= true;}}
			else if(month==2){
				if(((year%4 == 0) && (year%100 !=0)) || (year%400 == 0)){//是闰年
					if(day>0 && day<30){b = true;}
					}
				if(day>0 && day<29){b= true;}
			}
		 }
		return b;
	}
	private boolean CheckDateRexgex(String str){
		Pattern re = Pattern.compile("[0-9]{4}\\/[0-9]{1,2}\\/[0-9]{1,2}");
		Matcher m = re.matcher(str);
		boolean b = m.matches();
		return b;
	}
		
	public class SelectionListener implements ListSelectionListener {
	    JTable table;
	    JTextField jKeyName;
	    JTextArea jTemplete;
	    // It is necessary to keep the table since it is not possible
	    // to determine the table from the event's source
	    SelectionListener(JTable table) {
	        this.table = table;
	    }
	    public void setTextObject(JTextField jText, JTextArea jTextArea){
	    	jKeyName = jText;
	    	jTemplete = jTextArea;
	    }
	    public void valueChanged(ListSelectionEvent e) {
	    	//log.debug("cell1=");
	    	//System.out.println("cell1=");
	        // If cell selection is enabled, both row and column change events are fired
	        if (e.getSource() == table.getSelectionModel()
	              && table.getRowSelectionAllowed()) {
	            // Row selection changed
	            //int first = e.getFirstIndex();
	            int iRow = table.getSelectedRow();
	            if(iRow>-1){
	            	jKeyName.setText( table.getValueAt(iRow, 1).toString() );
	            	jTemplete.setText( table.getValueAt(iRow, 2).toString() );
	            }	            
	        } else if (e.getSource() == table.getColumnModel().getSelectionModel()
	               && table.getColumnSelectionAllowed() ){
	            // Column selection changed
	            /*int first = e.getFirstIndex();
	            int last = e.getLastIndex();
	            log.debug("first="+first);*/
	        }

	        if (e.getValueIsAdjusting()) {
	            // The mouse button has not yet been released
	        }
	    }
	}
}
