http://www.shjsit.com


	? 2012.2.26
		�� ��������
		�� �쳣����
		�� UI ����
		�� ���

2012.2.27


http://www.javaswing.org/java-swing-table.aspx
http://docs.oracle.com/javase/tutorial/uiswing/components/table.html#eg

��Ϊ��ҳ��½|ע�� �켫��ý��������|�켫��|ITר����|52PK��Ϸ��|�ֻ��켫|IT����
      ��ҳ| �����ܹ��洢�����������������⻯| ���簲ȫ���簲ȫ| ����뿪����������������ݿ�| ����ϵͳWinsystemLinuxUnix �Ƽ���ϢITר���� > ����ƽ̨��վ > JAVAר�� >����Java Swing�����ȫ��ʵ������ 
���ߣ�����������ITר����2009-12-14 13:00���Ľ���һ��Java Swing�����ȫ��ʵ������ ��

������������˲��ã������ؼ�ȫ������ȥ�ˡ����������������������


��package ch02.section02; 
����import javax.swing.*; 
����import java.awt.*; 
����import java.awt.event.*; 
����import javax.swing.tree.*; 
����import javax.swing.event.*; 
����import javax.swing.border.*; 
����import javax.swing.table.*; 
����public class SwingTest extends JFrame{ 
����public SwingTest(){ 
����MenuTest menuTest=new MenuTest(); 
����LeftPanel leftPanel=new LeftPanel(); 
����RightPanel rightPanel=new RightPanel(); 
����BottomPanel bottomPanel=new BottomPanel(); 
����CenterPanel centerPanel=new CenterPanel(); 
����Container c=this.getContentPane(); 
����this.setJMenuBar(menuTest); 
����c.add(leftPanel,BorderLayout.WEST); 
����c.add(rightPanel,BorderLayout.EAST); 
����c.add(centerPanel,BorderLayout.CENTER); 
����c.add(bottomPanel,BorderLayout.SOUTH); 
����this.addWindowListener(new WindowAdapter(){ 
����public void WindowClosing(WindowEvent e){ 
����dispose(); 
����System.exit(0); 
����} 
����}); 
����setSize(700,500); 
����setTitle("Swing �����ȫ�����"); 
����setUndecorated(true); 
����setLocation(200,150); 
����show(); 
����} 
����class MenuTest extends JMenuBar{ 
����private JDialog aboutDialog; 
����public MenuTest(){ 
����JMenu fileMenu=new JMenu("�ļ�"); 
����JMenuItem exitMenuItem=new JMenuItem("�˳�",KeyEvent.VK_E); 
����JMenuItem aboutMenuItem=new JMenuItem("����..",KeyEvent.VK_A); 
����fileMenu.add(exitMenuItem); 
����fileMenu.add(aboutMenuItem); 
����this.add(fileMenu); 
����aboutDialog=new JDialog(); 
����initAboutDialog(); 
����exitMenuItem.addActionListener(new ActionListener(){ 
����public void actionPerformed(ActionEvent e){ 
����dispose(); 
����System.exit(0); 
����} 
����}); 

�����Ϣ��1234��һҳ ���α༭��л����
�ؼ��֣�Java,Swing,����
�����ĵ��� ��ӡ�ر� ר�Ҳ��İ�Ƥ���켫���� | �������� | About us | ��վ��ʦ | �������� | ��ϵ���� | ���ҵ�� | �������� | ��վ��ͼ 
All Rights Reserved, Copyright 2004-2011, Ctocio.com.cn ��ICP֤B2-20030003�������������������ϵ powered by �켫���ݹ���ƽ̨CMS4i  

aboutMenuItem.addActionListener(new ActionListener(){ 
����public void actionPerformed(ActionEvent e){ 
����aboutDialog.show(); 
����} 
����}); 
����} 
����public JDialog get(){ 
����return aboutDialog; 
����} 
����public void initAboutDialog(){ 
����aboutDialog.setTitle("����"); 
����Container con=aboutDialog.getContentPane(); 
����Icon icon=new ImageIcon("sdmile.gif"); 
����JLabel aboutLabel=new JLabel(""+" 
����Swing!"+" 
����",icon,JLabel.CENTER); 
����con.add(aboutLabel,BorderLayout.CENTER); 
����aboutDialog.setSize(450,225); 
����aboutDialog.setLocation(300,300); 
����aboutDialog.addWindowListener(new WindowAdapter(){ 
����public void WindowClosing(WindowEvent e){ 
����dispose(); 
����} 
����}); 
����} 
����} 
����class LeftPanel extends JPanel{ 
����private int i=0; 
����public LeftPanel(){ 
����DefaultMutableTreeNode root=new DefaultMutableTreeNode("Root"); 
����DefaultMutableTreeNode child=new DefaultMutableTreeNode("Child"); 
����DefaultMutableTreeNode select=new DefaultMutableTreeNode("select"); 
����DefaultMutableTreeNode child1=new DefaultMutableTreeNode(""+i); 
����root.add(child); 
����root.add(select); 
����child.add(child1); 
����JTree tree=new JTree(root); 
����tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION); 
����tree.setRowHeight(20); 
����tree.addTreeSelectionListener(new TreeSelectionListener(){ 
����public void valueChanged(TreeSelectionEvent e){ 
����JTree tree=(JTree)e.getSource(); 
����DefaultMutableTreeNode selectNode=(DefaultMutableTreeNode)tree.getLastSelectedPathComponent(); 
����i++; 
����selectNode.add(new DefaultMutableTreeNode(""+i)); 
����} 
����});
tree.setPreferredSize(new Dimension(100,300)); 
����JScrollPane scrollPane=new JScrollPane(tree); 
����this.add(scrollPane); 
����} 
����} 
����class BottomPanel extends JPanel{ 
����private JProgressBar pb; 
����public BottomPanel(){ 
����pb=new JProgressBar(); 
����pb.setPreferredSize(new Dimension(680,20)); 
����Timer time=new Timer(1,new ActionListener(){ 
����int counter=0; 
����public void actionPerformed(ActionEvent e){ 
����counter++; 
����pb.setValue(counter); 
����Timer t=(Timer)e.getSource(); 
����if(counter==pb.getMaximum()){ 
����t.stop(); 
����counter=0; 
����t.start(); 
����} 
����} 
����}); 
����time.start(); 
����pb.setStringPainted(true); 
����pb.setMinimum(0); 
����pb.setMaximum(1000); 
����pb.setBackground(Color.white); 
����pb.setForeground(Color.red); 
����this.add(pb); 
����} 
����public void setProcessBar(BoundedRangeModel rangeModel){ 
����pb.setModel(rangeModel); 
����} 
����} 
����class RightPanel extends JPanel{ 
����public RightPanel(){ 
����this.setLayout(new GridLayout(8,1)); 
����JCheckBox checkBox=new JCheckBox("��ѡ��ť"); 
����JButton button=new JButton("���ļ�"); 
����button.addActionListener(new ActionListener(){ 
����public void actionPerformed(ActionEvent e){ 
����JFileChooser file=new JFileChooser(); 
����int resule=file.showOpenDialog(new JPanel()); 
����if(resule==file.APPROVE_OPTION){ 
����String fileName=file.getSelectedFile().getName(); 
����String dir=file.getSelectedFile().getName(); 
����JOptionPane.showConfirmDialog(null,dir+"\\"+fileName,"ѡ����ļ�",JOptionPane.YES_OPTION); 
����} 
����} 
����});

JToggleButton toggleButton=new JToggleButton("˫̥��ť"); 
����ButtonGroup buttonGroup=new ButtonGroup(); 
����JRadioButton radioButton1=new JRadioButton("��ѡ��ť1",false); 
����JRadioButton radioButton2=new JRadioButton("��ѡ��ť2",false); 
����JComboBox comboBox=new JComboBox(); 
����comboBox.setToolTipText("��������б�����ѡ��"); 
����comboBox.addActionListener(new ActionListener(){ 
����public void actionPerformed(ActionEvent e){ 
����JComboBox comboBox=(JComboBox)e.getSource(); 
����comboBox.addItem("����Ա"); 
����comboBox.addItem("����Ա"); 
����} 
����}); 
����DefaultListModel litem=new DefaultListModel(); 
����litem.addElement("�㽶"); 
����litem.addElement("ˮ��"); 
����JList list=new JList(litem); 
����list.addListSelectionListener(new ListSelectionListener(){ 
����public void valueChanged(ListSelectionEvent e){ 
����JList l=(JList)e.getSource(); 
����Object s=l.getSelectedValue(); 
����JOptionPane.showMessageDialog(null,s,"��Ϣ��",JOptionPane.YES_OPTION); 
����} 
����}); 
����buttonGroup.add(radioButton1); 
����buttonGroup.add(radioButton2); 
����add(button); 
����add(toggleButton); 
����add(checkBox); 
����add(radioButton1); 
����add(radioButton2); 
����add(comboBox); 
����add(list); 
����this.setBorder(new EtchedBorder(EtchedBorder.LOWERED,Color.LIGHT_GRAY,Color.blue)); 
����} 
����} 
����class CenterPanel extends JPanel{ 
����public CenterPanel(){ 
����JTabbedPane tab=new JTabbedPane(JTabbedPane.TOP); 
����JTextField textField=new JTextField("�ı��򣬵����<�ļ���ť>��ѡ���ļ�"); 
����textField.setActionCommand("textField"); 
����JTextPane textPane=new JTextPane(); 
����textPane.setCursor(new Cursor(Cursor.TEXT_CURSOR)); 
����textPane.setText("�༭�������ŵ���ı��������������ָ�����"); 
����textPane.addMouseListener(new MouseAdapter(){ 
����public void mousePressed(MouseEvent e){ 
����JTextPane textPane=(JTextPane)e.getSource(); 
����textPane.setText("�༭���������ɹ�"); 
����} 
����}); 
����JSplitPane splitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,textField,textPane); 
����JTable table=new JTable(10,10); 
����JPanel pane=new JPanel(); 
����pane.add(table.getTableHeader(),BorderLayout.NORTH); 
����pane.add(table); 
����tab.addTab("�ı���ʾ",splitPane); 
����tab.addTab("�����ʾ", pane); 
����tab.setPreferredSize(new Dimension(500,600)); 
����this.add(tab); 
����this.setEnabled(true); 
����} 
����} 
����public static void main(String args[]){ 
����new SwingTest(); 
����} 
����}


