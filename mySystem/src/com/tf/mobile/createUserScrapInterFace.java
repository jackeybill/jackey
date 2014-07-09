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

public class createUserScrapInterFace extends javax.swing.JPanel {
	private final static long serialVersionUID = 2300002232323L;
	private javax.swing.JTextField scrap_id = null;
	private javax.swing.JTextField user_name = null;
	private javax.swing.JTextField handle_date = null;
	private javax.swing.JTextField scrap_text = null;
	private javax.swing.JButton enter = null;
	private javax.swing.JButton cancle = null;
	private javax.swing.JButton quit = null;
	private String sql = null;
	
	public createUserScrapInterFace(){//废料处理记录
		this.setLayout(new java.awt.GridBagLayout());
		this.setBackground(java.awt.Color.PINK);
		javax.swing.JLabel scrap_id_lable = new javax.swing.JLabel("废料编号:");
		setupComponent(scrap_id_lable,0,0,1,1,true);
		scrap_id = new javax.swing.JTextField(10);
		scrap_id.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (scrap_id.getText().trim() !="")){
					user_name.requestFocus();
				}
				scrap_id.requestFocus();
			}
			public void keyReleased(KeyEvent e){}
		});
		setupComponent(scrap_id,1,0,1,1,true);
		javax.swing.JLabel user_name_lable = new javax.swing.JLabel("处理人员:");
		setupComponent(user_name_lable,2,0,1,1,true);
		user_name = new javax.swing.JTextField(10);
		user_name.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (user_name.getText().trim() !="")){
					handle_date.requestFocus();
				}
				user_name.requestFocus();
			}
			public void keyReleased(KeyEvent e){}
		});
		setupComponent(user_name,3,0,1,1,true);
		javax.swing.JLabel handle_date_lable = new javax.swing.JLabel("处理日期:");
		setupComponent(handle_date_lable,4,0,1,1,true);
		handle_date = new javax.swing.JTextField(10);
		handle_date.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (handle_date.getText().trim() !="")){
					if(CheckDate(handle_date.getText().trim())){
						scrap_text.requestFocus();
					}else{
						javax.swing.JOptionPane.showMessageDialog(null,"你输入的时间不正确，请重新输入！","时间错误",javax.swing.JOptionPane.ERROR_MESSAGE);
						handle_date.setText("");
						handle_date.requestFocus();
					}
				}
				handle_date.requestFocus();
			}
			public void keyReleased(KeyEvent e){}
		});
		setupComponent(handle_date,5,0,1,1,true);
		javax.swing.JLabel scrap_text_lable = new javax.swing.JLabel("处理备注:");
		setupComponent(scrap_text_lable,6,0,1,1,true);
		scrap_text = new javax.swing.JTextField(10);
		scrap_text.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if(e.getKeyChar()==KeyEvent.VK_ENTER){
					enter.requestFocus();
				}
			}
			public void keyReleased(KeyEvent e){}
		});
		setupComponent(scrap_text,7,0,1,1,true);
		
		enter = new javax.swing.JButton("录入");
		enter.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if(e.getKeyChar()==KeyEvent.VK_ENTER){
					String id = scrap_id.getText().trim();
					String name = user_name.getText().trim();
					String date = handle_date.getText().trim();
					String text = scrap_text.getText().trim();
					String str[] = {id,name,date,text};
					for(int j=0;j<str.length-1;j++){
						if(str[j] == ""){
							javax.swing.JOptionPane.showMessageDialog(null,"用户名、编号、时间为必填项，请务必填写完整！","填写记录",javax.swing.JOptionPane.ERROR_MESSAGE);
							scrap_id.requestFocus();
							return;
						}
					}
					if(!CheckDate(str[2])){
						javax.swing.JOptionPane.showMessageDialog(null,"你输入的时间不正确，请重新输入！","时间错误",javax.swing.JOptionPane.ERROR_MESSAGE);
						handle_date.setText("");
						handle_date.requestFocus();
						return;
					}
					sql = "'"+id+"','"+name+"','"+date+"','"+text+"'";
					sql ="insert into * from flotsam_record values("+sql+")";
					USeDB.UpdateDB(sql);
					
				}
			}
			public void keyReleased(KeyEvent e){}
		});
		enter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == enter){
					String id = scrap_id.getText().trim();
					String name = user_name.getText().trim();
					String date = handle_date.getText().trim();
					String text = scrap_text.getText().trim();
					String str[] = {id,name,date,text};
					for(int j=0;j<str.length-1;j++){
						if(str[j] == ""){
							javax.swing.JOptionPane.showMessageDialog(null,"用户名、编号、时间为必填项，请务必填写完整！","填写记录",javax.swing.JOptionPane.ERROR_MESSAGE);
							scrap_id.requestFocus();
							return;
						}
					}
					if(!CheckDate(str[2])){
						javax.swing.JOptionPane.showMessageDialog(null,"你输入的时间不正确，请重新输入！","时间错误",javax.swing.JOptionPane.ERROR_MESSAGE);
						handle_date.setText("");
						handle_date.requestFocus();
						return;
					}
					sql = "'"+id+"','"+name+"','"+date+"','"+text+"'";
					sql ="insert into flotsam_record values("+sql+")";
					USeDB.UpdateDB(sql);
					scrap_id.setText("");
					user_name.setText("");
					handle_date.setText("");
					scrap_text.setText("");
					scrap_id.requestFocus();
				}
			}
		});
		setupComponent(enter,1,1,1,1,true);
		cancle = new javax.swing.JButton("清空");
		cancle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				scrap_id.setText("");
				user_name.setText("");
				handle_date.setText("");
				scrap_text.setText("");
			}
		});
		setupComponent(cancle,3,1,1,1,true);
		quit = new javax.swing.JButton("退出");
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		setupComponent(quit,5,1,1,1,true);
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
	/*public static void main(String args[]){
		new createUserScrapInterFace("");
	}
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
}
