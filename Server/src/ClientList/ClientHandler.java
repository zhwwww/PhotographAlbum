package ClientList;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ClientHandler extends DefaultHandler{

	private Client client;
	private List<Client> clients;
	private String tag;
	
	public List<Client> getClients() {
		return clients;
	}

	
	@Override
	public void startDocument() throws SAXException {
			clients = new ArrayList<Client>();
	}

	@Override
	public void endDocument() throws SAXException {

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName!=null) {
			tag = qName;
		}
		if(tag!=null && tag.equals("client")) {
			client = new Client();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("client")) {
			this.clients.add(client);	
		}
		tag = null;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException{
		String str = new String(ch,start,length);
		if(tag!=null && tag.equals("name")) {
			client.setName(str);
		}else if(tag!=null && tag.equals("pwd")) {
			client.setPwd(str);
		}	
	
	}
	
}
