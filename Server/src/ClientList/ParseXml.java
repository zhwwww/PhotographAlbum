package ClientList;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ParseXml {

	private static List<Client> clientList = null ;
	
	private static void parse(){
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parse = null;
		try {
			parse = factory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e1) {
			e1.printStackTrace();
		}
		
		ClientHandler ph = new ClientHandler();
		
		try {
			parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("ClientList/clients.xml")
									, ph);
		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}
		
		clientList = ph.getClients();
	}
	
	public static List<Client> update(){
		parse();
		return clientList;
	}
	
	
}