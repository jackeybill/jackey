package com.tf.mobile;
import java.awt.AlphaComposite;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
public class createUserStuffInterFace extends javax.swing.JPanel{
	private final static long serialVersionUID = 230002328992L;
	private javax.swing.JTextField stuff_id = null;
	private javax.swing.JTextArea stuff_name = null;
	private javax.swing.JTextField stuff_company = null;
	private javax.swing.JTextField stuff_people = null;
	private javax.swing.JTextField check_people = null;
	private javax.swing.JTextField stuff_unit = null;
	private javax.swing.JTextField stuff_value = null;
	private javax.swing.JTextField stuff_spec = null;
	private javax.swing.JTextField stuff_color = null;
	private javax.swing.JTextField stuff_place = null;
	private javax.swing.JTextField stock_date = null;
	private javax.swing.JTextField stuff_text = null;
	private javax.swing.JButton enter = null;
	private javax.swing.JButton clean = null;
	private javax.swing.JButton cancle = null;
	private String sql = null;

	public createUserStuffInterFace(){//发送短信
		this.setLayout(new GridBagLayout());
		//this.setBackground(java.awt.Color.PINK);
		
		javax.swing.JLabel stuff_id_label = new javax.swing.JLabel("接收人:");
		setupComponent(stuff_id_label,0,0,1,1,true);
		stuff_id = new javax.swing.JTextField(20);
		stuff_id.requestFocus();
		stuff_id.addKeyListener(new KeyListener(){
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar() == KeyEvent.VK_ENTER) && ((stuff_id.getText().trim()) != "")){
					stuff_name.requestFocus();
				}
			}
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
		setupComponent(stuff_id,1,0,1,1,true);
		
		javax.swing.JLabel stock_date_label = new javax.swing.JLabel("入库时间：");
		setupComponent(stock_date_label,0,2,1,1,true);
		stock_date = new javax.swing.JTextField(20);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String day = sdf.format(date);
		stock_date.setText( day );
		stock_date.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (stock_date.getText().trim() != "")){
					if(CheckDate(stock_date.getText().trim())){
						stuff_text.requestFocus();
					}
					else{
						javax.swing.JOptionPane.showMessageDialog(null,"数据录入错误格式为:YYYY/MM/DD，请你重新输入！","数据错误",javax.swing.JOptionPane.ERROR_MESSAGE);
						stock_date.setText("");
						stock_date.requestFocus();
					}
				}
			}
		});
		setupComponent(stock_date,1,2,1,1,true);
		
		javax.swing.JLabel stuff_name_label = new javax.swing.JLabel("短信内容：");
		setupComponent(stuff_name_label,0,3,1,1,true);
		stuff_name = new javax.swing.JTextArea(10,20);
		stuff_name.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (stuff_name.getText().trim() != "")){
					//stuff_company.requestFocus();
				}
			}
		});
		setupComponent(stuff_name,1,3,1,1,true);
		
		enter = new javax.swing.JButton("录   入");
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
		setupComponent(enter,1,5,1,1,true);
		clean = new javax.swing.JButton("清   空");
		clean.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clean();
			}
		});
		/*setupComponent(clean,3,5,1,1,true);
		cancle = new javax.swing.JButton("更   改");
		cancle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				IsUpdate();
			}
		});
		setupComponent(cancle,5,5,1,1,true);*/
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
		stuff_id.setText("");
		stuff_name.setText("");
		stock_date.setText("");
	}
	private void IsGood(){
		String id = stuff_id.getText().trim();
		String name = stuff_name.getText().trim();
		String date = stock_date.getText().trim();
		String str [] = {id,name,date};
		for(int i =0;i<str.length;i++){
			if(str[i].isEmpty()){
				javax.swing.JOptionPane.showMessageDialog(null,"必填项，请务必输入！","必须输入",javax.swing.JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		/*if(!CheckValue(str[6])){
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
		if(DBUtil.isExist("select stuff_ID from stuff_in where stuff_ID='"+str[0]+"'")){
			javax.swing.JOptionPane.showMessageDialog(null, "请注意数据同样的编号也存在，不能录入，请更改之后重新输入！","数据有误",javax.swing.JOptionPane.ERROR_MESSAGE);
			clean();
			return;
		}
		"INSERT INTO Person(name, address, " +
							"phone, email) VALUES (?,?,?,?) ";
		*/
		String sql1 = "'"+id+"','"+date+"','"+name+"'";
		sql ="insert into mo(sendto,sendtime,sendmsg) values("+sql1+")";
		System.out.println(sql);
		USeDB.UpdateDB(sql);
		clean();
	}
	private void IsUpdate(){
		String id = stuff_id.getText().trim();
		String name = stuff_name.getText().trim();
		String company = stuff_company.getText().trim();
		String people = stuff_people.getText().trim();
		String check = check_people.getText().trim();
		String unit = stuff_unit.getText().trim();
		String value = stuff_value.getText().trim();
		String spec = stuff_spec.getText().trim();
		String color = stuff_color.getText().trim();
		String place = stuff_place.getText().trim();
		String date = stock_date.getText().trim();
		String text = stuff_text.getText().trim();
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
		clean();
		
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
}
