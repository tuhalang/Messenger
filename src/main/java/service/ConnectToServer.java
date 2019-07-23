package service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.HomeController;
import javafx.application.Platform;
import model.Client;
import model.Key;
import model.Message;
import model.User;

public class ConnectToServer {

	private Client client = null;
	ObjectMapper mapper = new ObjectMapper();

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<User> findAllUser() {
		client.send("5", "");
		client.receivePretreatment();
		if (client.getAllUser() != null)
			return client.getAllUser();
		else
			return null;
	}

	public void searchByName(String name) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Key key = new Key();
			key.setKey(name);
			client.send("4", mapper.writeValueAsString(key));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(Message m) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			client.send("3", mapper.writeValueAsString(m));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
