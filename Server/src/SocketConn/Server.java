package SocketConn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	static private List <RunServer> clients= new ArrayList<RunServer>();
	static private ServerSocket socketServer = null;
	
	public static boolean findClients(String name) {
		for (RunServer runServer : clients) {
			if(runServer.name.equals(name)) {
				return true;
			}
		}
		return false;
	} 

	public void start() throws IOException {
		socketServer = new ServerSocket(20000); 
		while(true) {
			Socket socket = socketServer.accept();
			LoginJudge lj = new LoginJudge(socket);	
			String name = lj.judge();
			if(name!=null) {
				RunServer rs = new RunServer(socket,name);
				clients.add(rs);
				new Thread(rs).start();
				System.out.println("name : " + name);
			}else {
				socket.close();
			}
		}	
	}
	
	class RunServer implements Runnable{
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private String name;
		private boolean isRunning = true;
		private BufferedInputStream imgIps = null;
		private File[] files = null ;
		private Socket socket;
		@Override
		public void run() {
			System.out.println("start");
			while(isRunning) {
				handle();
			}
			System.out.println("thread stop");
		}
		
		public RunServer(Socket socket,String name) {
			try {
				dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
				this.name = name;
				this.socket = socket;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("err");
				isRunning = false;
				CloseUtil.closeAll(dis,dos);
				clients.remove(this);
			}
		}
	
		public void sendStr(String str) {
			try {
				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e) {
				isRunning = false;
				CloseUtil.closeAll(dis,dos);
				clients.remove(this);
			}	
		}
	
		private String rev() {
			String str = "";
			try {
				str = dis.readUTF();
			}
//			catch(SocketTimeoutException e1) {
//				System.out.println("time_out");
//				boolean isConnected = socket.isConnected() && !socket.isClosed();  
//				System.out.println(isConnected);
//			} 
			catch ( IOException e) {
				System.out.println(this + "socket close");
				CloseUtil.closeAll(dis,dos,socket);
				clients.remove(this);
				isRunning = false;
			}
			return str;	
		}
		
		public void handle() {
			String str = rev();
			
			if(str!=null && str.contains("get/num")) {
				files = ServerUtil.ServerUtil.searchFile(new File("src/pic/"+name+"/"),".JPG");
				System.out.println(files.length);
				sendStr("num/"+files.length);
			}
			else if(str!=null && str.contains("get/pic")) {
				try {
					int i = Integer.valueOf(str.substring(str.indexOf("/pic") + 4));
					imgIps = new BufferedInputStream (new FileInputStream(files[i]));
					System.out.println(files[i].getName());
					System.out.println(files[i].canWrite());
					System.out.println(imgIps);
					
				} catch (FileNotFoundException e) {
					CloseUtil.closeAll(imgIps);	
				}
				int len = 0;
				byte[] buffer = new byte[1024*100];
				try {
					while((len = imgIps.read(buffer)) != -1) {
						dos.write(buffer, 0, len);
						System.out.println(len);
					}	
					dos.writeBytes("\r\n");
					dos.flush();
				} catch (IOException e) {
					CloseUtil.closeAll(imgIps);
				}
			}
		}
	}

}
