package service.impl;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.User;
import service.ConnectToServer;
import service.Execute;

public class ExeLogin implements Execute {

	@Override
	public void exe(ConnectToServer connect, String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			User u=mapper.readValue(message, User.class);
			connect.setLoginUser(u);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}