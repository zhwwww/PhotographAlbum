package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import Constant.Constant;
import Socket.SocketConn;

public class PicViewFrame {
	
	private JFrame f ;
	
	public JFrame getF() {
		return f;
	}
	
	private JPanel panel;
	private JPanel bPanel;
	private SocketConn sc ;
	private JButton button1;
	private JButton button2;
	private JLabel labelPage;
	private int picSum;
	private int pageSum;
	private int numViewed = 0;
	private int currentPage = 0;
	public List<BufferedImage> picList = new ArrayList<>();
	
	public PicViewFrame(SocketConn sc,Point p) {
			
			this.sc = sc;
			f  = new JFrame("我的相册");
			f.setVisible(false);
			panel = new JPanel();
			bPanel = new JPanel();
			p.setLocation(p.x+10,p.y+10);
			f.setLocation(p);
			f.setResizable(false);
			f.setIconImage(Constant.iconFrame);
			f.add(panel,BorderLayout.CENTER);
			f.add(bPanel,BorderLayout.BEFORE_FIRST_LINE);
			f.setSize(Constant.windowWidth,Constant.windowHeight);
			GridLayout layout = new GridLayout(0,5,5,5);
			panel.setLayout(layout);
			panel.setLocation(100, 100);
			panel.setVisible(true);
		
			bPanel.setVisible(true);
			bPanel.setLocation(100, 100);
			
		}
	
