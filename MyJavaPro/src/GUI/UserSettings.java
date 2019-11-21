package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Constant.Constant;

public class UserSettings {
	private JFrame frame ;
	private JTextField pathText;
	private JFrame mainFrame;

	public  UserSettings(JFrame frame) {
		this.mainFrame = frame;
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public void createAndShowGUI() {
		JButton fileDictChooseButton ;
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("设置");
		frame.setSize(Constant.windowWidth,Constant.windowHeight);
        frame.setLocation(mainFrame.getLocation());
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setIconImage(Constant.iconFrame);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel serverLabel = new JLabel("下载路径:");
        serverLabel.setBounds(10,20,80,25);
        panel.add(serverLabel);
        
        pathText = new JTextField(20);
        pathText.setText(Constant.downloadPath);
        pathText.setBounds(80,20,225,25);
        pathText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				e.consume();
			}
        	
        });
        panel.add(pathText);
            
        fileDictChooseButton = new JButton("更改目录");
        fileDictChooseButton.setBounds(130,50,95,25);
        panel.add(fileDictChooseButton);
        
        frame.getContentPane().add(panel);
        fileDictChooseButton.addActionListener(bt2_ls);
        
        frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String path = pathText.getText();
				Constant.setDownloadPath(path);
				mainFrame.setVisible(true);
				mainFrame.setLocation(frame.getLocation());
			}
        });
        
	}
				
	private String tempPath=Constant.downloadPath;
	ActionListener bt2_ls=new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			ChoseDictFrame cdf =  new ChoseDictFrame(frame);
			File file = cdf.createAndShowGUI();
			if(file.getName()==""||file==null) {
				pathText.setText(tempPath);
			}else if(file.exists()){
				pathText.setText(file.getPath());
				tempPath = file.getPath();
			}else {
				new ErrorFrame(frame).createAndShowGUI("错误路径!");
			}
		}
	};

}
