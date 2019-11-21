package GUI;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Constant.Constant;
import Socket.SocketConn;
public class MainLayout  {

	private JFrame f ;
	private JButton button1;
	private JButton button2;
	private JPanel bPanel;
	private SocketConn sc ;
	
	public JFrame getF() {
		return f;
	}

	public MainLayout(SocketConn sc,Point p) {
		
		this.sc = sc;
		f  = new JFrame("用户界面");
		bPanel = new JPanel();
		f.setLocation(p);
		f.setIconImage(Constant.iconFrame);
		f.add(bPanel,BorderLayout.BEFORE_FIRST_LINE);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(Constant.windowWidth, Constant.windowHeight);
		bPanel.setLocation(0, 0);
		bPanel.setSize(300, 300);
		bPanel.setLayout(null);
		f.setResizable(false);
		f.setVisible(true);
	}
	
	public void createGUI() {
		
		button1 = new JButton("我的相册");
		button1.addActionListener(bt1_ls);
		button1.setBounds(110, 30,120,30);
		button2 = new JButton("我的设置");
		button2.addActionListener(bt2_ls);
		button2.setBounds(110, 100,120,30);
		bPanel.add(button1);
		bPanel.add(button2);	
		f.getContentPane().add(bPanel);
	}
	
	ActionListener bt1_ls=new ActionListener() {
		PicViewFrame picViewFrame = null;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(picViewFrame==null) {
				picViewFrame = new PicViewFrame(sc,f.getLocation());
				picViewFrame.getF().addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						picViewFrame = null;
					}
					@Override
					public void windowClosed(WindowEvent e) {
						picViewFrame = null;
						f.dispose();
					}
					 
				});
				picViewFrame.createGUI();
			}
			
		}
	};
	
	ActionListener bt2_ls=new ActionListener() {
		UserSettings userSettings = null;
		@Override
		public void actionPerformed(ActionEvent e) {
			f.setVisible(false);
			userSettings = new UserSettings(f);
			userSettings.createAndShowGUI();
		}
		
	};
	
	
}
