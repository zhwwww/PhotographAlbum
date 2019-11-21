package Socket;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javax.imageio.ImageIO;

public class SocketConn{

	private Socket socketClient;
	
	public Socket getSocketClient() {
		return socketClient;
	}

	private DataOutputStream dos;
	private DataInputStream dis;
	private String path;
	private int port;
	
	public SocketConn(String path , int port) {
		this.path = path;
		this.port = port;
	}

	public void createSocket() {
		try {
			socketClient = new Socket();
			SocketAddress addr = new InetSocketAddress(this.path,this.port);
			socketClient.connect(addr,2000);
			dos = new DataOutputStream(new BufferedOutputStream(socketClient.getOutputStream()));
			dis = new DataInputStream(new BufferedInputStream(socketClient.getInputStream()));
		}catch(Exception e1 ) {
			CloseUtil.closeAll(dos,dis,socketClient);
			socketClient = null;
		} 
	}
		
	public boolean sendStr(String str) {
		try {
			dos.writeUTF(str);
			dos.flush();
		} catch (IOException e) {
			//CloseUtil.closeAll(dos,dis,socketClient);
			return false;
		}
		return true;
	}
	
	public String revStr() {
		String info = null;
		try {
			info = dis.readUTF();
			
		} catch (IOException e) {
			CloseUtil.closeAll(dis);
		}
		return info;
	}
	
	public BufferedImage revImg() {
		byte[] bytes = new byte[1024*100];
		int len = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			while(( len = dis.read(bytes)) != -1) {
			//	 System.out.println("a" + len);
				 if(bytes[len-1]=='\n'&&bytes[len-2]=='\r') {
					 baos.write(bytes,0,len-2);
					 break;
				 }else {
					 baos.write(bytes,0,len);
				 }
			 } 
			 baos.flush();		
		//	 System.out.println("b");
		} catch (IOException e) {
			CloseUtil.closeAll(baos,dis);
		}
		BufferedImage image = null;
		try {
			 image = ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

}
