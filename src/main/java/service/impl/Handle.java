package service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Client;
import model.User;

public class Handle {

	public static void receive(Client client) {

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
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				response = br.readLine();
				System.out.println(response);
				if (response != null) {
					if (!response.substring(0, 1).equals("0")) {
						//TODO decode message 
						ObjectMapper mapper = new ObjectMapper();
						User user = mapper.readValue(response.substring(1), User.class);
						client.setValid(true);
						client.setUser(user);
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