JToggleButton toggleButton=new JToggleButton("˫̥��ť"); 
����ButtonGroup buttonGroup=new ButtonGroup(); 
����JRadioButton radioButton1=new JRadioButton("��ѡ��ť1",false); 
����JRadioButton radioButton2=new JRadioButton("��ѡ��ť2",false); 
����JComboBox comboBox=new JComboBox(); 
����comboBox.setToolTipText("��������б�����ѡ��"); 
����comboBox.addActionListener(new ActionListener(){ 
����public void actionPerformed(ActionEvent e){ 
����JComboBox comboBox=(JComboBox)e.getSource(); 
����comboBox.addItem("����Ա"); 
����comboBox.addItem("����Ա"); 
����} 
����}); 
����DefaultListModel litem=new DefaultListModel(); 
����litem.addElement("�㽶"); 
����litem.addElement("ˮ��"); 
����JList list=new JList(litem); 
����list.addListSelectionListener(new ListSelectionListener(){ 
����public void valueChanged(ListSelectionEvent e){ 
����JList l=(JList)e.getSource(); 
����Object s=l.getSelectedValue(); 
����JOptionPane.showMessageDialog(null,s,"��Ϣ��",JOptionPane.YES_OPTION); 
����} 
����}); 
����buttonGroup.add(radioButton1); 
����buttonGroup.add(radioButton2); 
����add(button); 
����add(toggleButton); 
����add(checkBox); 
����add(radioButton1); 
����add(radioButton2); 
����add(comboBox); 
����add(list); 
����this.setBorder(new EtchedBorder(EtchedBorder.LOWERED,Color.LIGHT_GRAY,Color.blue)); 
����} 
����} 
����class CenterPanel extends JPanel{ 
����public CenterPanel(){ 
����JTabbedPane tab=new JTabbedPane(JTabbedPane.TOP); 
����JTextField textField=new JTextField("�ı��򣬵����<�ļ���ť>��ѡ���ļ�"); 
����textField.setActionCommand("textField"); 
����JTextPane textPane=new JTextPane(); 
����textPane.setCursor(new Cursor(Cursor.TEXT_CURSOR)); 
����textPane.setText("�༭�������ŵ���ı��������������ָ�����"); 
����textPane.addMouseListener(new MouseAdapter(){ 
����public void mousePressed(MouseEvent e){ 
����JTextPane textPane=(JTextPane)e.getSource(); 
����textPane.setText("�༭���������ɹ�"); 
����} 
����}); 
����JSplitPane splitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,textField,textPane); 
����JTable table=new JTable(10,10); 
����JPanel pane=new JPanel(); 
����pane.add(table.getTableHeader(),BorderLayout.NORTH); 
����pane.add(table); 
����tab.addTab("�ı���ʾ",splitPane); 
����tab.addTab("�����ʾ", pane); 
����tab.setPreferredSize(new Dimension(500,600)); 
����this.add(tab); 
����this.setEnabled(true); 
����} 
����} 
����public static void main(String args[]){ 
����new SwingTest(); 
����} 
����}

