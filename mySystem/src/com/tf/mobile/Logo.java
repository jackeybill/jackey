package com.tf.mobile;
import java.awt.AlphaComposite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Logo implements ActionListener{
	public static final String logoon = "登录";
	public static final String helpt = "帮助";
	public static final String logooff ="退出";
	public static final String user = "用    户:";
	public static final String password = "密    码:";
	private javax.swing.JTextField userText;
	private javax.swing.JPasswordField userpwd;
	private javax.swing.JPanel jp = new javax.swing.JPanel(){
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
	private javax.swing.JButton logoin = null;
	private JFrame logo_Frame = new JFrame(logoon);
	/*public Logo() {
		// TODO 自动生成构造函数存根
	}
	*/
	public Logo(){
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}catch(Exception exe){System.err.print(exe.getMessage());}
		JFrame.setDefaultLookAndFeelDecorated(true);
		Toolkit tools = logo_Frame.getToolkit();
		Image logo = tools.getImage("res/logo.jpg");
		logo_Frame = new JFrame(logoon);
		logo_Frame.setIconImage(logo);
		
		
		jp.setLayout(new GridBagLayout());
		jp.setBackground(java.awt.Color.pink);
		
		javax.swing.ImageIcon pic = new javax.swing.ImageIcon("res/logo1.jpg");
		javax.swing.JLabel picture = new javax.swing.JLabel(pic);
		setupComponent(picture,0,0,GridBagConstraints.ABOVE_BASELINE_LEADING,1,true);
		javax.swing.JLabel users = new javax.swing.JLabel(user);
		setupComponent(users,0,1,1,1,false);
		javax.swing.JLabel pas = new javax.swing.JLabel(password);
		setupComponent(pas,0,2,1,1,false);
		userText = new javax.swing.JTextField(12);
		userText.setText("admin");
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
		userpwd.setText("123");
		userpwd.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if((e.getKeyChar() == KeyEvent.VK_ENTER) && (userpwd.getText().trim() != "")){
					logoin.requestFocus();
				}
				else{
					userpwd.requestFocus();
				}
			}
			public void keyReleased(KeyEvent e){
				
			}
		});
		setupComponent(userpwd,1,2,1,1,false);
		
		logoin = new javax.swing.JButton(logoon);
		logoin.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
			public void keyPressed(KeyEvent e){
				if((e.getKeyChar()==KeyEvent.VK_ENTER )){
					if((userText.getText().trim() == "") ){
						javax.swing.JOptionPane.showMessageDialog(null, "必须输入用户名！","输入用户名",JOptionPane.ERROR_MESSAGE);
						userText.requestFocus();}
					if((userpwd.getText().trim() =="")){
						javax.swing.JOptionPane.showMessageDialog(null, "必须输入密码！","输入密码",JOptionPane.ERROR_MESSAGE);
						userpwd.requestFocus();
						}
					boolean b = USeDB.logoon(userText.getText().trim(), userpwd.getText().trim());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date(System.currentTimeMillis());
					String day = sdf.format(date);
					String s ="'"+day+"','"+userText.getText().trim()+"','"+"登录"+"'";
					String sql = "insert into handle_record values("+s+")";
					USeDB.UpdateDB(sql);
					if(b){
						logo_Frame.dispose();
						new My_MainFrame(userText.getText().trim());
						}
					userText.setText("");
					userpwd.setText("");
					userText.requestFocus();
				}
			}
		});
		logoin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if((userText.getText().trim() == "")){
				userText.requestFocus();
				}
				if((userpwd.getText().trim() =="")){
					userpwd.requestFocus();
				}
				if(userText.getText().trim()!=""&& userpwd.getText().trim()!=""){
				boolean b = USeDB.logoon(userText.getText().trim(), userpwd.getText().trim());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				String day = sdf.format(date);
				String s ="'"+day+"','"+userText.getText().trim()+"','"+"登录"+"'";
				String sql = "insert into handle_record values("+s+")";
				USeDB.UpdateDB(sql);
				if(b==true){
					logo_Frame.dispose();
					new My_MainFrame(userText.getText().trim());
					}else{
				userText.setText("admin");
				userpwd.setText("123");
				userText.requestFocus();}
			}
			}
		});
		setupComponent(logoin,0,3,1,1,true);
		
		javax.swing.JButton help = new javax.swing.JButton(helpt);
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e1){
			JOptionPane.showMessageDialog(null,"你需要一个注册的用户名和正确的密码！","帮助",JOptionPane.ERROR_MESSAGE);
			userText.setText("");
			userpwd.setText("");
			userText.setFocusable(true);
			}
		});
		setupComponent(help,2,3,1,1,true);
		
		javax.swing.JButton exit = new javax.swing.JButton(logooff);
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e2){
				logo_Frame.dispose();
				System.exit(0);
			}
		});
		setupComponent(exit,1,3,1,1,true);
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
	/**
	 * @param args
	 */
	public void actionPerformed(ActionEvent e){
		
	}
	public static void main(String[] args) {
		new Logo();
	}
	public void setupComponent(JComponent component,int gridx,int gridy,int gridwidth,int ipadx,boolean fill){
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
