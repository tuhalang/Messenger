package service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import login.client;
import model.Message;
import model.User;

public class ConnectToServer implements Command{
	private client c=new client();
	private User loginUser=null;
	private User registerUser;
	private User search=null;
	private List<User> findUser=null;
	public static Queue<Message> listMess=new LinkedList<Message>();
	public ConnectToServer() {
		super();
	}
	public ConnectToServer(client c) {
		super();
		this.c = c;
		this.c.setConnectSever(this);
	}
	
	public client getC() {
		return c;
	}

	public void setC(client c) {
		this.c = c;
	}
	

	public User getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	public User isRegisterUser() {
		return registerUser;
	}
	public void setRegisterUser(User registerUser) {
		this.registerUser = registerUser;
	}
	public User getSearch() {
		return search;
	}
	public void setSearch(User search) {
		this.search = search;
	}
	public List<User> getFindUser() {
		return findUser;
	}
	public void setFindUser(List<User> findUser) {
		this.findUser = findUser;
	}
	@Override
	public User login(User user) {
		return new User(2, "user3","123");
	}

	@Override
	public User register(User user) {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public User searchByName(String name) {
		// TODO Auto-generated method stub
		return new User(0, "tvd", "123");
	}

	@Override
	public void sendMessage(Message message) {
		ObjectMapper mapper=new ObjectMapper();
		try {
			c.sendMess(mapper.writeValueAsString(message));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		ArrayList<User> listU=new ArrayList<User>();
		listU.add(new User(0, "tvd", "123"));
		listU.add(new User(1, "qwer", "123"));
		listU.add(new User(2, "user3","123"));
		return listU;
	}
}
