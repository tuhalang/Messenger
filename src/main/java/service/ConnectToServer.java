package service;

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
		return user;
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
		return null;
	}
}