JToggleButton toggleButton=new JToggleButton("˫̥��ť"); 
����ButtonGroup buttonGroup=new ButtonGroup(); 
����JRadioButton radioButton1=new JRadioButton("��ѡ��ť1",false); 
����JRadioButton radioButton2=new JRadioButton("��ѡ��ť2",false); 
����JComboBox comboBox=new JComboBox(); 
����comboBox.setToolTipText("��������б�����ѡ��"); 
����comboBox.addActionListener(new ActionListener(){ 
����public void actionPerformed(ActionEvent e){ 
����JComboBox comboBox=(JComboBox)e.getSource(); 
����comboBox.addItem("����Ա"); 
����comboBox.addItem("����Ա"); 
����} 
����}); 
����DefaultListModel litem=new DefaultListModel(); 
����litem.addElement("�㽶"); 
����litem.addElement("ˮ��"); 
����JList list=new JList(litem); 
����list.addListSelectionListener(new ListSelectionListener(){ 
����public void valueChanged(ListSelectionEvent e){ 
����JList l=(JList)e.getSource(); 
����Object s=l.getSelectedValue(); 
����JOptionPane.showMessageDialog(null,s,"��Ϣ��",JOptionPane.YES_OPTION); 
����} 
����}); 
����buttonGroup.add(radioButton1); 
����buttonGroup.add(radioButton2); 
����add(button); 
����add(toggleButton); 
����add(checkBox); 
����add(radioButton1); 
����add(radioButton2); 
����add(comboBox); 
����add(list); 
����this.setBorder(new EtchedBorder(EtchedBorder.LOWERED,Color.LIGHT_GRAY,Color.blue)); 
����} 
����} 
����class CenterPanel extends JPanel{ 
����public CenterPanel(){ 
����JTabbedPane tab=new JTabbedPane(JTabbedPane.TOP); 
����JTextField textField=new JTextField("�ı��򣬵����<�ļ���ť>��ѡ���ļ�"); 
����textField.setActionCommand("textField"); 
����JTextPane textPane=new JTextPane(); 
����textPane.setCursor(new Cursor(Cursor.TEXT_CURSOR)); 
����textPane.setText("�༭�������ŵ���ı��������������ָ�����"); 
����textPane.addMouseListener(new MouseAdapter(){ 
����public void mousePressed(MouseEvent e){ 
����JTextPane textPane=(JTextPane)e.getSource(); 
����textPane.setText("�༭���������ɹ�"); 
����} 
����}); 
����JSplitPane splitPane=new JSplitPane(JSplitPane.VERTICAL_SPLIT,textField,textPane); 
����JTable table=new JTable(10,10); 
����JPanel pane=new JPanel(); 
����pane.add(table.getTableHeader(),BorderLayout.NORTH); 
����pane.add(table); 
����tab.addTab("�ı���ʾ",splitPane); 
����tab.addTab("�����ʾ", pane); 
����tab.setPreferredSize(new Dimension(500,600)); 
����this.add(tab); 
����this.setEnabled(true); 
����} 
����} 
����public static void main(String args[]){ 
����new SwingTest(); 
����} 
����}