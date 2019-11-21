package ClientList;

/**
 * xml解析
 * @author dell
 *
 */
public class Client {
	private String name;
	private String pwd;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Client(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;
	}
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public Client() {
		
	}
	
	
}
