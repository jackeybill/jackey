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
	private static final String s = "\n\n\t��������Ӧ�������������������ʱ��ͱ��˼����ϵ�ԭ���޷������ƾ��������в���֮���������ָ֤!\n"+
	"��ӭ������ź���̽�֣��ҽ��黳�Դ�����ʤ��ϲ��\n\n\n"+
	"\t �����ߣ�\t\t aMeng \n"+
	"\t QQ: \t\t 6885231 \n"+
	"\t e-mail: \t\t jackey_bill@21cn.com \n"+
	"\t ������\t\tΩ���ܰ\n\n\n";
	/**
	 * 
	 */
	public UserAbout() {
		// TODO �Զ����ɹ��캯�����
		//try{
		//	UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		//}catch(Exception exe){System.err.print(exe.getMessage());}
		//JFrame.setDefaultLookAndFeelDecorated(true);
		setTitle("����");
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
