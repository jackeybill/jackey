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
public class CreateContactsInterFace extends javax.swing.JPanel{
	private static final Logger log = Logger.getLogger(CreateContactsInterFace.class);
	private final static long serialVersionUID = 3079399095274806204L;
	//Fields
	private javax.swing.JTextField username = null; //姓名    
	//private javax.swing.JTextField sex = null;		//性别
	private javax.swing.JTextField mobileno = null;	//手机号
	//private javax.swing.JTextField birthday = null;	//生日
	private javax.swing.JTextField company = null;	//公司
	//Buttons
	private javax.swing.JButton enter = null;
	private javax.swing.JButton update = null;
	private javax.swing.JButton del = null;
	private javax.swing.JButton clean = null;
	private javax.swing.JTable table = null;
	//private javax.swing.JButton cancle = null;
	//Others
	private String sql = null;
	//private javax.swing.table.DefaultTableModel m = null;
	private Object data[][] = null;
	private String st[] = null;

	public CreateContactsInterFace(){//楼层短信模版
		int i=0;
		this.setLayout(new GridBagLayout());
		this.setSize(800, 600);
		//this.setBackground(java.awt.Color.PINK);
		javax.swing.JLabel mobileno_label = new javax.swing.JLabel("手机号：");
		setupComponent(mobileno_label,++i,0,1,1,true);
		mobileno = new javax.swing.JTextField(10);
		mobileno.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (mobileno.getText().trim() != "")){
					mobileno.requestFocus();
				}
			}
		});
		setupComponent(mobileno,++i,0,1,1,true);
		
		javax.swing.JLabel username_label = new javax.swing.JLabel("姓名:");
		setupComponent(username_label,++i,0,1,1,true);
		username = new javax.swing.JTextField(10);
		username.requestFocus();
		username.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar() == KeyEvent.VK_ENTER) && ((username.getText().trim()) != "")){
					username.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(username,++i,0,1,1,true);
				
		javax.swing.JLabel company_label = new javax.swing.JLabel("公司：");
		setupComponent(company_label,++i,0,1,1,true);
		company = new javax.swing.JTextField(10);
		company.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (company.getText().trim() != "")){
					company.requestFocus();
				}
			}
		});
		setupComponent(company,++i,0,1,1,true);
				
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
		setupComponent(enter,++i,0,1,1,true);
		
		update = new javax.swing.JButton("修改");
		update.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				update();
			}
		});
		setupComponent(update,++i,0,1,1,true);
		
		del = new javax.swing.JButton("删除");
		del.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				del();
			}
		});
		setupComponent(del,++i,0,1,1,true);
		
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
		setupComponent(clean,++i,0,1,1,true);
		
		//add table
		setupComponent(createTable(),0,2,1600,1,false);
	}
	
	private JScrollPane createTable(){
		setDataModel();
		javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(table);
		return scrollPane;
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
		username.setText("");
		mobileno.setText("");
		company.setText("");
	}
	private void update(){
		//String strID = null;
		String strUserName = username.getText().trim();
		String strMobileNo = mobileno.getText().trim();
		String strCompany = company.getText().trim();
		//String date = stock_date.getText().trim();
		int iSelectedRow = table.getSelectedRow();
		if(iSelectedRow<0){
			javax.swing.JOptionPane.showMessageDialog(null,"请选择要修改的记录！","请选择",javax.swing.JOptionPane.ERROR_MESSAGE);
		}else{
			strMobileNo = table.getValueAt(iSelectedRow, 0).toString();
			sql ="update contacts set username='"+strUserName+"',company='"+strCompany+"' " +
					"where mobileno='"+strMobileNo+"'";
			log.debug(sql);
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
			
			sql ="delete from contacts where mobileno='"+strID+"'";
			log.debug(sql);
			USeDB.UpdateDB(sql);
			clean();
			setDataModel();			
		}
	}
	private void IsGood(){
		String strUserName = username.getText().trim();
		String strMobileNo = mobileno.getText().trim();
		String strCompany = company.getText().trim();
		//String date = stock_date.getText().trim();
		String sql1 = "'"+strMobileNo+"','"+strUserName+"','"+strCompany+"'";
		sql ="insert into contacts(mobileno,username,company) values("+sql1+")";
		log.debug(sql);
		USeDB.UpdateDB(sql);
		clean();
		setDataModel();
	}
	private void setDataModel(){
		String tit[] = {"手机号","姓名","公司"};
		String sql = "select mobileno,username,company from contacts order by mobileno";
		data = USeDB.getData(sql);
		st = tit;
		 TableModel dataModel = new AbstractTableModel() {
			//private static final long serialVersionUID = 2743162040184868480L;
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
			listener.setTextObject(this.username,this.mobileno, this.company);
		}else{
			table.setModel(dataModel);
		} 
		table.doLayout();		
	}
	
	public class SelectionListener implements ListSelectionListener {
	    JTable table;
	    JTextField jUsernameName;
	    JTextArea jTemplete;
	    // It is necessary to keep the table since it is not possible
	    // to determine the table from the event's source
	    SelectionListener(JTable table) {
	        this.table = table;
	    }
	    public void setTextObject(JTextField jTextUsername, JTextField jTextMobileNo, JTextField jTextCompany){
	    	//jKeyName = jTextUsername;
	    	//jTemplete = jTextArea;
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
	            	mobileno.setText( table.getValueAt(iRow, 0).toString() );
	            	username.setText( table.getValueAt(iRow, 1).toString() );
	            	company.setText( table.getValueAt(iRow, 2).toString() );
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
