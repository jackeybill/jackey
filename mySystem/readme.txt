http://www.shjsit.com


	? 2012.2.26
		○ 功能完善
		○ 异常报警
		○ UI 美化
		○ 检查

2012.2.27


http://www.javaswing.org/java-swing-table.aspx
http://docs.oracle.com/javase/tutorial/uiswing/components/table.html#eg

设为首页登陆|注册 天极传媒：比特网|天极网|IT专家网|52PK游戏网|手机天极|IT分众
      首页| 基础架构存储服务器数据中心虚拟化| 网络安全网络安全| 软件与开发管理软件开发数据库| 操作系统WinsystemLinuxUnix 推荐信息IT专家网 > 开发平台子站 > JAVA专区 >正文Java Swing组件大全的实用例子 
作者：佚名出处：IT专家网2009-12-14 13:00本文介绍一个Java Swing组件大全的实用例子 。

　　这个程序了不得，基本控件全包含进去了。。。真佩服这个程序的作者


　package ch02.section02; 
　　import javax.swing.*; 
　　import java.awt.*; 
　　import java.awt.event.*; 
　　import javax.swing.tree.*; 
　　import javax.swing.event.*; 
　　import javax.swing.border.*; 
　　import javax.swing.table.*; 
　　public class SwingTest extends JFrame{ 
　　public SwingTest(){ 
　　MenuTest menuTest=new MenuTest(); 
　　LeftPanel leftPanel=new LeftPanel(); 
　　RightPanel rightPanel=new RightPanel(); 
　　BottomPanel bottomPanel=new BottomPanel(); 
　　CenterPanel centerPanel=new CenterPanel(); 
　　Container c=this.getContentPane(); 
　　this.setJMenuBar(menuTest); 
　　c.add(leftPanel,BorderLayout.WEST); 
　　c.add(rightPanel,BorderLayout.EAST); 
　　c.add(centerPanel,BorderLayout.CENTER); 
　　c.add(bottomPanel,BorderLayout.SOUTH); 
　　this.addWindowListener(new WindowAdapter(){ 
　　public void WindowClosing(WindowEvent e){ 
　　dispose(); 
　　System.exit(0); 
　　} 
　　}); 
　　setSize(700,500); 
　　setTitle("Swing 组件大全简体版"); 
　　setUndecorated(true); 
　　setLocation(200,150); 
　　show(); 
　　} 
　　class MenuTest extends JMenuBar{ 
　　private JDialog aboutDialog; 
　　public MenuTest(){ 
　　JMenu fileMenu=new JMenu("文件"); 
　　JMenuItem exitMenuItem=new JMenuItem("退出",KeyEvent.VK_E); 
　　JMenuItem aboutMenuItem=new JMenuItem("关于..",KeyEvent.VK_A); 
　　fileMenu.add(exitMenuItem); 
　　fileMenu.add(aboutMenuItem); 
　　this.add(fileMenu); 
　　aboutDialog=new JDialog(); 
　　initAboutDialog(); 
　　exitMenuItem.addActionListener(new ActionListener(){ 
　　public void actionPerformed(ActionEvent e){ 
　　dispose(); 
　　System.exit(0); 
　　} 
　　}); 

相关消息：1234下一页 责任编辑：谢妍妍
关键字：Java,Swing,技巧
分享本文到： 打印关闭 专家博文白皮书天极服务 | 关于我们 | About us | 网站律师 | 加入我们 | 联系我们 | 广告业务 | 友情链接 | 网站地图 
All Rights Reserved, Copyright 2004-2011, Ctocio.com.cn 渝ICP证B2-20030003号如有意见请与我们联系 powered by 天极内容管理平台CMS4i  

aboutMenuItem.addActionListener(new ActionListener(){ 
　　public void actionPerformed(ActionEvent e){ 
　　aboutDialog.show(); 
　　} 
　　}); 
　　} 
　　public JDialog get(){ 
　　return aboutDialog; 
　　} 
　　public void initAboutDialog(){ 
　　aboutDialog.setTitle("关于"); 
　　Container con=aboutDialog.getContentPane(); 
　　Icon icon=new ImageIcon("sdmile.gif"); 
　　JLabel aboutLabel=new JLabel(""+" 
　　Swing!"+" 
　　",icon,JLabel.CENTER); 
　　con.add(aboutLabel,BorderLayout.CENTER); 
　　aboutDialog.setSize(450,225); 
　　aboutDialog.setLocation(300,300); 
　　aboutDialog.addWindowListener(new WindowAdapter(){ 
　　public void WindowClosing(WindowEvent e){ 
　　dispose(); 
　　} 
　　}); 
　　} 
　　} 
　　class LeftPanel extends JPanel{ 
　　private int i=0; 
　　public LeftPanel(){ 
　　DefaultMutableTreeNode root=new DefaultMutableTreeNode("Root"); 
　　DefaultMutableTreeNode child=new DefaultMutableTreeNode("Child"); 
　　DefaultMutableTreeNode select=new DefaultMutableTreeNode("select"); 
　　DefaultMutableTreeNode child1=new DefaultMutableTreeNode(""+i); 
　　root.add(child); 
　　root.add(select); 
　　child.add(child1); 
　　JTree tree=new JTree(root); 
　　tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION); 
　　tree.setRowHeight(20); 
　　tree.addTreeSelectionListener(new TreeSelectionListener(){ 
　　public void valueChanged(TreeSelectionEvent e){ 
　　JTree tree=(JTree)e.getSource(); 
　　DefaultMutableTreeNode selectNode=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent(); 
　　i++; 
　　selectNode.add(new DefaultMutableTreeNode(""+i)); 
　　} 
　　});
tree.setPreferredSize(new Dimension(100,300)); 
　　JScrollPane scrollPane=new JScrollPane(tree); 
　　this.add(scrollPane); 
　　} 
　　} 
　　class BottomPanel extends JPanel{ 
　　private JProgressBar pb; 
　　public BottomPanel(){ 
　　pb=new JProgressBar(); 
　　pb.setPreferredSize(new Dimension(680,20)); 
　　Timer time=new Timer(1,new ActionListener(){ 
　　int counter=0; 
　　public void actionPerformed(ActionEvent e){ 
　　counter++; 
　　pb.setValue(counter); 
　　Timer t=(Timer)e.getSource(); 
　　if(counter==pb.getMaximum()){ 
　　t.stop(); 
　　counter=0; 
　　t.start(); 
　　} 
　　} 
　　}); 
　　time.start(); 
　　pb.setStringPainted(true); 
　　pb.setMinimum(0); 
　　pb.setMaximum(1000); 
　　pb.setBackground(Color.white); 
　　pb.setForeground(Color.red); 
　　this.add(pb); 
　　} 
　　public void setProcessBar(BoundedRangeModel rangeModel){ 
　　pb.setModel(rangeModel); 
　　} 
　　} 
　　class RightPanel extends JPanel{ 
　　public RightPanel(){ 
　　this.setLayout(new GridLayout(8,1)); 
　　JCheckBox checkBox=new JCheckBox("复选按钮"); 
　　JButton button=new JButton("打开文件"); 
　　button.addActionListener(new ActionListener(){ 
　　public void actionPerformed(ActionEvent e){ 
　　JFileChooser file=new JFileChooser(); 
　　int resule=file.showOpenDialog(new JPanel()); 
　　if(resule==file.APPROVE_OPTION){ 
　　String fileName=file.getSelectedFile().getName(); 
　　String dir=file.getSelectedFile().getName(); 
　　JOptionPane.showConfirmDialog(null,dir+"\\"+fileName,"选择的文件",JOptionPane.YES_OPTION); 
　　} 
　　} 
　　});

JToggleButton toggleButton=new JToggleButton("双胎按钮"); 
　　ButtonGroup buttonGroup=new ButtonGroup(); 
　　JRadioButton radioButton1=new JRadioButton("单选按钮1",false); 
　　JRadioButton radioButton2=new JRadioButton("单选按钮2",false); 
　　JComboBox comboBox=new JComboBox(); 
　　comboBox.setToolTipText("点击下拉列表增加选项"); 
　　comboBox.addActionListener(new ActionListener(){ 
　　public void actionPerformed(ActionEvent e){ 
　　JComboBox comboBox=(JComboBox)e.getSource(); 
　　comboBox.addItem("程序员"); 
　　comboBox.addItem("分析员"); 
　　} 
　　}); 
　　DefaultListModel litem=new DefaultListModel(); 
　　litem.addElement("香蕉"); 
　　litem.addElement("水果"); 
　　JList list=new JList(litem); 
　　list.addListSelectionListener(new ListSelectionListener(){ 
　　public void valueChanged(ListSelectionEvent e){ 
　　JList l=(JList)e.getSource(); 
　　Object s=l.getSelectedValue(); 
　　JOptionPane.showMessageDialog(null,s,"消息框",JOptionPane.YES_OPTION); 
　　} 
　　}); 
　　buttonGroup.add(radioButton1); 
　　buttonGroup.add(radioButton2); 
　　add(button); 
　　add(toggleButton); 
　　add(checkBox); 
　　add(radioButton1); 
　　add(radioButton2); 
　　add(comboBox); 
　　add(list); 
　　this.setBorder(new EtchedBorder(EtchedBorder.LOWERED,Color.LIGHT_GRAY,Color.blue)); 
　　} 
　　} 
　　class CenterPanel extends JPanel{ 
　　public CenterPanel(){ 
　　JTabbedPane tab=new JTabbedPane(JTabbedPane.TOP); 
　　JTextField textField=new JTextField("文本域，点击打开<文件按钮>可选择文件"); 
　　textField.setActionCommand("textField"); 
　　JTextPane textPane=new JTextPane(); 
　　textPane.setCursor(new Cursor(Cursor.TEXT_CURSOR)); 
　　textPane.setText("编辑器，试着点击文本区，试着拉动分隔条。"); 
　　textPane.addMouseListener(new MouseAdapter(){ 
　　public void mousePressed(MouseEvent e){ 
　　JTextPane textPane=(JTextPane)e.getSource(); 
　　textPane.setText("编辑器点击命令成功"); 
　　} 
　　}); 
　　JSplitPane splitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,textField,textPane); 
　　JTable table=new JTable(10,10); 
　　JPanel pane=new JPanel(); 
　　pane.add(table.getTableHeader(),BorderLayout.NORTH); 
　　pane.add(table); 
　　tab.addTab("文本演示",splitPane); 
　　tab.addTab("表格演示", pane); 
　　tab.setPreferredSize(new Dimension(500,600)); 
　　this.add(tab); 
　　this.setEnabled(true); 
　　} 
　　} 
　　public static void main(String args[]){ 
　　new SwingTest(); 
　　} 
　　}


