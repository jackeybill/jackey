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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.log4j.Logger;
public class PendingSmsListInterFace extends javax.swing.JPanel{
	private static final Logger log = Logger.getLogger(PendingSmsListInterFace.class);
	private final static long serialVersionUID = 3079399095274806204L;
	//Fields
	//private javax.swing.JTextField isSend = null;	//发送状态
	private javax.swing.JTextField sendtime = null;	//发送时间
	private javax.swing.JTextField sendto = null; 	//发送列表    
	private javax.swing.JTextField sendmsg = null;	//发送内容
	
	//Buttons select sendto,sendtime,sendmsg from mo where isSend='0'";
	private javax.swing.JButton enter = null;
	private javax.swing.JButton update = null;
	private javax.swing.JButton del = null;
	private javax.swing.JButton clean = null;
	private javax.swing.JTable table = null;
	//Others
	private String sql = null;
	private Object data[][] = null;
	private String st[] = null;

	public PendingSmsListInterFace(){//待发短信维护
		int i=0;
		this.setLayout(new GridBagLayout());
		//this.setSize(800, 600);
		//this.setBackground(java.awt.Color.PINK);
		javax.swing.JLabel sendtime_label = new javax.swing.JLabel("发送时间：");
		setupComponent(sendtime_label,++i,0,1,1,true);
		sendtime = new javax.swing.JTextField(11);
		sendtime.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (sendtime.getText().trim() != "")){
					sendtime.requestFocus();
				}
			}
		});
		setupComponent(sendtime,++i,0,1,1,true);
		
		javax.swing.JLabel sendto_label = new javax.swing.JLabel("发送列表：");
		setupComponent(sendto_label,++i,0,1,1,true);
		sendto = new javax.swing.JTextField(20);
		sendto.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (sendto.getText().trim() != "")){
					sendto.requestFocus();
				}
			}
		});
		setupComponent(sendto,++i,0,1,1,true);
		
		javax.swing.JLabel sendmsg_label = new javax.swing.JLabel("发送内容:");
		setupComponent(sendmsg_label,++i,0,1,1,true);
		sendmsg = new javax.swing.JTextField(45);
		sendmsg.requestFocus();
		sendmsg.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar() == KeyEvent.VK_ENTER) && ((sendmsg.getText().trim()) != "")){
					sendmsg.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(sendmsg,++i,0,1,1,true);
				
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
		sendmsg.setText("");
		sendtime.setText("");
		sendto.setText("");
	}
	private void update(){
		String strID = null;
		String strsendmsg = sendmsg.getText().trim();
		String strsendtime = sendtime.getText().trim();
		String strsendto = sendto.getText().trim();
		int iSelectedRow = table.getSelectedRow();
		if(iSelectedRow<0){
			javax.swing.JOptionPane.showMessageDialog(null,"请选择要修改的记录！","请选择",javax.swing.JOptionPane.ERROR_MESSAGE);
		}else{
			strID = table.getValueAt(iSelectedRow, 0).toString();
			sql ="update mo set sendmsg='"+strsendmsg+"', sendtime=format('"+strsendtime+"','yyyy-mm-dd hh:mm:ss'),sendto='"+strsendto+"' " +
					"where id="+strID;
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
			
			sql ="delete from mo where id="+strID;
			log.debug(sql);
			USeDB.UpdateDB(sql);
			clean();
			setDataModel();			
		}
	}
	private void IsGood(){
		String strsendmsg = sendmsg.getText().trim();
		String strsendtime = sendtime.getText().trim();
		String strsendto = sendto.getText().trim();
		String sql1 = "'"+strsendto+"','"+strsendtime+"','"+strsendmsg+"'";
		sql ="insert into mo(sendto,sendtime,sendmsg) values("+sql1+")";
		log.debug(sql);
		USeDB.UpdateDB(sql);
		clean();
		setDataModel();
	}
	private void setDataModel(){
		String tit[] = {"ID","发送时间","发送列表","发送内容"};
		//sql = 
		
		String sql = "select id,format(sendtime,'yyyy-mm-dd hh:mm:ss') as sendtimes,sendto,sendmsg from mo where isSend='0' order by sendtime";
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
          };
		if(table == null){
			TableRowSorter trs = new TableRowSorter(dataModel);
			table = new JTable(dataModel);
			table.setRowSorter(trs);			
			table.setOpaque(true);
			table.getTableHeader().setReorderingAllowed(false);
			table.setRowHeight(30);
			table.setRowMargin(5);
			table.setShowGrid(true);
			table.setShowHorizontalLines(true);
			table.setShowVerticalLines(true);
			table.setVisible(true);
			
			/*AutoResizeTableColumns resizer = new AutoResizeTableColumns(table,
					dataModel, 32, true, true,
		                new boolean[table.getColumnCount()]);*/
			
			java.awt.Toolkit tools = this.getToolkit();
			java.awt.Dimension dimension = tools.getScreenSize();
			dimension.width -= 100;
			dimension.height -= 280;
			//System.out.print("getWidth()="+dimension.width+"getHeight()="+dimension.height);
			//table.setPreferredScrollableViewportSize(dimension);
			table.setPreferredScrollableViewportSize(dimension);
			//table.setSize(dimension);
			
			SelectionListener listener = new SelectionListener(table);
			table.getSelectionModel().addListSelectionListener(listener);
			table.getColumnModel().getSelectionModel()
			    .addListSelectionListener(listener);
		}else{
			table.setModel(dataModel);
		} 
		table.doLayout();		
	}
	
	public class SelectionListener implements ListSelectionListener {
	    JTable table;
	    SelectionListener(JTable table) {
	        this.table = table;
	    }
	    public void valueChanged(ListSelectionEvent e) {
	        if (e.getSource() == table.getSelectionModel()
	              && table.getRowSelectionAllowed()) {
	            // Row selection changed
	            //int first = e.getFirstIndex();
	            int iRow = table.getSelectedRow();
	            if(iRow>-1){
	            	sendtime.setText( table.getValueAt(iRow, 1).toString() );
	            	sendto.setText( table.getValueAt(iRow, 2).toString() );
	            	sendmsg.setText( table.getValueAt(iRow, 3).toString() );
	            }	            
	        } else if (e.getSource() == table.getColumnModel().getSelectionModel()
	               && table.getColumnSelectionAllowed() ){
	            // Column selection changed
	        }

	        if (e.getValueIsAdjusting()) {
	            // The mouse button has not yet been released
	        }
	    }
	}
}
