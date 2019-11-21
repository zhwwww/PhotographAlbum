package GUI;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Constant.Constant;

public class ErrorFrame {
	
	private JFrame frame;
	
	public ErrorFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public void createAndShowGUI(String info) {
		JFrame errorFrame = new JFrame("错误");
		errorFrame.setIconImage(Constant.iconFrame);
		errorFrame.setSize(300, 100);
		Point point = frame.getLocationOnScreen();
		errorFrame.setLocation(point.x+20,point.y+20);
		errorFrame.setVisible(true);
		JLabel errorText = new JLabel(info);
		errorText.setHorizontalAlignment(SwingConstants.CENTER);
		errorFrame.getContentPane().add(errorText);
	}
}