JToggleButton toggleButton=new JToggleButton("双胎按钮"); 
　　ButtonGroup buttonGroup=new ButtonGroup(); 
　　JRadioButton radioButton1=new JRadioButton("单选按钮1",false); 
　　JRadioButton radioButton2=new JRadioButton("单选按钮2",false); 
　　JComboBox comboBox=new JComboBox(); 
　　comboBox.setToolTipText("点击下拉列表增加选项"); 
　　comboBox.addActionListener(new ActionListener(){ 
　　public void actionPerformed(ActionEvent e){ 
　　JComboBox comboBox=(JComboBox)e.getSource(); 
　　comboBox.addItem("程序员"); 
　　comboBox.addItem("分析员"); 
　　} 
　　}); 
　　DefaultListModel litem=new DefaultListModel(); 
　　litem.addElement("香蕉"); 
　　litem.addElement("水果"); 
　　JList list=new JList(litem); 
　　list.addListSelectionListener(new ListSelectionListener(){ 
　　public void valueChanged(ListSelectionEvent e){ 
　　JList l=(JList)e.getSource(); 
　　Object s=l.getSelectedValue(); 
　　JOptionPane.showMessageDialog(null,s,"消息框",JOptionPane.YES_OPTION); 
　　} 
　　}); 
　　buttonGroup.add(radioButton1); 
　　buttonGroup.add(radioButton2); 
　　add(button); 
　　add(toggleButton); 
　　add(checkBox); 
　　add(radioButton1); 
　　add(radioButton2); 
　　add(comboBox); 
　　add(list); 
　　this.setBorder(new EtchedBorder(EtchedBorder.LOWERED,Color.LIGHT_GRAY,Color.blue)); 
　　} 
　　} 
　　class CenterPanel extends JPanel{ 
　　public CenterPanel(){ 
　　JTabbedPane tab=new JTabbedPane(JTabbedPane.TOP); 
　　JTextField textField=new JTextField("文本域，点击打开<文件按钮>可选择文件"); 
　　textField.setActionCommand("textField"); 
　　JTextPane textPane=new JTextPane(); 
　　textPane.setCursor(new Cursor(Cursor.TEXT_CURSOR)); 
　　textPane.setText("编辑器，试着点击文本区，试着拉动分隔条。"); 
　　textPane.addMouseListener(new MouseAdapter(){ 
　　public void mousePressed(MouseEvent e){ 
　　JTextPane textPane=(JTextPane)e.getSource(); 
　　textPane.setText("编辑器点击命令成功"); 
　　} 
　　}); 
　　JSplitPane splitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,textField,textPane); 
　　JTable table=new JTable(10,10); 
　　JPanel pane=new JPanel(); 
　　pane.add(table.getTableHeader(),BorderLayout.NORTH); 
　　pane.add(table); 
　　tab.addTab("文本演示",splitPane); 
　　tab.addTab("表格演示", pane); 
　　tab.setPreferredSize(new Dimension(500,600)); 
　　this.add(tab); 
　　this.setEnabled(true); 
　　} 
　　} 
　　public static void main(String args[]){ 
　　new SwingTest(); 
　　} 
　　}

