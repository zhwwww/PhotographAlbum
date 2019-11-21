package Constant;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;

import Socket.CloseUtil;

public class Constant {
	
	static final public int picPerPageMax = 25;
	static final public int targetPicWidth = 128;
	static final public int targetPicHeight = 128;
	static final private String configFilePath = "/Cfg/LoveMy.cig";
	static final private String userConfigPath = "/Cfg/Usr.cig";
	static public String serverIp = "";
	static public int serverPort = 0;
	static public String currentClientName = "";
	final static public String serverIpDefault = "localhost";
	final static public int serverPortDefault = 8888;
	static public String downloadPath = "";
	final public static String defaultDownloadPath="";
	final public static int windowWidth = 350;
	final public static int windowHeight = 200;
	final private static String fileDescPre = "";
	final private static String exePath = System.getProperty("exe.path")==null?
												Constant.class.getProtectionDomain().getCodeSource().getLocation().getPath():
												System.getProperty("exe.path");
	final public static Image iconFrame ;
	
	static{
		Constant.updateServerAdd();
		Constant.updateDownloadPath();
		iconFrame = new ImageIcon(Constant.class.getResource("/PIC/icon.png")).getImage();
		System.out.println(iconFrame);
	}
	
	
	public static void updateServerAdd() {
		BufferedReader br = null;
		File f = new File(fileDescPre+new File(exePath).getPath()+configFilePath);
		if(!f.exists()) {
			setServerAdd(serverIpDefault,serverPortDefault);
		}
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String str = br.readLine();
			if(str.contains("ip:")) {
				serverIp = str.substring(str.indexOf("ip:")+3);
			}
			str = br.readLine();
			if(str.contains("port:")) {
				serverPort = Integer.valueOf(str.substring(str.indexOf("port:")+5));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			CloseUtil.closeAll(br);
		}
		
	}
	
	public static void updateDownloadPath() {
		BufferedReader br = null;
		File f = new File(fileDescPre+new File(exePath).getPath()+userConfigPath);
		if(!f.exists()) {
			setDownloadPath(defaultDownloadPath);
		}
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String str = br.readLine();
			if(str.contains("path:")) {
				downloadPath = str.substring(str.indexOf("path:")+5);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			CloseUtil.closeAll(br);
		}
		
	}
	
	public static void setServerAdd(String ip,int port){
		File f = new File(fileDescPre+new File(exePath).getPath()+configFilePath);
		if(!f.exists()) {
			File fd = new File(fileDescPre+f.getParent());
			if(!fd.exists()) {
				fd.mkdirs();
//				try {
//					Runtime.getRuntime().exec("attrib " + "\"" + fd.getAbsolutePath() + "\""+ " +H");
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				} 
			}
			try {
				f.createNewFile();
//				try {
//					Runtime.getRuntime().exec("attrib " + "\"" + f.getAbsolutePath() + "\""+ " +H");
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		serverPort = port;
		serverIp = ip;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f,false));
			bw.write("ip:"+ip);
			bw.newLine();
			bw.write("port:"+port);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			CloseUtil.closeAll(bw);
		}
		
	}
	public static void setDownloadPath(String path) {
		File f = new File(fileDescPre+new File(exePath).getPath()+userConfigPath);
		if(!f.exists()) {
			File fd = new File(fileDescPre+f.getParent());
			if(!fd.exists()) {
				fd.mkdirs();
//				try {
//					Runtime.getRuntime().exec("attrib " + "\"" + fd.getAbsolutePath() + "\""+ " +H");
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				} 
			}
			try {
				f.createNewFile();
//				try {
//					Runtime.getRuntime().exec("attrib " + "\"" + f.getAbsolutePath() + "\""+ " +H");
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		downloadPath = path;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f,false));
			bw.write("path:"+path);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			CloseUtil.closeAll(bw);
		}
	}

	public static void setDefaultServer() {
		setServerAdd(serverIpDefault,serverPortDefault);
		updateServerAdd();
	}
	
	public static void setDefaultDownloadPath() {
		setDownloadPath(defaultDownloadPath);
		updateDownloadPath();
	}
	
}
