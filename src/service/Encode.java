package service;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import model.Message;
import model.User;

public class Encode {
	
	private String LOGIN_CODE;
	private String REGISTER_CODE;
	private String MESSAGE_CODE;
	private String SEARCH_CODE;
	
	public Encode() {
		FileReader reader = null;
		Properties p = null;
		try {
			reader = new FileReader("config/application.properties");
			p = new Properties();  
		    p.load(reader);  
		    LOGIN_CODE = p.getProperty("LOGIN_CODE");
		    REGISTER_CODE = p.getProperty("REGISTER_CODE");
		    MESSAGE_CODE = p.getProperty("MESSAGE_CODE");
		    SEARCH_CODE = p.getProperty("SEARCH_CODE");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String encodeLogin(User user) {
		return LOGIN_CODE + user.toString();
	}
	
	public String encodeRegister(User user) {
		return REGISTER_CODE + user.toString();
	}
	
	public String encodeMessage(Message message) {
		return MESSAGE_CODE + message.toString();
	}
	
	public String encodeSearch(String key) {
		return SEARCH_CODE + "{\"key\":\""+key+"\"}";
	}

}
