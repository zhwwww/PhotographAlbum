package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Constant.Constant;

public class LoginSettings {
	
	private JFrame frame ;
	private JTextField serverText;
	private JTextField portText;
	private JFrame mainFrame;
	public  LoginSettings(JFrame frame) {
		this.mainFrame = frame;
	}
	
	public void createAndShowGUI() {
		JButton saveButton ;
		JButton defaultButton ;
        frame = new JFrame("设置");
        
        frame.setSize(Constant.windowWidth, Constant.windowHeight);
        frame.setLocation(mainFrame.getLocation().x,mainFrame.getLocation().y);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setIconImage(Constant.iconFrame);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel serverLabel = new JLabel("服务器ip:");
        serverLabel.setBounds(10,20,90,25);
        panel.add(serverLabel);
        
        serverText = new JTextField(20);
        serverText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				String s = serverText.getText();
				if(s.length()>=15) {
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
        
        });
        serverText.setText(Constant.serverIp);
        serverText.setBounds(110,20,165,25);
        panel.add(serverText);
            
        JLabel portLabel = new JLabel("服务器端口:");
        portLabel.setBounds(10,50,90,25);
        panel.add(portLabel);
        
        portText = new JTextField(20);
        portText.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				String s = portText.getText();
				if(s.length()>=5) {
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
        	
        });
        portText.setBounds(110,50,165,25);
        Integer port = Constant.serverPort;
        portText.setText(port.toString());
        panel.add(portText);
        
        saveButton = new JButton("保存");
        saveButton.setBounds(0, 0, 80, 25);
        saveButton.setLocation(120,90);
        panel.add(saveButton);
        
        defaultButton = new JButton("恢复默认");
        defaultButton.setBounds(0, 0, 100, 25);
        defaultButton.setLocation(230,130);
        panel.add(defaultButton);
        
        frame.getContentPane().add(panel);
        saveButton.addActionListener(bt1_ls);
        defaultButton.addActionListener(bt2_ls);
        
        frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.setLocation(frame.getLocation());
				mainFrame.setVisible(true);
			}
        });
	}
			
	ActionListener bt1_ls=new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String ip = serverText.getText();
			String port = portText.getText();
			Constant.setServerAdd(ip, Integer.valueOf(port));
			mainFrame.setLocation(frame.getLocation());
			frame.dispose();
			mainFrame.setVisible(true);
		}
	};
	
	ActionListener bt2_ls=new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Constant.setDefaultServer();
			serverText.setText(Constant.serverIp);
			Integer port = Constant.serverPort;
			portText.setText(port.toString());
		}
	};
	
}
