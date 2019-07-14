package service;

import java.util.List;

import model.Message;
import model.User;

public interface Command {
	User login(User user);
	boolean register(User user);
	User searchByName(String name);
	void sendMessage(Message message);
	List<User> findAllUser();
}
