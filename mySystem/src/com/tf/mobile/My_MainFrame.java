package com.tf.mobile;
//Download by http://www.codefans.net 
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.Vector;

public class My_MainFrame {
	private final static javax.swing.ImageIcon icon = new javax.swing.ImageIcon("res/logo1.jpg");
	private JFrame frame = new javax.swing.JFrame();
	private JMenuBar menuBar = null;
	private JToolBar toolBar = null;
	private JSplitPane splitPane = null;
	private JScrollPane scrollPane = null;
	private JPanel panel = null;
	private JTabbedPane tabbedPane = new javax.swing.JTabbedPane();
	
	private JButton stuff_in = null;//发送短信
	private JButton stuff_quit = null;//已发短信
	private JButton stuff_stock = null;//待发短信
	private JButton pendingSms = null;//待发短信
	private JButton yield_draw = null;//已发短信
	private JButton yield_off = null;//楼层短信模版
	private JButton yield_scrap = null;//楼层联系人
	private JButton contacts = null;//通讯录
	private JButton contacts_list = null;//通讯录列表
	private JButton mo_list = null;
	private JButton contact_list = null;
	
	private JButton help = null;//帮助
	private JButton about = null;//关于
	private JButton exit_system = null;//退出系统
	
	private String sql = null;
	private String strTitle = null;
	
