package GUI;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Constant.Constant;
import Socket.SocketConn;
import Socket.SocketCreate;

public class LoginIn {
		
	private JTextField userText;
	private JPasswordField passwordText;
	private JFrame frame ;
	private JButton loginButton = null;
	private SocketConn sc = null;
	private JButton settingButton = null;
	public LoginIn() {
		
	}
	
	public void createAndShowGUI() {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);
        // 创建及设置窗口
        frame = new JFrame("用户登陆");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Constant.windowWidth, Constant.windowHeight);
        frame.setIconImage(Constant.iconFrame);
        JPanel panel = new JPanel();
        placePanel(panel);
        frame.getContentPane().add(panel);
        frame.setLocationRelativeTo(null);
        //frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        loginButton.addActionListener(bt1_ls);
        settingButton.addActionListener(bt2_ls);
	}

	private void placePanel(JPanel panel) {
	
        panel.setLayout(null);
        
        JLabel userLabel = new JLabel("用户名:");

        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);
       
        passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);
        
        loginButton = new JButton("登陆");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.setLocation(130,90);
        panel.add(loginButton);
        
        settingButton = new JButton();
        settingButton.setBounds(0, 0, 25, 25);
        settingButton.setBorderPainted(false);
 
        settingButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/PIC/setting.jpg")).
								getImage().getScaledInstance(settingButton.getWidth(),settingButton.getHeight(),
								Image.SCALE_DEFAULT)));
        settingButton.setLocation(300,130);
        settingButton.setVisible(true);
        panel.add(settingButton);

	}

	ActionListener bt1_ls=new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			sc = SocketCreate.createSocket();
			if(sc==null) {
				new ErrorFrame(frame).createAndShowGUI("服务器连接失败!");
			}else {
				String admin=userText.getText();
				char[] password=passwordText.getPassword();
				String pwd=String.valueOf(password); 
				sc.sendStr("login/"+admin+"?"+pwd);
				String revInfo = sc.revStr();
				if(revInfo.equals("login/ok")){
					Constant.currentClientName = admin;
					MainLayout mainLayout=new MainLayout(sc,frame.getLocation());
					//frame.dispose();
					frame.setVisible(false);
					mainLayout.getF().addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							frame.setVisible(true);
							new ErrorFrame(frame).createAndShowGUI("网络连接断开!");
						}
					});
					mainLayout.createGUI();
				}			
				else if(revInfo.equals("login/already")){
					new ErrorFrame(frame).createAndShowGUI("用户重复登陆!");
				}
				else if(revInfo.equals("login/error")){
					new ErrorFrame(frame).createAndShowGUI("用户名或密码错误!");
				}
			}
		}

	};
	
	ActionListener bt2_ls=new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			frame.setVisible(false);
			new LoginSettings(frame).createAndShowGUI();
		}
	};

	static {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
                //System.out.println(info.getName());
            }
        } catch (ClassNotFoundException ex) {
        	ex.printStackTrace();
        } catch (InstantiationException ex) {
        	ex.printStackTrace();
        } catch (IllegalAccessException ex) {
        	ex.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        	ex.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		new LoginIn().createAndShowGUI();		
    }

}