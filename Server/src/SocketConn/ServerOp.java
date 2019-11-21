package SocketConn;

import java.io.IOException;

public class ServerOp {

	public static void main(String[] args) {
		try {
			new Server().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
