package GUI;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Constant.Constant;

public class MovingFrame {

	private JFrame frame;
	private JLabel text;
	public MovingFrame(JFrame frame) {
		this.frame = frame;
	}
		
	public void createAndShowGUI(String title,String info) {
		JFrame movingFrame = new JFrame(title);
		movingFrame.setSize(300, 100);
		movingFrame.setIconImage(Constant.iconFrame);
		Point point = frame.getLocationOnScreen();
		movingFrame.setLocation(point.x+20,point.y+20);
		movingFrame.setVisible(true);
		text = new JLabel("                        " + info);
		movingFrame.getContentPane().add(text);
		//text.setLayout(null);
		text.setLocation(100,100);
	}
	
	public JFrame getFrame() {
		return frame;
	}

		
}
