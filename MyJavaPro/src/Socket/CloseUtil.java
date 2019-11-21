package Socket;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil {
	public static void closeAll(Closeable... io) {
		for(Closeable temp:io) {
			if(temp!=null) {
				try {
					temp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
//	public static void closeObj(Object...objects) {
//		for(Object temp:objects) {
//			if(temp!=null) {
//				temp = null;
//			}
//		}
//	}
	
}
