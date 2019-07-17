package service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.Client;
import model.User;

public class Handle {
	
	public static void receive(Client client) {
		
	}
	
	public static void send(Client client, String code, String command) {
//		Runnable run = new Runnable() {
//			
//			@Override
//			public void run() {
				try {
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getSocket().getOutputStream()));
					bw.write(code+command);
					bw.newLine();
					bw.flush();
					String response = "";
					while(response.equals("")) {
						InputStreamReader isr = new InputStreamReader(client.getSocket().getInputStream());
						BufferedReader br = new BufferedReader(isr);
						response = br.readLine();
						System.out.println(response);
					}
					if(response.substring(0,1).equals("1")) {
						ObjectMapper mapper = new ObjectMapper();
						User user = mapper.readValue(response.substring(1), User.class);
						client.setValid(true);
						client.setUser(user);
					}else {
						//TODO login false
						
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
//				
//				
//			}
//		};
//		
//		Thread thread = new Thread(run);
//		thread.start();
	}
}
