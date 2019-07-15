package service;

import java.util.ArrayList;
import java.util.List;

import login.client;
import model.Message;
import model.User;

public class ConnectToServer implements Command{
	private client c=new client();
	
	public ConnectToServer() {
		super();
	}
	public ConnectToServer(client c) {
		super();
		this.c = c;
	}
	
	public client getC() {
		return c;
	}

	public void setC(client c) {
		this.c = c;
	}

	@Override
	public User login(User user) {
		return new User(0, "tvd", "123");
	}

	@Override
	public boolean register(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User searchByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		ArrayList<User> listU=new ArrayList<User>();
		listU.add(new User(0, "tvd", "123"));
		listU.add(new User(1, "qwer", "123"));
		return listU;
	}
}
