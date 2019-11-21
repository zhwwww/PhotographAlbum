package Socket;

import java.io.IOException;
import java.net.InetAddress;

public class PingIp {

	public static boolean ping() {
		boolean flag = false;
		InetAddress address = null;
		try {
			address = InetAddress.getByName(Constant.Constant.serverIp);
			flag = address.isReachable(3000);
		} catch (IOException e) {
		}
		return flag;
	}
	
}
