package service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Client;
import model.Message;
import model.User;

public class Handle {

	//only use to receive message
	public static void receive(Client client) {
		Socket socket = client.getSocket();
		while(socket.isConnected()) {
			try {
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String message = "";
				if(message != null && message.startsWith("3")) {
					//TODO display message
				}
			} catch (IOException e) {
				
			}
			
		}
	}
	
	/**
	 * CODE RESPONSE
	 * 0: error
	 * 1: login
	 * 2: register
	 * 3: send successful
	 * 4: search
	 * 5: findAll
	 * */
	public static void send(Client client, String code, String command) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getSocket().getOutputStream()));
			bw.write(code + command);
			bw.newLine();
			bw.flush();
			String response = null;
			Socket socket = client.getSocket();
			while (socket.isConnected()) {
				System.out.println("#");
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				response = br.readLine();
				System.out.println(response);
				if (response != null) {
					if (response.substring(0, 1).equals("1")) {
						//TODO decode message 
						ObjectMapper mapper = new ObjectMapper();
						User user = mapper.readValue(response.substring(1), User.class);
						client.setValid(true);
						client.setUser(user);
					} else {
						// TODO decode error
					}
					if (response.substring(0, 1).equals("2")) {
						//TODO decode message 
						ObjectMapper mapper = new ObjectMapper();
						User user = mapper.readValue(response.substring(1), User.class);
						client.setValidRegister(true);
						client.setUser(user);
					} else {
						// TODO decode error
					}
					if (response.substring(0,1).equals("3")) {
//						ObjectMapper mapper = new ObjectMapper();
						//client.connect.receiveMessage(mapper.readValue(response.substring(1), Message.class));
						//do nothing
						
					}
					if (response.substring(0,1).equals("4")) {
						ObjectMapper mapper = new ObjectMapper();
						List<User> user = mapper.readValue(response.substring(1),new TypeReference<List<User>>(){});
						client.setSearchUser(user);
					}
					if (response.substring(0, 1).equals("5")) {
						//TODO decode message 
						ObjectMapper mapper = new ObjectMapper();
						List<User> allUser = mapper.readValue(response.substring(1), new TypeReference<List<User>>(){});
						for (User u: allUser) System.out.println(mapper.writeValueAsString(u));
						client.setAllUser(allUser);
					} else {
						// TODO decode error
					}
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