	public My_MainFrame() {
		// TODO 自动生成构造函数存根
	}
	public My_MainFrame(String name){
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			
			Thread thread = new LoadFileTask();
			thread.start();
		}catch(Exception exe){
			System.err.print(exe.getMessage());
		}
		java.awt.Toolkit tool = frame.getToolkit();
		Image image = tool.getImage("res/logo.jpg");
		java.awt.Dimension dimn = tool.getScreenSize();
		String title = "报警监控短信系统--"+name;
		frame.setTitle(title);
		frame.setIconImage(image);
		frame.setFocusable(true);
		frame.setLayout(new java.awt.BorderLayout());
		frame.setJMenuBar(createJMenuBar());
		frame.add(createJToolBar(),"North");
		frame.add(createSplitPane(),"Center");
		//frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowListener(){
			public void windowActivated(WindowEvent e){}
			//因对窗口调用dispose()而将其关闭时调用
			public void windowClosed(WindowEvent e){}
			//用户试图从窗口的系统菜单中关闭窗口时调用
			public void windowClosing(WindowEvent e){
				String name = frame.getTitle().trim().substring(8);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				String day = sdf.format(date);
				String s ="'"+day+"','"+name+"','"+"退出"+"'";
				sql = "insert into handle_record values("+s+")";
				USeDB.UpdateDB(sql);
				frame.dispose();
				System.exit(0);
			}
			//当窗口不再是活动窗口时调用
			public void windowDeactivated(WindowEvent e){}
			//窗口从最小化状态变为正常状态时调用
			public void windowDeiconified(WindowEvent e){}
			//窗口从正常状态变为最小状态时调用
			public void windowIconified(WindowEvent e){}
			//窗口首次变为可见时调用
			public void windowOpened(WindowEvent e){}
		});
		//frame.setSize(dimn);
		frame.setPreferredSize(dimn);
		frame.setBackground(java.awt.Color.pink);
		frame.pack();
		frame.validate();
		frame.setVisible(true);
	}
	private JMenu createSystem_Manage_Menu(){
		javax.swing.JMenu systemMenu = new javax.swing.JMenu("系统管理");
		javax.swing.JMenuItem logoin = new javax.swing.JMenuItem("注      册");
		logoin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new AddUser();
			}
		});
		javax.swing.JMenuItem logoout = new javax.swing.JMenuItem("删除用户");
		logoout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String jp = new JOptionPane().showInputDialog(frame,"请输入要删除的用户名！","输入用户名",JOptionPane.YES_NO_CANCEL_OPTION).trim();
				if(USeDB.delUser(jp)){
					JOptionPane.showConfirmDialog(null, "用户删除成功!");
				}
				JOptionPane.showConfirmDialog(null, "用户删除失败!");
			}
		});
		javax.swing.JMenuItem datatidy = new javax.swing.JMenuItem("数据整理");
		logoin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		systemMenu.add(logoin);
		systemMenu.add(logoout);
		systemMenu.add(datatidy);
		return systemMenu;
	}
	
	private JMenu createHelp_Menu(){
		javax.swing.JMenu helpMenu = new javax.swing.JMenu("帮助");
		/*javax.swing.JMenuItem help = new javax.swing.JMenuItem("帮助");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddHelp();
			}
		});*/
		javax.swing.JMenuItem about = new javax.swing.JMenuItem("关于");
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new UserAbout();
			}
		});
		//helpMenu.add(help);
		helpMenu.add(about);
		return helpMenu;
	}
	private JMenuBar createJMenuBar(){
		menuBar = new javax.swing.JMenuBar(){
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
		String strRole = SingletonClient.currentRole;
		if("admin".equals(strRole))
			menuBar.add(createSystem_Manage_Menu());
		//menuBar.add(createStuff_Manage_Menu());
		//menuBar.add(createProduct_Manage_Menu());
		//menuBar.add(createStroeroom_Manage_Stock());
		menuBar.add(createHelp_Menu());
		menuBar.setBackground(java.awt.Color.pink);
		return menuBar;
	}
	private JToolBar createJToolBar(){
		toolBar = new javax.swing.JToolBar(){
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
		
		ImageIcon iconBtn = new javax.swing.ImageIcon("res/btn1.jpg");
		stuff_in = new javax.swing.JButton(iconBtn);
		stuff_in.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddStuffIn("发送短信");
			}
		});
		
		iconBtn = new javax.swing.ImageIcon("res/btn3.jpg");
		stuff_quit = new javax.swing.JButton(iconBtn);
		stuff_quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddStuffQuit("开始监控");
				/*stuff_quit.setText("监控关闭");
				stuff_quit.updateUI();*/
			}
		});
		
		iconBtn = new javax.swing.ImageIcon("res/btn0.jpg");
		stuff_stock = new javax.swing.JButton(iconBtn);
		stuff_stock.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String tit[] = {"接收人","发送时间","发送内容"};
				sql = "select sendto,sendtime,sendmsg from mo where isSend='0'";
				AddTable("待发短信",tit,sql);
			}
		});
		
		pendingSms = new javax.swing.JButton(iconBtn);
		pendingSms.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				PendingSmsList("待发短信");
			}
		});
		
		iconBtn = new javax.swing.ImageIcon("res/btn2.jpg");
		yield_draw = new javax.swing.JButton(iconBtn);
		yield_draw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				//AddYieldDraw("生产开单");
				String tit[] = {"接收人","发送时间","发送内容"};
				sql = "select sendto,sendtime,sendmsg from mo where isSend<>'0'";
				AddTable("已发短信",tit,sql);
			}
		});
		iconBtn = new javax.swing.ImageIcon("res/btn4.jpg");
		yield_off = new javax.swing.JButton(iconBtn);
		yield_off.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddMoTemplete("楼层短信模版");
			}
		});
		iconBtn = new javax.swing.ImageIcon("res/btn5.jpg");
		mo_list = new javax.swing.JButton(iconBtn);
		mo_list.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				//AddYieldDraw("生产开单");
				String tit[] = {"楼层名称","短信模版"};
				sql = "select keyname,templete from mo_templete";
				AddTable("楼层短信模版列表",tit,sql);
			}
		});
		
		iconBtn = new javax.swing.ImageIcon("res/btn6.jpg");
		yield_scrap = new javax.swing.JButton(iconBtn);
		yield_scrap.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddMobileLib("楼层联系人");
			}
		});
		iconBtn = new javax.swing.ImageIcon("res/btn7.jpg");
		contact_list = new javax.swing.JButton(iconBtn);
		contact_list.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				//AddYieldDraw("生产开单");
				String tit[] = {"楼层名称","联系人手机列表"};
				sql = "select keyname,keyvalues from mobile_lib";
				AddTable("楼层联系人列表",tit,sql);
			}
		});
		// contact management
		strTitle = "通讯录";
		iconBtn = new javax.swing.ImageIcon("res/btn8.jpg");
		contacts = new javax.swing.JButton(iconBtn);
		contacts.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddContacts(strTitle);
			}
		});
		strTitle = "通讯录列表";
		iconBtn = new javax.swing.ImageIcon("res/btn9.jpg");
		contacts_list = new javax.swing.JButton(iconBtn);
		contacts_list.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				//AddYieldDraw("生产开单");
				String tit[] = {"手机号","姓名","公司"};
				sql = "select mobileno,username,company from contacts order by mobileno";
				AddTable(strTitle,tit,sql);
			}
		});
		help = new javax.swing.JButton("帮     助");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddHelp();
			}
		});
		about = new javax.swing.JButton("关     于");
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new UserAbout();
			}
		});
		iconBtn = new javax.swing.ImageIcon("res/btn.jpg");
		exit_system = new javax.swing.JButton(iconBtn);
		exit_system.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = frame.getTitle().trim().substring(8);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(System.currentTimeMillis());
				String day = sdf.format(date);
				String s ="'"+day+"','"+name+"','"+"退出"+"'";
				sql = "insert into handle_record values("+s+")";
				USeDB.UpdateDB(sql);
				frame.dispose();
				System.exit(0);
			}
		});
		// Access control
		String strRole = SingletonClient.currentRole;
		
		toolBar.add(stuff_stock);
		toolBar.addSeparator();
		toolBar.add(yield_draw);
		toolBar.addSeparator();
		
		if ("operator".equals(strRole) || "admin".equals(strRole)){			
			toolBar.add(stuff_in);
			toolBar.addSeparator();
			toolBar.add(mo_list);
			toolBar.addSeparator();
			toolBar.add(contact_list);
			toolBar.addSeparator();
			toolBar.add(contacts_list);
		}
		if ("admin".equals(strRole)){
			toolBar.addSeparator();
			toolBar.add(stuff_quit);
			toolBar.addSeparator();
			toolBar.add(yield_off);			
			toolBar.addSeparator();
			toolBar.add(yield_scrap);
			toolBar.addSeparator();
			toolBar.add(contacts);
		}
		/*toolBar.addSeparator();
		toolBar.add(about);*/
		toolBar.addSeparator();
		toolBar.add(exit_system);		
		toolBar.setBackground(java.awt.Color.pink);
		return toolBar;
	}
	private JScrollPane createScrollPane4List(){
		panel = new javax.swing.JPanel(){
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
		panel.setLayout(new javax.swing.BoxLayout(panel,BoxLayout.Y_AXIS ));
		javax.swing.ImageIcon image6 = new javax.swing.ImageIcon("res/stuffin.jpg");
		stuff_in = new javax.swing.JButton(image6);
		stuff_in.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddStuffIn("发送短信");
			}
		});
		panel.add(stuff_in);
		panel.setBackground(java.awt.Color.pink);
		scrollPane = new javax.swing.JScrollPane(panel);
		scrollPane.setBackground(java.awt.Color.pink);
		return scrollPane;
	}
	private JScrollPane createScrollPane(){
		panel = new javax.swing.JPanel(){
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
		panel.setLayout(new javax.swing.BoxLayout(panel,BoxLayout.Y_AXIS ));
		javax.swing.ImageIcon image6 = new javax.swing.ImageIcon("res/stuffin.jpg");
		stuff_in = new javax.swing.JButton(image6);
		stuff_in.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AddStuffIn("发送短信");
			}
		});
		panel.add(stuff_in);		
		panel.setBackground(java.awt.Color.pink);
		scrollPane = new javax.swing.JScrollPane(panel);
		scrollPane.setBackground(java.awt.Color.pink);
		return scrollPane;
	}
	private JTabbedPane createHandlePane(){
		//tabbedPane = new javax.swing.JTabbedPane();
		javax.swing.ImageIcon image = new javax.swing.ImageIcon("res/welcome1.jpg");
		javax.swing.JLabel component = new javax.swing.JLabel(image);
		tabbedPane.addTab("欢迎",icon ,component);
		//tabbedPane.setBackground(java.awt.Color.pink);
		tabbedPane.setBackgroundAt(0, java.awt.Color.pink);
		return tabbedPane;
	}
	private JTabbedPane createTablePane(){
		//tabbedPane = new javax.swing.JTabbedPane();
		javax.swing.ImageIcon image = new javax.swing.ImageIcon("res/welcome1.jpg");
		javax.swing.JLabel component = new javax.swing.JLabel(image);
		tabbedPane.addTab("欢迎",icon ,component);
		//tabbedPane.setBackground(java.awt.Color.pink);
		tabbedPane.setBackgroundAt(0, java.awt.Color.pink);
		return tabbedPane;
	}
	private JSplitPane createSplitPane(){
		splitPane = new javax.swing.JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,createScrollPane(),createHandlePane()){
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
		splitPane.setDividerLocation(1);
		splitPane.setDividerSize(5);
		splitPane.setOneTouchExpandable(true);
		splitPane.setBackground(java.awt.Color.pink);
		return splitPane;
	}
	private JSplitPane createListPane(){
		splitPane = new javax.swing.JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,createScrollPane(),createTablePane()){
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
		splitPane.setDividerLocation(1);
		splitPane.setDividerSize(5);
		splitPane.setOneTouchExpandable(true);
		splitPane.setBackground(java.awt.Color.pink);
		return splitPane;
	}	
	private void AddStuffIn(String s){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab(s, icon,new PendingSmsListInterFace());
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	private void AddMoTemplete(String s){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab(s, icon,new createMoTempleteInterFace());
		//tabbedPane.addTab(s, icon,createListPane());
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	private void AddMobileLib(String s){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab(s, icon,new CreateMobileLibInterFace());
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	private void PendingSmsList(String s){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab(s, icon,new PendingSmsListInterFace());
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	
	private void AddContacts(String s){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab(s, icon,new CreateContactsInterFace());
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	private void AddStuffQuit(String s){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab(s,icon ,new createStuffQuitInterFace());
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	private void AddTable(String title,String st[],String sq){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab(title,icon,new createUserTable(st,sq));
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	/*private void AddTable(String title,Vector v,String sql){
		/*
		 * 此方法致数据错误
		 */
		/*int i = tabbedPane.indexOfTab(title);
		if(i>-1)tabbedPane.remove(i);
		tabbedPane.addTab(title, new createUserTable(v,sql));
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	*/
	private void AddProductIn(String title){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab(title,icon ,new createUserProductInterFace());
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
	private void AddHelp(){
		int i = tabbedPane.getTabCount();
		if(i>0)tabbedPane.removeAll();
		tabbedPane.addTab("帮助",icon ,new UserHelp());
		int b = tabbedPane.getTabCount();
		tabbedPane.setSelectedIndex(b-1);
	}
}
