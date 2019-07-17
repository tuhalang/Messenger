package service.impl;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Message;
import service.ConnectToServer;
import service.Execute;

public class ExeMessage implements Execute{
	@Override
	public void exe(ConnectToServer connect, String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Message m=mapper.readValue(message, Message.class);
			ConnectToServer.listMess.add(m);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
