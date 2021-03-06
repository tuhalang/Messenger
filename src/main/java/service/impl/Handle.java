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

import application.HomeController;
import login.LogInController;
import model.Client;
import model.Message;
import model.User;

public class Handle {
	private Client client = null;

	public Handle(Client client) {
		super();
		this.client = client;
	}

	// only use to receive message
	public static void receive(Client client, HomeController controller) {
		Runnable run = new Runnable() {

			@Override
			public void run() {
				Socket socket = client.getSocket();
				System.out.println("Đang lắng nghe tin nhắn");
				while (socket.isConnected()) {

					try {
						InputStreamReader isr = new InputStreamReader(socket.getInputStream());
						BufferedReader br = new BufferedReader(isr);
						String message = br.readLine();
						if (message != null && message.startsWith("3")) {
							// TODO display message
							System.out.println("message to you: " + message);
							ObjectMapper mapper = new ObjectMapper();
							controller.addM(mapper.readValue(message.substring(1), Message.class));
						}else if (message != null && message.startsWith("4")) {
							ObjectMapper mapper = new ObjectMapper();
							List<User> users = mapper.readValue(message.substring(1), new TypeReference<List<User>>() {
							});
							client.setSearchUser(users);
							controller.changeFriend(users);
						}
						if (message != null && !message.equals(""))
							System.out.println(message);
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}

				}
				System.out.println("socket is closed");
			}

		};
		Thread thread = new Thread(run);
		thread.start();
	}

	public static void receivePretreatment(Client client) {
		String response = null;
		Socket socket = client.getSocket();
		while (socket.isConnected()) {
			System.out.println("#");
			InputStreamReader isr;
			try {
				isr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				response = br.readLine();
				System.out.println(response);
				if (response != null) {
					if (response.substring(0, 1).equals("1")) {
						// TODO decode message
						ObjectMapper mapper = new ObjectMapper();
						User user = mapper.readValue(response.substring(1), User.class);
						client.setValid(true);
						client.setUser(user);
					} else {
						// TODO decode error
					}
					if (response.substring(0, 1).equals("2")) {
						// TODO decode message
						ObjectMapper mapper = new ObjectMapper();
						User user = mapper.readValue(response.substring(1), User.class);
						client.setValidRegister(true);		
						client.setUser(user);
					} else {
						// TODO decode error
					}
//					if (response != null && response.startsWith("4")) {
//						ObjectMapper mapper = new ObjectMapper();
//						List<User> users = mapper.readValue(response.substring(1), new TypeReference<List<User>>() {
//						});
//						if (users!=null) for (User u:users) System.out.println(u); else System.out.println("No");
//						client.setSearchUser(users);
//					}
					if (response != null && response.startsWith("5")) {
						// TODO decode message
						ObjectMapper mapper = new ObjectMapper();
						List<User> allUser = mapper.readValue(response.substring(1),
								new TypeReference<List<User>>() {
								});
						for (User u : allUser)
							System.out.println(mapper.writeValueAsString(u));
						client.setAllUser(allUser);
					} else {
						// TODO decode error
					}
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * CODE RESPONSE 0: error 1: login 2: register 3: send successful 4: search 5:
	 * findAll
	 */
	public static void send(Client client, String code, String command) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getSocket().getOutputStream()));
			bw.write(code + command);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