JToggleButton toggleButton=new JToggleButton("双胎按钮"); 
　　ButtonGroup buttonGroup=new ButtonGroup(); 
　　JRadioButton radioButton1=new JRadioButton("单选按钮1",false); 
　　JRadioButton radioButton2=new JRadioButton("单选按钮2",false); 
　　JComboBox comboBox=new JComboBox(); 
　　comboBox.setToolTipText("点击下拉列表增加选项"); 
　　comboBox.addActionListener(new ActionListener(){ 
　　public void actionPerformed(ActionEvent e){ 
　　JComboBox comboBox=(JComboBox)e.getSource(); 
　　comboBox.addItem("程序员"); 
　　comboBox.addItem("分析员"); 
　　} 
　　}); 
　　DefaultListModel litem=new DefaultListModel(); 
　　litem.addElement("香蕉"); 
　　litem.addElement("水果"); 
　　JList list=new JList(litem); 
　　list.addListSelectionListener(new ListSelectionListener(){ 
　　public void valueChanged(ListSelectionEvent e){ 
　　JList l=(JList)e.getSource(); 
　　Object s=l.getSelectedValue(); 
　　JOptionPane.showMessageDialog(null,s,"消息框",JOptionPane.YES_OPTION); 
　　} 
　　}); 
　　buttonGroup.add(radioButton1); 
　　buttonGroup.add(radioButton2); 
　　add(button); 
　　add(toggleButton); 
　　add(checkBox); 
　　add(radioButton1); 
　　add(radioButton2); 
　　add(comboBox); 
　　add(list); 
　　this.setBorder(new EtchedBorder(EtchedBorder.LOWERED,Color.LIGHT_GRAY,Color.blue)); 
　　} 
　　} 
　　class CenterPanel extends JPanel{ 
　　public CenterPanel(){ 
　　JTabbedPane tab=new JTabbedPane(JTabbedPane.TOP); 
　　JTextField textField=new JTextField("文本域，点击打开<文件按钮>可选择文件"); 
　　textField.setActionCommand("textField"); 
　　JTextPane textPane=new JTextPane(); 
　　textPane.setCursor(new Cursor(Cursor.TEXT_CURSOR)); 
　　textPane.setText("编辑器，试着点击文本区，试着拉动分隔条。"); 
　　textPane.addMouseListener(new MouseAdapter(){ 
　　public void mousePressed(MouseEvent e){ 
　　JTextPane textPane=(JTextPane)e.getSource(); 
　　textPane.setText("编辑器点击命令成功"); 
　　} 
　　}); 
　　JSplitPane splitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,textField,textPane); 
　　JTable table=new JTable(10,10); 
　　JPanel pane=new JPanel(); 
　　pane.add(table.getTableHeader(),BorderLayout.NORTH); 
　　pane.add(table); 
　　tab.addTab("文本演示",splitPane); 
　　tab.addTab("表格演示", pane); 
　　tab.setPreferredSize(new Dimension(500,600)); 
　　this.add(tab); 
　　this.setEnabled(true); 
　　} 
　　} 
　　public static void main(String args[]){ 
　　new SwingTest(); 
　　} 
　　}