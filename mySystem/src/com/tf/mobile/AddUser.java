package com.tf.mobile;
//Download by http://www.codefans.net 
import java.awt.AlphaComposite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class AddUser {
	private static final String logoon = "添加用户";
	private static final String helpt = "添加";
	private static final String logooff ="退出";
	private static final String user = "用         户:";
	private static final String password = "密       码:";
	private static final String enterpwd ="再次输入密码:";
	private javax.swing.JTextField userText=null;
	private javax.swing.JPasswordField userpwd=null;
	private javax.swing.JPasswordField enterpassword=null;
	private javax.swing.JPanel jp = new javax.swing.JPanel(){
		final static long serialVersionUID = 2309585L;
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
	};
	private javax.swing.JButton add = new javax.swing.JButton(helpt);
	private javax.swing.JButton cancle = new javax.swing.JButton(logooff);
	private JFrame logo_Frame = new JFrame(logoon);
	public AddUser() {
		// TODO 自动生成构造函数存根
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}catch(Exception exe){System.err.print(exe.getMessage());}
		JFrame.setDefaultLookAndFeelDecorated(true);
		Toolkit tools = logo_Frame.getToolkit();
		Image logo = tools.getImage("res/logo.jpg");
		logo_Frame = new JFrame(logoon);
		logo_Frame.setIconImage(logo);
		
		
		jp.setLayout(new GridBagLayout());
		javax.swing.ImageIcon pic = new javax.swing.ImageIcon("res/logo1.jpg");
		javax.swing.JLabel picture = new javax.swing.JLabel(pic);
		setupComponent(picture,0,0,GridBagConstraints.ABOVE_BASELINE_LEADING,1,true);
		javax.swing.JLabel users = new javax.swing.JLabel(user);
		setupComponent(users,0,1,1,1,false);
		javax.swing.JLabel pas = new javax.swing.JLabel(password);
		setupComponent(pas,0,2,1,1,false);
		javax.swing.JLabel enpas = new javax.swing.JLabel(enterpwd);
		setupComponent(enpas,0,3,1,1,false);
		userText = new javax.swing.JTextField(12);
		userText.requestFocus();
		userText.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if((e.getKeyChar() == KeyEvent.VK_ENTER )&&(userText.getText().trim() != "")){
					userpwd.requestFocus();
				}
			}
		});
		setupComponent(userText,1,1,1,1,false);
		userpwd = new javax.swing.JPasswordField(12);
		userpwd.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if((e.getKeyChar() == KeyEvent.VK_ENTER) && (! userpwd.getPassword().toString().trim().isEmpty())){
					enterpassword.requestFocus();
				}
				else{
					userpwd.requestFocus();
				}
			}
			public void keyReleased(KeyEvent e){
				
			}
		});
		setupComponent(userpwd,1,2,1,1,false);
		
		enterpassword = new javax.swing.JPasswordField(12);
		enterpassword.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				//String pwd = userpwd.getPassword().toString().trim();
				//String epwd = enterpassword.getPassword().toString().trim();
				if((e.getKeyChar()==KeyEvent.VK_ENTER) && ((userpwd.getPassword().toString().trim())==(enterpassword.getPassword().toString().trim()))){
						add.requestFocus();
				}
				else if((e.getKeyChar()==(KeyEvent.VK_ENTER)) && ((userpwd.getPassword().toString().trim())!=(enterpassword.getPassword().toString().trim()))){
					userpwd.setText("");
					enterpassword.setText("");
					userpwd.requestFocus();
				}
				
			}
			
		});
		setupComponent(enterpassword,1,3,1,1,false);
		//logoin = new javax.swing.JButton(logoon);
		add.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER )){
					String users =userText.getText().trim();
					String pwd =userpwd.getPassword().toString().trim();
					String epwd =enterpassword.getPassword().toString().trim();
					if(users.isEmpty()){
						javax.swing.JOptionPane.showMessageDialog(logo_Frame, "必须输入用户名！","输入用户名",JOptionPane.ERROR_MESSAGE);
						userText.requestFocus();}
					if(pwd.isEmpty()){
						javax.swing.JOptionPane.showMessageDialog(logo_Frame, "必须输入密码！","输入密码",JOptionPane.ERROR_MESSAGE);
						userpwd.requestFocus();
						}
					if(epwd.isEmpty()){
						javax.swing.JOptionPane.showMessageDialog(logo_Frame, "第二个输入密码必须与第一个密码相同！","密码不一致",JOptionPane.ERROR_MESSAGE);
						enterpassword.requestFocus();
					}
					boolean b = DBUtil.isExist("select user_name from user where user_name="+"'"+users+"'");
					if(b){
						javax.swing.JOptionPane.showMessageDialog(logo_Frame, "用户已经存在,请另外输入！","不能添加",JOptionPane.ERROR_MESSAGE);
						userText.setText("");
						userpwd.setText("");
						enterpassword.setText("");
						userText.requestFocus();
					}
					int vale = DBUtil.getValue("select user_name from user where user_name='"+users+"'")+1;
					if(vale>0){
						String sql = "insert into user values('"+vale+",'"+users+"','"+pwd+"')";
						boolean bo = USeDB.Update(sql);
						if(bo)JOptionPane.showConfirmDialog(logo_Frame, "用户名添加成功！");	
						
					}
					
				}
			}
		});
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String users =userText.getText().trim();
				String pwd =userpwd.getPassword().toString().trim();
				String epwd =enterpassword.getPassword().toString().trim();
				if(users.isEmpty()){
					javax.swing.JOptionPane.showMessageDialog(null, "必须输入用户名！","输入用户名",JOptionPane.ERROR_MESSAGE);
					userText.requestFocus();}
				if(pwd.isEmpty()){
					javax.swing.JOptionPane.showMessageDialog(null, "必须输入密码！","输入密码",JOptionPane.ERROR_MESSAGE);
					userpwd.requestFocus();
					}
				if(epwd.isEmpty()){
					javax.swing.JOptionPane.showMessageDialog(null, "第二个输入密码必须与第一个密码相同！","密码不一致",JOptionPane.ERROR_MESSAGE);
					enterpassword.requestFocus();
				}
				boolean b = DBUtil.isExist("select user_name from user where user_name='"+users+"'");
				if(b){
					javax.swing.JOptionPane.showMessageDialog(logo_Frame, "用户已经存在,请另外输入！","不能添加",JOptionPane.ERROR_MESSAGE);
					userText.setText("");
					userpwd.setText("");
					enterpassword.setText("");
					userText.requestFocus();
				}
				int vale = DBUtil.getValue("select user_name from user where user_name='"+users+"'")+1;
				if(vale>0){
					String sql = "insert into user values('"+vale+",'"+users+"','"+pwd+"')";
					USeDB.UpdateDB(sql);
					boolean bo = DBUtil.isExist("select user_name from user where id=vale");
					if(bo){
						JOptionPane.showConfirmDialog(null, "用户名添加成功！");	
					}
					JOptionPane.showConfirmDialog(null, "用户名已经存在！");	
				}
				userText.setText("");
				userpwd.setText("");
				enterpassword.setText("");
				userText.requestFocus();
			}
		});
		setupComponent(add,0,4,1,1,true);
		//Download by http://www.codefans.net 
		//javax.swing.JButton exit = new javax.swing.JButton(logooff);
		cancle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e2){
				logo_Frame.dispose();
				//System.exit(0);
			}
		});
		setupComponent(cancle,1,4,1,1,true);
		java.awt.Dimension d = tools.getScreenSize();
		int width = (int)(d.getWidth()-350) /2;
		int height = (int)(d.getHeight()-240)/2;
		logo_Frame.add(jp);
		logo_Frame.setAlwaysOnTop(true);
		logo_Frame.setLocation(width, height);
		logo_Frame.setSize(350, 240);
		logo_Frame.pack();
		logo_Frame.validate();
		logo_Frame.setVisible(true);
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
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		jp.add(component,gridBagConstrains);
	}
}
