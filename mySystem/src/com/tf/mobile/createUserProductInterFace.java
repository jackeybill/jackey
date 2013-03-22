package com.tf.mobile;
//Download by http://www.codefans.net 
import java.awt.AlphaComposite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;

public class createUserProductInterFace extends javax.swing.JPanel{//implements ActionListener
	private final static long serialVersionUID = 230002323189L;
	private String sql = null;
	private javax.swing.JTextField product_id = null;
	private javax.swing.JTextField product_client = null;
	private javax.swing.JTextField product_name = null;
	private javax.swing.JTextField product_spec = null;
	private javax.swing.JTextField product_unit = null;
	private javax.swing.JTextField product_value = null;
	private javax.swing.JTextField product_make = null;
	private javax.swing.JTextField stock_date = null;
	private javax.swing.JTextField product_text = null;
	private javax.swing.JButton enter = null;
	private javax.swing.JButton cancle = null;
	private javax.swing.JButton quit = null;
	//private javax.swing.JButton find = null;
	

	public  createUserProductInterFace(){//增加删除生产产品
		//pane = new javax.swing.JPanel();
		this.setLayout(new java.awt.GridBagLayout());
		this.setBackground(java.awt.Color.PINK);
		javax.swing.JLabel product_id_label = new javax.swing.JLabel("产品编号:");
		setupComponent(product_id_label,0,0,1,1,true);
		product_id = new javax.swing.JTextField(10);
		product_id.requestFocus();
		product_id.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyChar()== KeyEvent.VK_ENTER && product_id.getText().trim() != ""){
					product_client.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(product_id,1,0,1,1,true);
		javax.swing.JLabel product_client_label = new javax.swing.JLabel("客户名称:");
		setupComponent(product_client_label,2,0,1,1,true);
		product_client = new javax.swing.JTextField(10);
		product_client.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyChar()== KeyEvent.VK_ENTER && product_client.getText().trim() != ""){
					product_name.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(product_client,3,0,1,1,true);
		javax.swing.JLabel product_name_label = new javax.swing.JLabel("产品名称:");
		setupComponent(product_name_label,4,0,1,1,true);
		product_name = new javax.swing.JTextField(10);
		product_name.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyChar()== KeyEvent.VK_ENTER && product_name.getText().trim() != ""){
					product_spec.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(product_name,5,0,1,1,true);
		javax.swing.JLabel product_spec_label = new javax.swing.JLabel("产品规格:");
		setupComponent(product_spec_label,6,0,1,1,true);
		product_spec = new javax.swing.JTextField(10);
		product_spec.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyChar()== KeyEvent.VK_ENTER && product_spec.getText().trim() != ""){
					product_unit.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(product_spec,7,0,1,1,true);
		
		javax.swing.JLabel product_unit_label = new javax.swing.JLabel("产品单位:");
		setupComponent(product_unit_label,0,1,1,1,true);
		product_unit = new javax.swing.JTextField(10);
		product_unit.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyChar()== KeyEvent.VK_ENTER && product_unit.getText().trim() != ""){
					product_value.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(product_unit,1,1,1,1,true);
		javax.swing.JLabel product_value_label = new javax.swing.JLabel("产品数量:");
		setupComponent(product_value_label,2,1,1,1,true);
		product_value = new javax.swing.JTextField(10);
		product_value.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyChar()== KeyEvent.VK_ENTER && product_value.getText().trim() != ""){
					if(CheckValue(product_value.getText().trim())){	
					product_make.requestFocus();
				}else{
				javax.swing.JOptionPane.showMessageDialog(null, "数量输入错误，只接收0至9之间的数字,请重新输入！","数字错误",javax.swing.JOptionPane.ERROR_MESSAGE);
				product_value.setText("");
				product_value.requestFocus();
				}
				}
			}
			public void keyTyped(KeyEvent e){
			}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(product_value,3,1,1,1,true);
		javax.swing.JLabel product_make_label = new javax.swing.JLabel("生产单位:");
		setupComponent(product_make_label,4,1,1,1,true);
		product_make = new javax.swing.JTextField(10);
		product_make.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyChar()== KeyEvent.VK_ENTER && product_make.getText().trim() != ""){
					stock_date.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(product_make,5,1,1,1,true);
		javax.swing.JLabel stock_date_label = new javax.swing.JLabel("入库时间:");
		setupComponent(stock_date_label,6,1,1,1,true);
		stock_date = new javax.swing.JTextField(10);
		stock_date.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar() == KeyEvent.VK_ENTER) && (stock_date.getText().trim() != "")){
					if(CheckDate(stock_date.getText().trim())){
						product_text.requestFocus();
					}else{
					javax.swing.JOptionPane.showMessageDialog(null, "你的日期或格式输入不对，应为数字YYYY/MM/DD,请再输入！","输入日期格式错误",javax.swing.JOptionPane.ERROR_MESSAGE);
					stock_date.setText("");
					stock_date.requestFocus();}
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(stock_date,7,1,1,1,true);
		javax.swing.JLabel product_text_label = new javax.swing.JLabel("产品备注:");
		setupComponent(product_text_label,0,2,1,1,true);
		product_text = new javax.swing.JTextField(10);
		product_text.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyChar()== KeyEvent.VK_ENTER){
					enter.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(product_text,1,2,1,1,true);
		
		enter = new javax.swing.JButton("录   入");
		enter.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if(e.getKeyChar()== KeyEvent.VK_ENTER){
					Input();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		enter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Input();
			}
		});
		setupComponent(enter,1,3,1,1,true);
		cancle = new javax.swing.JButton("清   空");
		cancle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clean();
			}
		});
		setupComponent(cancle,3,3,1,1,true);
		quit = new javax.swing.JButton("更   改");
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				IsUpdate();
			}
		});
		setupComponent(quit,5,3,1,1,true);
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
	private void Input(){
		String id = product_id.getText().trim();
		String client = product_client.getText().trim();
		String name = product_name.getText().trim();
		String spec = product_spec.getText().trim();
		String unit = product_unit.getText().trim();
		String value = product_value.getText().trim();
		String make = product_make.getText().trim();
		String date = stock_date.getText().trim();
		String text = product_text.getText().trim();
		String baba[] = {id,client,name,spec,unit,value,make,date,text}; 
		for(int i=0;i<baba.length-1;i++){
			if(baba[i].isEmpty()){
				javax.swing.JOptionPane.showMessageDialog(null, "除备注以外，其他参数都是必要的，请重新输入！","输入数据不完整",javax.swing.JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if(!CheckValue(baba[5])){
			javax.swing.JOptionPane.showMessageDialog(null, "数量输入错误，只接收0至9之间的数字,请重新输入！","数字错误",javax.swing.JOptionPane.ERROR_MESSAGE);
			product_value.setText("");
			product_value.requestFocus();
			return;
		}
		if(!CheckDate(baba[7])){
			javax.swing.JOptionPane.showMessageDialog(null, "你的日期或格式输入不对，应为数字YYYY/MM/DD,请再输入！","输入日期格式错误",javax.swing.JOptionPane.ERROR_MESSAGE);
			stock_date.setText("");
			stock_date.requestFocus();
			return;
		}
		if(DBUtil.isExist("select * from product_in where product_ID="+"'"+baba[0]+"'")){
			javax.swing.JOptionPane.showMessageDialog(null, "请注意数据同样的编号也存在，不能录入，请更改之后重新输入！","数据有误",javax.swing.JOptionPane.ERROR_MESSAGE);
			clean();
			return;
		}
			String sql1 = "'"+id +"','"+client+"','"+name+"','"+spec+"','"+unit+"',"+value+",'"+make+"','"+date+"','"+text+"'";
			sql = "insert into product_in values("+sql1+")";
			USeDB.UpdateDB(sql);
			sql = "insert into product_old_record values("+sql1+")";
			USeDB.UpdateDB(sql);
			//frame.setTitle("数据录入成功！");
			clean();
			
	}
	private void IsUpdate(){
		String id = product_id.getText().trim();
		String client = product_client.getText().trim();
		String name = product_name.getText().trim();
		String spec = product_spec.getText().trim();
		String unit = product_unit.getText().trim();
		String value = product_value.getText().trim();
		String make = product_make.getText().trim();
		String date = stock_date.getText().trim();
		String text = product_text.getText().trim();
		String baba[] = {id,client,name,spec,unit,value,make,date,text}; 
		for(int i=0;i<baba.length-1;i++){
			if(baba[i].isEmpty()){
				javax.swing.JOptionPane.showMessageDialog(null, "除备注以外，其他参数都是必要的，请重新输入！","输入数据不完整",javax.swing.JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		if(!CheckValue(baba[5])){
			javax.swing.JOptionPane.showMessageDialog(null, "数量输入错误，只接收0至9之间的数字,请重新输入！","数字错误",javax.swing.JOptionPane.ERROR_MESSAGE);
			product_value.setText("");
			product_value.requestFocus();
			return;
		}
		if(!CheckDate(baba[7])){
			javax.swing.JOptionPane.showMessageDialog(null, "你的日期或格式输入不对，应为数字YYYY/MM/DD,请再输入！","输入日期格式错误",javax.swing.JOptionPane.ERROR_MESSAGE);
			stock_date.setText("");
			stock_date.requestFocus();
			return;
		}
		if(!DBUtil.isExist("select * from product_in where product_ID="+"'"+baba[0]+"'")){
			javax.swing.JOptionPane.showMessageDialog(null, "请注意更改的数据编号不存在，不能更改，请重新输入！","数据有误",javax.swing.JOptionPane.ERROR_MESSAGE);
			clean();
			return;
		}
			sql = "'"+id +"','"+client+"','"+name+"','"+spec+"','"+unit+"',"+value+",'"+make+"','"+date+"','"+text+"'";
			sql = "update product_in set product_ID='"+id+",'product_client='"+client+",'product_name='"+name+"',product_spec='"+spec+",'product_unit='"+unit+
			",'product_value='"+value+",'product_make='"+make+",'stock_date='"+date+",'product_text='"+text+"' where product_ID='"+id+"'";
			USeDB.UpdateDB(sql);
			//frame.setTitle("数据录入成功！");
			clean();
			
	}
	private void clean(){
		product_id.setText("");
		product_client.setText("");
		product_name.setText("");
		product_spec.setText("");
		product_unit.setText("");
		product_value.setText("");
		product_make.setText("");
		stock_date.setText("");
		product_text.setText("");
		product_id.requestFocus();
	}
	/*@Override
	*/
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
	//public static void main(String args[]){
	//	new createUserProductInterFace("录入");
	//}
}
