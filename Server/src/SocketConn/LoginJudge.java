package SocketConn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import ClientList.Client;
import ClientList.ParseXml;

public class LoginJudge {

	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private List<Client> clientList = null;
	
	public LoginJudge(Socket socket) {
		try {
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			CloseUtil.closeAll(dis,dos);
		}
		clientList = ParseXml.update();
	}
	
	public String judge() {
		String str = null;
		try {
			 str = dis.readUTF();   // login/name?pwd
		} catch (IOException e) {
			CloseUtil.closeAll(dis);
		}
		if(str.contains("login/")) {
			String name = str.substring(str.indexOf("/")+1,str.indexOf("?"));
			for(Client l : clientList ) {
				if(l.getName().equals(name)) {
					String pwd = str.substring(str.indexOf("?")+1);
					if(l.getPwd().equals(pwd)) {
						if(Server.findClients(name)) {
							try {
								dos.writeUTF("login/already");
								dos.flush();
							} catch (IOException e) {
								CloseUtil.closeAll(dos);
							}finally {
								CloseUtil.closeAll(dos,dis);
								dis = null;
								dos = null;
							}
							return null;
						}else {
							try {
								dos.writeUTF("login/ok");
								dos.flush();
							} catch (IOException e) {
								CloseUtil.closeAll(dos);
							}finally {
								dis = null;
								dos = null;
							}
							return name;
						}
					}
				}
			}
		}
		try {
			dos.writeUTF("login/error");
			dos.flush();
		}catch (IOException e) {
			CloseUtil.closeAll(dos);
		}finally{
			CloseUtil.closeAll(dos,dis);
			dis = null;
			dos = null;
		}
		return null;	
	}
}
	