	public void createGUI() {
		
		picSum = getSum();
		if(picSum==-1) {
			return;
		}
		
		pageSum = picSum/(Constant.picPerPageMax+1) + 1;
		JLabel label;
	
		button1 = new JButton("下一页");
		button1.setEnabled(true);
		button1.setBounds(10, 80, 80, 25);
		bPanel.add(button1);	
		button1.setLocation(300,300);
		button1.addActionListener(bt1_ls);
		
		button2 = new JButton("上一页");
		button2.setEnabled(true);
		button2.setBounds(10, 80, 80, 25);
		bPanel.add(button2);	
		button2.setLocation(380,300);
		button2.addActionListener(bt2_ls);
		button2.setVisible(false);
	
		
		label = new JLabel();
		label.setText("共"+picSum+"张照片");
		label.setVisible(true);
		label.setLocation(500,350);
		bPanel.add(label);
		
		labelPage = new JLabel();
		labelPage.setVisible(true);
		labelPage.setLocation(500,301);
		bPanel.add(labelPage);
		
		buttonAction();
		f.setVisible(true);
		
		if(numViewed!=Constant.picPerPageMax && pageSum>=2) {  //不是第一页
			button2.setVisible(true);
		}
		if(picSum==numViewed ) { //最后一页
			button1.setVisible(false);
		}
		labelPage.setText("页:"+(currentPage+1)+"/"+pageSum);
		currentPage ++ ;
	}
	
	
	ActionListener bt1_ls=new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			buttonAction();
			if(numViewed!=0) {  //不是第一页
				button2.setVisible(true);
			}
			if(picSum==numViewed) { //最后一页
				button1.setVisible(false);
			}
			labelPage.setText("页:"+(currentPage+1)+"/"+pageSum);
			currentPage ++;
		}	
	};
	
	ActionListener bt2_ls=new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			currentPage -=2;
			if(picSum!=numViewed) { //不是最后一页
				numViewed = numViewed - Constant.picPerPageMax - Constant.picPerPageMax;
			}else {
				numViewed = numViewed-Constant.picPerPageMax-(picSum%Constant.picPerPageMax);
			}
			buttonAction();
			if(numViewed==Constant.picPerPageMax) {  //第一页
				button2.setVisible(false);
			}
			if(picSum!=numViewed) {
				button1.setVisible(true);
			}
			labelPage.setText("页:"+(currentPage+1)+"/"+pageSum);
			currentPage ++;
		}	
	};

		
	public void buttonAction() {
		int i = 0 ;
		panel.setVisible(false);
		clearPic();
		if((picSum-numViewed)>Constant.picPerPageMax) {
			for(i=numViewed;i<Constant.picPerPageMax+numViewed;i++) {
				if(!sc.sendStr("get/pic"+i)) {
					f.dispose();
					return ;
				}
				BufferedImage img = sc.revImg();
				picList.add(img);
				showPic(img,0,0);				
			}
		}else {
			for(i=numViewed;i<picSum;i++) {
				if(!sc.sendStr("get/pic"+i)) {
					f.dispose();
					return ;
				}
				BufferedImage img = sc.revImg();
				picList.add(img);
				showPic(img,0,0);
			}
		}
		numViewed = i;
		panel.setVisible(true);
		if(i!=0) {
			f.pack();
		}
			
	}	
		
	public int getSum() {
		if(!sc.sendStr("get/num")) {
			f.dispose();    
			return -1;
		}
		String strNum = sc.revStr();
		int num = 0;
		if(strNum.contains("num/")) {
			num = Integer.valueOf(strNum.substring(strNum.indexOf("/")+1));
		}	
		return num;
	}	
		
	public void clearPic() {
		panel.removeAll();
	}
	private JPopupMenu popupMenu = null;
	
	private void showPic(BufferedImage img,int x ,int y) {
		ImagePanel ip = new ImagePanel(img,x,y);
		ip.addMouseListener(new MouseAdapter() {
			JFrame fP = null;
			List<String> frameList = new ArrayList<String>();
			@Override
			public void mouseReleased(MouseEvent e) {
				
				if(e.getButton()==MouseEvent.BUTTON3) {
					 showPopupMenu(e.getComponent(), e.getX(), e.getY(),img);
				}
				else if(e.getButton()==MouseEvent.BUTTON1) {
					if(popupMenu!=null) {
						//CloseUtil.closeObj(popupMenu,downloadMenuItem,pasteMenuItem,editMenu,findMenuItem,replaceMenuItem);
						popupMenu = null;
						//System.out.println(popupMenu);
						//System.out.println(downloadMenuItem);
					}
					else if(!frameList.contains(""+img)) {
						fP = new JFrame();
						fP.addWindowListener(new WindowAdapter() {
							@Override
							public void windowClosing(WindowEvent e) {
								frameList.remove(""+img);
							}
						});
						frameList.add(""+img);
						fP.setIconImage(Constant.iconFrame);
						fP.setSize(Constant.windowWidth, Constant.windowHeight);
						fP.setLocation(f.getLocation());
						fP.setMaximumSize(new Dimension(img.getWidth(), img.getHeight()));
						fP.setVisible(true);	
						fP.setResizable(true);
						fP.addComponentListener(new ComponentAdapter() {
						      public void componentResized(ComponentEvent evt) {
						        Dimension size = fP.getSize();
						        Dimension max = fP.getMaximumSize();
						        if (size.getWidth() > max.getWidth()) {
						          fP.setSize(max.width,size.height);
						        }
						        if (size.getHeight() > max.getHeight()) {
						          fP.setSize(size.width,max.height);
						        }
						      }
						    });
						fP.add(new PicFullViewPanel(img,0,0));
					}
				}
			}
		});
		panel.add(ip);
		ip = null;
	}
	
	 public void showPopupMenu(Component invoker, int x, int y , BufferedImage img) {
		 
		 popupMenu = new JPopupMenu();
		 JMenuItem downloadMenuItem = new JMenuItem("下载");
		 downloadMenuItem.addMouseListener(new MouseAdapter() {
 				@Override
 				public void mouseReleased(MouseEvent e) {
 					if(e.getButton()==MouseEvent.BUTTON1) {
 						try {
 		            		File f = new File(Constant.downloadPath+File.separator+img.hashCode()+".jpg");
 		            		if(!f.exists()) {
 		            			f.createNewFile();
 		            			ImageIO.write(img,"jpg",f);
 		            		}
 							f = null;
 						} catch (IOException e1) {
 							e1.printStackTrace();
 						}
 					}
 				}
 	        });
		  	popupMenu.add(downloadMenuItem);
//	        popupMenu.addSeparator();     
	        popupMenu.show(invoker, x, y);
	    }
	
}
