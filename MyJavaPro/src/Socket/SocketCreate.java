package Socket;

public class SocketCreate {
	
	public static SocketConn createSocket() {
		SocketConn sc = null;
		sc = new SocketConn(Constant.Constant.serverIp,Constant.Constant.serverPort);
		sc.createSocket();
		if(sc.getSocketClient()==null) {
			sc = null;
		}
		
		return sc;
		
	} 
	
}
