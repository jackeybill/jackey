/**
Download by http://www.codefans.net  
 */
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * @author aMeng
 *
 */
public class createUserYieldScrapInterFace extends JPanel{
	private final static long serialVersionUID = 2300067448L;
	private javax.swing.JTextField stuff_id = null;
	private javax.swing.JTextField stuff_name = null;
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
	/**
	 * 
	 */
	public createUserYieldScrapInterFace() {
		// TODO 自动生成构造函数存根
		this.setLayout(new GridBagLayout());
		this.setBackground(java.awt.Color.pink);
		javax.swing.JLabel stuff_id_label = new javax.swing.JLabel("编     号:");
		setupComponent(stuff_id_label,0,0,1,1,true);
		stuff_id = new javax.swing.JTextField(10);
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
		javax.swing.JLabel stuff_name_label = new javax.swing.JLabel("原料名：");
		setupComponent(stuff_name_label,2,0,1,1,true);
		stuff_name = new javax.swing.JTextField(10);
		stuff_name.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (stuff_name.getText().trim() != "")){
					stuff_company.requestFocus();
				}
			}
		});
		setupComponent(stuff_name,3,0,1,1,true);
		javax.swing.JLabel stuff_company_label = new javax.swing.JLabel("生产商：");
		setupComponent(stuff_company_label,4,0,1,1,true);
		stuff_company = new javax.swing.JTextField(10);
		stuff_company.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (stuff_company.getText().trim() != "")){
					stuff_people.requestFocus();
				}
			}
		});
		setupComponent(stuff_company,5,0,1,1,true);
		javax.swing.JLabel stuff_people_label = new javax.swing.JLabel("收货员：");
		setupComponent(stuff_people_label,6,0,1,1,true);
		stuff_people = new javax.swing.JTextField(10);
		stuff_people.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (stuff_people.getText().trim() != "")){
					check_people.requestFocus();
				}
			}
		});
		setupComponent(stuff_people,7,0,1,1,true);
		
		javax.swing.JLabel check_people_label = new javax.swing.JLabel("检验员：");
		setupComponent(check_people_label,0,1,1,1,true);
		check_people = new javax.swing.JTextField(10);
		check_people.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (check_people.getText().trim() != "")){
					stuff_unit.requestFocus();
				}
			}
		});
		setupComponent(check_people,1,1,1,1,true);
		javax.swing.JLabel stuff_unit_label = new javax.swing.JLabel("单  位：");
		setupComponent(stuff_unit_label,2,1,1,1,true);
		stuff_unit = new javax.swing.JTextField(10);
		stuff_unit.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (stuff_unit.getText().trim() != "")){
					stuff_value.requestFocus();
				}
			}
		});
		setupComponent(stuff_unit,3,1,1,1,true);
		javax.swing.JLabel stuff_value_label = new javax.swing.JLabel("数    量：");
		setupComponent(stuff_value_label,4,1,1,1,true);
		stuff_value = new javax.swing.JTextField(10);
		stuff_value.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (stuff_value.getText().trim() != "")){
					if(CheckValue(stuff_value.getText().trim())){
						stuff_spec.requestFocus();
					}else
					{
						javax.swing.JOptionPane.showMessageDialog(null,"你的录入有误，请录入0-9的数值！","录入错误",javax.swing.JOptionPane.ERROR_MESSAGE);
						stuff_value.setText("");
						stuff_value.requestFocus();
					}
				}
			}
		});
		setupComponent(stuff_value,5,1,1,1,true);
		javax.swing.JLabel stuff_spec_label = new javax.swing.JLabel("规    格：");
		setupComponent(stuff_spec_label,6,1,1,1,true);
		stuff_spec = new javax.swing.JTextField(10);
		stuff_spec.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (stuff_spec.getText().trim() != "")){
					stuff_color.requestFocus();
				}
			}
		});
		setupComponent(stuff_spec,7,1,1,1,true);
		
		javax.swing.JLabel stuff_color_label = new javax.swing.JLabel("颜    色：");
		setupComponent(stuff_color_label,0,2,1,1,true);
		stuff_color = new javax.swing.JTextField(10);
		stuff_color.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && (stuff_color.getText().trim() != "")){
					stuff_place.requestFocus();
				}
			}
		});
		setupComponent(stuff_color,1,2,1,1,true);
		javax.swing.JLabel stuff_place_label = new javax.swing.JLabel("存放位置：");
		setupComponent(stuff_place_label,2,2,1,1,true);
		stuff_place = new javax.swing.JTextField(10);
		stuff_place.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER)&&(stuff_place.getText().trim() !="")){
					stock_date.requestFocus();
				}
			}
		});
		setupComponent(stuff_place,3,2,1,1,true);
		javax.swing.JLabel stock_date_label = new javax.swing.JLabel("入库时间：");
		setupComponent(stock_date_label,4,2,1,1,true);
		stock_date = new javax.swing.JTextField(10);
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
		setupComponent(stock_date,5,2,1,1,true);
		javax.swing.JLabel stuff_text_label = new javax.swing.JLabel("原料备注：");
		setupComponent(stuff_text_label,6,2,1,1,true);
		stuff_text = new javax.swing.JTextField(10);
		stuff_text.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER)){
					enter.requestFocus();
				}
			}
		});
		setupComponent(stuff_text,7,2,1,1,true);
		
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
		setupComponent(enter,1,3,1,1,true);
		clean = new javax.swing.JButton("清   空");
		clean.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clean();
			}
		});
		setupComponent(clean,3,3,1,1,true);
		cancle = new javax.swing.JButton("退   出");
		cancle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		setupComponent(cancle,5,3,1,1,true);
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
		stuff_id.setText("");
		stuff_name.setText("");
		stuff_company.setText("");
		stuff_people.setText("");
		check_people.setText("");
		stuff_unit.setText("");
		stuff_value.setText("");
		stuff_spec.setText("");
		stuff_color.setText("");
		stuff_place.setText("");
		stock_date.setText("");
		stuff_text.setText("");
		stuff_id.requestFocus();
	}
	private void IsGood(){
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
		if(!CheckValue(value)){
			javax.swing.JOptionPane.showMessageDialog(null,"请检验你输入的数量是不是数字，请重新输入！","重新输入",javax.swing.JOptionPane.ERROR_MESSAGE);
			stuff_value.setText("");
			stuff_value.requestFocus();
			return;
		}
		sql = "select stuff_ID,stuff_name,stuff_value from stuff_in where stuff_ID="+id+" and stuff_name="+name+" and stuff_value =>"+value;
		if(!DBUtil.isExist(sql)){
			javax.swing.JOptionPane.showMessageDialog(null,"请检验你输入的项编号、产品名以有数量有没有超过以前输入的数量，请重新输入！","重新输入",javax.swing.JOptionPane.ERROR_MESSAGE);
			return;
		}
		sql = "'"+id+"','"+name+"','"+company+"','"+people+"','"+check+"','"+unit+"','"+value+"','"+spec+"','"+color+"','"+place+"','"+date+"','"+text+"'";
		sql ="insert into yield_scrap values("+sql+")";
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
