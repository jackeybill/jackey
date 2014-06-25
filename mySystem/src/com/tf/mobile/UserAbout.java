/**
 Download by http://www.codefans.net 
 */
package com.tf.mobile;
import java.awt.AlphaComposite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.*;
/**
 * @author aMeng
 *
 */
public class UserAbout extends JFrame{
	private final static long serialVersionUID=2309008900L;
	private static final String s = "\n\n\t本程序是应朋友需求而开发，由于时间和本人技术上的原因无法做尽善尽美，若有不足之处，请加以指证!\n"+
	"欢迎你的来信和我探讨，我将虚怀以待更不胜欢喜！\n\n\n"+
	"\t 开发者：\t\t aMeng \n"+
	"\t QQ: \t\t 6885231 \n"+
	"\t e-mail: \t\t jackey_bill@21cn.com \n"+
	"\t 信仰：\t\t惟吾德馨\n\n\n";
	/**
	 * 
	 */
	public UserAbout() {
		// TODO 自动生成构造函数存根
		//try{
		//	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		//}catch(Exception exe){System.err.print(exe.getMessage());}
		//JFrame.setDefaultLookAndFeelDecorated(true);
		setTitle("关于");
		JPanel panel = new JPanel(){
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
		JTextArea textArea = new JTextArea(s);
		textArea.setEditable(false);
		panel.add(textArea,"Center");
		panel.setBackground(java.awt.Color.pink);
		add(panel,"Center");
		Toolkit tools = this.getToolkit();
		Image logo = tools.getImage("res/logo.jpg");
		setIconImage(logo);
		java.awt.Dimension d = tools.getScreenSize();
		int width = (int)(d.getWidth()-450) /2;
		int height = (int)(d.getHeight()-240)/2;
		setAlwaysOnTop(true);
		setLocation(width, height);
		setSize(350, 240);
		pack();
		validate();
		setVisible(true);
	}
	public void paint(Graphics g){
		super.paint(g);
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
	
}
