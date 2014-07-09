package com.tf.mobile; 
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class createStuffQuitInterFace extends JPanel{
	private final static long serialVersionUID = 230000123423L;
	private javax.swing.JButton enter = null;
	//private String sql = null;
	public createStuffQuitInterFace() {
		// TODO 自动生成构造函数存根
		this.setLayout(new GridBagLayout());
		this.setBackground(java.awt.Color.PINK);
		
		String strTitle = SingletonClient.isRun?"关闭":"开启";
		ImageIcon iconBtn = new ImageIcon("res/start.jpg");
		enter = new javax.swing.JButton(strTitle);
		enter.setIcon(iconBtn);
		Font font = new Font("宋体",Font.PLAIN,88);
		enter.setFont(font);
		enter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//IsGood();
				String strTitle = SingletonClient.isRun?"开启":"关闭";
				enter.setText(strTitle);
				SingletonClient.isRun = !SingletonClient.isRun;
			}
		});
		setupComponent(enter,1,3,1,1,true);
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
}
